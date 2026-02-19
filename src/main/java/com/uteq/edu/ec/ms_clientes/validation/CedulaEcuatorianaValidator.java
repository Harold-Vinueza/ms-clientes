package com.uteq.edu.ec.ms_clientes.validation;

public class CedulaEcuatorianaValidator {

    public static boolean esCedulaValida(String cedula) {

        if (cedula == null || !cedula.matches("\\d{10}")) {
            return false;
        }

        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) {
            return false;
        }

        int tercerDigito = Character.getNumericValue(cedula.charAt(2));
        if (tercerDigito >= 6) {
            return false;
        }

        int suma = 0;

        for (int i = 0; i < 9; i++) {
            int num = Character.getNumericValue(cedula.charAt(i));

            if (i % 2 == 0) { // posiciones impares
                num *= 2;
                if (num > 9) {
                    num -= 9;
                }
            }

            suma += num;
        }

        int digitoVerificadorCalculado = (10 - (suma % 10)) % 10;
        int digitoVerificadorReal = Character.getNumericValue(cedula.charAt(9));

        return digitoVerificadorCalculado == digitoVerificadorReal;
    }
}
