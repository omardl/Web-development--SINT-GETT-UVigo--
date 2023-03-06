package p2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DataModel {

	 //Devuelve un arraylist con los degrees
	 public static ArrayList <String> getC2Degrees() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
	 	
	 	ArrayList<String> listado_degrees = new ArrayList<String>();
     
		 
    	    	//Obtiene el nombre de los degrees de los documentos validos y los agrega a un arraylist, que devuelve la funcion como resultado
		for (Entry<String, Document> entry : EAML_parser.map_degrees_docus.entrySet()) {
			
			listado_degrees.add(entry.getKey());
		
		}
	
		 
        	//Ordena el listado de carreras por longitud del nombre (de menor a mayor) o, alfabeticamente, si la longitud es la misma
        	Collections.sort(listado_degrees, new Comparator<String>() {
 
			@Override
			public int compare(String d1, String d2) {

				if (d1.length() > d2.length()) {
					
					return 1;
					
				} else {
					
					if (d1.length() < d2.length()) {
						
						return -1;
						
					} else {
						
						return  d1.compareToIgnoreCase(d2);	
						
					}	
				}
			}
        	});
		 
		return listado_degrees;
	}
	
	
	//Devuelve un arraylist con los alumnos de una carrera 
	public static ArrayList <Student> getC2Students (String degree) throws XPathExpressionException {
		
		XPathFactory xpf = XPathFactory.newInstance();
		XPath xp = xpf.newXPath();
		
		ArrayList <Student> alumnos = new ArrayList<Student>();
		
		//Se obtiene el documento de ese degree y un nodelist de los alumnos
	  	Document doc = EAML_parser.map_degrees_docus.get(degree);
	    
	    	NodeList nodelist_alumnos = (NodeList)xp.evaluate("/Degree/Course/Subject/Student", doc, XPathConstants.NODESET);
	    
	    	for (int i = 0; i < nodelist_alumnos.getLength(); i++) {
	    	
	    		Student nuevo_alumno = new Student();
	    	
	    		Element alumno = (Element) nodelist_alumnos.item(i);
	    	
	    		boolean sigue = false;
	    	
            		//Se comprueba si ya se ha encontrado a este alumno cursando otra asignatura 
	    		for (int a = 0; a < alumnos.size(); a++) {
	    		
	    			if (alumnos.get(a).getNombre().equals(alumno.getElementsByTagName("Name").item(0).getTextContent())) {
	    			
	    				sigue = true;					
	    			}
	    		}
			
	    		//Si el alumno aun no esta en la lista, se agrega
	    		if (!sigue) {
	    	
	    			nuevo_alumno.setNombre(alumno.getElementsByTagName("Name").item(0).getTextContent());
		    	
	    			//Su ID puede ser DNI o Resident
		    		if (alumno.getElementsByTagName("Dni").item(0) == null) {
		    		
		    			nuevo_alumno.setID(alumno.getElementsByTagName("Resident").item(0).getTextContent());
		    		
		    		} else {
		    		
		    			nuevo_alumno.setID(alumno.getElementsByTagName("Dni").item(0).getTextContent());
		    		
		    		}
		    		
		    		//Se busca entre los nodos hijo del alumno un campo de texto que no sea nulo (su direccion)
		    		for (int t = 0; t < alumno.getChildNodes().getLength(); t++) {
		    		
		    			if ((alumno.getChildNodes().item(t).getNodeType() == Node.TEXT_NODE) && !alumno.getChildNodes().item(t).getTextContent().trim().equals("")) {	    
		    			
		    				nuevo_alumno.setDireccion(alumno.getChildNodes().item(t).getTextContent().trim());
		    
		    			}
		    		}
		        	alumnos.add(nuevo_alumno);	 
	    		} 	
	   	 }
	   	Collections.sort(alumnos);
		return alumnos;
	}
	
	
	//Se buscan las asignaturas de una carrera que cursa un alumno
	public static ArrayList <Subject> getC2Subjects (String degree, String student) throws XPathExpressionException {
		
		XPathFactory xpf = XPathFactory.newInstance();
		XPath xp = xpf.newXPath();
		
		//Se obtiene el nodo de ese alumno, del documento que corresponde a la carrera
	        Document doc = EAML_parser.map_degrees_docus.get(degree);
	        
	        NodeList nodelist_asignaturas;
	        
	        if (Character.isDigit(student.charAt(0))) {
	        
	            nodelist_asignaturas = (NodeList)xp.evaluate("/Degree/Course/Subject/Student[Dni='" + student + "']", doc, XPathConstants.NODESET);
	        
	        } else {
	        
	            nodelist_asignaturas = (NodeList)xp.evaluate("/Degree/Course/Subject/Student[Resident='" + student + "']", doc, XPathConstants.NODESET);
	        
	        }
	    
	        ArrayList <Subject> asignaturas = new ArrayList<Subject>();
	    
	   	for (int i = 0; i < nodelist_asignaturas.getLength(); i++) {
	    	
	    		Subject nueva_asignatura = new Subject();
	
	    		//Se obtiene el nodo padre (Asignaturas) de ese alumno. Se obtiene despues el nombre y el ID. La nota se obtiene dentro del nodo del alumno.
	    		Element asignatura = (Element) nodelist_asignaturas.item(i).getParentNode();
	    	
	    		nueva_asignatura.setNombre(asignatura.getElementsByTagName("Name").item(0).getTextContent());
	    	
	    		nueva_asignatura.setID(asignatura.getAttribute("idSub").toString());
	    	
	    		Element alumno = (Element) nodelist_asignaturas.item(i);
	    	
	    		nueva_asignatura.setNota(alumno.getElementsByTagName("Grade").item(0).getTextContent());
	    	
	        	asignaturas.add(nueva_asignatura);
	    
	    	}
		
	   	Collections.sort(asignaturas);
	    
		return asignaturas;
	}
	
	
	//Clase Subject
	public static class Subject implements Comparable<Subject> {
		
		String nombre;
		String ID;
		String nota;		

		public void setNombre(String nombre) {
			
			this.nombre = nombre;
			
		}
		
		public void setID(String id) {
		
			this.ID = id;
			
		}
		
		public void setNota(String nota) {
			
			this.nota = nota;
			
		}
		
		public String getNombre() {
			
			return this.nombre;
			
		}
		
		public String getID() {
			
			return this.ID;
			
		}
		
		public String getNota() {
			
			return this.nota;
			
		}

		@Override
		public int compareTo(Subject asig1) {
			
			//Se ordenan de menor a mayor nota 
			//Si la nota es la misma, se ordenan alfabeticamente por Nombre
			
			if (Float.parseFloat(asig1.getNota()) < Float.parseFloat(nota)) {
			
				return 1;
				
			} else {
				
				if (Float.parseFloat(asig1.getNota()) > Float.parseFloat(nota)) {
					
					return -1;
					
				} else {
					
					return nombre.compareToIgnoreCase(asig1.getNombre());
					
				}				
			}				
		}		
	}
	

	//clase student
	public static class Student implements Comparable<Student> {
		
		String nombre;		
		String ID;		
		String direccion;
		
		public void setNombre (String nombre) {
		
			this.nombre = nombre;
	
		}
		
		public void setID (String id) {
			
			this.ID = id;
			
		}
		
		public void setDireccion (String direccion) {
			
			this.direccion = direccion;
			
		}
		
		public String getNombre () {
			
			return this.nombre;
			
		}
		
		public String getID () {
			
			return this.ID;
			
		}
		
		public String getDireccion () {
			
			return this.direccion;
			
		}

		@Override
		public int compareTo(Student al1) {
			
			//Primero se comprueba si es DNI o Resident (Resident empieza por letra)
			//Se ordenan primero los Resident
			//Dentro de DNI / Resident, se ordenan alfabeticamente por Nombre
			
			if (Character.isDigit(al1.getID().charAt(0))) {
				
				if (Character.isDigit(ID.charAt(0))) {
					
					return nombre.compareToIgnoreCase(al1.getNombre());
					
				} else {
					
					return -1;
					
				}
				
			} else  {
				
				if (Character.isDigit(ID.charAt(0))) {
					
					return 1;
					
				} else {
					
					return nombre.compareToIgnoreCase(al1.getNombre());
					
				}	
			}				
		}			
	}
}
