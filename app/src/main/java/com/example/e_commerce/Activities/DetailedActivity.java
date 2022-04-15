package com.example.e_commerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_commerce.R;
import com.example.e_commerce.fragments.HomeFragment;
import com.example.e_commerce.fragments.MobileFragment;
import com.example.e_commerce.models.MobileModel;
import com.example.e_commerce.models.NewProductsModel;
import com.example.e_commerce.models.PopularProductsModel;
import com.example.e_commerce.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class DetailedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView detailedImg, addItems, removeItems;
    TextView name, rating, description, price, quantity;
    Button addToCart, buyNow;
    int totalQuantity = 1, totalPrice = 0;
    Toolbar toolbar;

    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;

    //new products
    NewProductsModel newProductsModel = null;

    // Popular Products
    PopularProductsModel popularProductsModel = null;

    // show all
    ShowAllModel showAllModel = null;

    //mobile model
    MobileModel mobileModel = null;

    FirebaseAuth auth;

    private FirebaseFirestore firestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.detailed_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navmenu);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");


//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });


        if (obj instanceof NewProductsModel) {
            newProductsModel = (NewProductsModel) obj;

        } else if (obj instanceof PopularProductsModel) {
            popularProductsModel = (PopularProductsModel) obj;
        } else if (obj instanceof ShowAllModel) {
            showAllModel = (ShowAllModel) obj;
        } else if (obj instanceof MobileModel) {
            mobileModel = (MobileModel) obj;
        }





        detailedImg = findViewById(R.id.detailed_img);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);

        name = findViewById(R.id.detailed_name);
        quantity = findViewById(R.id.quantity);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_description);
        price = findViewById(R.id.detailed_price);

        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);

        // new Products

        if (newProductsModel != null) {
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));

            totalPrice = newProductsModel.getPrice() * totalQuantity;


        }

        // Popular Products

        if (popularProductsModel != null) {
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));

            totalPrice = popularProductsModel.getPrice() * totalQuantity;


        }

        //showAll Products

        if (showAllModel != null) {
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));

            totalPrice = showAllModel.getPrice() * totalQuantity;


        }

        // mobiles

        if (mobileModel != null) {
            Glide.with(getApplicationContext()).load(mobileModel.getImg_url()).into(detailedImg);
            name.setText(mobileModel.getName());
            rating.setText(mobileModel.getRating());
            description.setText(mobileModel.getDescription());
            price.setText(String.valueOf(mobileModel.getPrice()));

            totalPrice = mobileModel.getPrice() * totalQuantity;

        }

        //buy now

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DetailedActivity.this, AddressActivity.class);
                if (newProductsModel != null) {
                    intent.putExtra("item", newProductsModel);
                }
                if (popularProductsModel != null) {
                    intent.putExtra("item", popularProductsModel);
                }
                if (showAllModel != null) {
                    intent.putExtra("item", showAllModel);
                }
                if (mobileModel != null) {
                    intent.putExtra("item", mobileModel);
                }
                startActivity(intent);
            }
        });


        // Add to Cart

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoCart();
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    if (newProductsModel != null) {
                        totalPrice = newProductsModel.getPrice() * totalQuantity;
                    }
                    if (popularProductsModel != null) {
                        totalPrice = popularProductsModel.getPrice() * totalQuantity;
                    }
                    if (showAllModel != null) {
                        totalPrice = showAllModel.getPrice() * totalQuantity;
                    }
                    if (mobileModel != null) {
                        totalPrice = mobileModel.getPrice() * totalQuantity;
                    }
                }

            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity >= 1) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }

            }
        });

    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(DetailedActivity.this, MainActivity.class));
        finish();
        //    super.onBackPressed();
    }

    private void addtoCart() {
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();


        SimpleDateFormat currentDate = new SimpleDateFormat("DD :MM: YYYY");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH: MM: SS ");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", name.getText().toString());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        firestore.collection("AddToCart")
                .document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {


            case R.id.redmiPro:
                Intent intent = new Intent(DetailedActivity.this, MobileFragment.class);
                startActivity(intent);
                finish();
                break;
            case R.id.mobHome:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;

        }


        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    }

