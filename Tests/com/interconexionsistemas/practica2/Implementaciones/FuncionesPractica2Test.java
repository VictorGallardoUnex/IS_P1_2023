package com.interconexionsistemas.practica2.Implementaciones;
import static com.interconexionsistemas.practica2.Implementaciones.FuncionesPractica2.recibir;
import static com.interconexionsistemas.practica2.Utils.getMacAsString;
import junit.framework.TestCase;

public class FuncionesPractica2Test extends TestCase {

        public void testGetMacAsString() throws Exception {
            byte[] mac = new byte[]{(byte) 0x00, (byte) 0x0c, (byte) 0x29, (byte) 0x3a, (byte) 0x6b, (byte) 0x7c};
            assertEquals("00:0c:29:3a:6b:7c", getMacAsString(mac));
        }

        public void testGetMacAsString2() throws Exception {
            byte[] mac = new byte[]{(byte) 0x00, (byte) 0x0c, (byte) 0x29, (byte) 0x3a, (byte) 0x6b, (byte) 0x7c};
            assertEquals("00:0c:29:3a:6b:7c", getMacAsString(mac));
        }

        public void testRecibir() throws Exception {
            recibir("todo");
        }


}