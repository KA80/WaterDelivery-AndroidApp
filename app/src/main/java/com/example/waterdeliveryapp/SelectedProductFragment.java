package com.example.waterdeliveryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class SelectedProductFragment extends Fragment {

    private static final String PRODUCT_KEY = "PRODUCT";
    private SelectedProduct product;

    public static SelectedProductFragment newInstance(SelectedProduct product) {
        SelectedProductFragment fragment = new SelectedProductFragment();
        Bundle args = new Bundle();
        args.putSerializable(PRODUCT_KEY, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = (SelectedProduct) getArguments().getSerializable(PRODUCT_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_product, container, false);

        TextView TextName = view.findViewById(R.id.selected_product_name);
        TextView TextCount = view.findViewById(R.id.selected_product_count);

        TextName.setText(product.getName());
        TextCount.setText(String.valueOf(product.getCount()));

        ImageView img = view.findViewById(R.id.img_selected_product);

        Button deleteSelection = view.findViewById(R.id.delete_selection);
        deleteSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction().remove(SelectedProductFragment.this).commit();
                }
                BasketActivity.selectedProducts.remove(product);
            }
        });

        switch (product.getName()) {
            case "Juice":
                img.setImageResource(R.drawable.juice);
                break;
            case "Cola":
                img.setImageResource(R.drawable.cola);
                break;
            default:
                img.setImageResource(R.drawable.water);
                break;
        }

        return view;
    }

}
