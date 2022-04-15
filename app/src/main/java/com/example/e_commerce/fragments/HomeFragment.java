package com.example.e_commerce.fragments;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.e_commerce.Activities.ShowAllActivity;

import com.example.e_commerce.Adapters.NewProductsAdapter;
import com.example.e_commerce.Adapters.PopularProductsAdapter;
import com.example.e_commerce.R;

import com.example.e_commerce.models.NewProductsModel;
import com.example.e_commerce.models.PopularProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView  newProductsRecyclerView, popularRecyclerView;

    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    TextView  popularShowAll, newProductShowAll;




    //category recyclerview
//    CategoryAdapter categoryAdapter;
//    List<CategoryModel> categoryModelList;

    // new products recycler view
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;

    //popular products  recView
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;


    // fireStore
    FirebaseFirestore db;


    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(getActivity());

 //       catRecyclerView = root.findViewById(R.id.rec_category);                  //category recyclerview
        newProductsRecyclerView = root.findViewById(R.id.new_product_rec);        // new product recView
        popularRecyclerView =root.findViewById(R.id.popular_rec);                // popular products recView

 //      catShowAll = root.findViewById(R.id.category_see_all);
        popularShowAll = root.findViewById(R.id.popular_see_all);
        newProductShowAll = root.findViewById(R.id.newProducts_see_all);

//        catShowAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), ShowAllActivity.class);
//                startActivity(intent);
//            }
//        });

        newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });


        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);


            }
        });


        linearLayout= root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);


        // image slider implementation
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1, "Discount on Shoes Items ", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2, "Discount on Perfumes ", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3, "70% OFF", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);

        progressDialog.setTitle("Welcome To Sanduk Kashmir");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
//
//        // category
//        catRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
//        categoryModelList  = new ArrayList<>();
//        categoryAdapter = new CategoryAdapter(getContext(), categoryModelList);
//        catRecyclerView.setAdapter(categoryAdapter);
//        db.collection("category")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//
//                                CategoryModel categoryModel =document.toObject(CategoryModel.class);
//                                categoryModelList.add(categoryModel);
//                                categoryAdapter.notifyDataSetChanged();
//
//                                linearLayout.setVisibility(View.VISIBLE);
//
//                                progressDialog.dismiss();
//                            }
//                        } else {
//                            Toast.makeText(getContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });

        // new products

        newProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL, false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(),newProductsModelList);
        newProductsRecyclerView.setAdapter(newProductsAdapter);

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                NewProductsModel newProductsModel=document.toObject(NewProductsModel.class);
                                newProductsModelList.add(newProductsModel);
                                newProductsAdapter.notifyDataSetChanged();

                                linearLayout.setVisibility(View.VISIBLE);

                                progressDialog.dismiss();
                            }
                        } else {

                            Toast.makeText(getContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Popular Products

        popularRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        popularProductsModelList = new ArrayList<>();
        popularProductsAdapter = new PopularProductsAdapter(getContext(),popularProductsModelList);
        popularRecyclerView.setAdapter(popularProductsAdapter);

        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                PopularProductsModel popularProductsModel=document.toObject(PopularProductsModel.class);
                                popularProductsModelList.add(popularProductsModel);
                                popularProductsAdapter.notifyDataSetChanged();
                            }
                        } else {

                            Toast.makeText(getContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        return root;

    }
}