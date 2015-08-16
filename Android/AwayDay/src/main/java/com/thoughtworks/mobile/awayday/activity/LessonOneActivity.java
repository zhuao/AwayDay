package com.thoughtworks.mobile.awayday.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.domain.Settings;


/**
 * More details refer to InitializerActivity
 */
public class LessonOneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_one);
        ((Button) findViewById(R.id.lesson_one_save_name_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.lesson_one_name)).getText().toString();
                Settings.getSettings().saveUserName(name);
                startActivity(new Intent(LessonOneActivity.this, MainActivity.class));
            }
        });
    }
}
