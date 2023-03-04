import jpcap.NetworkInterfaceAddress;
import modelos.Bandera;
import modelos.Instruccion;

import java.util.ArrayList;
import java.util.Arrays;

public class Ejecutor {
    Configuracion configuracion;
    ControladorSalida syso = ControladorSalida.getInstance();
    ControladorTarjeta controladorTarjeta;

    public Ejecutor(Configuracion configuracion) {
        this.configuracion = configuracion;
        controladorTarjeta = new ControladorTarjeta(configuracion);
    }
    public void procesar_instrucciones(ArrayList<Instruccion> instrucciones) {
        for (Instruccion instruccion : instrucciones) {
            if (instruccion.getClass() == Bandera.class) {
                procesar_configuracion((Bandera) instruccion);
            } else {
                // Añadimos un try catch para nuestra propia excepcion
                try {
                    switch (instruccion.clave.toLowerCase()) {
                        case "ficherosalida":
                            ControladorSalida.getInstance().setArchivoSalida(instruccion.valor);
                        case "seleccionatarjeta":
                            seleccionatarjeta(Integer.parseInt(instruccion.valor));
                        case "infotarjeta":
                            infotarjeta(Integer.parseInt(instruccion.valor));
                        case "infoip":
                            infoIP(Integer.parseInt(instruccion.valor));
                        case "infoethernet":
                            infoEthernet(Integer.parseInt(instruccion.valor));
                    }
                } catch (ErrorTarjetaNoExiste excepcion) {
                    syso.print("Error al ejecutar el comando. La tarjeta no existe");
                }
            }
        }
    }

    public void procesar_configuracion(Bandera bandera) {
        switch (bandera.clave.toLowerCase()) {
            case "salidapantalla": {
                this.configuracion.salida_pantalla = bandera.valor.equals("ON");
                break;
            }
            case "salidafichero": {
                this.configuracion.salida_fichero = bandera.valor.equals("ON");
                break;
            }
            case "reescribirficherosalida": {
                this.configuracion.reescribir_fichero_salida = bandera.valor.equals("ON");
                break;
            }
        }
    }


    private void seleccionatarjeta(int valor) throws ErrorTarjetaNoExiste {
        controladorTarjeta.setTarjeta_seleccionada(valor);
    }

    private void infotarjeta(int valor) throws ErrorTarjetaNoExiste{
        jpcap.NetworkInterface tarjeta = controladorTarjeta.getTarjeta(valor);
        syso.println("Info de la tarjeta numero " + valor);
        syso.println("Nombre: " + tarjeta.name);
        syso.println("Nombre del enlace: " + tarjeta.datalink_name);
        syso.println("Mac:");
        byte b = tarjeta.mac_address[0];//mirar si es mayor a 6
        syso.println("Primer byte: " + Integer.toHexString(b & 0xff));
    }

    private void infoIP(int valor) throws ErrorTarjetaNoExiste {
        jpcap.NetworkInterface tarjeta = controladorTarjeta.getTarjeta(valor);
        syso.println("Informacion IP de la tarjeta seleccionada (" + valor + ")" + " " + tarjeta.name);
        for (int j = 0; j < tarjeta.addresses.length; j++) {
            System.out.print("Informacion del adaptador " + j + ": ");
            syso.println("    Direccion:" + tarjeta.addresses[j].address);
            syso.println("    Mascara:" + tarjeta.addresses[j].subnet);
            syso.println("    Destino:" + tarjeta.addresses[j].destination);
            syso.println("    Broadcast:" + tarjeta.addresses[j].broadcast);
        }
    }

    private void infoEthernet(int valor) throws ErrorTarjetaNoExiste {
        jpcap.NetworkInterface tarjeta = controladorTarjeta.getTarjeta(valor);
        syso.println("Direccion mac de la tarjeta (" + valor + ") " + Arrays.toString(tarjeta.mac_address));
    }
}
