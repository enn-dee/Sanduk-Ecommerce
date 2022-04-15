package com.example.e_commerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.e_commerce.Adapters.MyCartAdapter;
import com.example.e_commerce.R;
import com.example.e_commerce.models.MyCartModel;
import com.example.e_commerce.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
Toolbar toolbar;
TextView overAllAmount;
RecyclerView recyclerView;
List<MyCartModel> CartModelList;
MyCartAdapter cartAdapter;
private FirebaseAuth auth;
private FirebaseFirestore firestore;
int overAllTotalAmount;
Button buy_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.my_cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // get data from my cart adapter
        overAllAmount = findViewById(R.id.textView3);
        buy_now = findViewById(R.id.buy_now);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReciver,new IntentFilter("MyTotalAmount"));

        recyclerView = findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(this, CartModelList);
        recyclerView.setAdapter(cartAdapter);

       firestore.collection("AddToCart").document(auth.getCurrentUser().
               getUid()).collection("User")
               .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
if(task.isSuccessful()){
    for(DocumentSnapshot doc: task.getResult().getDocuments()){
        MyCartModel myCartModel = doc.toObject(MyCartModel.class);
        CartModelList.add(myCartModel);
        cartAdapter.notifyDataSetChanged();

    }
}
           }
       });
       buy_now.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(CartActivity.this, AddressActivity.class);
               startActivity(intent);
               finish();

           }
       });
    }


    public BroadcastReceiver mMessageReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

int totalBill =intent.getIntExtra("totalAmount", 0);
overAllAmount.setText("Total Amount: "+totalBill+"$");

        }
    };
}