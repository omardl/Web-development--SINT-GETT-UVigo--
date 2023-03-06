<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title> SINTonizaTV: Formulario Guardado </title>
        <link href="p1.css" rel="stylesheet"/>
    </head>
    <body> 

        <h1> SINTonizaTV </h1>
        <h2> ¡Tu mejor web para consultar todo lo que quieras ver en tu TV! </h2>
        <table>
            <tr>
                <td> <img class="imagen" src="imagenes/icono.jpg"> </td>
            </tr>
        </table>
        <h3> ¡Registro completado! </h3>

        <fieldset id="bloque_datos">
            <legend id="cabecera_datos"> Datos personales </legend>
            <table> 
                <tr>
                    <td> <p> Nombre: </p> </td>
                    <td> <p> <?php
                        if(!empty($_REQUEST['Nombre'])) {
                            echo $_REQUEST['Nombre'];
                        } else {
                            echo '-';
                        }
                    ?> </p> </td> 
                    <tr> 
                    <td> <p> Apellidos: </p> </td>
                    <td> <p> <?php 
                        if (!empty($_REQUEST['Apellidos'])) {
                            echo $_REQUEST['Apellidos'];
                        } else {
                            echo '-';
                        }
                    ?> </p> </td> 
                </tr>
                <tr>
                    <td> <p> Nacimiento: </p> </td>
                    <td> <p> <?php 
                        if (!empty($_REQUEST['Fecha'])) {
                            echo $_REQUEST['Fecha'];
                        } else {
                            echo '-';
                        }
                    ?> </p> </td> 
                </tr>
                <tr>
                    <td> <p> Usuario: </p> </td>
                    <td> <p> <?php 
                        if (!empty($_REQUEST['Usuario'])) {
                            echo $_REQUEST['Usuario'];
                        } else {
                            echo '-';
                        }
                    ?> </p> </td> 
                </tr>
                <tr> 
                    <td> <p> Password: </p> </td>
                    <td> <p> <?php 
                        if (!empty($_REQUEST['Psswd'])) {
                            echo $_REQUEST['Psswd'];
                        } else {
                            echo '-';
                        }
                    ?> </p> </td> 
                </tr> 
                <tr>
                    <td> <p> Correo: </p> </td>
                    <td> <p>
                    <?php if (!empty($_REQUEST['Correo'])) {
                            echo $_REQUEST['Correo'];
                        } else {
                            echo '-';
                        } ?> 
                    </p> </td> 
                </tr>
                <tr>
                    <td> <p> Telefono: </p> </td>
                    <td> <p> <?php 
                        if (!empty($_REQUEST['Tlfn'])) {
                            echo $_REQUEST['Tlfn'];
                        } else {
                            echo '-';
                        }
                    ?> </p> </td> 
                </tr>
                <tr>
                    <td> <p> Foto de perfil: </p> </td>
                    <td> <p> <?php 
                        if($_REQUEST['codificacion'] == 'multipart/form-data') {
                            $ruta_actual = getcwd();
                            $destino = $ruta_actual . "/imagenes/" . $_FILES['Foto']['name'];
                            if(move_uploaded_file($_FILES['Foto']['tmp_name'], $destino)) {
                                echo "<img src='imagenes/", $_FILES['Foto']['name'] ,"' class='imagen' alt='Error al cargar la imagen'>";
                            } else {
                                echo '-';
                            }
                        } else {
                            if (!empty($_REQUEST['Foto'])) {
                                echo $_REQUEST['Foto'];
                            } else {
                                echo '-';
                            }
                        }
                    ?></p> </td>

                </tr>
                </tr>
            </table>
        </fieldset>

        <fieldset id="bloque_gustos">
            <legend id="cabecera_gustos"> Gustos personales </legend>
            <table>
                <tr> 
                    <td> <p> Tu horario habitual: </p> </td>
                    <td> <p> <?php
                        if(isset($_REQUEST['Horario'])){
                            echo $_REQUEST['Horario'];
                        } else {
                            echo '-';
                        }
                    ?> </p> </td>
                </tr>
                <tr> 
                    <td> <p> Sueles ver la tele cada dia: </p> </td>
                    <td> <p> <?php
                        if(isset($_REQUEST['Tiempo'])){
                            echo $_REQUEST['Tiempo'];
                        } else {
                            echo '-';
                        }
                    ?> </p> </td>
                </tr>
                <tr> 
                    <td> <p> Tus programas favoritos: </p> </td>
                    <td> <p> <?php                   
                        if(!empty($_REQUEST['programa'])){
                            foreach($_REQUEST['programa'] as $selected) {
                                echo $selected."<br>";
                            }
                        } else {
                            echo '-';
                        }
                    ?> </p> </td>
                </tr>
                <tr> 
                    <td> <p> Tus categorias favoritas: </p> </td>
                    <td> <p> <?php                  
                        if(!empty($_REQUEST['categoria'])){
                            foreach($_REQUEST['categoria'] as $selected) {
                                echo $selected."<br>";
                            }
                        } else {
                            echo '-';
                        }
                    ?> </p> </td>
                </tr>                 
            </table>
        </fieldset>

        <fieldset id="bloque_envio">
            <legend id="cabecera_envio"> Formulario </legend>
            <table>
                <tr>
                    <td> <p> Metodo elegido: </p> </td>
                    <td> <p> <?php echo $_REQUEST['metodo']; ?> </p> </td> 
                </tr>
                <tr> 
                    <td> <p> Codificacion elegida: </p> </td>
                    <td> <p> <?php echo $_REQUEST['codificacion']; ?> </p> </td>
                </tr>
                <tr> 
                    <td> <p> URL elegido: </p> </td>
                    <td> <p> <?php echo $_REQUEST['envio']; ?> </p> </td>
                </tr>
            </table>
        </fieldset>

        <fieldset id="bloque_extra">
            <table>
                <tr> 
                    <td> <p> Navegador: </p> </td>
                    <td> <p> <?php echo $_REQUEST['navegador'] ?> </p> </td>
                </tr>
                <tr> 
                    <td> <p> Recibiras correos con las ultimas novedades de la web: </p> </td>
                    <?php if(isset($_REQUEST['novedades'])) { ?>
                        <td> <p> SI. </p> </td> 
                    <?php } else { ?>
                        <td> <p> NO. </p> </td>
                    <?php } ?>
                </tr>
                <tr>
                    <td> <p> Aceptas los terminos y condiciones de la web: </p> </td>
                    <?php if(isset($_REQUEST['terminos'])) { ?>
                        <td> <p> SI. </p> </td> 
                    <?php } else { ?>
                        <td> <p> NO. </p> </td>
                    <?php } ?> 
                </tr> 
                <tr>
                    <td> <p> Tu consulta: </p> </td>
                    <td> 
                        <p> <?php if(isset($_REQUEST['consulta'])) {
                            echo $_REQUEST['consulta'];
                        } ?> </p>
                    </td>
                <tr>              
            </table>
        </fieldset>

        <fieldset id="bloque_entorno"> 
            <legend id="cabecera_entorno"> Algunas variables de entorno </legend>
            <table> 
                <tr>
                    <td> <p> IP del servidor: </p> </td> 
                    <td> <p> <?php echo $_SERVER['SERVER_ADDR']; ?> </p> </td>
                </tr>
                <tr> 
                    <td> <p> Nombre del servidor: </p> </td>
                    <td> <p> <?php echo $_SERVER['SERVER_NAME']; ?> </p> </td>
                </tr>
                <tr> 
                    <td> <p> Puerto del servidor: </p> </td>
                    <td> <p> <?php echo $_SERVER['SERVER_PORT']; ?> </p> </td>
                </tr>
                <tr> 
                    <td> <p> Software del servidor: </p> </td>
                    <td> <p> <?php echo $_SERVER['SERVER_SOFTWARE']; ?> </p> </td>
                </tr>
                <tr> 
                    <td> <p> Protocolo del servidor: </p> </td>
                    <td> <p> <?php echo $_SERVER['SERVER_PROTOCOL']; ?> </p> </td>
                </tr>
            </table>
        </fieldset>
        
    </body>
</html>