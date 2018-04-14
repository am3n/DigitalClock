package com.amirhosein.hello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView txt;

    int f1=1, f2=0, f3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f3 = f1 + f2;
                txt.setText(String.valueOf(f3));
                f1 = f2;
                f2 = f3;
            }
        });
    }

    private void findViewsById() {
        btn = findViewById(R.id.btn);
        txt = findViewById(R.id.txt);
    }
}
