package com.example.eve.eve.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eve.eve.R;
import com.example.eve.eve.model.ShortCurse;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.eve.eve.R.layout.shortcurse_frament_layout;
import static com.example.eve.eve.R.layout.show_data_layout2;

/**
 * Created by Detran_2 on 20/10/2017.
 */

public class ShortCurseFragment extends Fragment {

    DatabaseReference myRef;
    RecyclerView recyclerView;
    public TextView data, titulo, palestrante, local, horaInicio,horaFim;
    public FirebaseRecyclerAdapter<ShortCurse, ShortCurseFragment.ShowDataViewHolder> mFirebaseAdapter;
    String dia;
    String mes;
    String ano;

    public static ShortCurseFragment newInstance() {
        return new ShortCurseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(show_data_layout2, container, false);
        recyclerView = v.findViewById(R.id.show_data_recycler_view);

        titulo = v.findViewById(R.id.tv_titulo);
        data = v.findViewById(R.id.tv_data);
        palestrante = v.findViewById(R.id.tv_palestrante);
        local = v.findViewById(R.id.tv_local);
        horaInicio = v.findViewById(R.id.tv_horaInicio);
        horaFim = v.findViewById(R.id.tv_horaFim);
        myRef = FirebaseDatabase.getInstance().getReference("minicurso");

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null) {
                parent.removeView(v);
            }
        }
        try {
            v = inflater.inflate(R.layout.show_data_layout2, container, false);
        } catch (InflateException e) {

        }
        return recyclerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ShortCurse, ShowDataViewHolder>(ShortCurse.class, shortcurse_frament_layout, ShowDataViewHolder.class, myRef.orderByChild("periodoM")) {

            @Override
            protected void populateViewHolder(final ShowDataViewHolder viewHolder, final ShortCurse model, int position) {
//                final String u = viewHolder.Image_URL(model.getImage_URL());
                final String di = viewHolder.data(model.getPeriodoMI());
                final String df = viewHolder.data(model.getPeriodoMF());
                final String p = viewHolder.palestrante(model.getInstrutorM());
                final String l = viewHolder.local(model.getLocalM());
                final String t = viewHolder.data(model.getNomeM());
                final String hi = viewHolder.data(model.getHoraMI());
                final String hf = viewHolder.data(model.getHoraMF());
                final String i = viewHolder.data(model.getIdUser());


                data = viewHolder.itemView.findViewById(R.id.tv_data);
                titulo = viewHolder.itemView.findViewById(R.id.tv_titulo);
                palestrante = viewHolder.itemView.findViewById(R.id.tv_palestrante);
                local = viewHolder.itemView.findViewById(R.id.tv_local);
                horaInicio = viewHolder.itemView.findViewById(R.id.tv_horaInicio);
                horaFim = viewHolder.itemView.findViewById(R.id.tv_horaFim);

                dia = di.substring(8, 10);
                mes = di.substring(5, 7);
                ano = di.substring(0, 4);

                Log.i("dia", dia);
                Log.i("mes", mes);
                Log.i("ano", ano);


                titulo.setText(t);
                data.setText(converteData(di));
                palestrante.setText(p);
                local.setText(l);
                horaInicio.setText(hi + "h");
                horaFim.setText(hf + "h");

                Log.i("data", di);
                Log.i("data", t);
                Log.i("data", p);
                Log.i("data", i);
                Log.i("data", hi);
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
        final TextView data, titulo, palestrante, local, hora_inicio,hora_fim;


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

        String horario(String h) {
            hora_inicio.setText(h);
            return h;
        }

    }
}
