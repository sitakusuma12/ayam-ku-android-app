package com.example.nando.ayamku;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class AyamAdapter extends RecyclerView.Adapter<AyamAdapter.AyamViewHolder> {
    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    private ArrayList<Ayam> dataList;
    private Activity activity;
    private ItemClickListener clickListener;
    private Context context;



    public AyamAdapter(ArrayList<Ayam> dataList, Activity activity, Context context) {
        this.dataList = dataList;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public AyamAdapter.AyamViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
        return new AyamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AyamAdapter.AyamViewHolder ayamViewHolder, int i) {
        AssetManager manager = activity.getAssets();
        InputStream inputStream;
        try {
            inputStream = manager.open(dataList.get(i).getImage()+".jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            System.out.print(dataList.get(i).getImage()+".jpg");
            ayamViewHolder.ayamImage.setImageBitmap(bitmap);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ayamViewHolder.ayamName.setText(dataList.get(i).getName());
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        ayamViewHolder.ayamPrice.setText("Rp " + decimalFormat.format(dataList.get(i).getPrice()));
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class AyamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ayamImage;
        private TextView ayamName;
        private TextView ayamPrice;
        private CardView cardView;

        public AyamViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            ayamImage = (ImageView) view.findViewById(R.id.ayamImage);
            ayamName = (TextView) view.findViewById(R.id.ayamName);
            ayamPrice = (TextView) view.findViewById(R.id.ayamPrice);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            ayamImage.setOnClickListener(this);
            ayamName.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }


    }
}
