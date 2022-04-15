package com.example.e_commerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Adapters.CategoryAdapter;
import com.example.e_commerce.R;
import com.example.e_commerce.models.CategoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory extends AppCompatActivity{


    RecyclerView catRecyclerView;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    TextView catShowAll;
    Toolbar toolbar;

    //category recyclerview
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(ProductCategory.this);

        catRecyclerView = findViewById(R.id.rec_category);
        catShowAll = findViewById(R.id.category_see_all);
        toolbar = findViewById(R.id.category_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductCategory.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductCategory.this ,ShowAllActivity.class);
                startActivity(intent);
            }
        });
       

        linearLayout= findViewById(R.id.home_layout);
//        linearLayout.setVisibility(View.GONE);

        progressDialog.setTitle("Opening Product Category");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        // set category in rec. view

        catRecyclerView.setLayoutManager(new GridLayoutManager(ProductCategory.this,  3));
        categoryModelList  = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(ProductCategory.this, categoryModelList);
        catRecyclerView.setAdapter(categoryAdapter);
        db.collection("category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                CategoryModel categoryModel =document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();

//                                linearLayout.setVisibility(View.VISIBLE);

                                progressDialog.dismiss();

                            }
                        } else {
                            Toast.makeText(ProductCategory.this, ""+task.getException(), Toast.LENGTH_SHORT).show();

                        }

                    }

                });
    }

}