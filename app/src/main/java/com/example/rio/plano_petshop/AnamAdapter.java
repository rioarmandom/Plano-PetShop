package com.example.rio.plano_petshop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by almanalfaruq on 13/12/2016.
 */

public class AnamAdapter extends RecyclerView.Adapter<AnamAdapter.AnamViewHolder> {

    DatabaseHelper dbHelper;
    List<Anamnesa> anamnesaList = new ArrayList<>();
    SparseBooleanArray mSelectedItemsIds;
    Context context;

    public AnamAdapter(Context context, List<Anamnesa> anamnesaList) {
        dbHelper = new DatabaseHelper(context);
        this.anamnesaList = anamnesaList;
        this.context = context;
        mSelectedItemsIds = new SparseBooleanArray();
    }


    @Override
    public AnamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.anamnesa_row, parent, false);
        return new AnamViewHolder(mainGroup);
    }

    @Override
    public void onBindViewHolder(AnamViewHolder holder, int position) {
        holder.txtAnamnesa.setText(anamnesaList.get(position).getAnamnesa());
        holder.txtTeraphy.setText(anamnesaList.get(position).getTeraphy());
        holder.txtDate.setText(anamnesaList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return anamnesaList.size();
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }


    //Remove selected selections
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }


    //Put or delete selected position into SparseBooleanArray
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    //Get total selected count
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    //Return all selected ids
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    public static class AnamViewHolder extends RecyclerView.ViewHolder {

        TextView txtAnamnesa, txtTeraphy, txtDate;

        public AnamViewHolder(View itemView) {
            super(itemView);
            txtAnamnesa = (TextView) itemView.findViewById(R.id.txtAnamnesa);
            txtTeraphy = (TextView) itemView.findViewById(R.id.txtTeraphy);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
        }
    }
}
