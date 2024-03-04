# Practicas de la asignatura Sistemas de Internet (3º) del GETT de la UVigo

Se realizaron 2 prácticas descritas a continuación, siguiendo unas especificaciones concretas proporciondas por el profesorado de la asignatura y pasando una batería de pruebas final. Dichos servicios se lanzaron mediante un servidor Apache del que disponían los equipos del laboratorio de la asignatura.

La primera práctica consiste en la realización de un formulario web de temática libre, en el que el usuario puede rellenar una serie de campos, algunos con restricciones, y que serán validados antes del envío del formulario y su posterior respuesta PHP en la que se indicarán los valores introducidos y una serie de parámetros extra requeridos. Se podrá elegir también el tipo de codificación del formulario y el método y URL del envío.

La segunda práctica consiste en la presentación de una serie de páginas entre las que el usuario podrá navegar y consultar los expedientes de diferentes alumnos en distintas carreras. 
Parte de ésta información es extraída de una URL inicial proporcionada por el profesor. Cada documento XML podrá contener referencias a otros, que tendrán un atributo referenciando a la URL del siguiente documento. El código no sólo deberá comprobar la información de todos los documentos referenciados si no que debe parsear cada XML con un schema XML de elaboración propia, siguiendo una serie de especificaciones. También deberá guardar todos los errores encontrados y clasificarlos según su importancia, pudiendo el usuario acceder a un listado con los mismos.
