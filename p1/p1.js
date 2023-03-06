    function valida() {

        //Se comprueban una serie de requisitos exigidos en los campos del formulario
        
        var date = document.getElementById("formulario").Fecha.value;
        var user = document.getElementById("formulario").Usuario.value;
        var passwd1 = document.getElementById("formulario").Psswd.value;
        var passwd2 = document.getElementById("formulario").Confirma.value;
        var email = document.getElementById("formulario").Correo.value;
        var tlfn = document.getElementById("formulario").Tlfn.value;

        
        if (date != "") {
            
            if (date.search("[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{4}") == -1) {
                
                alert("El formato de la fecha debe ser dd/mm/aaaa");
                return false;  

            }
        }


        if (user != "") {
        
            if ((user.search("[a-z]") == -1) || (user.search("[A-Z]") == -1) || (user.search("[0-9]") == -1)) {
        
                alert("El usuario debe contener al menos una letra mayuscula, una minuscula y un numero.");
                return false;

            }
        }


        if (passwd1 != passwd2) {
            
            alert("Las dos passwords deben coinicidir.");
            return false;

        }


        if (email != "") {   

            if (email.search("[a-zA-Z0-9]{1,}([.]{1}[a-zA-Z0-9]{1,})*@[a-zA-Z0-9]{1,}([.]{1}[a-zA-Z0-9]{1,})*[.]{1}com{1}") == -1) {

                alert("El formato del correo debe ser ejemplo@server.com");
                return false;

            }

        }


        if (tlfn != "") {

            if((tlfn.length != 9) || (tlfn.search("[0-9]{9}") == -1)) {
        
                alert("El telefono debe contener 9 numeros.");
                return false;

            }

        }
        

        if (document.getElementById("formulario").envio[0].checked) {

            document.getElementById("formulario").action = "p1.php"

        } else {

            document.getElementById("formulario").action = "phpinfo.php"

        }


        if(document.getElementById("formulario").metodo[0].checked) {

            if (document.getElementById("formulario").codificacion[1].checked) {

                alert("El metodo GET solo admite codificacion application/x-www-form-urlencoded");
                return false;

            } else {

                document.getElementById("formulario").method = "get";
            }

        } else {

            document.getElementById("formulario").method = "post";
        }

        
        if (document.getElementById("formulario").codificacion[0].checked) {

            document.getElementById("formulario").enctype = "application/x-www-form-urlencoded";

        } else {

            document.getElementById("formulario").enctype = "multipart/form-data";

        }

        document.getElementById("formulario").navegador.value = navigator.appVersion;
    
        return true;

    }



    //Funciones para los checkbox de los campos programa y categoría que permiten marcar o desmarcar todas las opciones a la vez
    function selecciona_todos() {

        var programas = document.getElementsByName("programa[]");
   
        for (i=0; i<programas.length; i++) {
        
            programas[i].checked = 1;
        }
    }


    
    function elimina_todos() {

        var programas = document.getElementsByName("programa[]");
       
        for (i=0; i<programas.length; i++) {

            programas[i].checked = 0;
        }
    }
    


    function selecciona_todas() {
    
        var categorias = document.getElementsByName("categoria[]");

        for (i=0; i<categorias.length; i++) {

            categorias[i].checked = 1;
        }  
    }
    


    function elimina_todas() {
    
        var categorias = document.getElementsByName("categoria[]")

        for (i=0; i<categorias.length; i++) {

            categorias[i].checked = 0;
        }
    }