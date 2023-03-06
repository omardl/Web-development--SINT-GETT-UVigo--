package p2;

import java.util.ArrayList;

import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class XML_ErrorHandler extends DefaultHandler {
	
	int warning;
	int error;
	int fatalerror;
	ArrayList<String> mensajes;
	
	public XML_ErrorHandler() { 
	
		this.warning = 0;
		
		this.error = 0;
		
		this.fatalerror = 0;
	
		this.mensajes = new ArrayList<String>();
		
	}

    public void warning (SAXParseException w) {
  
    	this.mensajes.add(w.toString());
		this.warning = 1;
		
	}
	
    public void error(SAXParseException e) {
   
    	
		this.mensajes.add(e.toString());
    	this.error = 1;
    	
	}

    public void fatalerror(SAXParseException f) {
        	
    	this.mensajes.add(f.toString());
    	this.fatalerror = 1;  	
    	
    }

    public boolean getWarnings() {
    	
    	if (this.warning == 1) {
        	
    		return true;
    	
    	} else {
    	
    		return false;
    	
    	}
    	
    }
    
    public boolean getErrors() {
    	
    	if (this.error == 1) {
    	
    		return true;
    	
    	} else {
    	
    		return false;
    	
    	}
    	
    }
    
    public boolean getFatalerrors() {
    	
    	if (this.fatalerror == 1) {
        	
    		return true;
    	
    	} else {
    	
    		return false;
    	
    	}
    	
    }
    
    public ArrayList<String> getMessages() {
    
    	return this.mensajes;
    	
    }
    
    public void resetErrorHandler() {
    	
    	this.warning = 0;
    	this.error = 0;
    	this.fatalerror = 0;
    	this.mensajes.clear();
    	
    }
    
}
