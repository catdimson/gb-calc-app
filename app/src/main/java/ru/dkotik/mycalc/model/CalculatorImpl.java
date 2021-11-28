package ru.dkotik.mycalc.model;

import android.annotation.SuppressLint;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class CalculatorImpl implements Calculator {

    @Override
    public String calculate(String expression) {
        return runEngineCalc(expression);
    }

    @SuppressLint("DefaultLocale")
    private String runEngineCalc(String ex) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        String result;
        try {
            Double jsResult = (Double) engine.eval(ex);
            result = String.valueOf(new BigDecimal(jsResult).setScale(5, RoundingMode.HALF_EVEN).doubleValue());
        } catch (ScriptException e) {
            e.printStackTrace();
            result = "Ошибка вычисления";
        }
        return result;
    }
}
