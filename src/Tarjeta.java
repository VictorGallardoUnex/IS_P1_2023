import jpcap.JpcapCaptor;
import jpcap.NetworkInterfaceAddress;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ifraa
 */
public class Tarjeta {

    jpcap.NetworkInterface[] tarjetas;
    Parametros P;

    public Tarjeta() {
    }

    public boolean init() {
        jpcap.NetworkInterface[] tarjetas;

        tarjetas = JpcapCaptor.getDeviceList();
        if (tarjetas == null) {
            System.out.println("Error de packet driver");
            return false;
        }
        return true;
    }

    public int cantidadTarjetas() {
        jpcap.NetworkInterface[] tarjetas;

        tarjetas = JpcapCaptor.getDeviceList();
        if (tarjetas == null) {
            System.out.println("Error de packet driver");
            return -1;
        }
        return tarjetas.length;
    }

    public void run() {
        if (!init()) {

            return;
        }
        System.out.println("Informando de las tarjetas que tiene esta mÃ¡quina, tiene las siguientes tarjetas: " + tarjetas.length);

        int numTarjeta = P.getNumeroTarjeta();
        System.out.println("Info de la tarjeta numero " + numTarjeta);
        System.out.println("Nombre: " + tarjetas[numTarjeta].name);
        System.out.println("Nombre del enlace: " + tarjetas[numTarjeta].datalink_name);
        System.out.println("Mac:");
        byte b = tarjetas[numTarjeta].mac_address[0];//mirar si es mayor a 6
        System.out.println("Primer byte: " + Integer.toHexString(b & 0xff));

        NetworkInterfaceAddress dir;
        for (int j = 0; j < tarjetas[numTarjeta].addresses.length; j++) {
            System.out.print("Direccion " + j + ": ");
            dir = tarjetas[numTarjeta].addresses[j];
            System.out.println("direccion:" + dir.address);
        }
    }
}
