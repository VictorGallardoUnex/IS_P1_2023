package com.interconexionsistemas.practica2.Implementaciones;

public class FuncionesPractica2 {

    // Practica 2 IS 2023

//                        case "recibir": {
//        // todo | longitud | tipo
//        //§ Mostrará las tramas que cumplan con la opción elegida, que son:
//        //• Todo.
//        //      Mostrará todas las tramas que lleguen a la estación.
//        //• Longitud.
//        //      Mostrará sólo las tramas ethernet en las que el
//        //      campo tipo/longitud actúa como campo longitud, es decir,
//        //      su valor es inferior a 1500.
//        //• Tipo.
//        //      Mostrará sólo las tramas ethernet en las que el campo
//        //      tipo/longitud actúa como campo tipo, es decir, su valor es
//        //      superior a 1500
//        //    El programa terminará cuando el usuario pulse la tecla “f”. Además, se asume que
//        //    se ha elegido una tarjeta válida, en el caso de que no se haya elegido una tarjeta
//        //    válida o ninguna se trabajará con la tarjeta por defecto que es la 0
//        break;
//    }
//    // Enviar tramas por la red
//                        case "repertirenvio": {
//                            /*
//                            o & repetirenvio <numero>
//                            § En número vendrá indicado la cantidad de veces que se enviará el
//                            mismo paquete de datos, donde irá el mismo texto, seguido de un
//                            numero que irá de 1 a <numero> tal cual se puede ver en el ejemplo
//                            del comando “texto”.
//                             */
//        break;
//    }
//                        case "texto": {
//        break;
//                            /*
//                            o & texto <cadena que se envía>
//                             En este caso irá en el campo datos de la trama ethernet el texto que
//                            esté escrito a partir del comando “texto” y además se le añadirá un
//                            número a ese texto indicando el número de vez que se produce es
//                            envío. Por ejemplo:
//                            • & texto esto se envia por la red
//                            • & repetirenvio 5
//                            § Lo anterior produciría el envio de las siguientes tramas:
//                            • Esto se envia por la red 1
//                            • Esto se envia por la red 2
//                            • Esto se envia por la red 3
//                            • Esto se envia por la red 4
//                            • Esto se envia por la red 5
//                            o Finalmente, también ha de considerarse que por defecto si el usuario no ha
//                            puesto nada con el comando “texto”, el texto por defecto que el programa
//                            enviará es: “esto se hace en la practica 2 de interconexión de sistemas”
//                             */
//    }
//    // Modulo C - Verificar	 que	 recibimos	 lo	 enviado	 en	 el	 módulo
//    // anterior
//                        case "pin": {
//                            /*
//                            & pin <palabra>
//                            § En “palabra” irá un pin que se colocará a partir del primer byte del
//                            campo datos de la trama ethernet y seguidamente se colocará el
//                            texto del comando texto, y se enviará las veces que indique el
//                            comando “repetirenvio” en número vendrá indicado la cantidad de
//                            veces que se enviará el mismo paquete de datos, donde irá el
//                            mismo texto, seguido de un numero que irá de 1 a <numero> tal
//                            cual se puede ver en el ejemplo del comando “texto”. Sólo cuando
//                            este comando esté en el fichero de configuración se modificará el
//                            comportamiento del módulo B.
//                             */
//    }
//                        case "recibirconpin": {
//                            /*
//                            & recibirconpin
//                            § En este caso se activará la recepción y mostrará sólo aquellas
//                            tramas que siendo de tipo “longitud” venga el “pin” correctamente.
//                            Si estuviera este comando activo, pero no hubiera pin definido en
//                            el fichero, serán válidas todas las tramas, dado que es como recibir
//                            sin pin
//                             */
//    }
}
