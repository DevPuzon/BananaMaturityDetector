package com.example.bananaanalyzer.HistoryModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.bananaanalyzer.AdapterModule.HistoryAdapter;
import com.example.bananaanalyzer.Pojo.HistoryPojo;
import com.example.bananaanalyzer.R;
import com.example.bananaanalyzer.Util.SaveTextFile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class HistoryMain extends AppCompatActivity {

    private RecyclerView recycler;
    private ArrayList<HistoryPojo> historyPojos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_main);
        init();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void init() {
        recycler = (RecyclerView) findViewById(R.id.recycler);
        historyPojos = new Gson().fromJson(SaveTextFile.getModel(),
                new TypeToken<ArrayList<HistoryPojo>>(){}.getType());
        if (historyPojos != null){
            HistoryAdapter adapter = new HistoryAdapter(this,historyPojos);
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(new LinearLayoutManager(this));
            recycler.setHasFixedSize(true);
        }else{
            findViewById(R.id.nodata).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
