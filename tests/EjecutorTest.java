import modelos.Bandera;
import modelos.Comando;
import modelos.Instruccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EjecutorTest {
    Configuracion config;
    Parser parseador;
    Ejecutor ejecutor;
    ControladorSalida syso;
    @BeforeEach
    void setUp() throws ErrorJpcap {
        config = new Configuracion();
        syso = ControladorSalida.getInstance(config);
        ArrayList<Instruccion> instrucciones = new ArrayList<Instruccion>();
        ControladorTarjeta.getInstance(config);
        ejecutor = new Ejecutor(config,syso);
        parseador = new Parser(instrucciones,config,syso);

    }

    @Test
    void test_procesar_configuracion() {
        syso = ControladorSalida.getInstance(config);
        ejecutor.procesar_configuracion(new Bandera("salidapantalla", "on"));
        assertTrue(config.salida_pantalla);
    }
    @Test
    void test_setInstruccion(){
        syso = ControladorSalida.getInstance(config);
        ejecutor.procesar_instrucciones(new ArrayList<Instruccion>(Arrays.asList(new Comando("ficheRoSalidA","fich_salida"),new Comando("sEleCCionaTarJeta","3"))));
        assertEquals("fich_salida", config.getFichero_salida());

        }
}
