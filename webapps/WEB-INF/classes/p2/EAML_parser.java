package p2;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.ServletConfig;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class EAML_parser {
	
	//Direccion inicial
	final String DIRECC_CONOCIDA = "http://alberto.gil.webs.uvigo.es/SINT/20-21/";
	final String FICH_CONOCIDO = "teleco.xml";
	
	//Namespaces para el parser
	final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
	
	public static HashMap <String, Document> map_degrees_docus;
	public static HashMap <String, ArrayList<String>> warnings;
	public static HashMap <String, ArrayList<String>> errors;
	public static HashMap <String, ArrayList<String>> fatalerrors;
		
	public EAML_parser(ServletConfig config) throws IOException, XPathExpressionException {
		
    		DocumentBuilder db = null;
    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    		dbf.setValidating(true);
		dbf.setNamespaceAware(true);
		dbf.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
		
		File schema = new File(config.getServletContext().getResource("/p2/eaml.xsd").getPath());
    		dbf.setAttribute(JAXP_SCHEMA_SOURCE, schema);
    	
    		XML_ErrorHandler ErrorHandler = new XML_ErrorHandler();
    	   	
		try {
		
			db = dbf.newDocumentBuilder();
			db.setErrorHandler(ErrorHandler);			
		
		} catch (ParserConfigurationException e) {

			System.out.println("Parser Configuration Exception" + e);
			
		}
		
		map_degrees_docus = new HashMap <String, Document>();
		warnings = new HashMap <String, ArrayList<String>>();
		errors = new HashMap <String, ArrayList<String>>();
		fatalerrors = new HashMap <String, ArrayList<String>>();
		
		XPathFactory xpf = XPathFactory.newInstance();
		XPath xp = xpf.newXPath();
		
		//Listado de documentos que se van obteniendo y que ya han sido parseados
		LinkedList<URL> sin_parsear = new LinkedList<URL> ();		
		LinkedList<URL> parseados = new LinkedList<URL> (); //PARSEADOS => PUEDEN O NO HABER SIDO AGREGADOS A VALIDOS
		
		//Se crea la URL de la direccion inicial
		URL direc_inicial = new URL(DIRECC_CONOCIDA + FICH_CONOCIDO);
		sin_parsear.add(direc_inicial);
		
		URLConnection conexion_docu = null;
		
		while (sin_parsear.isEmpty() == false) {
			
			String degree = null;
			Document parseando_docu = null;
			NodeList otros_urls = null;
			
			try {
				
				//Se intenta parsear capturando los errores que salten
				conexion_docu = sin_parsear.pop().openConnection();
				parseando_docu = db.parse(conexion_docu.getURL().toString());
							
			} catch (SAXParseException saxparseexc) {
				
				//Documento no well formed, se considera fatal error
				ErrorHandler.fatalerror(saxparseexc);
				
			} catch (SAXException saxexc) {
				
				//Ha ocurrido algun error al parsear (no pasa el parser, archivo no encontrado, etc)
				
			}
			
			if (ErrorHandler.getFatalerrors()) {
				
				//Se comprueba si hay fatalerror
				ArrayList<String> mensajes = new ArrayList<String>();
				
				for (int mssg = 0; mssg < ErrorHandler.getMessages().size(); mssg++) {
				
					mensajes.add(ErrorHandler.getMessages().get(mssg));
					
				}
				
				fatalerrors.put(conexion_docu.getURL().toString(), mensajes);
			
			} else {
				
				if (ErrorHandler.getErrors()) {
					
					//Se comprueba si hay error
					ArrayList<String> mensajes = new ArrayList<String>();
		
					for (int mssg = 0; mssg < ErrorHandler.getMessages().size(); mssg++) {

						mensajes.add(ErrorHandler.getMessages().get(mssg));
					
					}
					
					errors.put(conexion_docu.getURL().toString(), mensajes);
							
				} else {
					
					if (ErrorHandler.getWarnings()) {
						
						//Se comprueba si hay warning
						
						ArrayList<String> mensajes = new ArrayList<String>();
						
						for (int mssg = 0; mssg < ErrorHandler.getMessages().size(); mssg++) {
					
							mensajes.add(ErrorHandler.getMessages().get(mssg));
						
						}
						
						warnings.put(conexion_docu.getURL().toString(), mensajes);
										
					} else {
						
						//Si no se ha capturado ningun error, se agrega al hashmap carrera-documento
						//Se comprueba si en el nodo EAML hay otros documentos
						
						otros_urls = (NodeList)xp.evaluate("/Degree/Course/Subject/Student/EAML", parseando_docu, XPathConstants.NODESET);
						
						degree = ((Node)xp.evaluate("/Degree/Name", parseando_docu, XPathConstants.NODE)).getTextContent();
		
						map_degrees_docus.put(degree, parseando_docu);
					
						if (otros_urls != null) {
							
							for (int i = 0; i < otros_urls.getLength(); i++) {
								
								/*Si hay otros documentos:
								 * se comprueba si son URL relativos o absolutos
								 * se comprueba si ese documento ya ha sido parseado o está pendiente de parsear
								 */
								
								
								if (otros_urls.item(i).toString() != null) {
								
									String eaml_nuevo = (String)xp.evaluate("text()", otros_urls.item(i), XPathConstants.STRING); 
									
									URL url_nuevo;
									
									//URL absoluto
									if (eaml_nuevo.startsWith("http://")) {
										
										url_nuevo = new URL(eaml_nuevo);
										
									//URL relativo
									} else {
										
										url_nuevo = new URL(conexion_docu.getURL(), eaml_nuevo);
										
									}
									
									if ((parseados.contains(url_nuevo) == false) && (sin_parsear.contains(url_nuevo) == false)) {
										
										sin_parsear.add(url_nuevo);
										
									}								
								}											
							}						
						}							
					}	
				}
			}
			
			//Reseteamos gestor de errores y agregamos el docu a parseados		
			ErrorHandler.resetErrorHandler();			
			parseados.add(conexion_docu.getURL());
	
		}    			
	}
}
