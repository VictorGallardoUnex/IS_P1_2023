package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.FuncP3F1;
import com.interconexionsistemas.practica2.Singletons.Configuracion;
import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaAck;
import junit.framework.TestCase;

import java.util.Arrays;

public class FuncP3F1Test extends TestCase {

    public void testFormatear_trama() {
        Configuracion config = Configuracion.getInstance();
        config.setPosTramaIs(10);
        config.setPin("pin");

        byte[] trama = PacketHelper.formatear_trama("hola",0);
        System.out.println(Arrays.toString(trama));
    }

    public void test_trama_to_ack() {
        Configuracion config = Configuracion.getInstance();
        config.setPosTramaIs(10);
        config.setPin("pin");

        byte[] trama = PacketHelper.formatear_trama("hola 4 t45 y5h56",0);
        System.out.println(Arrays.toString(trama));
        System.out.println(new String(trama));
        // tenemos la trama leida
        TramaAck ack = TramaAck.fromBytes(trama);
        byte[] ack_bytes = ack.toBytes();
        System.out.println(Arrays.toString(ack_bytes));
        System.out.println(new String(ack_bytes));
        System.out.println("Numero trama ack: " + ack.getNumero_trama());

        trama = PacketHelper.formatear_trama("hola sdads",32);
        ack = TramaAck.fromBytes(trama);
        System.out.println("Numero trama ack: " + ack.getNumero_trama());
    }


    public void testProcesar() {
        FuncP3F1.procesar();
    }

    public void testLeer() {
        FuncP3F1.leer();
    }
}