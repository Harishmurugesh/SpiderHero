package com.example.spiderhero;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    public ImageView imageView2;
    public ImageView imageView3;
    public ImageView imageView4;
    ArrayList<String> strings1 = new ArrayList<>();
    ArrayList<String> strings2 = new ArrayList<>();
    public InfoAdapter infoAdapter;
    private RecyclerView recyclerView2;
    public FavDao favDao;
    public DataBase dataBase;
    public int id;
    public Fav fav1;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.share){
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView2.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"Some Title",null);
            Intent intent = new Intent(Intent.ACTION_SEND);
            Uri bitmapUri = Uri.parse(bitmapPath);

            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM,bitmapUri);
            intent.putExtra(Intent.EXTRA_TEXT,strings2.get(0)+" : "+strings1.get(0));
            startActivity(Intent.createChooser(intent,"Share Image"));
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView2 = (RecyclerView)findViewById(R.id.recycler_view2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);
        imageView4 = (ImageView)findViewById(R.id.imageView4);
        dataBase = Room.databaseBuilder(this, DataBase.class, "FavDataBase").allowMainThreadQueries().build();
        favDao = dataBase.favDao();
        Intent intent = getIntent();
        strings1 = new ArrayList<>();
        strings2 = new ArrayList<>();

        if(favDao.getById(intent.getStringExtra("id"))==null){
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.INVISIBLE);
        }else{
            imageView4.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.INVISIBLE);
        }

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favDao.Insert(new Fav(intent.getStringExtra("id"),""+intent.getStringExtra("name")));
                imageView3.setVisibility(View.INVISIBLE);
                imageView4.setVisibility(View.VISIBLE);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favDao.Delete(favDao.getById(intent.getStringExtra("id")));
                imageView4.setVisibility(View.INVISIBLE);
                imageView3.setVisibility(View.VISIBLE);
            }
        });

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