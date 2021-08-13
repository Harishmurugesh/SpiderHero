package com.example.spiderhero;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HEROAdapter extends RecyclerView.Adapter<HEROAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HERO> heroes = new ArrayList<>();
    public ArrayList<String> strings = new ArrayList<>();


    public HEROAdapter(Context context, ArrayList<HERO> heroes) {
        this.context = context;
        this.heroes = heroes;

    }

    @NonNull
    @Override
    public HEROAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_data,viewGroup,false);
        return new HEROAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HEROAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(heroes.get(i).getName());
        Picasso.get().load(heroes.get(i).getImages().getSm())
                .resize(400,400)
                .centerCrop()
                .into(viewHolder.imageView);

        viewHolder.setItemClickListener(new ItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClickListener(View v, int position) {



                Intent intent = new Intent(context,MainActivity2.class);
                intent.putExtra("url",heroes.get(i).getImages().getSm());
                intent.putExtra("id",heroes.get(i).getId());

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,viewHolder.imageView,ViewCompat.getTransitionName(viewHolder.imageView));

                intent.putExtra("name",heroes.get(i).getName());
                intent.putExtra("slug",heroes.get(i).getSlug());
                intent.putExtra("intel",heroes.get(i).getPowerstats().getIntelligence());
                intent.putExtra("stren",heroes.get(i).getPowerstats().getStrength());
                intent.putExtra("speed",heroes.get(i).getPowerstats().getSpeed());
                intent.putExtra("combat",heroes.get(i).getPowerstats().getCombat());
                intent.putExtra("gender",heroes.get(i).getAppearance().getGender());
                intent.putExtra("race",heroes.get(i).getAppearance().getRace());
                intent.putExtra("eye",heroes.get(i).getAppearance().getEyeColour());
                intent.putExtra("hair",heroes.get(i).getAppearance().getHairColour());
                intent.putExtra("full",heroes.get(i).getBiography().getFullName());
                intent.putExtra("pob",heroes.get(i).getBiography().getPlaceOfBirth());
                intent.putExtra("firstap",heroes.get(i).getBiography().getFirstAppearance());
                intent.putExtra("occ",heroes.get(i).getWork().getOccupation());
                intent.putExtra("grp",heroes.get(i).getConnections().getGroupAffiliation());
                context.startActivity(intent,options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textView;
        private final ImageView imageView;
        public ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = (TextView)itemView.findViewById(R.id.textView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener((View.OnClickListener) this);
        }


        public void onClick(View v) {

            this.itemClickListener.onItemClickListener(v,getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener = ic;
        }


    }
}
