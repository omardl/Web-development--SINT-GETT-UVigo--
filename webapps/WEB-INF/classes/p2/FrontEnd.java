package p2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathExpressionException;


public class FrontEnd {
	
        //RESPUESTAS EN MODO BROWSER
	public static void sin_passwd (HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		res.setContentType("text/html");	
		res.setCharacterEncoding("UTF-8");
		
		PrintWriter out = res.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title> Sint81P2 </title>");
		out.println("<h1>- Servicio de expedientes academicos -</h1>");
		out.println("</head>");
		out.println("<body>");
		out.println("<p> Error: No psswd </p>");
		out.println("</body>");
		out.println("</html>");
		
	}
	
	
	public static void passwd_erronea (HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		res.setContentType("text/html");
		res.setCharacterEncoding("UTF-8");
		
		PrintWriter out = res.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title> Sint81P2 </title>");
		out.println("<h1>- Servicio de expedientes academicos -</h1>");
		out.println("</head>");
		out.println("<body>");
		out.println("<p> Error: Bad password </p>");
		out.println("</body>");
		out.println("</html>");
		
		
	}
	
	
	public static void falta_param (HttpServletRequest req, HttpServletResponse res, String param) throws IOException {
	
	        res.setContentType("text/html");
		res.setCharacterEncoding("UTF-8");
		
		PrintWriter out = res.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title> Sint81P2 </title>");
		out.println("<h1>- Servicio de expedientes academicos -</h1>");
		out.println("</head>");
		out.println("<body>");
		out.println("<p>No param:" + param + "</p>");
		out.println("</body>");
		out.println("</html>");
	
	}
	
	
	public static void doGetpphase01 (HttpServletRequest req, HttpServletResponse res) throws IOException {
			
		res.setContentType("text/html");
		res.setCharacterEncoding("UTF-8");	
		PrintWriter out = res.getWriter();
			
		out.println("<html>");
		out.println("<head>");
		out.println("<title> Sint81P2 </title>");
		out.println("<h1>- Servicio de expedientes academicos -</h1>");
		out.println("</head>");		
		out.println("<body>");
		out.println("<h2> Bienvenido a este Servicio </h2>");
		out.println("<p><a href='?p=" + URLEncoder.encode(req.getParameter("p"), "UTF-8") + "&pphase=02'> - Ver los ficheros erroneos </a></p>");
		out.println("<h3> Selecciona una consulta: </h3>");
		out.println("<p> <a href='?p=" + URLEncoder.encode(req.getParameter("p"), "UTF-8") + "&pphase=21'> -- Consulta 2: notas de un alumno en una titulacion </a> </p>");
		out.println("</body>");
		out.println("</html>");
			
	}
		
		
	public static void doGetpphase02 (HttpServletRequest req, HttpServletResponse res) throws IOException {
			
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html");	
		PrintWriter out = res.getWriter();
			
		out.println("<html>");
		out.println("<head>");
		out.println("<title> Sint81P2 </title>");
		out.println("<h1>- Servicio de expedientes academicos -</h1>");
		out.println("</head>");
			
		out.println("<body>");
		
		ArrayList <String> fich_warn = new ArrayList<>(EAML_parser.warnings.keySet());
		Collections.sort(fich_warn);
			
		out.println("<h3> Ficheros con warnings: " + fich_warn.size() + "</h3>");
			
		for (int i = 0; i < fich_warn.size(); i++) {
				
			out.println("<h4> -" + fich_warn.get(i) + "</h4>");
				
			ArrayList <String> messg_warn = EAML_parser.warnings.get(fich_warn.get(i));
				
			for (int j = 0; j < messg_warn.size(); j++) {
					
				out.println("<p>  ---" + messg_warn.get(j) + "</p>");
					
			}	
		}
				
		ArrayList <String> fich_errores = new ArrayList<>(EAML_parser.errors.keySet());
		Collections.sort(fich_errores);
			
		out.println("<h3> Ficheros con errores: " + fich_errores.size() + "</h3>");
			
		for (int i = 0; i < fich_errores.size(); i++) {
				
			out.println("<h4> -" + fich_errores.get(i) + "</h4>");
				
			ArrayList <String> messg_errores = EAML_parser.errors.get(fich_errores.get(i));
				
			for (int j = 0; j < messg_errores.size(); j++) {
					
				out.println("<p> ---" + messg_errores.get(j) + "</p>");
					
			}
				
		}
			
		ArrayList <String> fich_fatalerrs = new ArrayList<>(EAML_parser.fatalerrors.keySet());
		Collections.sort(fich_fatalerrs);
			
		out.println("<h3> Ficheros con errores fatales: " + fich_fatalerrs.size() + "</h3>");
		
		for (int i = 0; i < fich_fatalerrs.size(); i++) {
				
			out.println("<h4> -" + fich_fatalerrs.get(i) + "</h4>");
				
			ArrayList <String> messg_fatalerrs = EAML_parser.fatalerrors.get(fich_fatalerrs.get(i));
				
			for (int j = 0; j < messg_fatalerrs.size(); j++) {
					
				out.println("<p> ---" + messg_fatalerrs.get(j) + "</p>");
					
			}		
		}
		
		out.println("<form method = 'GET'>");
		out.println("<input type='hidden' name='p' value='" + req.getParameter("p") + "'>");
		out.println("<input type='hidden' name='pphase' value='01'>");
		out.println("<input type='submit' value='Atras'>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
			
	}
		
	
	public static void doGetpphase21 (HttpServletRequest req, HttpServletResponse res, ArrayList<String> grados) throws Exception {
				
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html");	
		PrintWriter out = res.getWriter();
			
		out.println("<html>");
		out.println("<head>");
		out.println("<title> Sint81P2 </title>");
		out.println("<h1>- Servicio de expedientes academicos -</h1>");
		out.println("</head>");
			
		out.println("<body>");
		out.println("<h1> Consulta 2: Fase 1 </h1>");
		out.println("<h2> Selecciona una titulacion: </h2>");
		    		
		if (grados.isEmpty() == false) {
					
			for (int i = 0; i < grados.size(); i++) {
					
				out.println("<p> <a href='?p=" + URLEncoder.encode(req.getParameter("p"), "UTF-8") + "&pphase=22&pdegree=" + URLEncoder.encode(grados.get(i), "UTF-8") + "'> " + grados.get(i) +  " </a> </p>");
					
			}	
		}
			
		out.println("<br> <br>");
		out.println("<form method='GET'>");
		out.println("<input type='hidden' name='p' value='"+ req.getParameter("p") +"'>");
		out.println("<input type='hidden' name='pphase' value='01'>");
		out.println("<input type='submit' value='Inicio'>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
			
	}
	
	
	public static void doGetpphase22 (HttpServletRequest req, HttpServletResponse res, ArrayList<DataModel.Student> alumnos) throws IOException, XPathExpressionException {
			
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html");	
		PrintWriter out = res.getWriter();
			
		out.println("<html>");
		out.println("<head>");
		out.println("<title> Sint81P2 </title>");
		out.println("<h1>- Servicio de expedientes academicos -</h1>");
		out.println("</head>");
			
		out.println("<body>");
		out.println("<h2> Consulta 2: Fase 2 (Titulacion = " + req.getParameter("pdegree") + ") </h2>"); 
			
		if (alumnos.isEmpty() == false) {
				
			for (int i = 0; i < alumnos.size(); i++) {
				
				out.println("<p> <a href='?p=" + URLEncoder.encode(req.getParameter("p"), "UTF-8") + "&pphase=23&pdegree=" + URLEncoder.encode(req.getParameter("pdegree"), "UTF-8") + "&pstudent=" + URLEncoder.encode(alumnos.get(i).getID(), "UTF-8") + "'> Nombre = '" + alumnos.get(i).getNombre() + "' </a>");
				out.println(" --- ID = '" + alumnos.get(i).getID() + "' --- Direccion = '" + alumnos.get(i).getDireccion() + "' </p>");
				
			}	
		}		
		
		out.println("<form method='GET'>");
		out.println("<input type='hidden' name='p' value='"+ req.getParameter("p") + "'>");
		out.println("<input type='hidden' name='pphase'>");
		out.println("<input type='submit' value='Atras'  onclick=\"form.pphase.value='21'\">");
		out.println("<br> <br>");
		out.println("<input type='submit' value='Inicio' onclick=\"form.pphase.value='01'\">");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
			
	}
		
	
	public static void doGetpphase23 (HttpServletRequest req, HttpServletResponse res, ArrayList<DataModel.Subject> asignaturas) throws IOException, XPathExpressionException {
			
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html");	
		PrintWriter out = res.getWriter();
			
		out.println("<html>");
		out.println("<head>");
		out.println("<title> Sint81P2 </title>");
		out.println("<h1>- Servicio de expedientes academicos -</h1>");
		out.println("</head>");
			
		out.println("<body>");
		out.println("<h1> Consulta 2: Fase 3 </h1>");
		out.println("<h2> Este es el resultado: (Titulacion = " + req.getParameter("pdegree") + ", Alumno = " + req.getParameter("pstudent") + " )</h2>");
			
		if (asignaturas.isEmpty() == false) {
				
			for (int i = 0; i < asignaturas.size(); i++) {
				
				out.println("<p> Nombre = '" + asignaturas.get(i).getNombre() + "' --- ID Asignatura = '" + asignaturas.get(i).getID() + "' --- Nota = '" + asignaturas.get(i).getNota() + "'</p>");
					
			}		
		}	
			
		out.println("<br>");
		out.println("<form method='GET'>");
		out.println("<input type='hidden' name='p' value='"+ req.getParameter("p") + "'>");
		out.println("<input type='hidden' name='pphase' value='22'>");
		out.println("<input type='hidden' name='pdegree' value='" + req.getParameter("pdegree") + "'>");
		out.println("<input type='submit' value='Atras'>");
		out.println("</form>");
		out.println("<form method='GET'>");
		out.println("<input type='hidden' name='p' value='"+ req.getParameter("p") + "'>");
		out.println("<input type='hidden' name='pphase' value='01'>");
		out.println("<input type='submit' value='Inicio'>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
			
	}
		
		
	//RESPUESTAS EN MODO AUTO
	public static void sin_passwd_auto (HttpServletRequest req, HttpServletResponse res) throws IOException {
			
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/xml");
			
		PrintWriter out = res.getWriter();
			
		out.println("<?xml version='1.0' encoding='utf-8'?>");
		out.println("<wrongRequest>no passwd</wrongRequest>");
			
	}
	
	
	public static void passwd_erronea_auto (HttpServletRequest req, HttpServletResponse res) throws IOException {
			
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/xml");
			
		PrintWriter out = res.getWriter();
			
		out.println("<?xml version='1.0' encoding='utf-8'?>");
		out.println("<wrongRequest>bad passwd</wrongRequest>");
			
	}
	
	
	public static void falta_param_auto (HttpServletRequest req, HttpServletResponse res, String param) throws IOException {
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/xml");
			
		PrintWriter out = res.getWriter();
			
		out.println("<?xml version='1.0' encoding='utf-8'?>");
		out.println("<wrongRequest>no param:" + param + "</wrongRequest>");
			
	}
	
	
	public static void doGetpphase01_auto (HttpServletRequest req, HttpServletResponse res) throws IOException {
			
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/xml");	
		PrintWriter out = res.getWriter();
			
		out.println("<?xml version='1.0' encoding='utf-8'?>");
		out.println("<service>");
		out.println("<status>OK</status>");
		out.println("</service>");
			
	}
	
	
	public static void doGetpphase02_auto (HttpServletRequest req, HttpServletResponse res) throws IOException {
			
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/xml");
		PrintWriter out = res.getWriter();
			
		out.println("<?xml version='1.0' encoding='utf-8'?>");
		out.println("<wrongDocs>");
			
		ArrayList <String> fich_warn = new ArrayList<>(EAML_parser.warnings.keySet());
		Collections.sort(fich_warn);
		
		out.println("<warnings>");
			
		for (int i = 0; i < fich_warn.size(); i++) {
				
			out.println("<warning>");
				
			out.println("<file> " + fich_warn.get(i) + " </file>");
				
			ArrayList <String> messg_warn = EAML_parser.warnings.get(fich_warn.get(i));
				
			out.println("<cause> ");
				
			for (int j = 0; j < messg_warn.size(); j++) {
					
				out.println(messg_warn.get(j));
					
			}
				
			out.println(" </cause>");
				
			out.println("</warning>");
				
		}
			
		out.println("</warnings>");
			
		ArrayList <String> fich_errores = new ArrayList<>(EAML_parser.errors.keySet());
		Collections.sort(fich_errores);
			
		out.println("<errors>");
					
		for (int i = 0; i < fich_errores.size(); i++) {
				
			out.println("<error>");
				
			out.println("<file> " + fich_errores.get(i) + " </file>");
				
			ArrayList <String> messg_errores = EAML_parser.errors.get(fich_errores.get(i));
				
			out.println("<cause> ");
				
			for (int j = 0; j < messg_errores.size(); j++) {
					
				out.println(messg_errores.get(j));
					
			}
				
			out.println(" </cause>");
			
			out.println("</error>");
				
		}
			
	        out.println("</errors>");
		    
		ArrayList <String> fich_fatalerrs = new ArrayList<>(EAML_parser.fatalerrors.keySet());
		Collections.sort(fich_fatalerrs);
			
		out.println("<fatalerrors>");
			
		for (int i = 0; i < fich_fatalerrs.size(); i++) {
				
			out.println("<fatalerror>");
			    
		        out.println("<file> " + fich_fatalerrs.get(i) + " </file>");
				
			ArrayList <String> messg_fatalerrs = EAML_parser.fatalerrors.get(fich_fatalerrs.get(i));
				
			out.println("<cause> ");
				
			for (int j = 0; j < messg_fatalerrs.size(); j++) {
					
				out.println(messg_fatalerrs.get(j).replace('<', ' ').replace('>', ' '));
					
			}
				
			out.println(" </cause>");
			     
			out.println("</fatalerror>");
				
		}
		    
		out.println("</fatalerrors>");
		out.println("</wrongDocs>");
			
	}
		
	public static void doGetpphase21_auto (HttpServletRequest req, HttpServletResponse res, ArrayList<String> grados) throws Exception {
			
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/xml");
		PrintWriter out = res.getWriter();
			
		out.println("<?xml version='1.0' encoding='utf-8'?>");
		out.println("<degrees>"); 
			
		if (grados.isEmpty() == false) {
					
			for (int i = 0; i < grados.size(); i++) {		
				
				out.println("<degree> " + grados.get(i) + " </degree>");
					
			}		
		}
			
		out.println("</degrees>");
			
	}
		
		
	public static void doGetpphase22_auto (HttpServletRequest req, HttpServletResponse res, ArrayList<DataModel.Student> alumnos) throws Exception {
			
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/xml");
		PrintWriter out = res.getWriter();
			
		out.println("<?xml version='1.0' encoding='utf-8' ?>");
		out.println("<students>");
			
		if (alumnos.isEmpty() == false) {
				
			for (int i = 0; i < alumnos.size(); i++) {

				out.println("<student id='" + alumnos.get(i).getID() + "' address='" + alumnos.get(i).getDireccion() + "'>" + alumnos.get(i).getNombre() + "</student>");

			}	
		}	 
			
		out.println("</students>");
			
	}

	
	public static void doGetpphase23_auto (HttpServletRequest req, HttpServletResponse res, ArrayList<DataModel.Subject> asignaturas)  throws Exception {
			
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/xml");
		PrintWriter out = res.getWriter();
			
		out.println("<?xml version='1.0' encoding='utf-8'?>");
		out.println("<subjects>");
			
		if (asignaturas.isEmpty() == false) {
				
			for (int i = 0; i < asignaturas.size(); i++) {
					
				out.println("<subject idSub='" + asignaturas.get(i).getID() + "' grade='" + asignaturas.get(i).getNota() + "'> " + asignaturas.get(i).getNombre() + " </subject>");
					
			}	
		}
			
		out.println("</subjects>");
			
	}
}
