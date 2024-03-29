package com.interconexionsistemas.practica2.Singletons.Controladores;

import com.interconexionsistemas.practica2.Modelos.Errores.ErrorJpcap;
import com.interconexionsistemas.practica2.Modelos.Errores.ErrorTarjetaNoExiste;
import jpcap.*;

import java.io.IOException;

/**
 * Clase singleton (Solo existe una instancia en el codigo). Incluye la interfaz de las Tarjetas y las validaciones de los módulos
 * */
public class ControladorTarjeta {
    static ControladorSalida syso = ControladorSalida.getInstance();

    final NetworkInterface[] tarjetas;
     int tarjeta_seleccionada = 0;

    public jpcap.NetworkInterface getTarjeta() throws ErrorTarjetaNoExiste {
        if (is_not_init()) {
            return null;
        }
        if (tarjeta_seleccionada > tarjetas.length) {
            throw new ErrorTarjetaNoExiste("No existe esa tarjeta");

        }
        return tarjetas[tarjeta_seleccionada];
    }
    public jpcap.NetworkInterface getTarjeta(int valor) throws ErrorTarjetaNoExiste {
        if (is_not_init()) {
            throw new ErrorTarjetaNoExiste("No existe esa tarjeta");
        }
        if (valor > tarjetas.length) {
            throw new ErrorTarjetaNoExiste("No existe esa tarjeta");
        }
        return tarjetas[valor];
    }
    public void setTarjeta_seleccionada(int tarjeta_seleccionada) throws ErrorTarjetaNoExiste {
        if (tarjeta_seleccionada > tarjetas.length) {
            syso.println("No existe la tarjeta seleccionada: " + tarjeta_seleccionada + " - Estableciendo tarjeta por defecto");
//            throw new ErrorTarjetaNoExiste("No existe esa tarjeta");
            return;
        }
        this.tarjeta_seleccionada = tarjeta_seleccionada;
    }

    public int getTarjeta_seleccionada() {
        return tarjeta_seleccionada;
    }

    private boolean is_not_init() {
        return tarjetas == null;
    }


    // Metodos singleton
    private static ControladorTarjeta instance;

    protected ControladorTarjeta() {
        tarjetas = JpcapCaptor.getDeviceList();
        // Prevenimos que se cree una instancia desde fuera de la clase
    }

    public static ControladorTarjeta getInstance() throws ErrorJpcap {
        if (instance == null) {
            syso = ControladorSalida.getInstance();
            instance = new ControladorTarjeta();
            if (instance.tarjetas == null) {
                syso.println("Error de packet driver. No se han encontrado tarjetas");
                throw new ErrorJpcap("sdas");
            }
        }
        return instance;
    }

    JpcapCaptor receptor;
    JpcapSender emisor;

    public static JpcapCaptor getReceptor() {
        ControladorTarjeta ct = null;
        try {
            ct = ControladorTarjeta.getInstance();
        } catch (ErrorJpcap e) {
            throw new RuntimeException(e);
        }

        if (ct.receptor == null) {
            try {
                ct.receptor = JpcapCaptor.openDevice(ct.getTarjeta(), 2000, false, 20);
            } catch (ErrorTarjetaNoExiste | IOException errorTarjetaNoExiste) {
                errorTarjetaNoExiste.printStackTrace();
            }
        }
        return ct.receptor;
    }

    public static JpcapSender getEmisor() {
        ControladorTarjeta ct = null;
        try {
            ct = ControladorTarjeta.getInstance();
        } catch (ErrorJpcap e) {
            throw new RuntimeException(e);
        }

        if (ct.emisor == null) {
            try {
                ct.emisor = JpcapSender.openDevice(ct.getTarjeta());
            } catch (ErrorTarjetaNoExiste | IOException errorTarjetaNoExiste) {
                errorTarjetaNoExiste.printStackTrace();
            }
        }
        return ct.emisor;
    }

    public static byte[] getMacAdress() {

        try {
            return ControladorTarjeta.getInstance().getTarjeta().mac_address;
        } catch (ErrorTarjetaNoExiste e) {
            throw new RuntimeException(e);
        } catch (ErrorJpcap e) {
            throw new RuntimeException("Los drivers del controlador de tarjeta JPcap no estan instalados o initializados");
        }
    }
}

