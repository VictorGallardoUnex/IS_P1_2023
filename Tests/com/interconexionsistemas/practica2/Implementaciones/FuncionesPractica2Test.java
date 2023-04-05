package com.interconexionsistemas.practica2.Implementaciones;
import static com.interconexionsistemas.practica2.Implementaciones.FuncionesPractica2.recibir;
import static com.interconexionsistemas.practica2.Utils.getMacAsString;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;


public class FuncionesPractica2Test extends TestCase {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

        public void testGetMacAsString() throws Exception {
            byte[] mac = new byte[]{(byte) 0x00, (byte) 0x0c, (byte) 0x29, (byte) 0x3a, (byte) 0x6b, (byte) 0x7c};
            assertEquals("00:0C:29:3A:6B:7C", getMacAsString(mac));
        }

        public void testGetMacAsString2() throws Exception {
            byte[] mac = new byte[]{(byte) 0x00, (byte) 0x0c, (byte) 0x29, (byte) 0x3a, (byte) 0x6b, (byte) 0x7c};
            assertEquals("00:0C:29:3A:6B:7C", getMacAsString(mac));
        }

        public void testRecibir() throws Exception {
            recibir("todo");
        }


}