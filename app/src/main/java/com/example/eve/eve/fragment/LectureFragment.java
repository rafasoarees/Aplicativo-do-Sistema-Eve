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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eve.eve.R;
import com.example.eve.eve.model.Lecture;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.eve.eve.R.layout.lecture_frament_layout;


public class LectureFragment extends Fragment {

    DatabaseReference myRef;
    RecyclerView recyclerView;
    public TextView data, titulo, palestrante, local, hora_inicio, hora_fim;
    public FirebaseRecyclerAdapter<Lecture, ShowDataViewHolder> mFirebaseAdapter;
    String dia;
    String mes;
    String ano;

    public static LectureFragment newInstance() {
        return new LectureFragment();
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

        titulo = v.findViewById(R.id.tv_titulo);
        data = v.findViewById(R.id.tv_data);
        palestrante = v.findViewById(R.id.tv_palestrante);
        local = v.findViewById(R.id.tv_local);
        hora_inicio = v.findViewById(R.id.tv_horaInicio);
        hora_fim = v.findViewById(R.id.tv_horaFim);
        myRef = FirebaseDatabase.getInstance().getReference("palestra");


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Lecture, ShowDataViewHolder>(Lecture.class, lecture_frament_layout, ShowDataViewHolder.class, myRef.orderByChild("dataP")) {


            @Override
            protected void populateViewHolder(final ShowDataViewHolder viewHolder, final Lecture model, int position) {
//                final String u = viewHolder.Image_URL(model.getImage_URL());
                final String d = viewHolder.data(model.getDataP());
                final String t = viewHolder.titulo(model.getTituloP());
                final String n = viewHolder.palestrante(model.getNomeP());
                final String l = viewHolder.local(model.getLocalP());
                final String hi = viewHolder.hora_inicio(model.getHoraPI());
                final String hf = viewHolder.hora_fim(model.getHoraPF());

                data = viewHolder.itemView.findViewById(R.id.tv_data);
                titulo = viewHolder.itemView.findViewById(R.id.tv_titulo);
                palestrante = viewHolder.itemView.findViewById(R.id.tv_palestrante);
                local = viewHolder.itemView.findViewById(R.id.tv_local);
                hora_inicio = viewHolder.itemView.findViewById(R.id.tv_horaInicio);
                hora_fim = viewHolder.itemView.findViewById(R.id.tv_horaFim);

                titulo.setText(t);
                data.setText(converteData(d));
                palestrante.setText(n);
                local.setText(l);
                hora_inicio.setText(hi);
                hora_fim.setText(hf);
                hora_inicio.setText(hi + "h");
                hora_fim.setText(hf + "h");

                Log.i("data", d);
                Log.i("titulo", t);
                Log.i("nome", n);
                Log.i("local", l);
                Log.i("horaInicio", hi);
                Log.i("horaFim", hf);
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


    //View Holder For Recycler View
    public static class ShowDataViewHolder extends RecyclerView.ViewHolder {
        final TextView data, titulo, palestrante, local, hora_inicio, hora_fim;


        public ShowDataViewHolder(final View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tv_titulo);
            data = itemView.findViewById(R.id.tv_data);
            palestrante = itemView.findViewById(R.id.tv_palestrante);
            local = itemView.findViewById(R.id.tv_local);
            hora_inicio = itemView.findViewById(R.id.tv_horaInicio);
            hora_fim = itemView.findViewById(R.id.tv_horaFim);

        }

        String data(String d) {
            data.setText(d);
            return d;
        }

        String titulo(String t) {
            titulo.setText(t);
            return t;
        }

        String palestrante(String p) {
            palestrante.setText(p);
            return p;
        }

        String local(String l) {
            local.setText(l);
            return l;
        }

        String hora_inicio(String hi) {
            hora_inicio.setText(hi);
            return hi;
        }

        String hora_fim(String hf) {
            hora_fim.setText(hf);
            return hf;
        }

    }
}