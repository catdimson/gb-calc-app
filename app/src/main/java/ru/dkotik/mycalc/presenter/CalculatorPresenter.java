package ru.dkotik.mycalc.presenter;

import ru.dkotik.mycalc.model.Calculator;
import ru.dkotik.mycalc.model.Operation;
import ru.dkotik.mycalc.view.CalculatorView;

public class CalculatorPresenter {

    private final CalculatorView view;
    private final Calculator calculator;
    private StringBuilder ex;
    private boolean dotInLineFlag = false;

    public CalculatorPresenter(CalculatorView view, Calculator calculator) {
        this.view = view;
        this.calculator = calculator;
        this.ex = new StringBuilder("0");
    }

    public void onDotPressed() {
        if (!isLastOperator() && !isLastDot() && !dotInLineFlag) {
            ex.append(".");
            dotInLineFlag = true;
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
        if (isLastDot()) {
            return;
        }
        if (ex.length() == 1) {
            if (operation.getSign().equals("-")) {
                if (isStart()) {
                    ex = new StringBuilder("-");
                }
            } else if (operation.getSign().equals("+")) {
                if (ex.toString().equals("-")) {
                    ex = new StringBuilder("0");
                } else {
                    ex.append(operation.getSign());
                    dotInLineFlag = false;
                }
            } else if (!isLastOperator()){
                ex.append(operation.getSign());
                dotInLineFlag = false;
            }
        } else {
            if (!isLastOperator()) {
                ex.append(operation.getSign());
                dotInLineFlag = false;
            } else {
                ex.replace(ex.length() - 1, ex.length(), operation.getSign());
            }
        }
        view.showResult(ex.toString());
    }

    public void onResultPressed() {
        // добавить историю, если хватит времени
        ex = new StringBuilder(calculator.calculate(ex.toString()));
        view.showResult(ex.toString());
    }

    public void onCleanPressed() {
        ex = new StringBuilder("0");
        dotInLineFlag = false;
        view.showResult(ex.toString());
    }

    public void onDeletePressed() {
        if (ex.length() == 1) {
            ex = new StringBuilder("0");
        } else {
            if (ex.charAt(ex.length() - 1) == '.') {
                dotInLineFlag = false;
            };
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

    private boolean isLastDot() {
        return String.valueOf(ex.charAt(ex.length() - 1)).equals(".");
    }
}
