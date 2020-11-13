package com.example.waterdeliveryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {
    public static ArrayList<SelectedProduct> selectedProducts = new ArrayList<>();

    private TextView hint;
    private LinearLayout allProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        hint = findViewById(R.id.empty_basket_id);
        allProducts = findViewById(R.id.selectionFragmentContainer);

        if (selectedProducts != null && !selectedProducts.isEmpty()) {
            for (SelectedProduct i : selectedProducts) {
                if (savedInstanceState == null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.selectionFragmentContainer, SelectedProductFragment.newInstance(i))
                            .commit();
                }
            }
        } else {
            hint.setVisibility(View.VISIBLE);
        }
    }

    public void onAddClick(View view) {
        startActivity(new Intent(BasketActivity.this,
                AddToOrderActivity.class));
    }

    public void onOrderClick(View view) {
        if (!selectedProducts.isEmpty()) {
            final JsonArray jsonObj = new Gson().toJsonTree(selectedProducts).getAsJsonArray();
            AlertDialog.Builder builderOrder = new AlertDialog.Builder(BasketActivity.this);
            builderOrder
                    .setTitle("Confirm")
                    .setMessage(jsonObj.toString())
                    .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog.Builder builderSuccess = new AlertDialog.Builder(
                                    BasketActivity.this);
                            builderSuccess.setTitle("Successful")
                                    .setMessage(jsonObj.toString())
                                    .setPositiveButton("Ok", new DialogInterface
                                            .OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            selectedProducts.clear();
                                            allProducts.removeAllViews();
                                            hint.setVisibility(View.VISIBLE);
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
        } else {
            Toast.makeText(BasketActivity.this, "You picked nothing ", Toast.LENGTH_SHORT).show();
        }
    }
}
