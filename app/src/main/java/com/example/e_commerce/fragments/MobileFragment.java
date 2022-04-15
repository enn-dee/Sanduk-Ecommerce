package com.example.e_commerce.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Activities.ShowAllActivity;
import com.example.e_commerce.Adapters.MobileAdapter;
import com.example.e_commerce.R;
import com.example.e_commerce.models.MobileModel;
import com.example.e_commerce.models.NewProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MobileFragment extends Fragment {
    RecyclerView mobRecView;
    TextView ShowAll;

    MobileAdapter mobileAdapter;
    List<MobileModel> mobileModelList;
    FirebaseFirestore db;


    public MobileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_mobile, container, false);
        db = FirebaseFirestore.getInstance();

        mobRecView = root.findViewById(R.id.mob_rec);
       ShowAll = root.findViewById(R.id.mob_see_all);
       ShowAll.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getContext(), ShowAllActivity.class);
               startActivity(intent);

           }
       });

       mobRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
       mobileModelList = new ArrayList<>();
       mobileAdapter = new MobileAdapter(getContext(), mobileModelList);
       mobRecView.setAdapter(mobileAdapter);

        db.collection("Mobiles")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                MobileModel mobileModel = document.toObject(MobileModel.class);
                                mobileModelList.add(mobileModel);
                                mobileAdapter.notifyDataSetChanged();
                            }
                        } else {

                            Toast.makeText(getContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;

    }
}