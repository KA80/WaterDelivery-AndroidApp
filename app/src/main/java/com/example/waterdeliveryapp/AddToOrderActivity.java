package com.example.waterdeliveryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddToOrderActivity extends AppCompatActivity {

    String[] array = {"one", "two"};
    private Button btnPlus;
    private Button btnMinus;
    private Button btnConfirm;

    private Spinner spinnerGood;

    private EditText editCounter;
    private ImageView imgGood;

    private AdapterView.OnItemSelectedListener OnGoodSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            String[] goods = getResources().getStringArray(R.array.goods);
            Toast.makeText(AddToOrderActivity.this, goods[pos], Toast.LENGTH_SHORT).show();
            switch (goods[pos]) {
                case "Juice":
                    imgGood.setImageResource(R.drawable.juice);
                    break;
                case "Cola":
                    imgGood.setImageResource(R.drawable.cola);
                    break;
                default:
                    imgGood.setImageResource(R.drawable.water);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_order);

        btnPlus = findViewById(R.id.btn_plus_counter);
        btnMinus = findViewById(R.id.btn_minus_counter);
        btnConfirm = findViewById(R.id.btn_confirm_choice);

        spinnerGood = findViewById(R.id.select_good);
        editCounter = findViewById(R.id.counter);
        imgGood = findViewById(R.id.img_good);

        spinnerGood.setOnItemSelectedListener(OnGoodSelectedListener);
    }
}