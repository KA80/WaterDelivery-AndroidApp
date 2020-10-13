package com.example.waterdeliveryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {
    public static String LIST_PRODUCT_KEY = "LIST_PRODUCT_KEY";

    public static ArrayList<SelectedProduct> selectedProducts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            selectedProducts = (ArrayList<SelectedProduct>) bundle.getSerializable(LIST_PRODUCT_KEY);
        }

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
            final TextView hint = findViewById(R.id.empty_basket_id);
            hint.setVisibility(View.VISIBLE);
        }
    }

    public void onAddClick(View view) {
        Intent startAddToOrderActivity = new Intent(BasketActivity.this,
                AddToOrderActivity.class);
        startAddToOrderActivity.putExtra(AddToOrderActivity.LIST_PRODUCT_KEY, selectedProducts);
        startActivity(startAddToOrderActivity);
    }

    public void onOrderClick(View view) {
        AlertDialog.Builder builderOrder = new AlertDialog.Builder(BasketActivity.this);
        builderOrder
                .setTitle("Confirm")
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
                                        selectedProducts.clear();
                                        startActivity(new Intent(
                                                BasketActivity.this, BasketActivity.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                ));
                                        overridePendingTransition(0, 0);
                                        finish();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (selectedProducts.size() > 0) {
            selectedProducts.remove(selectedProducts.size() - 1);
        }
    }
}
