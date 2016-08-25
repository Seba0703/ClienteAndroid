package com.example.sebastian.copastock.Common;

public class Consts {

    //Server paths
    public static final String LOGIN = "/login";
    public final static String MAT_MATERIALS_ID = "/mat_matID";
    public static final String PROD_STATUS = "/prod_status";
    public static final String MAT_SUB = "/mat_sub";
    public static final String FURNITURE_HAS = "/tieneMueble";
    public static final String FURNITURE = "/muebles";
    public static final String FURNITURE_UPDATE = "/updateMueble";
    public static final String FURNITURE_NOT_UPDATE = "/notUpdateMueble";

    //Request methos
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";


    // Internet Client keys
    public static final String JSON_OUT = "json";
    public static final String SUCESS = "exito";

    //Broadcast keys
    public static final String LOGIN_call = "login";
    public static final String PROD_NAME = "nameProd";
    public static final String PROD_STATUS_call = "statusProd";
    public static final String PROD_EXTRACT = "extractProd";
    public static final String FURNITURE_HAS_call = "tieneMueble";
    public static final String FURNITURE_ADD = "agregaMueble";
    public static final String FURNITURE_UPDATE_call = "updateMueble";
    public static final String FURNITURE_NOT_UPDATE_call = "notUpdateMueble";

    //JSON keys
    public static final String USER = "user_id";
    public static final String PASS = "pass";
    public final static String RESULT = "resultado";
    public final static String MATERIALS_ID = "insumo_id";
    public final static String QUANTITY = "cantidad";
    public final static String DESTINY = "destino";
    public final static String SUCURSAL1 = "SUCURSAL1";
    public final static String SUCURSAL2 = "SUCURSAL2";
    public final static String SUCURSAL3 = "SUCURSAL3";
    public final static String NAZCA = "NAZCA";
    public final static String EVA = "EVA";
    public final static String CELINA = "CELINA";
    public final static String N_SUC = "sucN";
    public final static String N_MEMBER = "memberN";
    public final static String STATE = "estado";
    public final static int STATE_GOOD = 3;
    public final static int STATE_REGULAR = 2;
    public final static int STATE_BAD = 1;
    public final static int STATE_OUT = 0;
    public final static String BUY_DATE = "fechaCOmpra";
    public final static String FINAL_PRICE = "precioFinal";     //precio + IVA
    public final static String HAS_FURNITURE = "tieneMueble";
    public static final String LAST_UPDATE = "lastUpdate";

    public static int mapStateStringInt(String state) {
        if (state.equals("Bueno")) {
            return STATE_GOOD;
        } else if (state.equals("Malo")) {
            return STATE_BAD;
        } else if (state.equals("Regular")) {
            return STATE_REGULAR;
        } else {
            return STATE_OUT;
        }
    }

    //activity for result request code
    public final static int INFO_CODE = 12;

    //Spinner
    public final static String NOTHING = " ";

    public static String getMapNameSuc(String name) {
        switch (name) {
            case NAZCA:
                return SUCURSAL1;
            case EVA:
                return SUCURSAL2;
            default:
                return SUCURSAL3;
        }
    }

    //Bundle keys
    public final static String ACEPT = "aceptar";
    public final static String CANCEL = "cancelar";
    public final static String MSSG = "mssg";

    // result colores
    public final static int RED = 0;
    public final static int YELLOW = 1;
    public final static int WHITE = 2;

    //sucursal ID
    public final static int NAZCA_ID = 1;
    public final static int EVA_ID = 2;
    public final static int CELINA_ID = 3;

    public static String nameMatcher(int id) {

        switch (id) {
            case EVA_ID:
                return EVA;
            case CELINA_ID:
                return CELINA;
            case NAZCA_ID:
                return NAZCA;
            default:
                return "DESCONOCIDO";
        }
    }

    public static String parseDate(int year, int month, int day) {
        return year + parseWithZero(month) + parseWithZero(day);
    }

    private static String parseWithZero(int month) {
        if (month < 10) {
            return "0" + month;
        } else {
            return String.valueOf(month);
        }
    }
}
