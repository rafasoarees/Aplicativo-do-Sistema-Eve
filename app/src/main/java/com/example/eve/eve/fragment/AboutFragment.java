package com.example.eve.eve.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eve.eve.R;
import com.example.eve.eve.model.DataItems;
import com.example.eve.eve.view.DetailActivity;

import static com.example.eve.eve.view.DetailActivity.MAPS_INTENT_URI;

/**
 * Created by Detran_2 on 20/10/2017.
 */

public class AboutFragment extends android.support.v4.app.Fragment {
    public static final String ARG_POSITION = "1";
    TextView tv_location;
    TextView tv_description;
    TextView tv_phone;
    TextView tv_datainicio;
    TextView tv_datafim;
    TextView tv_site;


    public AboutFragment newInstance(String description) {
        tv_description.setText(description);
        return new AboutFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_about, container, false);

        tv_location = v.findViewById(R.id.tv_place);
        tv_description = v.findViewById(R.id.tv_description);
        tv_phone = v.findViewById(R.id.tv_phone);
        tv_datainicio = v.findViewById(R.id.tv_datainicio);
        tv_datafim = v.findViewById(R.id.tv_datafim);
        tv_site = v.findViewById(R.id.tv_site);

        DetailActivity detailActivity = (DetailActivity) getActivity();

        DataItems myDataFromActivity = detailActivity.getMyData();

        tv_description.setText(myDataFromActivity.getImage_Description());
        tv_location.setText(myDataFromActivity.getEnd());
        tv_phone.setText(myDataFromActivity.getTelefone());
        tv_datainicio.setText(myDataFromActivity.getDataInicio());
        tv_datafim.setText(myDataFromActivity.getDataFim());
        tv_site.setText(myDataFromActivity.getSite());

        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(MAPS_INTENT_URI +
                        Uri.encode("" + tv_location.getText())));

                startActivity(intent);
            }
        });
        tv_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_VIEW);
                Log.i("site-teste",""+tv_site.getText());
                it.setData(Uri.parse("http://" + tv_site.getText()));
                startActivity(it);
            }
        });
        Log.i("r", "" + myDataFromActivity.getTelefone());
        Log.i("r", "" + myDataFromActivity.getDataInicio());
        Log.i("r", "" + myDataFromActivity.getEnd());


        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}