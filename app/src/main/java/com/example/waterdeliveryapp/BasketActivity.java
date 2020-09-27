package com.example.waterdeliveryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BasketActivity extends AppCompatActivity {

    private View.OnClickListener onOrderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builderOrder = new AlertDialog.Builder(BasketActivity.this);
            builderOrder.setTitle("Confirm")
                    .setMessage("Order description")
                    .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog.Builder builderSuccess = new AlertDialog.Builder(
                                    BasketActivity.this);
                            builderSuccess.setTitle("Successful")
                                    .setMessage("Order description")
                                    .setPositiveButton("Ok", new DialogInterface
                                            .OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    })
                                    .show();
                        }
                    })
                    .setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .show();
        }
    };

    private View.OnClickListener onAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent startAddToOrderActivity = new Intent(BasketActivity.this,
                    AddToOrderActivity.class);
            startActivity(startAddToOrderActivity);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        Button orderButton = findViewById(R.id.btn_order);
        Button addButton = findViewById(R.id.btn_add);

        orderButton.setOnClickListener(onOrderClickListener);
        addButton.setOnClickListener(onAddClickListener);
    }
}