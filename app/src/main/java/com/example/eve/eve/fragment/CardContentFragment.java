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

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eve.eve.R;
import com.example.eve.eve.model.DataItems;
import com.example.eve.eve.view.DetailActivity;
import com.facebook.CallbackManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.os.Environment.*;


/**
 * Provides UI for the view with Cards.
 */
public class CardContentFragment extends Fragment {
    CallbackManager callbackManager;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    RecyclerView recyclerView;
    public ImageView picture;
    public TextView name;
    public TextView des;
    public ProgressDialog pDialog;
    File dir;
    String image_name;

    private static final int PERMISSION_REQUEST_CODE = 1;
    public FirebaseRecyclerAdapter<DataItems, ShowData.ShowDataViewHolder> mFirebaseAdapter;

    public CardContentFragment() {
        // Required empty public constructor
    }

    public static CardContentFragment newInstance() {
        return new CardContentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        dir = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/Eve/shared");
//        try {
//            if (dir.mkdir()) {
//                Log.i("OIOIOI", "Directoryted");
//            } else {
//                Log.i("AIAIAIAI", "Directoryot created");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        pDialog = new ProgressDialog(container.getContext());
        pDialog.setMessage("Carregando...");
        pDialog.show();

        View v = inflater.inflate(R.layout.show_data_layout, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();

        myRef = FirebaseDatabase.getInstance().getReference("evento");

        recyclerView = v.findViewById(R.id.show_data_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mFirebaseAdapter = new FirebaseRecyclerAdapter<DataItems, ShowData.ShowDataViewHolder>(DataItems.class, R.layout.item_card, ShowData.ShowDataViewHolder.class, myRef) {



            public void populateViewHolder(final ShowData.ShowDataViewHolder viewHolder, final DataItems model, final int position) {

                final String url = viewHolder.Image_URL(model.getImage_URL());
                final String title = viewHolder.Image_Title(model.getImage_Title());
                final String description = viewHolder.Image_Description(model.getImage_Description());
                final String telephone = model.getTelefone();
                final String cidade = model.getCidade();
                final String endereco = model.getEnd();
                final String contenos = model.getContenos();
                final String data_fim = model.getDataFim();
                final String data_inicio = model.getDataInicio();
                final String email = model.getEmail();
                final String site = model.getSite();
                final String nome = model.getNome();
                final String id_user = model.getIdUser();
                final String estado = model.getEstado();

                picture = viewHolder.itemView.findViewById(R.id.fetch_image);
                // name = (TextView) viewHolder.itemView.findViewById(R.id.fetch_image_title);
                des = viewHolder.itemView.findViewById(R.id.card_text);
                des.setText(title);

                // ImageButton instagramImageButton = viewHolder.itemView.findViewById(R.id.instagram);
                //ImageButton zapImageButton = viewHolder.itemView.findViewById(R.id.zap);
                ImageButton shareImageButton = viewHolder.itemView.findViewById(R.id.share);


                shareImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        Log.i("entrada", "" + url);
                        final StorageReference storageRef = storage.getReferenceFromUrl(url);
                        image_name = storageRef.getName();
                        Log.i("nome imagem", "" + image_name);


                                //get download file url
                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Log.i("Main", "File uri: " + uri.toString());

                                        //download the file
                                        try {
                                            // showProgressDialog("Download File", "Downloading File...");
                                            final File localFile = File.createTempFile("images", "jpg");

                                            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());

                                                    if (Build.VERSION.SDK_INT >= 23) {
                                                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                                                == PackageManager.PERMISSION_GRANTED) {
                                                            Log.v("TAG", "Permission is granted");
                                                            save(bitmap, image_name);
                                                            //File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/teste/" + image_name);

                                                            Intent share = new Intent(Intent.ACTION_SEND);


                                                            share.setType("image/*");

                                                            // Necessario ter a imagem no diretorio
                                                            String imagePath = getExternalStorageDirectory().getPath() + "/Pictures/" + image_name;
                                                            Log.i("path", imagePath);
                                                            File imageFileToShare = new File(imagePath);

                                                            Uri uri = Uri.fromFile(imageFileToShare);
                                                            share.putExtra(Intent.EXTRA_STREAM, uri);
                                                            share.putExtra(Intent.EXTRA_TEXT, "" + description);
                                                            startActivity(Intent.createChooser(share, "Compartilhar imagem"));
                                                        }
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception exception) {

                                                }
                                            });
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            Log.e("Main", "IOE Exception");
                                        }
                                    }
                                });

                            }
                });

                //OnClick Itempomy
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {

                        Bundle params = new Bundle();
                        params.putString("title", title);
                        params.putString("description", description);
                        params.putString("url", url);
                        params.putString("endereco", endereco);
                        params.putString("data_inicio", data_inicio);
                        params.putString("data_fim", data_fim);
                        params.putString("site", site);
                        params.putString("telephone", telephone);
                        params.putString("cidade", cidade);
                        params.putString("email", email);
                        params.putString("contenos", contenos);
                        params.putString("nome", nome);
                        params.putString("id", id_user);
                        params.putString("estado", estado);

//
//                        Log.i("p1title", "" + title);
//                        Log.i("p1description", "" + description);
//                        Log.i("p1url", "" + url);
//                        Log.i("p1datainicio", "" + data_inicio);
//                        Log.i("p1datafim", "" + data_fim);
//                        Log.i("p1telefone", "" + telephone);
//                        Log.i("p1endereco", "" + endereco);
//                        Log.i("p1site", "" + site);
//                        Log.i("p1cidade", "" + cidade);
//                        Log.i("p1email", "" + email);
//                        Log.i("p1contenos", "" + contenos);
//                        Log.i("p1nome", "" + nome);
//                        Log.i("p1id", "" + id_user);
//                        Log.i("p1estado", "" + estado);
                        Intent intent = new Intent(getContext(), DetailActivity.class);
                        intent.putExtras(params);
                        startActivity(intent);
                    }
                });

