package com.example.spiderhero;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spiapi2.databinding.ActivityMain2Binding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    public ImageView imageView2;
    ArrayList<String> strings1 = new ArrayList<>();
    ArrayList<String> strings2 = new ArrayList<>();
    public InfoAdapter infoAdapter;
    private RecyclerView recyclerView2;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        recyclerView2 = (RecyclerView)findViewById(R.id.recycler_view2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        Intent intent = getIntent();
        strings1 = new ArrayList<>();
        strings2 = new ArrayList<>();

        Picasso.get().load(intent.getStringExtra("url"))
                .resize(400,400)
                .centerCrop()
                .into(imageView2);
        strings1.add(intent.getStringExtra("name"));
        strings1.add(intent.getStringExtra("slug"));
        strings1.add(intent.getStringExtra("intel"));
        strings1.add(intent.getStringExtra("stren"));
        strings1.add(intent.getStringExtra("speed"));
        strings1.add(intent.getStringExtra("combat"));
        strings1.add(intent.getStringExtra("gender"));
        strings1.add(intent.getStringExtra("race"));
        strings1.add(intent.getStringExtra("eye"));
        strings1.add(intent.getStringExtra("hair"));
        strings1.add(intent.getStringExtra("full"));
        strings1.add(intent.getStringExtra("pob"));
        strings1.add(intent.getStringExtra("firstap"));
        strings1.add(intent.getStringExtra("occ"));
        strings1.add(intent.getStringExtra("grp"));

        strings2.add("Name");
        strings2.add("Slug");
        strings2.add("Intelligence");
        strings2.add("Strength");
        strings2.add("Speed");
        strings2.add("Combat");
        strings2.add("Gender");
        strings2.add("Race");
        strings2.add("Eye Color");
        strings2.add("Hair Color");
        strings2.add("Full Name");
        strings2.add("PlaceOfBirth");
        strings2.add("First Appearance");
        strings2.add("Occupation");
        strings2.add("Group Affiliation");

        infoAdapter = new InfoAdapter(this,strings1,strings2);
        recyclerView2.setAdapter(infoAdapter);
    }
}
