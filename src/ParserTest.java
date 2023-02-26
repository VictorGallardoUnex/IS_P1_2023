import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class ParserTest {

    @Test
    void procesar_linea_añade_configuraciones() {
        ArrayList<Instruccion> instrucciones = new ArrayList<Instruccion>();
        Configuracion config = new Configuracion();
        Parser parseador = new Parser("",instrucciones,config);
        String linea = "@ salidapantalla ON";
        parseador.procesar_linea(linea);
        assertTrue(config.salida_pantalla);

        linea = "@ reescribirficherosalida ON";
        parseador.procesar_linea(linea);
        assertTrue(config.reescribir_fichero_salida);
    }
//    @Test
    void procesar_linea_añade_instrucciones() {
        ArrayList<Instruccion> instrucciones = new ArrayList<Instruccion>();
        Configuracion config = new Configuracion();
        Parser parseador = new Parser("",instrucciones,config);
        String linea = "@ salidapantalla ON";
        parseador.procesar_linea(linea);
        assertTrue(config.salida_pantalla);

        linea = "& seleccionatarjeta 3";
        parseador.procesar_linea(linea);
        assertTrue(config.reescribir_fichero_salida);
    }

}