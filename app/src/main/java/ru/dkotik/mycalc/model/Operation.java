package ru.dkotik.mycalc.model;

public enum Operation {

    SUM("+"),
    SUB("-"),
    MULT("*"),
    DIV("/");

    Operation(String sign) {
        this.sign = sign;
    }

    private final String sign;

    public String getSign() {
        return sign;
    }
}
