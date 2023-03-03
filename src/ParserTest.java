import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class ParserTest {

    @Test
    void procesar_linea_añade_configuraciones() {
        ArrayList<Instruccion> instrucciones = new ArrayList<Instruccion>();
        Configuracion config = new Configuracion();
        Parser parseador = new Parser(instrucciones,config);
        String linea = "@ salidapantalla ON";
        parseador.procesar_linea(linea);
        assertTrue(config.salida_pantalla);

        linea = "@ reescribirficherosalida ON";
        parseador.procesar_linea(linea);
        assertTrue(config.reescribir_fichero_salida);
    }
    @Test
    void procesar_linea_añade_instrucciones() {
        ArrayList<Instruccion> instrucciones = new ArrayList<Instruccion>();
        Configuracion config = new Configuracion();
        Parser parseador = new Parser(instrucciones,config);

        String linea = "& infotarjeta 0";
        parseador.procesar_linea(linea);
        assertEquals("infotarjeta", instrucciones.get(0).clave);
        assertEquals("0", instrucciones.get(0).valor);
        linea = "& seleccionatarjeta 3";
        parseador.procesar_linea(linea);
        assertEquals(2, instrucciones.size());
        assertEquals("seleccionatarjeta", instrucciones.get(1).clave);
        assertEquals("3", instrucciones.get(1).valor);

    }
    @Test
    void procesar_argumentos() {
        ArrayList<Instruccion> instrucciones = new ArrayList<Instruccion>();
        Configuracion config = new Configuracion();
        Parser parseador = new Parser(instrucciones,config);
        String[] prueba = {"-fe","archivo_in", "-fs","archivo_salida"};
        parseador.leer_argumentos(config,prueba);

        assertEquals(config.getFichero_entrada(),"archivo_in");
        assertEquals(config.getFichero_salida(),"archivo_salida");
    }

}