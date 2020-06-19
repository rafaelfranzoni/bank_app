package br.com.bank_app.control;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {

    /**
     * Formato da saida e R$ 0.000,00.
     * @param valor
     * @return
     */
    public static String formatValor(String valor) {
        try {
            Locale localeBR = new Locale("pt","BR");
            NumberFormat dinheiro = NumberFormat.getCurrencyInstance(localeBR);
            Double numero = 0d;
            numero = Double.parseDouble(valor);
            return "R$ "+dinheiro.format(numero);
        } catch (Exception e) {
            return "R$ -----";
        }
    }

    /**
     * Formato da saida e 01.111222-4.
     * @param conta
     * @return
     */
    public static String formatConta(String conta) {
        return conta.substring(0, 2) + "." + conta.substring(2,8) + "-" + conta.substring(8,9);
    }

    /**
     * Formato da saida e MM/dd/yyyy.
     * @param data
     * @return
     */
    public static String getStrData(Date data) {
        if (data==null) return null;
        try {
            SimpleDateFormat sdf = (SimpleDateFormat)SimpleDateFormat.getInstance();
            sdf.applyPattern("dd/MM/yyyy");
            return sdf.format(data);
        } catch (Exception e) {
            return null;
        }
    }
}
