package com.example.priyalbabel.currencyconverter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import org.json.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button button;
    Button cagain;
    EditText fromCurrency;
    TextView currency;
    Spinner other_currency;
    static Double resultVal;
    Spinner fromSpinner;
    ImageView flag_left;
    ImageView flag_right;
    ImageView a1;
    ImageView a2;
    ConstraintLayout cooo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        final TextView text = (TextView) findViewById(R.id.textView);
        resultVal = 0.0;
        text.setText(resultVal.toString());
        flag_left = (ImageView) findViewById(R.id.aud);
        flag_right = (ImageView) findViewById(R.id.aud_right);
        cooo = (ConstraintLayout) findViewById(R.id.activity_main);
        a1 = (ImageView) findViewById(R.id.imageView2);
        a2 = (ImageView) findViewById(R.id.imageView3);
        cagain = (Button) findViewById(R.id.cagain);

        //Items
        List<String> currencies = new ArrayList<String>();
        currencies.add("AUD");
        currencies.add("BGN");
        currencies.add("BRL");
        currencies.add("CAD");
        currencies.add("CNY");
        currencies.add("EUR");
        currencies.add("HKD");
        currencies.add("HUF");
        currencies.add("INR");
        currencies.add("JPY");
        currencies.add("NOK");
        currencies.add("NZD");
        currencies.add("PHP");
        currencies.add("SGD");
        currencies.add("USD");
        //END OF ITEMS

        other_currency = (Spinner) findViewById(R.id.toCurrency);
        fromSpinner = (Spinner) findViewById(R.id.fromSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromSpinner.setAdapter(adapter);
        fromSpinner.setOnItemSelectedListener(this);
        other_currency.setAdapter(adapter);
        other_currency.setOnItemSelectedListener(this);

        fromCurrency = (EditText) findViewById(R.id.fromCurrency);
        fromSpinner.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        other_currency.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    try {
                        String mainUrl = "https://api.exchangeratesapi.io/latest";
                        String updatedUrl = mainUrl + "?base=" + fromSpinner.getSelectedItem();
                        URL url = new URL(updatedUrl);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.connect();
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader inReader = new BufferedReader(new InputStreamReader(in));
                        String inputLine = "";
                        String fullStr = "";

                        while ((inputLine = inReader.readLine()) != null) {
                            fullStr += inputLine;
                        }

                        JSONObject jsonObj = new JSONObject(fullStr);
                        JSONObject result = jsonObj.getJSONObject("rates");
                        double moneyValue = Double.parseDouble(fromCurrency.getText().toString());
                        if (fromSpinner.getSelectedItem().toString().equals(other_currency.getSelectedItem().toString())) {
                            resultVal = moneyValue;
                        } else {
                            double rateValue = Double.parseDouble(result.getString((String) other_currency.getSelectedItem().toString()));
                            double resultValue = moneyValue * rateValue;
                            resultVal = resultValue;
                        }
                    } finally {
                        if (urlConnection != null)
                            urlConnection.disconnect();
                    }
                } catch (NumberFormatException e) {

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        button.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick(View v) {
                thread.start();
                try {
                    thread.join();
                    String result = String.format("%.5f", resultVal);
                    text.setText("The value is:\n" + result.toString());
                    text.setVisibility(View.VISIBLE);
                    cooo.getBackground().setAlpha(50);
                    flag_right.setVisibility(View.INVISIBLE);
                    flag_left.setVisibility(View.INVISIBLE);
                    a1.setVisibility(View.INVISIBLE);
                    a2.setVisibility(View.INVISIBLE);
                    fromSpinner.setVisibility(View.GONE);
                    other_currency.setVisibility(View.GONE);
                    fromCurrency.setVisibility(View.GONE);
                    cagain.setVisibility(View.VISIBLE);
                    button.setVisibility(View.GONE);

                    //text.animate().rotation(360f).alphaBy(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        cagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                cooo.getBackground().setAlpha(255);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView) parent.getChildAt(0)).setTextSize(25);

        switch (parent.getId()) {
            case R.id.fromSpinner:
                switch (position) {
                    case 0:
                        flag_left.setImageResource(R.drawable.aud);
                        break;
                    case 1:
                        flag_left.setImageResource(R.drawable.bgn);
                        break;
                    case 2:
                        flag_left.setImageResource(R.drawable.brl);
                        break;
                    case 3:
                        flag_left.setImageResource(R.drawable.cad);
                        break;
                    case 4:
                        flag_left.setImageResource(R.drawable.cny);
                        break;
                    case 5:
                        flag_left.setImageResource(R.drawable.eur);
                        break;
                    case 6:
                        flag_left.setImageResource(R.drawable.hkd);
                        break;
                    case 7:
                        flag_left.setImageResource(R.drawable.huf);
                        break;
                    case 8:
                        flag_left.setImageResource(R.drawable.inr);
                        break;
                    case 9:
                        flag_left.setImageResource(R.drawable.jpy);
                        break;
                    case 10:
                        flag_left.setImageResource(R.drawable.nok);
                        break;
                    case 11:
                        flag_left.setImageResource(R.drawable.nzd);
                        break;
                    case 12:
                        flag_left.setImageResource(R.drawable.php);
                        break;
                    case 13:
                        flag_left.setImageResource(R.drawable.sgd);
                        break;
                    case 14:
                        flag_left.setImageResource(R.drawable.usd);
                        break;

                }
                break;
            case R.id.toCurrency:
                switch (position) {
                    case 0:
                        flag_right.setImageResource(R.drawable.aud);
                        break;
                    case 1:
                        flag_right.setImageResource(R.drawable.bgn);
                        break;
                    case 2:
                        flag_right.setImageResource(R.drawable.brl);
                        break;
                    case 3:
                        flag_right.setImageResource(R.drawable.cad);
                        break;
                    case 4:
                        flag_right.setImageResource(R.drawable.cny);
                        break;
                    case 5:
                        flag_right.setImageResource(R.drawable.eur);
                        break;
                    case 6:
                        flag_right.setImageResource(R.drawable.hkd);
                        break;
                    case 7:
                        flag_right.setImageResource(R.drawable.huf);
                        break;
                    case 8:
                        flag_right.setImageResource(R.drawable.inr);
                        break;
                    case 9:
                        flag_right.setImageResource(R.drawable.jpy);
                        break;
                    case 10:
                        flag_right.setImageResource(R.drawable.nok);
                        break;
                    case 11:
                        flag_right.setImageResource(R.drawable.nzd);
                        break;
                    case 12:
                        flag_right.setImageResource(R.drawable.php);
                        break;
                    case 13:
                        flag_right.setImageResource(R.drawable.sgd);
                        break;
                    case 14:
                        flag_right.setImageResource(R.drawable.usd);
                        break;

                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
