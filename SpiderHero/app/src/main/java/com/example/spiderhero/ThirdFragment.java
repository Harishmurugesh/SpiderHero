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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThirdFragment extends Fragment {

    ArrayList<HERO> heroes = new ArrayList<>();
    ArrayList<HERO> heroes2 = new ArrayList<>();
    private HEROAdapter heroAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayoutManager linearLayoutManager;
    private SearchView searchView;
    public int count = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_male,container,false);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        progressBar  = view.findViewById(R.id.progressBar);
        searchView = view.findViewById(R.id.search_view);

        getResponse(count,null);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.equals("")||query.equals(null)){
                    count = 0;
                    getResponse(count,null);
                }else{
                    count =1;
                    getResponse(count,query);
                    Log.i("Tag","Submit");
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")||newText.equals(null)){
                    count = 0;
                    getResponse(count,null);
                }else{
                    count =1;
                    getResponse(count,newText);
                }
                return false;
            }
        });

        return view;
    }


    private void getResponse(int c , String q) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<List<HERO>> call = apiInterface.getList();

        call.enqueue(new Callback<List<HERO>>() {
            @Override
            public void onResponse(Call<List<HERO>> call, Response<List<HERO>> response) {
                if (response.isSuccessful()) {
                    int j =0;

                    if (c == 0) {
                        heroes = new ArrayList<>(response.body());
                        for (int i = 0; i < heroes.size(); i++) {
                            if ((heroes.get(i).getAppearance().getGender()).equals("Female"))
                                heroes2.add(heroes.get(i));

                            if (i == heroes.size() - 1) {
                                heroAdapter = new HEROAdapter(getContext(), heroes2);
                                recyclerView.setAdapter(heroAdapter);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                    else   if(c==1){
                        heroes2 = new ArrayList<>();
                        for(int i =0 ; i < heroes.size() ; i++){
                            if(((heroes.get(i).getName().toLowerCase().contains(q.toString().toLowerCase()))||(heroes.get(i).getId().toString().toLowerCase().contains(q.toString().toLowerCase())))&&((heroes.get(i).getAppearance().getGender()).equals("Female"))){
                                heroes2.add(heroes.get(i));
                            }
                            if(i==heroes.size()-1){
                                heroAdapter = new HEROAdapter(getContext(), heroes2);
                                recyclerView.setAdapter(heroAdapter);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<HERO>> call, Throwable t) {

            }
        });
    }
}
