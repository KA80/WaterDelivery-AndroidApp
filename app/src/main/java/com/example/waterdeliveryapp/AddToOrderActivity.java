package com.example.waterdeliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class AddToOrderActivity extends AppCompatActivity {

    public static ArrayList<SelectedProduct> selectedProducts;

    public static String LIST_PRODUCT_KEY = "LIST_PRODUCT_KEY";

    private Button btnCancel;
    private Button btnPlus;
    private Button btnMinus;
    private Button btnConfirm;

    private Spinner spinnerProduct;

    private EditText editCounter;
    private ImageView imgProduct;

    private String selectedProduct;

    private AdapterView.OnItemSelectedListener OnProductSelectedListener = new AdapterView
            .OnItemSelectedListener() {
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
    };

    private Button.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_to_order);
        setSupportActionBar((Toolbar) findViewById(R.id.tool_bar));

        btnCancel = findViewById(R.id.btn_close_selection);
        btnCancel.setOnClickListener(onCancelClickListener);


        btnPlus = findViewById(R.id.btn_plus_counter);
        btnMinus = findViewById(R.id.btn_minus_counter);
        btnConfirm = findViewById(R.id.btn_confirm_selection);

        spinnerProduct = findViewById(R.id.select_product);
        editCounter = findViewById(R.id.counter);

        imgProduct = findViewById(R.id.img_product);

        spinnerProduct.setOnItemSelectedListener(OnProductSelectedListener);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            selectedProducts = (ArrayList<SelectedProduct>) bundle.get(LIST_PRODUCT_KEY);
        }

    }

    public void OnPlusClick(View view) {
        editCounter.setText(String.valueOf((!editCounter.getText().toString().isEmpty()
                ? Integer.parseInt(editCounter.getText().toString()) : 0) + 1));
    }

    public void OnMinusClick(View view) {
        if (!editCounter.getText().toString().isEmpty()
                && !editCounter.getText().toString().equals("0")) {
            editCounter.setText(String.valueOf(Integer.parseInt(
                    editCounter.getText().toString()) - 1));
        }
    }

    public void OnConfirmClick(View view) {
        if (!editCounter.getText().toString().isEmpty() && !editCounter.getText().toString().equals("0")) {
            SelectedProduct product = new SelectedProduct(selectedProduct,
                    Integer.parseInt(editCounter.getText().toString()));

            boolean is_found_equal_product = false;
            int prevCount = 0;
            int index = 0;
            for (int i = 0; i < selectedProducts.size(); i++) {
                if (selectedProducts.get(i).getName().equals(product.getName())) {
                    index = i;
                    prevCount = selectedProducts.get(i).getCount();
                    selectedProducts.get(i).setCount(selectedProducts.get(i).getCount() + product.getCount());
                    is_found_equal_product = true;
                    break;
                }
            }
            if (!is_found_equal_product)
                selectedProducts.add(product);

            Intent startBasketActivity = new Intent(AddToOrderActivity.this, BasketActivity.class);
            startBasketActivity.putExtra(BasketActivity.LIST_PRODUCT_KEY, selectedProducts);
            startActivity(startBasketActivity);
            if (!is_found_equal_product)
                selectedProducts.remove(selectedProducts.size() - 1);
            else
                selectedProducts.get(index).setCount(prevCount);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BasketActivity.selectedProducts = selectedProducts;
    }
}