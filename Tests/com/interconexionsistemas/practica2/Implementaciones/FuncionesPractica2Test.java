package com.interconexionsistemas.practica2.Implementaciones;
import static com.interconexionsistemas.practica2.Implementaciones.Practica2.FuncionesPractica2.recibirTramas;
import static com.interconexionsistemas.practica2.Utils.getMacComoString;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;


public class FuncionesPractica2Test extends TestCase {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

        public void testGetMacAsString() {
            byte[] mac = new byte[]{(byte) 0x00, (byte) 0x0c, (byte) 0x29, (byte) 0x3a, (byte) 0x6b, (byte) 0x7c};
            assertEquals("00:0C:29:3A:6B:7C", getMacComoString(mac));
        }

        public void testGetMacAsString2() {
            byte[] mac = new byte[]{(byte) 0x00, (byte) 0x0c, (byte) 0x29, (byte) 0x3a, (byte) 0x6b, (byte) 0x7c};
            assertEquals("00:0C:29:3A:6B:7C", getMacComoString(mac));
        }

        public void testRecibir() throws Exception {
            recibirTramas("todo");
        }


}