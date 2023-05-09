package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama;


public class TramaAck extends TramaIS {
        public TramaAck(byte[] bytes) {
            super(bytes);
        }
        @Override
        public byte[] toBytes() {
            byte[] bytes = new byte[5];
            bytes[0] = caracter_syn.value(); // SYN
            bytes[1] = caracter_control.value(); // control
            bytes[2] = (byte) numero_trama;
            bytes[3] = (byte) direccion; // direccion
            bytes[4] = (byte) longitud; // longitud 34
            return bytes;
        }
}
