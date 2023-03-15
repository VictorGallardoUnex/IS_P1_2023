import modelos.Bandera;
import modelos.Instruccion;

import java.util.ArrayList;

/**
 * Clase que contiene los metodos que se ejecutan, es decir los comandos o instrucciones ya leidos son hechas su funcion
 */
public class Ejecutor {
    Configuracion configuracion;
    ControladorSalida syso;
    ControladorTarjeta controladorTarjeta;

    public Ejecutor(Configuracion configuracion,ControladorSalida syso) throws ErrorJpcap {
        this.configuracion = configuracion;
        this.syso = syso;
        controladorTarjeta = ControladorTarjeta.getInstance();
    }
    /**
     * Recibe una lista de instrucciones y ejecuta el comanado de cada instruccion si este esta registrado
     * */
    public void procesar_instrucciones(ArrayList<Instruccion> instrucciones) {
        for (Instruccion instruccion : instrucciones) {
            if (instruccion.getClass() == Bandera.class) {
                procesar_configuracion((Bandera) instruccion);
            } else {
                // Añadimos un try catch para nuestra propia excepcion
                System.out.println("Ejecutando comando: "+instruccion.clave);
                try {
                    // Comparamos el nombre de instruccion en minusculas para que no se sensible a mayus y minusculas
                    switch (instruccion.clave.toLowerCase()) {
                        case "ficherosalida": {
                            syso.setArchivoSalida(instruccion.valor);
                            break;
                        }
                        case "seleccionatarjeta": {
                            controladorTarjeta.setTarjeta_seleccionada(Integer.parseInt(instruccion.valor));
                            break;
                        }
                        case "infotarjeta": {
                            if (instruccion.valor.equals("")) {
                                infoTarjeta(controladorTarjeta.tarjeta_seleccionada);
                            } else {
                                infoTarjeta(Integer.parseInt(instruccion.valor));
                            }
                            break;
                        }
                        case "infoip": {
                            infoIP(Integer.parseInt(instruccion.valor));
                            break;
                        }
                        case "infoethernet": {
                            infoEthernet(Integer.parseInt(instruccion.valor));
                            break;
                        }

                        // Practica 2 IS 2023

                        case "recibir": {
                            // todo | longitud | tipo
                                //§ Mostrará las tramas que cumplan con la opción elegida, que son:
                                //• Todo.
                                //      Mostrará todas las tramas que lleguen a la estación.
                                //• Longitud.
                                //      Mostrará sólo las tramas ethernet en las que el
                                //      campo tipo/longitud actúa como campo longitud, es decir,
                                //      su valor es inferior a 1500.
                                //• Tipo.
                                //      Mostrará sólo las tramas ethernet en las que el campo
                                //      tipo/longitud actúa como campo tipo, es decir, su valor es
                                //      superior a 1500
                            //    El programa terminará cuando el usuario pulse la tecla “f”. Además, se asume que
                            //    se ha elegido una tarjeta válida, en el caso de que no se haya elegido una tarjeta
                            //    válida o ninguna se trabajará con la tarjeta por defecto que es la 0
                            break;
                        }
                        // Enviar tramas por la red
                        case "repertirenvio": {
                            /*
                            o & repetirenvio <numero>
                            § En número vendrá indicado la cantidad de veces que se enviará el
                            mismo paquete de datos, donde irá el mismo texto, seguido de un
                            numero que irá de 1 a <numero> tal cual se puede ver en el ejemplo
                            del comando “texto”.
                             */
                            break;
                        }
                        case "texto":{
                            break;
                            /*
                            o & texto <cadena que se envía>
                             En este caso irá en el campo datos de la trama ethernet el texto que
                            esté escrito a partir del comando “texto” y además se le añadirá un
                            número a ese texto indicando el número de vez que se produce es
                            envío. Por ejemplo:
                            • & texto esto se envia por la red
                            • & repetirenvio 5
                            § Lo anterior produciría el envio de las siguientes tramas:
                            • Esto se envia por la red 1
                            • Esto se envia por la red 2
                            • Esto se envia por la red 3
                            • Esto se envia por la red 4
                            • Esto se envia por la red 5
                            o Finalmente, también ha de considerarse que por defecto si el usuario no ha
                            puesto nada con el comando “texto”, el texto por defecto que el programa
                            enviará es: “esto se hace en la practica 2 de interconexión de sistemas”
                             */
                        }
                        // Modulo C - Verificar	 que	 recibimos	 lo	 enviado	 en	 el	 módulo
                        // anterior
                        case "pin": {
                            /*
                            & pin <palabra>
                            § En “palabra” irá un pin que se colocará a partir del primer byte del
                            campo datos de la trama ethernet y seguidamente se colocará el
                            texto del comando texto, y se enviará las veces que indique el
                            comando “repetirenvio” en número vendrá indicado la cantidad de
                            veces que se enviará el mismo paquete de datos, donde irá el
                            mismo texto, seguido de un numero que irá de 1 a <numero> tal
                            cual se puede ver en el ejemplo del comando “texto”. Sólo cuando
                            este comando esté en el fichero de configuración se modificará el
                            comportamiento del módulo B.
                             */
                        }
                        case "recibirconpin": {
                            /*
                            & recibirconpin
                            § En este caso se activará la recepción y mostrará sólo aquellas
                            tramas que siendo de tipo “longitud” venga el “pin” correctamente.
                            Si estuviera este comando activo, pero no hubiera pin definido en
                            el fichero, serán válidas todas las tramas, dado que es como recibir
                            sin pin
                             */
                        }

                        default: {
                            syso.println("No existe el comando");
                            break;
                        }
                    }
                } catch (ErrorTarjetaNoExiste excepcion) {
                    syso.println("Error al ejecutar el comando. La tarjeta no existe");
                } catch (Exception exception) {
                    syso.println("Error al ejecutar comando '"+instruccion.clave + "' con valor: '"+instruccion.valor+"'");
                    exception.printStackTrace();
                }
            }
        }
    }

