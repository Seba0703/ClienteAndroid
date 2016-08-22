package com.example.sebastian.copastock.Common;

public class CodeQRParser {

    public static void parse(String code, Integer sucCode, Integer numProd) {
       sucCode = Integer.parseInt(code.substring(0, 1));
        numProd = Integer.parseInt(code.substring(1, code.length()));

    }

}