//                instagramImageButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        if (Build.VERSION.SDK_INT >= 23) {
//                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                    == PackageManager.PERMISSION_GRANTED) {
//                                Log.v("TAG", "Permission is granted");
//
////                                Intent share = new Intent(Intent.ACTION_SEND);
////
////                                // Se desejar compartilhar somente PNG use:
////                                // setType("image/png"); ou para jpeg: setType("image/jpeg");
////                                share.setType("image/*");
////
////                                // Necessario ter a imagem no diretorio
////                                String imagePath = Environment.getExternalStorageDirectory() + "/Pictures/teste/forcas.jpg";
////                                Log.i("path", imagePath);
////                                File imageFileToShare = new File(imagePath);
////
////                                Uri uri = Uri.fromFile(imageFileToShare);
////                                share.putExtra(Intent.EXTRA_STREAM, uri);
////
////                                startActivity(Intent.createChooser(share, "Compartilhar imagem"));
//
//                                FirebaseStorage storage = FirebaseStorage.getInstance();
//                                Log.i("entrada", "" + url);
//                                final StorageReference storageRef = storage.getReferenceFromUrl(url);
//                                image_name = storageRef.getName();
//                                Log.i("nome imagem", "" + image_name);
//
//                                //get download file url
//                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        Log.i("Main", "File uri: " + uri.toString());
//
//                                        //download the file
//                                        try {
//                                            // showProgressDialog("Download File", "Downloading File...");
//                                            final File localFile = File.createTempFile("images", "jpg");
//
//                                            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                                                @Override
//                                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                                                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//
//                                                    save(bitmap, image_name);
//                                                    //File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/teste/" + image_name);
//
//
//                                                    Intent share = new Intent(Intent.ACTION_SEND);
//
//                                                    // Se desejar compartilhar somente PNG use:
//                                                    // setType("image/png"); ou para jpeg: setType("image/jpeg");
//                                                    share.setType("image/*");
//
//                                                    // Necessario ter a imagem no diretorio
//                                                    String imagePath = Environment.getExternalStorageDirectory().getPath() + "/Pictures/teste/" + image_name;
//                                                    Log.i("path", imagePath);
//                                                    File imageFileToShare = new File(imagePath);
//
//                                                    Uri uri = Uri.fromFile(imageFileToShare);
//                                                    share.putExtra(Intent.EXTRA_STREAM, uri);
//
//                                                    startActivity(Intent.createChooser(share, "Compartilhar imagem"));
//                                                }
//                                            }).addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception exception) {
//
//                                                }
//                                            });
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                            Log.e("Main", "IOE Exception");
//                                        }
//
//                                    }
//                                });
////
//                            } else {
//                                Log.v("TAG", "Permission is revoked");
//                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                            }
//                        } else { //permission is automatically granted on sdk<23 upon installation
//                            Log.v("TAG", "Permission is granted");
//                        }
//
//
//                    }
//                });


