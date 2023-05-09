package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

import com.interconexionsistemas.practica2.Singletons.Configuracion;
import junit.framework.TestCase;

import java.util.concurrent.TimeUnit;

import static com.interconexionsistemas.practica2.Main.syso;

public class Tests extends TestCase {

        public void testEstablecerConexion() {
            Configuracion config = Configuracion.getInstance();
            config.setPin("Hola");

            EstablecerConexion.establecerConexion();

            syso.println("testEstablecerConexion");
            int numero_trama = 0;

            String texto = "Lorem ipsus donor desdad d asa d a";

            byte[] bytes = new byte[5 + texto.getBytes().length];
            bytes[0] = Caracteres.SYN.value(); // SYN
            bytes[1] = Caracteres.STX.value(); // control
            bytes[2] = (byte) numero_trama;
            bytes[3] = Caracteres.R.value(); // direccion
            bytes[4] = (byte) texto.getBytes().length; // longitud 34
            System.arraycopy(texto.getBytes(), 0, bytes, 5, texto.getBytes().length); // texto

            syso.println("Enviando trama IS");
            EnviarPaquetes.enviarTramaIs(bytes);
            syso.println("Esperando ACK");
            EsperarPaquetes.esperarPaquete(Caracteres.ACK);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {

            }
            syso.println("Enviando trama IS");
            EnviarPaquetes.enviarTramaIs(bytes);
            syso.println("Esperando ACK");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {

            }
            EsperarPaquetes.esperarPaquete(Caracteres.EOT);
            syso.println("Enviando trama IS");
            bytes = TramaHelper.setTipoTrama(bytes, Caracteres.EOT);
            EnviarPaquetes.enviarTramaIs(bytes);
            syso.println("Esperando Fin");
            //EsperarPaquetes.esperarPaquete(Caracteres.ACK);
        }

        public void testRecibirConexion(){
            Configuracion config = Configuracion.getInstance();
            config.setPin("Hola");
            byte[] bytesRecibidos = EsperarPaquetes.esperarPaquete(Caracteres.ENQ);

            byte[] bytes = new byte[4];
            bytes[0] = Caracteres.SYN.value(); // SYN
            bytes[1] = Caracteres.STX.value(); // control
            bytes[2] = (byte) TramaHelper.getNumTrama(bytesRecibidos);
            bytes[3] = Caracteres.R.value(); // direccion
            // Respondemos ok a la peticion de conexion
            EnviarPaquetes.enviarTramaIs(bytes);

            //Esperamos la informacion
            bytesRecibidos = EsperarPaquetes.esperarPaquete(Caracteres.STX);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {

            }
            // Enviar ACK
            bytes[1] = Caracteres.ACK.value(); // control
            bytes[2] = (byte) TramaHelper.getNumTrama(bytesRecibidos);

            bytes = TramaHelper.setNumTrama(bytes, TramaHelper.getNumTrama(bytesRecibidos)+1);
            EnviarPaquetes.enviarTramaIs(bytes);


            bytesRecibidos = EsperarPaquetes.esperarPaquete(Caracteres.STX);

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {

            }

            assertEquals(Caracteres.EOT, TramaHelper.getTipoTrama(bytesRecibidos));
            // Enviar ACK
            bytes[1] = Caracteres.ACK.value(); // control
            bytes[2] = (byte) TramaHelper.getNumTrama(bytesRecibidos);

            bytes = TramaHelper.setNumTrama(bytes, TramaHelper.getNumTrama(bytesRecibidos)+1);
            EnviarPaquetes.enviarTramaIs(bytes);




        }
}