    /**
     * Ejecuta el cambio de configuracion
     * */
    public void procesar_configuracion(Bandera bandera) {
        switch (bandera.clave.toLowerCase()) {
            case "salidapantalla": {
                this.configuracion.setSalida_pantalla(bandera.valor.equals("ON"));
                break;
            }
            case "salidafichero": {
                this.configuracion.setSalida_fichero(bandera.valor.equals("ON"));
                break;
            }
            case "reescribirficherosalida": {
                this.configuracion.setReescribir_fichero_salida(bandera.valor.equals("ON"));
                break;
            }
        }
    }

    /**
     * Muestra la informacion de la tarjeta, si hay multiples tarjetas acepta un numero para seleccionarla
     * @param valor
     * @throws ErrorTarjetaNoExiste
     */
    private void infoTarjeta(int valor) throws ErrorTarjetaNoExiste{
        jpcap.NetworkInterface tarjeta = controladorTarjeta.getTarjeta(valor);
        syso.println("Info de la tarjeta numero " + valor);
        syso.println("Nombre: " + tarjeta.name);
        syso.println("Nombre del enlace: " + tarjeta.datalink_name);
        syso.println("Mac: " + getMacAsString(tarjeta.mac_address));
    }

    /**
     * Muestra la informacion de la tarjeta, si hay multiples tarjetas acepta un numero para seleccionarla
     */
    private void infoTarjeta() throws ErrorTarjetaNoExiste {
        infoTarjeta(0);
    }

    /**
     * Muestra la informacion de la direccion ip
     */
    private void infoIP(int valor) throws ErrorTarjetaNoExiste {
        jpcap.NetworkInterface tarjeta = controladorTarjeta.getTarjeta(valor);
        syso.println("Informacion IP de la tarjeta seleccionada (" + valor + ")" + " " + tarjeta.name);
        for (int j = 0; j < tarjeta.addresses.length; j++) {
            syso.println("Informacion del adaptador " + j + ": ");
            syso.println("    Direccion:" + tarjeta.addresses[j].address);
            syso.println("    Mascara:" + tarjeta.addresses[j].subnet);
            syso.println("    Destino:" + tarjeta.addresses[j].destination);
            syso.println("    Broadcast:" + tarjeta.addresses[j].broadcast);
        }
    }

    /**
     * Muestra la mac
     * @param valor
     * @throws ErrorTarjetaNoExiste
     */
    private void infoEthernet(int valor) throws ErrorTarjetaNoExiste {
        jpcap.NetworkInterface tarjeta = controladorTarjeta.getTarjeta(valor);
        ;
        syso.println("Direccion mac de la tarjeta (" + valor + ") " + getMacAsString(tarjeta.mac_address));
    }

    /**
     * Formatea los bytes de la mac en una string
     * @param mac
     * @return
     */
    private String getMacAsString(byte[] mac) {
        return String.format("%02X:%02X:%02X:%02X:%02X:%02X", mac[0], mac[1], mac[2], mac[3], mac[4], mac[5]);
    }
}
