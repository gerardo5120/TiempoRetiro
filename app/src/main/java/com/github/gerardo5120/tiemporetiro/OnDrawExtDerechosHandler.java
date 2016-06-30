package com.github.gerardo5120.tiemporetiro;

import com.github.gerardo5120.zpiechart.OnDrawChartParams;
import com.github.gerardo5120.zpiechart.OnDrawChartSimpleHandler;
import com.github.gerardo5120.zpiechart.OnDrawSliceParams;
import com.github.gerardo5120.zpiechart.OnDrawValueDialParams;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by cruzgerardoyanezteran on 27/5/16.
 */
public class OnDrawExtDerechosHandler extends OnDrawChartSimpleHandler {
    private GregorianCalendar birthDate;
    private GregorianCalendar now;
    private SimpleDateFormat dateFormat;

    private GregorianCalendar weeksAgo;

    public OnDrawExtDerechosHandler() {
        birthDate = new GregorianCalendar(1988, 02, 15);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        weeksAgo = new GregorianCalendar();

        // System.out.println(dateFormat.format(birthDate.getTime()));
    }

    @Override
    public boolean onDrawBegin(OnDrawChartParams params) {
        weeksAgo.set(birthDate.get(GregorianCalendar.YEAR),
                birthDate.get(GregorianCalendar.MONTH),
                birthDate.get(GregorianCalendar.DAY_OF_MONTH));

        return true;
    }

    @Override
    public boolean onDrawValueDial(OnDrawChartParams params) {
        OnDrawValueDialParams dialParams = (OnDrawValueDialParams) params;

        if (dialParams.getValue() == 47f)
            dialParams.setText("Tienes " + String.valueOf((int) dialParams.getValue()) + " años de edad");
        else if (dialParams.getValue() == 9.25f)
            dialParams.setText(String.valueOf(9) + " años p/retiro (ED)");

        return super.onDrawValueDial(params);
    }

    @Override
    public boolean onDrawSlice(OnDrawChartParams params) {
        OnDrawSliceParams sliceParams = (OnDrawSliceParams) params;

        if (sliceParams.getValue() == 32) {
            //sliceParams.setText("A los 32 años empezaste");
            return false;
        }
        else if (sliceParams.getValue() == 15) {
            sliceParams.setText("772 sem's cot's (15 años)");
        }
        else if (sliceParams.getValue() == 9.25) {
            sliceParams.setText(sliceParams.getValue() + " años");
        }

        else if (sliceParams.getValue() == 3.75) {
            sliceParams.setText("Ext. derechos: " + sliceParams.getValue() + " años");
        }
        else if (sliceParams.getValue() == 1.0f)
            sliceParams.setText("Hello ;)");


        return super.onDrawSlice(params);
    }
}
