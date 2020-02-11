package com.example.bananaanalyzer.AdapterModule;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananaanalyzer.AnalyzeModule.AnalyzeMain;
import com.example.bananaanalyzer.HistoryModule.ViewHistory;
import com.example.bananaanalyzer.Pojo.HistoryPojo;
import com.example.bananaanalyzer.R;
import com.example.bananaanalyzer.Util.BitmapUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private String TAG = "TAG HistoryAdapter";

    private final ArrayList<HistoryPojo> historyPojos;
    private Context context;
    public HistoryAdapter(Context context, ArrayList<HistoryPojo>historyPojos) {
        this.context = context;
        this.historyPojos = historyPojos;
        Log.d(TAG,new Gson().toJson(this.historyPojos));
    }
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_content, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryAdapter.ViewHolder holder, final int i) {
        final HistoryPojo historyPojo = historyPojos.get(i);
        historyPojo.getTitle();
        holder.img_banana.setImageBitmap(BitmapUtils.decodeBase64(historyPojo.getImageBitmap()));

        String[] titles = historyPojo.getAnalyzeData().getTitle().split("-");
        String weekMaturity= getMaturity(titles[0],titles[1]);
        holder.txt_type.setText(Html.fromHtml("<b> Type of Banana : </b> "+titles[0].substring(0, 1).toUpperCase() + titles[0].substring(1)));
        holder.txt_maturity.setText(Html.fromHtml("<b> Maturity : </b>                    "+titles[1].substring(0, 1).toUpperCase() + titles[1].substring(1)));
        holder.txt_week.setText(Html.fromHtml("<b> Week of maturity : </b>  "+weekMaturity));
        holder.txt_createdAt.setText(historyPojo.getCreatedAt());
        holder.txt_title.setText(historyPojo.getTitle());
        holder.txt_desc.setText(historyPojo.getDesc());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ViewHistory.class)
                        .putExtra("historyPojo",new Gson().toJson(historyPojo));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_banana;
        private TextView txt_type,txt_maturity,txt_week,txt_createdAt,
                txt_title,txt_desc;
        private CardView card;
        public ViewHolder(View view) {
            super(view);
            img_banana = (ImageView) view.findViewById(R.id.img_banana);
            txt_type = (TextView)view.findViewById(R.id.txt_typebanana);
            txt_maturity = (TextView) view.findViewById(R.id.txt_maturity);
            txt_week = (TextView) view.findViewById(R.id.txt_week);
            txt_createdAt = (TextView) view.findViewById(R.id.txt_createdAt);
            txt_title = (TextView) view.findViewById(R.id.txt_title);
            txt_desc = (TextView) view.findViewById(R.id.txt_desc);
            card = (CardView) view.findViewById(R.id.card);
        }
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

}
