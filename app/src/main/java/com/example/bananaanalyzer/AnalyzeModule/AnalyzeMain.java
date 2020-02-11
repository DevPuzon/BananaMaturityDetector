package com.example.bananaanalyzer.AnalyzeModule;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.bananaanalyzer.CameraActivity;
import com.example.bananaanalyzer.R;
import com.example.bananaanalyzer.TensorflowApi.Classifier;
import com.example.bananaanalyzer.TensorflowApi.TensorflowUtil;
import com.example.bananaanalyzer.Util.BitmapUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeMain extends CameraActivity {
    private String TAG = "TAG AnalyzeMain";
    private ImageView btn_detect,btn_back;
    private LinearLayout loading;
    private Bitmap lastbitmap ;
    private ArrayList<Classifier.Recognition> recognitions = new ArrayList<Classifier.Recognition>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analyze_main);
        Log.d(TAG, "oncreate AnalyzeMain");
        init();
        initProperties();
    }

    private void init() {
        String graph;
        String labels;
        graph = "file:///android_asset/retrained_graph.pb";
        labels = "file:///android_asset/retrained_labels.txt";
        tensorflow = new TensorflowUtil.Tensorflow(this,graph,labels);

        btn_detect = (ImageView) findViewById(R.id.btn_detect);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        loading = (LinearLayout) findViewById(R.id.loading);

        setiCameraActivity(new ICameraActivity() {
            @Override
            public void tensorflowResult(List<Classifier.Recognition> results) {
                if (isStart){
                    if (count > score){
                        if (highConf > 50){
                            Validation();
                            isStart = false;
                        }
                        count = 0;
                        recognitions.clear();
                    }else{
                        //add element
                        Log.d(TAG,new Gson().toJson(results));
                        for (int i = 0 ; i < results.size();i++){
                            recognitions.add(results.get(i));
                            if (results.get(i).getConfidence() > 50){
                                highConf = results.get(i).getConfidence();
                            }
                        }
                    }
                    count ++;
                }
            }

            @Override
            public void lastPicture(Bitmap bitmap) {
                if (isStart){
                    lastbitmap = bitmap;
                }
            }
        });
    }

    private void initProperties() {
        loading.setVisibility(View.GONE);
        btn_detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetect();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private int count = 0 ;
    private int score = 50;
    private float highConf = 0;
    private boolean isStart = false;
    private void onDetect() {
        loading.setVisibility(View.VISIBLE);
        btn_detect.setVisibility(View.GONE);
        isStart = true;
    }





    //////////////////////// backend
    private void Validation() {
        ArrayList<AnalyzeData> analyzeDatas = new ArrayList<>();

        //set the count each title
        for (int i = 0 ; i < recognitions.size() ;i++){
            Classifier.Recognition recognition = recognitions.get(i);
            boolean isset = false;
            for (int x = 0 ; x < analyzeDatas.size() ; x ++){
                AnalyzeData analyzeData = analyzeDatas.get(x);
                if (recognition.getTitle().equals(analyzeData.getTitle())
                        && analyzeDatas.size() > 0){
                    analyzeData.setCount(analyzeData.getCount()+1);

                    analyzeData.setConfidence(checkHighConf(recognition.getConfidence(),analyzeData.getConfidence()));
                    analyzeDatas.set(x,analyzeData);
                    x = analyzeDatas.size() + 1;
                    isset = true;
                }
            }
            if (!isset){
                AnalyzeData analyzeData = new AnalyzeData(recognition.getTitle(),
                        recognition.getConfidence(),0);
                analyzeDatas.add(analyzeData);
            }
        }
        Log.d(TAG + " result", new Gson().toJson(analyzeDatas));

        //ranking
        analyzeDatas = getFinalConf(analyzeDatas);
        analyzeDatas = getRank(analyzeDatas);
        Log.d(TAG + " result ranked", new Gson().toJson(analyzeDatas));

        onResult(analyzeDatas);
    }

    private void onResult(ArrayList<AnalyzeData> analyzeDatas) {
        String strBitmap =BitmapUtils.compStringBitmap(BitmapUtils.encodeTobase64(lastbitmap),
                300);
        Intent intent = new Intent(AnalyzeMain.this,Result.class)
                .putExtra("list",new Gson().toJson(analyzeDatas))
                .putExtra("lastBitmap", strBitmap) ;
        startActivity(intent);
    }

    private ArrayList<AnalyzeData> getFinalConf(ArrayList<AnalyzeData> analyzeDatas) {
        ArrayList<AnalyzeData> ret = new ArrayList<>();
        for (int i = 0 ; i < analyzeDatas.size() ; i ++){
            AnalyzeData analyzeData = analyzeDatas.get(i);
            float conf = analyzeData.getConfidence();
            Log.d(TAG, "conf "+String.valueOf(conf));
            int count = analyzeData.getCount();
            Log.d(TAG, "count "+String.valueOf(count));
//            int scorePercent = (int) (score *0.15);
            int scorePercent = (int) (score - (score *0.25));
            Log.d(TAG, "scorePercent "+scorePercent);


            int xConf = (int) ((70f / 80f)*conf);
            Log.d(TAG, "xConf "+String.valueOf(xConf));
            int xCount = (int) ((30f / scorePercent)*count);
            Log.d(TAG, "xConf "+String.valueOf(xConf));

            float finalConf = xConf + xCount;
            analyzeData.setConfidence(finalConf);
            ret.add(analyzeData);
        }
        return ret;
    }

    private ArrayList<AnalyzeData> getRank(ArrayList<AnalyzeData> analyzeDatas) {
        ArrayList<AnalyzeData> ret = analyzeDatas;
        int size = ret.size();
        for (int i = 0 ; i < size ;i++){
            AnalyzeData analyzeData = ret.get(i);
            Log.d(TAG,"find "+new Gson().toJson(analyzeData));
            float secInt = analyzeData.getCount();
            AnalyzeData passAnalyzedata = analyzeData;
            int index = 0;
            boolean isGreat = false;
            for (int x = i+1 ; x < size ;x++){
                float nowCount = ret.get(x).getCount();
                if (analyzeData.getCount() < nowCount && secInt < nowCount ){
                    secInt = nowCount;
                    passAnalyzedata = ret.get(x);
                    Log.d(TAG,"greater "+
                            String.valueOf(secInt)+" | "+String.valueOf(nowCount));
                    index = x;
                    isGreat = true;
                }
            }
            if (isGreat){
                ret.set(i,passAnalyzedata);
                ret.remove(index);
                ret.add(analyzeData);
                Log.d(TAG,new Gson().toJson(ret));
            }
        }
        return ret;
    }

    private float checkHighConf(Float passConf, float nowConf) {
        float ret = 0 ;
        if (passConf > nowConf){
            ret = passConf;
        }else{
            ret = nowConf;
        }
        return ret;
    }


    public class AnalyzeData{
        private String title;
        private float confidence;
        private int count;
        public AnalyzeData(){}
        public AnalyzeData(String title, float confidence, int count) {
            this.title = title;
            this.confidence = confidence;
            this.count = count;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public float getConfidence() {
            return confidence;
        }

        public void setConfidence(float confidence) {
            this.confidence = confidence;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
