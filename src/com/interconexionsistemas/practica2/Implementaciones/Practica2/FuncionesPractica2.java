package com.interconexionsistemas.practica2.Implementaciones.Practica2;

import com.interconexionsistemas.practica2.Modelos.Errores.ErrorTarjetaNoExiste;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.interconexionsistemas.practica2.Main.*;
import static com.interconexionsistemas.practica2.Implementaciones.Practica2.UtilsP2.*;

public class FuncionesPractica2 {

    // Practica 2 IS 2023
    /**
     * Recibe tramas de la red y las muestra por pantalla
     * @param value puede ser "todo", "longitud" o "tipo"
     * @throws IOException
     */
    static public void recibirTramas(String value) throws IOException {
        if (!(value.equals("todo") || value.equals("longitud") || value.equals("tipo"))) {
            syso.println("Parametro incorrecto para 'recibirTramas tramas'");
            return;
        }

        JpcapCaptor captor;
        captor = controladorTarjeta.getReceptor();
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader entradaTeclado = new BufferedReader(reader);

        boolean fin = false;
        do {
            Packet paquete = captor.getPacket();
            if(paquete==null) continue;
            boolean is_longitud_or_tipo = castByteToShort(paquete.header[12], paquete.header[13]) < 1500;
            if(!(is_longitud_or_tipo && value.equals("longitud") || !is_longitud_or_tipo && value.equals("tipo") || value.equals("todo"))) {
                continue;
            }
            syso.println("Es un paquete de tipo " + (is_longitud_or_tipo ? "longitud" : "tipo"));

            mostrarPaquete(paquete);
            if (entradaTeclado.readLine().equals("f")) {
                syso.println("Fin de la captura");
                fin = true;
            }
        } while (!fin);
    }

    static public void repetirEnvio(int numero) {
        if (numero < 1) {
            syso.println("El numero de veces a repetir el envio debe ser mayor que 0");
            return;
        }
        String texto_original = configuracion.getMensaje_a_enviar();
        String texto_enviado = "";
        for (int i = 0; i < numero; i++) {
            texto_enviado = texto_original + " " + (i + 1);
            enviarTexto(texto_enviado);
        }
    }
    private static void enviarTexto(String texto) {
        byte[] bytesDatos = texto.getBytes();
        byte[] mac_origen;
        try {
             mac_origen = controladorTarjeta.getTarjeta().mac_address;
        } catch (ErrorTarjetaNoExiste e) {
            // No hacemos nada, es imposible. si no existen tarjetas, el programa sale antes con codigo 1
            return;
        }
        Packet paquete = new Packet();

        EthernetPacket EthP = new EthernetPacket();

        EthP.frametype = (short) bytesDatos.length;

        EthP.dst_mac = MAC_BROADCAST;
        EthP.src_mac = mac_origen;

        paquete.datalink = EthP;
        paquete.data = bytesDatos;


        JpcapSender emisor;
        emisor = controladorTarjeta.getEmisor();
        emisor.sendPacket(paquete);
        syso.println("Paquete enviado correctamente a la direccion MAC " + MAC_BROADCAST + "\n Informacion del paquete: \n" + mostrarCampoDatos(paquete.data));

    }

//        break;
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
//                            el fichero, serán válidas todas las tramas, dado que es como recibirTramas
//                            sin pin
//                             */
//    }
}
