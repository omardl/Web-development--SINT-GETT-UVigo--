package p2;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathExpressionException;


public class Sint81P2 extends HttpServlet {

    public final String PASSWD = "43rl3v53d1";
	
	
    public void init (ServletConfig config) {
    	
    	try {
    		
    		new EAML_parser(config);
    		
    	} catch (XPathExpressionException | IOException e) {
			
		System.out.println("Error en la funcion init del servlet, fallo en la clase EAML_parser: " + e);
		
	} 
    	
    }
	
	
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        boolean auto;
	
	//Se comprueba si existe el parametro auto y su valor es true, si no, esta en modo browser
	if ((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true"))) {
	
	    auto = true;
	
	} else {
	
	    auto = false;
	
	}	
		
	//Se comprueba si hay password
	if (req.getParameter("p") == null) {				
	    
	    if (auto) {
	    
	        FrontEnd.sin_passwd_auto(req, res);
	    
	    } else {
	    
	        FrontEnd.sin_passwd(req, res);
	    
	    }	
		
	} else {
	
	    //Se comprueba si la password es erronea
	    if (!req.getParameter("p").equals(PASSWD)) {
	    
	        if (auto) {
	        
	            FrontEnd.passwd_erronea_auto(req, res);
	        
	        } else {
	        
	            FrontEnd.passwd_erronea(req, res);
	        
	        }
	    
	    } else {
	    
	        //Si no hay parametro pfase, es 01 por defecto
	        if (req.getParameter("pphase") == null) {
					
		    if (auto) {
		    
		        FrontEnd.doGetpphase01_auto(req, res);
		    
		    } else {
		    
		        FrontEnd.doGetpphase01(req, res);
		    
		    }					
					
		} else {
		
		    switch(req.getParameter("pphase")) {
					
			case "01": 
			 
			    if (auto) {
			    
			        FrontEnd.doGetpphase01_auto(req, res);	
			    
			    } else {
			    
			        FrontEnd.doGetpphase01(req, res);	
			    
			    }				
 		           break;
						
			case "02":
		        
		            if (auto) {
		            
		                FrontEnd.doGetpphase02_auto(req, res);
		            
		            } else {
		            
		                FrontEnd.doGetpphase02(req, res);
		            
		            }         	
		            break;
		                
			case "21":
		                
			    try { 
	                		
	     	                ArrayList<String> grados = DataModel.getC2Degrees();
	                	
	                	if (auto) {
	                	
	                	    FrontEnd.doGetpphase21_auto(req, res, grados);
	                	
	                	} else {
	                	
	                	    FrontEnd.doGetpphase21(req, res, grados);
	                	
	                	}	                     
							
  			   } catch (Exception e) { 
							
  				 System.out.println("Ha ocurrido un error en la ejecucion del servlet: " + e);
							
	 		   }
 			   break;
						
			case "22":
			
			    if (req.getParameter("pdegree") == null) {
			    
			        if (auto) {
			        
			            FrontEnd.falta_param_auto(req, res, "pdegree");
			        
			        } else {
			        
			            FrontEnd.falta_param(req, res, "pdegree");
			        
			        }
			    
			    } else {
			        
			        try {
						    
				     ArrayList<DataModel.Student> alumnos = DataModel.getC2Students(req.getParameter("pdegree"));
				 
	        		     if (auto) {
				 
				         FrontEnd.doGetpphase22_auto(req, res, alumnos);
				 
				     } else {
				 
				         FrontEnd.doGetpphase22(req, res, alumnos);
				 
				     }
							
			        } catch (Exception e) {
							
				     System.out.println("Ha ocurrido un error en la ejecucion del servlet: " + e);
							
	 		        }
			    }	
			    break;
						
			case "23":
			
			    if (req.getParameter("pdegree") == null) {
			    
			        if (auto) {
			        
			            FrontEnd.falta_param_auto(req, res, "pdegree");
			        
			        } else {
			        
			            FrontEnd.falta_param(req, res, "pdegree");
			        
			        }
			    
			    } else {
			    
			        if (req.getParameter("pstudent") == null) {
			    
			            if (auto) {
			        
			                FrontEnd.falta_param_auto(req, res, "pstudent");
			        
			            } else {
			        
			                FrontEnd.falta_param(req, res, "pstudent");
			        
			            }
			    
			        } else  {
			        
			            try {
						    
				         ArrayList<DataModel.Subject> asignaturas = DataModel.getC2Subjects(req.getParameter("pdegree"), req.getParameter("pstudent"));
				 
	        		         if (auto) {
				 
	   			             FrontEnd.doGetpphase23_auto(req, res, asignaturas);
				 
        				 } else {
				 
				             FrontEnd.doGetpphase23(req, res, asignaturas);
				         }
							
			            } catch (Exception e) {
							
				         System.out.println("Ha ocurrido un error en la ejecucion del servlet: " + e);
							
	 		            }
			        }   			 
			    }	                   
  			    break;	
					
	         	default:					
			    break;
		        				
		    }		
	        }
	    }
        }
    }	
}
