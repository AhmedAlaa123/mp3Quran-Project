package com.example.qurantvproject.ui.activityMain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qurantvproject.Data.activityMainData.Language;
import com.example.qurantvproject.R;

import java.util.ArrayList;
import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageHolder> {
    private List<Language> languages = new ArrayList<>();
    private OnItemClickListenr listner;

    @NonNull
    @Override
    public LanguageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_item, parent, false);
        return new LanguageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageHolder holder, int position) {
        holder.textViewLanguage.setText(languages.get(position).getLanguage());
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    private Language getItem(int position) {
        return languages.get(position);
    }

    public void setItemClickListner(OnItemClickListenr listner) {
        this.listner = listner;
    }

    class LanguageHolder extends RecyclerView.ViewHolder {
        public TextView textViewLanguage;

        public LanguageHolder(@NonNull View itemView) {
            super(itemView);
            textViewLanguage = itemView.findViewById(R.id.text_view_language);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listner != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listner.onItemClick(getItem(getAdapterPosition()));
                    }
                }
            });

        }
    }

    public interface OnItemClickListenr {
        void onItemClick(Language language);
    }
}
