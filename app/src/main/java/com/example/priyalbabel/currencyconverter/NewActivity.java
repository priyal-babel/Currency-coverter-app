package com.example.priyalbabel.currencyconverter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NewActivity extends AppCompatActivity {
    ImageView appName;
    ImageView cash;
    private Button click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        appName = (ImageView) findViewById(R.id.appname);
        cash = (ImageView) findViewById(R.id.cash);
        click = (Button) findViewById(R.id.start);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
