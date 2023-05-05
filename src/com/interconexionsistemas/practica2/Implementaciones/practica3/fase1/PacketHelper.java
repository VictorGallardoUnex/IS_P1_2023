package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaDatos;
import com.interconexionsistemas.practica2.Modelos.Errores.ErrorJpcap;
import com.interconexionsistemas.practica2.Modelos.Errores.ErrorTarjetaNoExiste;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;
import jpcap.JpcapSender;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

import static com.interconexionsistemas.practica2.Main.configuracion;
import static com.interconexionsistemas.practica2.Main.syso;
import static com.interconexionsistemas.practica2.Utils.getMacComoString;

public class PacketHelper {
    public static final byte[] MAC_BROADCAST = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff };

    /**
     * Constructor de Paquete
     * Carga en el campo datos el contenido byteDatos
     * @param bytesDatos el contenido a introducir en el paquete en el campo datos
     * @return
     */
    public static Packet buildPacket(byte[] bytesDatos) {

        byte[] mac_origen;
        mac_origen = ControladorTarjeta.getMacAdress();

        // Creamos el paquete
        Packet paquete = new Packet();
        // Creamos la cabecera Ethernet
        EthernetPacket EthP = new EthernetPacket();
        // Rellenamos los campos de la cabecera Ethernet
        EthP.frametype = (short) bytesDatos.length;
        // La direccion MAC de destino es la direccion MAC de broadcast
        EthP.dst_mac = MAC_BROADCAST;
        // La direccion MAC de origen es la direccion MAC de la tarjeta de red
        EthP.src_mac = mac_origen;

        paquete.datalink = EthP;
        paquete.data = bytesDatos;

        // Enviamos el paquete
        return paquete;
    }


    /**
     * Recibe un texto/datos y lo coloca en la posición requerida por la configuracion
     * Tambien establece el pin en la posicion deseada con el offset deseado
     * @param texto El texto o datos que se va a enviar
     * @param contador_tramas El numero de trama que se va a enviar
     * @return
     */
    public static byte[] formatear_trama(String texto, int contador_tramas) {
        byte[] bytesDatos_temp = new TramaDatos(texto,contador_tramas).toBytes();
        // Nos aseguramos que el posttrama_is es menor que el final de pin
        int posicion_ultimo_caracter_pin = configuracion.getPospin() + configuracion.getPin().getBytes().length;
        assert posicion_ultimo_caracter_pin <= configuracion.getPosTramaIs() : "El valor posttrama_is solapa con el pin. Introduce un postrama mayor";

        // Crear nueva array de bytes con el tamaño de la trama IS + el offset de postramaIS
        byte[] bytesTramaISMovidos = new byte[bytesDatos_temp.length + (configuracion.getPosTramaIs()-1)];
        System.arraycopy(bytesDatos_temp, 0, bytesTramaISMovidos, (configuracion.getPosTramaIs()-1), bytesDatos_temp.length);

        // crear nueva array con el tamaño del pin y el offset de pospin
        byte[] bytesPin = new byte[(configuracion.getPospin()-1) + configuracion.getPin().getBytes().length];
        // llenamos la nueva array con el pin
        System.arraycopy(configuracion.getPin().getBytes(), 0, bytesPin, 0, configuracion.getPin().getBytes().length);

        // Copiamos la array de bytes del pin en la array original donde está ya la trama_is con su offset establecido
        System.arraycopy(bytesPin, 0, bytesTramaISMovidos, 0, bytesPin.length);

        return bytesTramaISMovidos;
    }




    public static TramaDatos extraerTexto(byte[] bytesTrama) {
        // Obtener la longitud del pin y la trama IS
        int longitudPin = configuracion.getPospin() + configuracion.getPin().getBytes().length - 1;
        int longitudTramaIS = configuracion.getPosTramaIs() - 1;

        // Obtener la longitud del campo de datos
        int longitudDatos = bytesTrama[configuracion.getPosTramaIs() + 1] & 0xFF;

        // Crear una nueva array de bytes sin el pin y la trama IS
        byte[] bytesDatos = new byte[longitudDatos];
        System.arraycopy(bytesTrama, configuracion.getPosTramaIs() + 2, bytesDatos, 0, longitudDatos);
        //System.arraycopy(bytesTrama, longitudPin + longitudTramaIS, bytesDatos, 0, longitudDatos);

        // Convertir los bytes de los datos en una cadena de caracteres
        String texto = new String(bytesDatos);

        // Extraer el número de trama de la cadena de caracteres
        int numeroTrama = (int) bytesTrama[configuracion.getPosTramaIs()];

        // Devolver el texto y el número de trama como una cadena de caracteres
        return new TramaDatos(texto,numeroTrama);
    }

}
