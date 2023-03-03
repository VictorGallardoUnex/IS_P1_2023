import jpcap.JpcapCaptor;
import jpcap.NetworkInterfaceAddress;

public class ControladorTarjeta {
    jpcap.NetworkInterface[] tarjetas = null;
    public void ControladorTarjeta() {
        jpcap.NetworkInterface[] tarjetas;

        tarjetas = JpcapCaptor.getDeviceList();
        if (tarjetas == null) {
            System.out.println("Error de packet driver");
        }
    }
    public int contar_tarjeta() {
        if (is_not_init()) {
            return -1;
        }
        return tarjetas.length;
    }
    private boolean is_not_init() {
        return tarjetas != null;
    }

    public void run() {
        if (is_not_init()) {
            return;
        }

        System.out.println("Informando de las tarjetas que tiene esta mÃ¡quina, tiene las siguientes tarjetas: " + tarjetas.length);

        int numTarjeta = 0;
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