//                zapImageButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (Build.VERSION.SDK_INT >= 23) {
//                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                    == PackageManager.PERMISSION_GRANTED) {
//                                Log.v("TAG", "Permission is granted");
////
////                                new Download_GIF(url).execute();
////                                Uri imageUri = Uri.parse( "/storage/emulated/0/Pictures/share/WhatsApp Image 2017-09-22 at 12.33.09.jpeg");
//////                                Uri u = Uri.parse(imageUri.toString());
//////                                Log.i("u", "" + u);
////                                Log.i("imageuri", "" + imageUri);
////
////                                Intent intent = new Intent(Intent.ACTION_SEND);
////                                intent.setType("image/*");
////                                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
////                                intent.putExtra(Intent.EXTRA_TEXT, "" + description);
////                                intent.setPackage("com.whatsapp");
////
////                                getActivity().startActivity(Intent.createChooser(intent, "Compartilhar imagem"));
//                                FirebaseStorage storage = FirebaseStorage.getInstance();
//
//                                //StorageReference storageRef = storage.getReferenceFromUrl(url);
//
//                                //StorageReference storageRef = storage.getReference();
//                                //StorageReference pathReference = storageRef.child("images/stars.jpg");
//
//
//                                final File localFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/teste/" + image_name);
//
//                                StorageReference f = storage.getReferenceFromUrl(url);
//                                image_name = f.getName();
//                                FileDownloadTask task = f.getFile(localFile);
//
//                                task.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//
//                                        save(bitmap, image_name);
//                                        //File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/teste/" + image_name);
//
//
//                                        Intent share = new Intent(Intent.ACTION_SEND);
//
//                                        // Se desejar compartilhar somente PNG use:
//                                        // setType("image/png"); ou para jpeg: setType("image/jpeg");
//                                        share.setType("image/*");
//
//                                        // Necessario ter a imagem no diretorio
//                                        String imagePath = Environment.getExternalStorageDirectory().getPath() + "/Pictures/teste/" + image_name;
//                                        Log.i("path", imagePath);
//                                        File imageFileToShare = new File(imagePath);
//
//                                        Uri uri = Uri.fromFile(imageFileToShare);
//                                        share.putExtra(Intent.EXTRA_STREAM, uri);
//
//                                        startActivity(Intent.createChooser(share, "Compartilhar imagem"));
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception exception) {
//                                        Log.e("firebase ", ";local tem file not created  created " + exception.toString());
//                                    }
//                                });
//
//
//                            } else {
//                                Log.v("TAG", "Permission is revoked");
//                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                            }
//                        } else { //permission is automatically granted on sdk<23 upon installation
//                            Log.v("TAG", "Permission is granted");
//                        }
//
//                    }
//                });
//                zapImageButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (Build.VERSION.SDK_INT >= 23) {
//                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                    == PackageManager.PERMISSION_GRANTED) {
//                                Log.v("TAG", "Permission is granted");
////
////                                new Download_GIF(url).execute();
////                                Uri imageUri = Uri.parse( "/storage/emulated/0/Pictures/share/WhatsApp Image 2017-09-22 at 12.33.09.jpeg");
//////                                Uri u = Uri.parse(imageUri.toString());
//////                                Log.i("u", "" + u);
////                                Log.i("imageuri", "" + imageUri);
////
////                                Intent intent = new Intent(Intent.ACTION_SEND);
////                                intent.setType("image/*");
////                                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
////                                intent.putExtra(Intent.EXTRA_TEXT, "" + description);
////                                intent.setPackage("com.whatsapp");
////
////                                getActivity().startActivity(Intent.createChooser(intent, "Compartilhar imagem"));
//
//                                FirebaseStorage storage = FirebaseStorage.getInstance();
//                                final StorageReference storageRef = storage.getReferenceFromUrl(url);
//                                storageRef.getName();
//                                Log.i("ref", "" + storageRef.getName());
//
//                                //get download file url
//                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        Log.i("Main", "File uri: " + uri.toString());
//
//                                        //download the file
//                                        try {
//                                            // showProgressDialog("Download File", "Downloading File...");
//                                            final File localFile = File.createTempFile("images", "jpg");
//                                            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                                                @Override
//                                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                                                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//
//                                                    // save(bitmap);
//                                                    String teste = "IMG_" + storageRef.getName();
//
//
//                                                    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/share");
//                                                    saveArrayToInternalStorage(teste, bitmap);
//
////                                                    Uri uri = Uri.fromFile(file);
//                                                    Uri u = Uri.parse(Uri.fromFile(file) + "/" + teste + ".jpg");
//                                                    Log.i("u", "" + u);
//                                                    Intent intent = new Intent(Intent.ACTION_SEND);
//                                                    intent.setType("image/*");
//                                                    intent.putExtra(Intent.EXTRA_STREAM, u);
//                                                    intent.putExtra(Intent.EXTRA_TEXT, "" + description);
//                                                    //intent.setPackage("com.whatsapp");
//
//                                                    getActivity().startActivity(Intent.createChooser(intent, "Compartilhar imagem"));
//
//                                                }
//                                            }).addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception exception) {
//
//                                                }
//                                            });
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                            Log.e("Main", "IOE Exception");
//                                        }
//
//                                    }
//                                });
//
//                            } else {
//                                Log.v("TAG", "Permission is revoked");
//                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                            }
//                        } else { //permission is automatically granted on sdk<23 upon installation
//                            Log.v("TAG", "Permission is granted");
//                        }
//
//                    }
//                });
                pDialog.dismiss();
            }
        };

        recyclerView.setAdapter(mFirebaseAdapter);
        return recyclerView;
    }

    @Override
    public void onStart() {
        super.onStart();
            }

    public void save(Bitmap pBitmap, String image_name) {

        try {
            File file = new File(getExternalStorageDirectory().getPath() + "//Pictures/");
            file.mkdir();

            File ifile = new File(file, "" + image_name);
            FileOutputStream outStream = new FileOutputStream(ifile);
            pBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();

        } catch (Exception e) {
            Log.e("Could not save", e.toString());
        }
    }


}