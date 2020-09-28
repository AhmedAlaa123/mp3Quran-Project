package com.example.qurantvproject.ui.recitersActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qurantvproject.Data.recitersActivityData.Reciter;
import com.example.qurantvproject.Data.recitersActivityData.RecitersPrain;
import com.example.qurantvproject.R;

import java.util.ArrayList;
import java.util.List;

public class RecitersAdapter extends RecyclerView.Adapter<RecitersAdapter.RecitersHolder> {
    private List<Reciter> reciters = new ArrayList<>();
    private OnItemClickListner listner;

    @NonNull
    @Override
    public RecitersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reciter_item, parent, false);

        return new RecitersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecitersHolder holder, int position) {
        holder.textViewReciter.setText(reciters.get(position).getName());
        holder.textViewRewaya.setText(reciters.get(position).getRewaya());

    }

    @Override
    public int getItemCount() {
        return reciters.size();
    }

    public void setReciters(RecitersPrain reciters) {
        this.reciters = reciters.getReciters();
        notifyDataSetChanged();
        Log.d("TAG", "setReciters: "+reciters.getReciters().size());
    }

    public void setOnItemClickListner(OnItemClickListner listner) {
        this.listner = listner;
    }

    public interface OnItemClickListner {
        void onItemClick(Reciter reciter);
    }

    private Reciter getReciter(int position) {
        return this.reciters.get(position);
    }

    class RecitersHolder extends RecyclerView.ViewHolder {
        public TextView textViewReciter;
        public TextView textViewRewaya;

        public RecitersHolder(@NonNull View itemView) {
            super(itemView);
            textViewReciter = itemView.findViewById(R.id.text_view_reciter);
            textViewRewaya = itemView.findViewById(R.id.text_view_rewaya);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listner != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listner.onItemClick(getReciter(getAdapterPosition()));
                    }
                }
            });

        }
    }

}
