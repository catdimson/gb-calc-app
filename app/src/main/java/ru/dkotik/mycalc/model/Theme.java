package ru.dkotik.mycalc.model;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import ru.dkotik.mycalc.R;

public enum Theme {

    ONE(R.style.Theme_CalcApp, R.string.theme_one, "one"),
    TWO(R.style.Theme_CalcApp_SecondTheme, R.string.theme_two, "two"),
    THREE(R.style.Theme_CalcApp_ThirdTheme, R.string.theme_three, "three");

    @StyleRes
    private final int theme;

    @StringRes
    private final int name;

    private String key;

    Theme(int theme, int name, String key) {
        this.theme = theme;
        this.name = name;
        this.key = key;
    }

    public int getTheme() {
        return theme;
    }

    public int getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

}
