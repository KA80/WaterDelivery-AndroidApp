package com.example.waterdeliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddToOrderActivity extends AppCompatActivity {

    private EditText editCounter;
    private ImageView imgProduct;

    private String selectedProduct;

    private final InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (dest.toString().equals("0") && ((dstart == 0 && source.toString().equals("0")) || (dstart == 1))) {
                return "";
            }
            if (dest.length() > 5) {
                return "";
            }
            if (dest.length() > 0 && source.toString().equals("0") && dstart == 0) {
                return "";
            }
            return null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_to_order);
        setSupportActionBar((Toolbar) findViewById(R.id.tool_bar));

        Spinner spinnerProduct = findViewById(R.id.select_product);
        spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String[] products = getResources().getStringArray(R.array.products);
                switch (products[pos]) {
                    case "Juice":
                        imgProduct.setImageResource(R.drawable.juice);
                        break;
                    case "Cola":
                        imgProduct.setImageResource(R.drawable.cola);
                        break;
                    default:
                        imgProduct.setImageResource(R.drawable.water);
                        break;
                }
                selectedProduct = products[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        editCounter = findViewById(R.id.counter);
        imgProduct = findViewById(R.id.img_product);

        editCounter.setFilters(new InputFilter[]{filter});
    }

    public void onPlusClick(View view) {
        if (editCounter.getText().toString().equals("") || Integer.parseInt(editCounter.getText().toString()) < 999999) {
            editCounter.setText(String.valueOf((!editCounter.getText().toString().isEmpty()
                    ? Integer.parseInt(editCounter.getText().toString()) : 0) + 1));
        }
    }

    public void onMinusClick(View view) {
        if (!editCounter.getText().toString().isEmpty()
                && !editCounter.getText().toString().equals("0")) {
            editCounter.setText(String.valueOf(Integer.parseInt(
                    editCounter.getText().toString()) - 1));
        }
    }

    public void onConfirmClick(View view) {
        if (!editCounter.getText().toString().isEmpty() && !editCounter.getText().toString().equals("0")) {
            SelectedProduct product = new SelectedProduct(
                    selectedProduct, Integer.parseInt(editCounter.getText().toString()));

            boolean is_found_equal_product = false;

            for (int i = 0; i < BasketActivity.selectedProducts.size(); i++) {
                if (BasketActivity.selectedProducts.get(i).getName().equals(product.getName())) {
                    BasketActivity.selectedProducts.get(i).setCount(BasketActivity.selectedProducts.get(i).getCount() + product.getCount());
                    if (BasketActivity.selectedProducts.get(i).getCount() > 999999) {
                        BasketActivity.selectedProducts.get(i).setCount(999999);
                    }
                    is_found_equal_product = true;
                    break;
                }
            }
            if (!is_found_equal_product)
                BasketActivity.selectedProducts.add(product);
            startActivity(new Intent(AddToOrderActivity.this, BasketActivity.class).
                    setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            this.finish();
        }

    }

    public void onCancelClick(View view) {
        onBackPressed();
    }
}