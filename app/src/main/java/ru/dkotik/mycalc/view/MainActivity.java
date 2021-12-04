package ru.dkotik.mycalc.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import ru.dkotik.mycalc.R;
import ru.dkotik.mycalc.model.CalculatorImpl;
import ru.dkotik.mycalc.model.Operation;
import ru.dkotik.mycalc.model.Theme;
import ru.dkotik.mycalc.presenter.CalculatorPresenter;
import ru.dkotik.mycalc.storage.ThemeStorage;
import ru.dkotik.mycalc.view.CalculatorView;

public class MainActivity extends AppCompatActivity implements CalculatorView {

    private TextView result;
    private ThemeStorage storage;
    private CalculatorPresenter presenter;
    private static final String EXPRESSION = "EXPRESSION";

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Theme theme = (Theme) result.getData().getSerializableExtra(SelectThemeActivity.EXTRA_THEME);

                        storage.saveTheme(theme);

                        recreate();
                    }
                }
    });

    // Здесь будет решение задания из 5 урока

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storage = new ThemeStorage(this);
        setTheme(storage.getSavedTheme().getTheme());

        setContentView(R.layout.activity_main);

        presenter = new CalculatorPresenter(this, new CalculatorImpl());
        result = findViewById(R.id.result);

        // для точки
        findViewById(R.id.key_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDotPressed();
            }
        });

        // для чисел
        Map<Integer, Integer> digits = new HashMap<>();
        digits.put(R.id.key_0, 0);
        digits.put(R.id.key_1, 1);
        digits.put(R.id.key_2, 2);
        digits.put(R.id.key_3, 3);
        digits.put(R.id.key_4, 4);
        digits.put(R.id.key_5, 5);
        digits.put(R.id.key_6, 6);
        digits.put(R.id.key_7, 7);
        digits.put(R.id.key_8, 8);
        digits.put(R.id.key_9, 9);

        View.OnClickListener digitClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDigitPressed(digits.get(v.getId()));
            }
        };

        findViewById(R.id.key_0).setOnClickListener(digitClickListener);
        findViewById(R.id.key_1).setOnClickListener(digitClickListener);
        findViewById(R.id.key_2).setOnClickListener(digitClickListener);
        findViewById(R.id.key_3).setOnClickListener(digitClickListener);
        findViewById(R.id.key_4).setOnClickListener(digitClickListener);
        findViewById(R.id.key_5).setOnClickListener(digitClickListener);
        findViewById(R.id.key_6).setOnClickListener(digitClickListener);
        findViewById(R.id.key_7).setOnClickListener(digitClickListener);
        findViewById(R.id.key_8).setOnClickListener(digitClickListener);
        findViewById(R.id.key_9).setOnClickListener(digitClickListener);

        // для операций
        HashMap<Integer, Operation> operations = new HashMap<>();
        operations.put(R.id.key_plus, Operation.SUM);
        operations.put(R.id.key_minus, Operation.SUB);
        operations.put(R.id.key_div, Operation.DIV);
        operations.put(R.id.key_mult, Operation.MULT);

        View.OnClickListener operationClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onOperationPressed(operations.get(v.getId()));
            }
        };

        findViewById(R.id.key_plus).setOnClickListener(operationClickListener);
        findViewById(R.id.key_minus).setOnClickListener(operationClickListener);
        findViewById(R.id.key_div).setOnClickListener(operationClickListener);
        findViewById(R.id.key_mult).setOnClickListener(operationClickListener);

        // для результата
        findViewById(R.id.key_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onResultPressed();
            }
        });

        // для очистки экрана
        findViewById(R.id.key_clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCleanPressed();
            }
        });

        // для удаления символа
        findViewById(R.id.key_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDeletePressed();
            }
        });

        if (savedInstanceState != null) {
            String ex = savedInstanceState.getString(EXPRESSION);
            ((TextView)findViewById(R.id.result)).setText(ex);
            presenter.refreshStates(ex);
        }

        findViewById(R.id.theme_choose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectThemeActivity.class);
                intent.putExtra(SelectThemeActivity.EXTRA_THEME, storage.getSavedTheme());

                launcher.launch(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        TextView res = findViewById(R.id.result);
        outState.putString(EXPRESSION, res.getText().toString().trim());
    }

    @Override
    public void showResult(String value) {
        result.setText(value);
    }
}