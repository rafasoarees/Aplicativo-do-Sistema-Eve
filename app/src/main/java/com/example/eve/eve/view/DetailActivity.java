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

package com.example.eve.eve.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eve.eve.R;
import com.example.eve.eve.fragment.AboutFragment;
import com.example.eve.eve.fragment.LectureFragment;
import com.example.eve.eve.fragment.ShortCurseFragment;
import com.example.eve.eve.model.DataItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {
    // Maps values
    public static final String MAPS_INTENT_URI = "geo:0,0?q=";
    public static final String MAPS_NAVIGATION_INTENT_URI = "google.navigation:mode=w&q=";
    public static final String EXTRA_POSITION = "position";
    TextView placeLocation;
    TextView placeDescription;
    ImageView placeImage;
    private String url, description, title, data_inicio, data_fim, telephone, endereco, site, email, cidade, contenos, nome, id,estado;
    public static final String FRAGMENT_VIEWPAGER = "FRAGMENT_VIEWPAGER";
    public static final String FRAGMENT_FIRST = "FRAGMENT_FIRST";
    public static final String FRAGMENT_SECOND = "FRAGMENT_SECOND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle params = getIntent().getExtras();
        if (params != null) {

            title = params.getString("title");
            description = params.getString("description");
            url = params.getString("url");
            data_inicio = params.getString("data_inicio");
            data_fim = params.getString("data_fim");
            telephone = params.getString("telephone");
            endereco = params.getString("endereco");
            site = params.getString("site");
            cidade = params.getString("cidade");
            email = params.getString("email");
            contenos = params.getString("contenos");
            nome = params.getString("nome");
            id = params.getString("id");
            estado= params.getString("estado");

//            Log.i("p", "" + title);
//            Log.i("p", "" + description);
//            Log.i("p", "" + url);
//            Log.i("p", "" + data_inicio);
//            Log.i("p", "" + data_fim);
//            Log.i("p", "" + telephone);
//            Log.i("p", "" + endereco);
//            Log.i("p", "" + site);
//            Log.i("p", "" + cidade);
//            Log.i("p", "" + email);
//            Log.i("p", "" + contenos);
//            Log.i("p", "" + nome);
//            Log.i("p", "" + id);


        }


        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(title);
        // Set title of Detail page
        // collapsing_toolbar.setTitle(title);
        // collapsing_toolbar.setExpandedTitleTextAppearance(R.style.TransparentText);
        placeLocation = findViewById(R.id.tv_place);
        placeDescription = findViewById(R.id.tv_description);
        placeImage = findViewById(R.id.backdrop);


        loadBackdrop();

        ViewPager viewPager = findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public DataItems getMyData() {
        DataItems dataItems = new DataItems(url,
                title,
                description,
                cidade,
                endereco,
                contenos,
                data_fim,
                data_inicio,
                email,
                site,
                nome,
                telephone,
                id,
                estado);
        dataItems.setImage_Description(description);
        dataItems.setImage_Title(title);
        dataItems.setImage_URL(url);
        dataItems.setDataInicio(data_inicio);
        dataItems.setCidade(cidade);
        dataItems.setEmail(email);
        dataItems.setNome(nome);
        dataItems.setTelefone(telephone);
        dataItems.setIdUser(id);
        dataItems.setDataFim(data_fim);
        dataItems.setEnd(endereco);
        dataItems.setSite(site);
        dataItems.setContenos(contenos);
        dataItems.setEstado(estado);

        Log.i("p3title", "" + title);
        Log.i("p3description", "" + description);
        Log.i("p3url", "" + url);
        Log.i("p3data_inicio", "" + data_inicio);
        Log.i("p3data_fim", "" + data_fim);
        Log.i("p3telephone", "" + telephone);
        Log.i("p3endereco", "" + endereco);
        Log.i("p3cidade", "" + cidade);
        Log.i("p3email", "" + email);
        Log.i("p3site", "" + site);
        Log.i("p3contenos", "" + contenos);
        Log.i("p3nome", "" + nome);
        Log.i("p3id", "" + id);
        Log.i("p3estado", "" + estado);

        return dataItems;
    }

    private void setupViewPager(ViewPager viewPager) {
        DetailActivity.Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new AboutFragment(), "Sobre");
        adapter.addFragment(new LectureFragment(), "Palestras");
        adapter.addFragment(new ShortCurseFragment(), "Minicursos");

        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
    }

    private void loadBackdrop() {
        Glide.with(this).load(url).centerCrop().into(placeImage);
//        Picasso.with(this)
//                .load(url)
//                .resize(300, 300)
//                .centerCrop()
//                .into(placeImage);

    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
