package ru.dkotik.mycalc.model;

public interface Calculator {

    double performOperation(double argOne, double argTwo, Operation operation);

    String calculate(String expression);
}
