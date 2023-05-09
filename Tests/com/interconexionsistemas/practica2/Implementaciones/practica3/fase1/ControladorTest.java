package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.*;
import com.interconexionsistemas.practica2.Singletons.Configuracion;
import jpcap.packet.Packet;
import junit.framework.TestCase;

import java.util.concurrent.TimeUnit;

import static com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Receptor.*;
import static com.interconexionsistemas.practica2.Main.syso;

public class ControladorTest extends TestCase {

    public void testEstablecerConexion() {
        Configuracion config = Configuracion.getInstance();
        config.setPin("Hola");
        Controlador.establecerConexion(0);
    }
    public void testRecibirConexion() {
        Configuracion config = Configuracion.getInstance();
        config.setPin("Hola");
        TramaIS trama = recibirTrama(TramaISENQ.class);
        syso.println(trama.toString());

    }
    public void testFinalizarConexion() {
    }
    public void testComunicacion() {
        Configuracion config = Configuracion.getInstance();
        config.setPin("Hola");
        TramaIS trama = recibirTrama(TramaISENQ.class);
        syso.println(trama.toString());
//        do {
//            trama = recibirTrama(TramaISENQ.class);
//        }
    }


    public void testComunicacionReceptor() {
        Configuracion config = Configuracion.getInstance();
        config.setPin("Hola");
        TramaIS trama = recibirTrama(TramaISENQ.class);
        syso.println("[TRAZA] trama recibida: "+trama.getNumero_trama() +"tipo: " + trama.getCaracter_control().name());
        trama.setNumero_trama(trama.getNumero_trama()+1);
        TramaIS trama_respuesta = new TramaAck(trama.toBytes());
        trama_respuesta.setCaracter_control(Caracteres.ACK);

        Packet paquete = PacketHelper.buildPacket(PacketHelper.formatear_trama(trama_respuesta.toBytes(),trama.getNumero_trama()));
        syso.println("Enviando ACK");
        Emisor.enviarPaquete(paquete);
        syso.println("[TRAZA] ACK enviado. Conexion iniciada");
        syso.println("[TRAZA] procediendo a recibir paquetes");
        do {
            trama = recibirTrama(TramaISSTX.class);
            syso.println("[TRAZA] trama con contador " + trama.getNumero_trama() + " con caracter de control " + trama.getCaracter_control() + " ha sido reecibida");
            syso.println("[TEXTO] '" + trama.getTexto() + "'");
            trama_respuesta.setNumero_trama(trama.getNumero_trama());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {

            }
            syso.println("[TRAZA] enviando ACK: ' "+trama_respuesta.getCaracter_control().name() +"'");
            Emisor.enviarPaquete(PacketHelper.buildPacket(trama_respuesta.toBytes()));
            syso.println("Esperando siguiente paquete...\n\n\n");
        } while (trama.getClass() != TramaISEOT.class);
        Emisor.enviarPaquete(PacketHelper.buildPacket(new TramaISEOT(trama.getNumero_trama()).toBytes()));
    }
    public void testProcesarEnvio() {
        Configuracion config = Configuracion.getInstance();
        config.setPin("Hola");
        int contadorTrama = Controlador.establecerConexion(0);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {

        }
        syso.println("[TRACE] CONEXION ESTABLECIDA - Procedemos a enviar datos");
        Controlador.enviarDatos(contadorTrama);
        Controlador.finalizarConexion(contadorTrama);
//        syso.println(trama.toString());
//        do {
//            trama = recibirTrama(null);
//        } while (trama.getClass() != TramaISEOT.class);
//        Emisor.enviarPaquete(PacketHelper.buildPacket(new TramaISEOT(trama.getNumero_trama()).toBytes()));
    }
}