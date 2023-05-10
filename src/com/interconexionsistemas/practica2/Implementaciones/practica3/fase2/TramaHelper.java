package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

import static com.interconexionsistemas.practica2.Main.syso;

public class TramaHelper {
    public static String getTexto(byte[] data) {
        if (data.length>5) {
            int longitud = data[4];
            return new String(data, 5, longitud);
        }
        return "";
    }
    public static int getNumTrama(byte[] data) {
            return data[2];
    }
    public static byte[] setNumTrama(byte[] data, int num_trama) {
            data[2] = (byte) num_trama;
            return data;
    }
    public static byte[] setTipoTrama(byte[] data, Caracteres tipo_trama) {
            data[1] = tipo_trama.value();
            return data;
    }
    public static Caracteres getTipoTrama(byte[] data) {
        return Caracteres.fromByte(data[1]);
    }
    public static byte getBCE(byte[] data){
        //Esperamos la información
        int longitud = 4;
        byte bce;
        if (data[1] != Caracteres.STX.value()){
            return data[4];
        }
        longitud = longitud + data[4];

        byte[] tramais = new byte[4+longitud];

        if (data[1] == Caracteres.EOT.value()) {
            syso.println("sda");
        }

        return data[1+longitud];
    }

}
