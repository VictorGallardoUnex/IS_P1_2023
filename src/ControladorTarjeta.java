import jpcap.JpcapCaptor;
import jpcap.NetworkInterfaceAddress;

import javax.swing.*;
/**
 * Clase de apoyo. Incluye la interfaz de las Tarjetas y las validaciones de los módulos
 * */
public class ControladorTarjeta {
    jpcap.NetworkInterface[] tarjetas = JpcapCaptor.getDeviceList();
    public void ControladorTarjeta() {
        if (tarjetas == null) {
            System.out.println("Error de packet driver");
        }
    }


    public jpcap.NetworkInterface getTarjeta(int valor) {
        if (is_not_init()) {
            return null;
        }
        if (valor > tarjetas.length) {
            System.out.println("No existe esa tarjeta");
            return null;
        }
        return tarjetas[valor];
    }
    /**
     * Devuelve el numero de tarjetas detectadas.
     * returns: -1 si no existe o hay error
     * */
    public int contar_tarjetas() {
        if (is_not_init()) {
            return -1;
        }
        return tarjetas.length;
    }
    private boolean is_not_init() {
        return tarjetas != null;
    }
    public void info_tarjeta() {
        if (is_not_init()) {
            return;
        }

        System.out.println("Informando de las tarjetas que tiene esta mÃ¡quina, tiene las siguientes tarjetas: " + tarjetas.length);

        int numTarjeta = 0;
        jpcap.NetworkInterface tarjeta = tarjetas[numTarjeta];
        System.out.println("Info de la tarjeta numero " + numTarjeta);
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

}
