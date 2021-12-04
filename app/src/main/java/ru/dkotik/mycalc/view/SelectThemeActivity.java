package ru.dkotik.mycalc.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.dkotik.mycalc.R;
import ru.dkotik.mycalc.model.Theme;

public class SelectThemeActivity extends AppCompatActivity {

    public static final String EXTRA_THEME = "EXTRA_THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_theme);

        Intent launchIntent = getIntent();

        Theme selectedTheme = (Theme) launchIntent.getSerializableExtra(EXTRA_THEME);

        LinearLayout themeContainer = findViewById(R.id.themes);

        for (Theme theme: Theme.values()) {
            View view = LayoutInflater.from(this).inflate(R.layout.theme_item, themeContainer, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent data = new Intent();
                    data.putExtra(EXTRA_THEME, theme);
                    setResult(Activity.RESULT_OK, data);
                    finish();
                }
            });

            TextView viewThemeItem = view.findViewById(R.id.theme_item);
            viewThemeItem.setText(theme.getName());

            ImageView check = view.findViewById(R.id.check);
            if (theme.equals(selectedTheme)) {
                check.setVisibility(View.VISIBLE);
            } else {
                check.setVisibility(View.GONE);
            }

            themeContainer.addView(view);
        }
    }
}
