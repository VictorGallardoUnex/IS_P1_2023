import jpcap.JpcapCaptor;
import jpcap.packet.Packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase se encarga de administrar las tramas de la interfaz
 */
public class ControladorTramas {
    // Enviar y recibir paquetes
    Configuracion configuracion;
    ControladorTarjeta controladorTarjeta;
    ControladorSalida syso = ControladorSalida.getInstance();
    JpcapCaptor receptor;


    public ControladorTramas(Configuracion configuracion) throws ErrorJpcap, ErrorTarjetaNoExiste {
        this.configuracion = configuracion;
        controladorTarjeta = ControladorTarjeta.getInstance();
        try {
            receptor = JpcapCaptor.openDevice(controladorTarjeta.getTarjeta(),65535,false,20);
        } catch (IOException ex) {
            //Logger.getLogger(RecibirTramasEthernet.class.getName()).log(Level.SEVERE, null, ex);
            syso.println("No se ha podido abrir el receptor de paquetes");
        }
    }

    // Modulo A
    /**
     * Muestra todas las tramas que lleguen
     */
    public void recibirTodo() {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader entrada = new BufferedReader(reader);
        boolean fin = false;
        do {
            Packet paquete = receptor.getPacket();
            if (paquete!=null) {
                //todo
            }
            try {
                if (entrada.ready()) {
                    if (entrada.readLine().equals("f")) {
                        fin = true;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        } while (!fin);
    }

    /**
     * Muestra las tramas de ethernet en las que el capo tipo/longitud es menor que 1500
     */
    public void recibirLongitud() {
    }

    /**
     * Muestra las tramas de ethernet en las que el capo tipo/longitud es superior que 1500
      */
    public void recibirTipo() {
    }
    private boolean salir() {

    }
}
