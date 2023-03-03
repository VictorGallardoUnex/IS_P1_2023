import jpcap.NetworkInterfaceAddress;
import modelos.Bandera;
import modelos.Instruccion;

import java.util.ArrayList;

public class Ejecutor {
    ArrayList<Instruccion> instrucciones;
    Configuracion configuracion;

    ControladorTarjeta controlador = new ControladorTarjeta();

    int tarjeta_seleccionada = -1;
    public Ejecutor(ArrayList<Instruccion> instrucciones,Configuracion configuracion) {

        this.instrucciones = instrucciones;
        this.configuracion = configuracion;

        for (Instruccion instruccion : instrucciones) {
            if (instruccion.getClass() == Bandera.class) {
                procesar_configuracion((Bandera) instruccion);
            } else {
                switch (instruccion.clave) {
                    case "ficheroSalida":
                        ficheroSalida(instruccion.valor);
                    case "seleccionatarjeta":
                        seleccionatarjeta(Integer.parseInt(instruccion.valor));
                    case "infotarjeta":
                        infotarjeta(Integer.parseInt(instruccion.valor));
                    case "infoIP":
                        infoIP(Integer.parseInt(instruccion.valor));
                    case "infoEthernet":
                        infoEthernet(Integer.parseInt(instruccion.valor));
                }
            }
        }
    }

    private void procesar_configuracion(Bandera bandera) {
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


    private void ficheroSalida(String valor) {

    }

    private void seleccionatarjeta(int valor) {
        this.tarjeta_seleccionada = valor;


    }

    private void infotarjeta(int valor) {
        jpcap.NetworkInterface tarjeta = controlador.getTarjeta(valor);
        System.out.println("Nombre: " + tarjeta.name);
        System.out.println("Nombre del enlace: " + tarjeta.datalink_name);
        System.out.println("Mac:");
        byte b = tarjeta.mac_address[0];//mirar si es mayor a 6
        System.out.println("Primer byte: " + Integer.toHexString(b & 0xff));

        NetworkInterfaceAddress dir;
        for (int j = 0; j < tarjeta.addresses.length; j++) {
            System.out.print("Direccion " + j + ": ");
            dir = tarjeta.addresses[j];
            System.out.println("direccion:" + dir.address);
        }

    }

    private void infoIP(int valor) {
        jpcap.NetworkInterface tarjeta = controlador.getTarjeta(valor);

    }

    private void infoEthernet(int valor) {

    }
}
