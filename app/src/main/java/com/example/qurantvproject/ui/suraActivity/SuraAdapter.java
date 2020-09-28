package com.example.qurantvproject.ui.suraActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qurantvproject.Data.suraActivityData.Sura;
import com.example.qurantvproject.R;

import java.util.ArrayList;
import java.util.List;

public class SuraAdapter extends RecyclerView.Adapter<SuraAdapter.SuraHolder> {
    private List<Sura> suras = new ArrayList<>();
    private OnItemClickListner listner;

    @NonNull
    @Override
    public SuraHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.soura_item, parent, false);

        return new SuraHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuraHolder holder, int position) {
        holder.textViewSuraName.setText(suras.get(position).getSuraNumber());

    }

    @Override
    public int getItemCount() {
        return this.suras.size();
    }
    public void setSuras(List<Sura>suras)
    {
        this.suras=suras;
        notifyDataSetChanged();
    }

    private Sura getSura(int position) {
        return this.suras.get(position);
    }

    class SuraHolder extends RecyclerView.ViewHolder {

        public TextView textViewSuraName;

        public SuraHolder(@NonNull View itemView) {
            super(itemView);
            textViewSuraName = itemView.findViewById(R.id.text_view_soura_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listner != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listner.onItemClick(getSura(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public interface OnItemClickListner {
        void onItemClick(Sura sura);
    }
    public void setOnItemClickListner(OnItemClickListner listner)
    {
        this.listner=listner;
    }
}
