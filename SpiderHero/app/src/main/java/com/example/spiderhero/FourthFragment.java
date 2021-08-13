package com.example.spiderhero;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FourthFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SearchView searchView;
    private HEROAdapter heroAdapter;
    public ArrayList<Fav> favs;
    public ArrayList<HERO> heroes;
    FavDao favDao;
    private DataBase dataBase;
    public int count = 0;
    public ProgressBar progressBar;


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    Call<List<HERO>> call = apiInterface.getList();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_male, container, false);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        dataBase = Room.databaseBuilder(getContext(), DataBase.class, "FavDataBase").allowMainThreadQueries().build();
        favDao = dataBase.favDao();
        searchView = view.findViewById(R.id.search_view);
        progressBar.setVisibility(View.VISIBLE);

        getResponse(count,null);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.equals("")||query.equals(null)){
                    count = 0;
                    getResponse(count,query);
                }else{
                    count=1;
                    getResponse(count,query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")||newText.equals(null)){
                    count = 0;
                    getResponse(count,newText);
                }else{
                    count=1;
                    getResponse(count,newText);
                }
                return false;
            }
        });

        return view;
    }

    private void getResponse(int c , String q) {

        favs = new ArrayList<>();
        heroes = new ArrayList<>();
        favs = (ArrayList<Fav>) favDao.getFav();

        for(int i=0;i<favs.size();i++){
            Call<HERO> call = apiInterface.getHero(favs.get(i).getId());
            int finalI = i;
            call.enqueue(new Callback<HERO>() {
                @Override
                public void onResponse(Call<HERO> call, Response<HERO> response) {
                    if(response.isSuccessful()){
                        heroes.add(response.body());

                        if(finalI ==favs.size()-1){
                            getHeroes(heroes,c,q);
                        }
                    }
                }

                @Override
                public void onFailure(Call<HERO> call, Throwable t) {

                }
            });
        }
    }

    private void getHeroes(ArrayList<HERO> heroes , int c ,String q) {

        if(c==0){
            heroAdapter = new HEROAdapter(getContext(),heroes);
            recyclerView.setAdapter(heroAdapter);
            progressBar.setVisibility(View.INVISIBLE);
        }
        else if(c==1){
            ArrayList<HERO> heroes2 = new ArrayList<>();
            for(int i=0;i<heroes.size();i++) {
                if (((heroes.get(i).getName().toLowerCase().contains(q.toString().toLowerCase())) || (heroes.get(i).getId().toString().toLowerCase().contains(q.toString().toLowerCase())))) {
                    heroes2.add(heroes.get(i));
                }

                if (i == heroes.size() - 1) {
                    heroAdapter = new HEROAdapter(getContext(), heroes2);
                    recyclerView.setAdapter(heroAdapter);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

        }
    }


}
