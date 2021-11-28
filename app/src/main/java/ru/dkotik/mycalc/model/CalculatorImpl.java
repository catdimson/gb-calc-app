package ru.dkotik.mycalc.model;

public class CalculatorImpl implements Calculator {

    @Override
    public double performOperation(double argOne, double argTwo, Operation operation) {
        switch (operation) {
            case SUM:
                return argOne + argTwo;
            case SUB:
                return argOne - argTwo;
            case DIV:
                return argOne / argTwo;
            case MULT:
                return argOne * argTwo;
            default:
                throw new ArithmeticException("Ошибка вычисления");
        }
    }

    @Override
    public String calculate(String expression) {
        return null;
    }
}
