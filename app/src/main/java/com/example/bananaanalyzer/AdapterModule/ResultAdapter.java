package com.example.bananaanalyzer.AdapterModule;
 
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bananaanalyzer.AnalyzeModule.AnalyzeMain;
import com.example.bananaanalyzer.R;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private String TAG = "TAG ResultAdapter";

    private final ArrayList<AnalyzeMain.AnalyzeData> analyzeDatas;
    private Context context;
    public ResultAdapter(Context context, ArrayList<AnalyzeMain.AnalyzeData>analyzeDatas) {
        this.context = context;
        this.analyzeDatas = analyzeDatas;
        Log.d(TAG,new Gson().toJson(this.analyzeDatas));
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_content_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        holder.txt_detected.setText(String.valueOf(analyzeDatas.get(i).getCount())+" count");
        holder.txt_title.setText(analyzeDatas.get(i).getTitle().substring(0, 1).toUpperCase() + analyzeDatas.get(i).getTitle().substring(1));
        int widthconf = 200;
        int finalwidth = (int) ((widthconf / 100f)*analyzeDatas.get(i).getConfidence());
        Log.d(TAG,String.valueOf(analyzeDatas.get(i).getConfidence()));
        Log.d(TAG,String.valueOf(finalwidth));
        Log.d(TAG,String.valueOf(new Gson().toJson(analyzeDatas.get(i))));
        holder.txt_confidence.setText(String.format("%.0f%% ",analyzeDatas.get(i).getConfidence()) );
//        holder.view_confidence.getLayoutParams().height = finalwidth;
//        holder.view_confidence.requestLayout();
        ViewGroup.LayoutParams params = holder.view_confidence.getLayoutParams();
        params.width = finalwidth;
        holder.view_confidence.setLayoutParams(params);
        holder.view_confidence.requestLayout();
    }

    @Override
    public int getItemCount() {
        return analyzeDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view_confidence ;
        TextView txt_detected,txt_confidence,txt_title;
        public ViewHolder(View view) {
            super(view);
             view_confidence = (View) view.findViewById(R.id.view_confidence);
             txt_detected = (TextView) view.findViewById(R.id.txt_detected);
             txt_confidence = (TextView) view.findViewById(R.id.txt_confidence);
            txt_title = (TextView) view.findViewById(R.id.txt_title);
        }
    }
}
