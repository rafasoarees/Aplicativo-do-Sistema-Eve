/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.eve.eve.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.eve.eve.R;
import com.example.eve.eve.model.DataItems;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.eve.eve.R.layout.item_tile;

/**
 * Provides UI for the view with Tile.
 */
public class TileContentFragment extends Fragment {

    DatabaseReference myRef;
    RecyclerView recyclerView;
    public TextView data;
    public FirebaseRecyclerAdapter<DataItems, ShowDataViewHolder> mFirebaseAdapter;
    String dia;
    String mes;
    String ano;

    public static TileContentFragment newInstance() {
        return new TileContentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.show_data_layout, container, false);
        recyclerView = v.findViewById(R.id.show_data_recycler_view);


        myRef = FirebaseDatabase.getInstance().getReference("evento");


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<DataItems, ShowDataViewHolder>(DataItems.class, item_tile, ShowDataViewHolder.class, myRef.orderByChild("dataInicio")) {

            private static final int IMAGE_VIEW_ACTIVITY_REQUEST_CODE = 101;

            @Override
            protected void populateViewHolder(final ShowDataViewHolder viewHolder, final DataItems model, int position) {
                final String u = viewHolder.Image_URL(model.getImage_URL());
                String d = viewHolder.Image_Data(model.getDataInicio());
                RelativeLayout relativeLayout;

                relativeLayout = viewHolder.itemView.findViewById(R.id.tile_layout);
                data = viewHolder.itemView.findViewById(R.id.tile_data);

                dia = d.substring(8, 10);
                mes = d.substring(5, 7);
                ano = d.substring(0, 4);

                Log.i("dia", dia);
                Log.i("mes", mes);
                Log.i("ano", ano);


                data.setText(converteData(d));


//                relativeLayout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                       Intent intent = new Intent(getContext(),FullScreenImageActivity.class);
////                        startActivity(intent);
//
//                        FirebaseStorage storage = FirebaseStorage.getInstance();
//                        final StorageReference storageRef = storage.getReferenceFromUrl(u);
//
//                        //get download file url
//                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(final Uri uri) {
//                                Log.i("Main", "File uri: " + uri.toString());
//
//
//                            }
//                        });
//
//                    }
//                });

            }
        };
        recyclerView.setAdapter(mFirebaseAdapter);
    }

    public String converteData(String d) {

        dia = d.substring(8, 10);
        mes = d.substring(5, 7);
        ano = d.substring(0, 4);

        String[] meses = {"JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};

        String op = mes;

        switch (op) {
            case "01":
                mes = meses[0];
                break;
            case "02":
                mes = meses[1];
                break;
            case "03":
                mes = meses[2];
                break;
            case "04":
                mes = meses[3];
                break;
            case "05":
                mes = meses[4];
                break;
            case "06":
                mes = meses[5];
                break;
            case "07":
                mes = meses[6];
                break;
            case "08":
                mes = meses[7];
                break;
            case "09":
                mes = meses[8];
                break;
            case "10":
                mes = meses[9];
                break;
            case "11":
                mes = meses[10];
                break;
            case "12":
                mes = meses[11];
                break;

        }
        return dia + "\n" + mes;

    }

    public void saveArrayToInternalStorage(String fileName, Bitmap bmp) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        try {
            FileOutputStream fos = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(byteArray);
            fos.close();

        } catch (IOException e) {
            Log.w("InternalStorage", "Error writing", e);
        }
    }

    public void save(Bitmap pBitmap) {

        try {

            File file = new File("/sdcard/Pictures/imgsApp/");
            file.mkdir();

            File ifile = new File(file + "/", "teste.jpg");
            FileOutputStream outStream = new FileOutputStream(ifile);
            pBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();

        } catch (Exception e) {
            Log.e("Could not save", e.toString());
        }
    }

    //View Holder For Recycler View
    public static class ShowDataViewHolder extends RecyclerView.ViewHolder {
        final TextView image_data;
        final ImageView image_url;

        public ShowDataViewHolder(final View itemView) {
            super(itemView);
            image_url = itemView.findViewById(R.id.tile_picture);
            image_data = itemView.findViewById(R.id.tile_data);

        }

        String Image_Data(String data) {
            image_data.setText(data);
            return data;
        }

        String Image_URL(String url) {
            // image_url.setImageResource(R.drawable.loading);
            Glide.with(itemView.getContext())
                    .load(url)
                    .crossFade()
                    .placeholder(R.mipmap.ic_launcher)
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image_url);
            return url;
        }


    }
}


///*
// * Copyright (C) 2015 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.example.emersonnascimento.girocultural;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.content.res.TypedArray;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
///**
// * Provides UI for the view with Tile.
// */
//public class TileContentFragment extends Fragment {
//    public static TileContentFragment newInstance() {
//        TileContentFragment fragment = new TileContentFragment();
//        return fragment;
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
//                R.layout.recycler_view, container, false);
//        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setHasFixedSize(true);
//        // Set padding for Tiles
//        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
//        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
//        return recyclerView;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView picture;
//        public TextView name;
//        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater.inflate(R.layout.item_tile, parent, false));
//            picture = (ImageView) itemView.findViewById(R.id.tile_picture);
//            name = (TextView) itemView.findViewById(R.id.tile_title);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
//                    context.startActivity(intent);
//                }
//            });
//        }
//    }
//
//    /**
//     * Adapter to display recycler view.
//     */
//    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
//        // Set numbers of Tiles in RecyclerView.
//        private static final int LENGTH = 18;
//
//        private final String[] mPlaces;
//        private final Drawable[] mPlacePictures;
//        public ContentAdapter(Context context) {
//            Resources resources = context.getResources();
//            mPlaces = resources.getStringArray(R.array.places);
//            TypedArray a = resources.obtainTypedArray(R.array.places_picture);
//            mPlacePictures = new Drawable[a.length()];
//            for (int i = 0; i < mPlacePictures.length; i++) {
//                mPlacePictures[i] = a.getDrawable(i);
//            }
//            a.recycle();
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//            holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.length]);
//            holder.name.setText(mPlaces[position % mPlaces.length]);
//        }
//
//        @Override
//        public int getItemCount() {
//            return LENGTH;
//        }
//    }
//}