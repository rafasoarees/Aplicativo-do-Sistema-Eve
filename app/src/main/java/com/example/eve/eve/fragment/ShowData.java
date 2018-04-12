package com.example.eve.eve.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.eve.eve.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Administrator on 16-03-2017.
 */

public class ShowData extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;


    public ShowData() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_data_layout);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("evento");
        recyclerView = (RecyclerView) findViewById(R.id.show_data_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(ShowData.this));
        //Toast.makeText(ShowData.this, "Wait !  Fetching List...", Toast.LENGTH_SHORT).show();


    }

    //View Holder For Recycler View
    public static class ShowDataViewHolder extends RecyclerView.ViewHolder {
       //  final TextView image_title;
         final TextView card_text;
         final ImageView image_url;

        public ShowDataViewHolder(final View itemView) {
            super(itemView);
            image_url = (ImageView) itemView.findViewById(R.id.fetch_image);
//            image_title = (TextView) itemView.findViewById(R.id.place_image_title);
            card_text = (TextView) itemView.findViewById(R.id.card_text);
        }


        String Image_Title(String title) {
            //image_title.setText(title);
            return title;
        }

        String Image_Description(String description) {
            //card_text.setText(description);
            return description;
        }

        String Image_URL(String title) {
            // image_url.setImageResource(R.drawable.loading);
            Glide.with(itemView.getContext())
                    .load(title)
                    .crossFade()
                    .placeholder(R.mipmap.ic_launcher)
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image_url);

            return title;
        }



    }
}


