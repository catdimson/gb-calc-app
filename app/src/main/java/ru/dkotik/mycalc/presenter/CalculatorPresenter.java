package ru.dkotik.mycalc.presenter;

import android.widget.TextView;

import ru.dkotik.mycalc.R;
import ru.dkotik.mycalc.model.Calculator;
import ru.dkotik.mycalc.model.Operation;
import ru.dkotik.mycalc.view.CalculatorView;

public class CalculatorPresenter {

    private final CalculatorView view;
    private final Calculator calculator;
    private StringBuilder ex;

    public CalculatorPresenter(CalculatorView view, Calculator calculator) {
        this.view = view;
        this.calculator = calculator;
        this.ex = new StringBuilder("0");
    }

    public void onDotPressed() {
        if (!isLastOperator()) {
            ex.append(".");
        }
        view.showResult(ex.toString());
    }

    public void onDigitPressed(int digit) {
        if (isStart()) {
            ex = new StringBuilder(String.valueOf(digit));
        } else {
            ex.append(digit);
        }
        view.showResult(ex.toString());
    }

    public void onOperationPressed(Operation operation) {
        if (ex.length() == 1) {
            if (operation.getSign().equals("-")) {
                if (isStart()) {
                    ex = new StringBuilder("-");
                } else {
                    ex.append(operation.getSign());
                }
            } else if (operation.getSign().equals("+")) {
                if (ex.toString().equals("-")) {
                    ex = new StringBuilder("0");
                }
            } else {
                ex.append(operation.getSign());
            }
        } else {
            if (!isLastOperator()) {
                ex.append(operation.getSign());
            } else {
                ex.replace(ex.length() - 1, ex.length(), operation.getSign());
            }
        }
        view.showResult(ex.toString());
    }

    public void onResultPressed() {
        // добавить историю
        ex = new StringBuilder(calculator.calculate(ex.toString()));
        view.showResult(ex.toString());
    }

    public void onCleanPressed() {
        ex = new StringBuilder("0");
        view.showResult(ex.toString());
    }

    public void onDeletePressed() {
        if (ex.length() == 1) {
            ex = new StringBuilder("0");
        } else {
            ex.deleteCharAt(ex.length() - 1);
            if (ex.length() == 1 && ex.toString().equals("-")) {
                ex = new StringBuilder("0");
            }
        }
        view.showResult(ex.toString());
    }

    private boolean isStart() {
        return ex.toString().equals("0");
    }

    private boolean isLastOperator() {
        for (Operation o : Operation.values()) {
            if (String.valueOf(ex.charAt(ex.length() - 1)).equals(o.getSign())) {
                return true;
            }
        }
        return false;
    }
}
