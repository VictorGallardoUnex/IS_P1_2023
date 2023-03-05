import modelos.Bandera;
import modelos.Instruccion;

import java.util.ArrayList;
import java.util.Arrays;

public class Ejecutor {
    Configuracion configuracion;
    ControladorSalida syso;
    ControladorTarjeta controladorTarjeta;

    public Ejecutor(Configuracion configuracion,ControladorSalida syso) {
        this.configuracion = configuracion;
        this.syso = syso;
        controladorTarjeta = new ControladorTarjeta(configuracion);
    }
    /**
     * Recibe una lista de instrucciones y ejecuta el comnado de cada instruccion si esta registrado
     * */
    public void procesar_instrucciones(ArrayList<Instruccion> instrucciones) {
        for (Instruccion instruccion : instrucciones) {
            if (instruccion.getClass() == Bandera.class) {
                procesar_configuracion((Bandera) instruccion);
            } else {
                // Añadimos un try catch para nuestra propia excepcion
                syso.println("Ejecutando comando: "+instruccion.clave);
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

    private void infoTarjeta(int valor) throws ErrorTarjetaNoExiste{
        jpcap.NetworkInterface tarjeta = controladorTarjeta.getTarjeta(valor);
        syso.println("Info de la tarjeta numero " + valor);
        syso.println("Nombre: " + tarjeta.name);
        syso.println("Nombre del enlace: " + tarjeta.datalink_name);
        syso.println("Mac: " + getMacAsString(tarjeta.mac_address));
    }

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

    private void infoEthernet(int valor) throws ErrorTarjetaNoExiste {
        jpcap.NetworkInterface tarjeta = controladorTarjeta.getTarjeta(valor);
        ;
        syso.println("Direccion mac de la tarjeta (" + valor + ") " + getMacAsString(tarjeta.mac_address));
    }
    private String getMacAsString(byte[] mac) {
        return String.format("%02X:%02X:%02X:%02X:%02X:%02X", mac[0], mac[1], mac[2], mac[3], mac[4], mac[5]);
    }
}
