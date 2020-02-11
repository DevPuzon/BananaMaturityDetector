package com.example.bananaanalyzer.AnalyzeModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bananaanalyzer.AdapterModule.ResultAdapter;
import com.example.bananaanalyzer.Pojo.HistoryPojo;
import com.example.bananaanalyzer.R;
import com.example.bananaanalyzer.Util.BitmapUtils;
import com.example.bananaanalyzer.Util.SaveTextFile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Result extends AppCompatActivity {

    private ArrayList<AnalyzeMain.AnalyzeData> analyzeDatas = new ArrayList<>();
    private ImageView img_banana;
    private TextView txt_type,txt_maturity,txt_week;
    private Button btn_save;
    private EditText txt_title,txt_desc;
    private RecyclerView recycler;
    private AnalyzeMain.AnalyzeData analyzeData;
    private String stringBitmap ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        init();
        initProperties();
    }

    private void initProperties() {
//        list lastBitmap
        analyzeDatas = new Gson().fromJson(getIntent().getStringExtra("list")
                ,new TypeToken<ArrayList<AnalyzeMain.AnalyzeData>>(){}.getType());
        stringBitmap= getIntent().getStringExtra("lastBitmap");
        img_banana.setImageBitmap(BitmapUtils.decodeBase64(stringBitmap));
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave();
            }
        });
        analyzeData = analyzeDatas.get(0);
        analyzeDatas.remove(0);

        ResultAdapter adapter  = new ResultAdapter(this,analyzeDatas);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);

        String[] titles = analyzeData.getTitle().split("-");
        String weekMaturity= getMaturity(titles[0],titles[1]);
        txt_type.setText(Html.fromHtml("<b> Type of Banana : </b> "+titles[0].substring(0, 1).toUpperCase() + titles[0].substring(1)));
        txt_maturity.setText(Html.fromHtml("<b> Maturity : </b>                    "+titles[1].substring(0, 1).toUpperCase() + titles[1].substring(1)));
        txt_week.setText(Html.fromHtml("<b> Week of maturity : </b>  "+weekMaturity));
    }

    private String getMaturity(String title,String maturity) {
        String ret="";

//    Cavendish
//       1-9weeks Under Mature
//       10-11weeks Mature
//       12weeks onwards Over Mature
//    SAB-A
//      1-13weeks Under Mature
//      14-15weeks Mature
//      16weeks Onwards Over Mature
//    Lakatan and Latondan
//         1-12weeks Under Mature
//         13-14weeks Mature
//         15weeks Onwards Over Mature
        if (title.equals("cavendish")){
            if (maturity.equals("mature")){
                ret = "10 - 11 weeks";
            }
            if (maturity.equals("ripe over mature")){
                ret = "12 weeks";
            }
            if (maturity.equals("under mature")){
                ret = "1 - 9 week(s)";
            }
        }
        if (title.equals("lakatan")){
            if (maturity.equals("mature")){
                ret = "14 - 15 weeks";
            }
            if (maturity.equals("ripe over mature")){
                ret = "16 weeks";
            }
            if (maturity.equals("under mature")){
                ret = "1 - 13 week(s)";
            }
        }
        if (title.equals("latondan")){
            if (maturity.equals("mature")){
                ret = "13 -14 weeks";
            }
            if (maturity.equals("ripe over mature")){
                ret = "15 weeks";
            }
            if (maturity.equals("under mature")){
                ret = "1 - 12 week(s)";
            }
        }
        if (title.equals("saba")){
            if (maturity.equals("mature")){
                ret = "13 -14 weeks";
            }
            if (maturity.equals("ripe over mature")){
                ret = "15 weeks";
            }
            if (maturity.equals("under mature")){
                ret = "1 - 12 week(s)";
            }
        }

        return ret;
    }

    private void init() {
        img_banana = (ImageView) findViewById(R.id.img_banana);
        txt_type = (TextView)findViewById(R.id.txt_typebanana);
        txt_maturity = (TextView) findViewById(R.id.txt_maturity);
        txt_week = (TextView) findViewById(R.id.txt_week);
        txt_title = (EditText) findViewById(R.id.txt_title);
        txt_desc = (EditText) findViewById(R.id.txt_desc);
        btn_save =(Button) findViewById(R.id.btn_save);
        recycler = (RecyclerView)findViewById(R.id.recycler);
    }

    private void onSave() {
        String title = txt_title.getText().toString();
        String desc = txt_desc.getText().toString();

        SimpleDateFormat timeFormatter = new SimpleDateFormat("MMM-dd-yyyy hh:mm a");
        String date = timeFormatter.format(new Date()).toString();
        if (!TextUtils.isEmpty(title)  && !TextUtils.isEmpty(desc)){
            HistoryPojo historyPojo =
                    new HistoryPojo(title,desc,analyzeData,analyzeDatas,
                            stringBitmap,date);
            String strPojo = SaveTextFile.getModel();
            ArrayList<HistoryPojo> historyPojos = new ArrayList<>();
            if (!TextUtils.isEmpty(strPojo)){
                historyPojos = new Gson().fromJson(strPojo,
                        new TypeToken<ArrayList<HistoryPojo>>(){}.getType());
            }
            historyPojos.add(historyPojo);
            SaveTextFile.saveData(new Gson().toJson(historyPojos));
            Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show();
            onBackPressed();
        }else{
            Toast.makeText(this,"Please fill all the data information",Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
