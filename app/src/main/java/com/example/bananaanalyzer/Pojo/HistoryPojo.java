package com.example.bananaanalyzer.Pojo;

import com.example.bananaanalyzer.AnalyzeModule.AnalyzeMain;

import java.util.ArrayList;

public class HistoryPojo {
    private String title;
    private String desc;
    private AnalyzeMain.AnalyzeData analyzeData;
    private ArrayList<AnalyzeMain.AnalyzeData> analyzeDatas;
    private String imageBitmap;
    private String createdAt;

    public HistoryPojo(String title, String desc, AnalyzeMain.AnalyzeData analyzeData, ArrayList<AnalyzeMain.AnalyzeData> analyzeDatas, String imageBitmap, String createdAt) {
        this.title = title;
        this.desc = desc;
        this.analyzeData = analyzeData;
        this.analyzeDatas = analyzeDatas;
        this.imageBitmap = imageBitmap;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public AnalyzeMain.AnalyzeData getAnalyzeData() {
        return analyzeData;
    }

    public void setAnalyzeData(AnalyzeMain.AnalyzeData analyzeData) {
        this.analyzeData = analyzeData;
    }

    public ArrayList<AnalyzeMain.AnalyzeData> getAnalyzeDatas() {
        return analyzeDatas;
    }

    public void setAnalyzeDatas(ArrayList<AnalyzeMain.AnalyzeData> analyzeDatas) {
        this.analyzeDatas = analyzeDatas;
    }

    public String getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(String imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
