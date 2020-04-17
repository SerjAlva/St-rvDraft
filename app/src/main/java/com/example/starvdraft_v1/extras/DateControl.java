package com.example.starvdraft_v1.extras;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateControl {

    public static String getActualDate(){
        /*Con Local.getDefault tomamos la zona horaria de el celular donde se instale la aplicacion*/
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }

}
