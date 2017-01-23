package com.example.rio.plano_petshop.Animal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rio.plano_petshop.DatabaseHelper;
import com.example.rio.plano_petshop.Model.Animal;
import com.example.rio.plano_petshop.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by almanalfaruq on 22/11/2016.
 */

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private List<Animal> animalsList = new ArrayList<>();
    private Context context;
    private SparseBooleanArray mSelectedItemsIds;
    private DatabaseHelper databaseHelper;

    public AnimalAdapter(Context context, List<Animal> animalList) {
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
        this.animalsList = animalList;
        mSelectedItemsIds = new SparseBooleanArray();

    }

    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.animal_row, parent, false);
        return new AnimalViewHolder(mainGroup);
    }

    @Override
    public void onBindViewHolder(AnimalViewHolder holder, int position) {
        holder.txtAniID.setText(String.valueOf(animalsList.get(position).getAni_id()));
        holder.txtAniName.setText(animalsList.get(position).getAni_name());
        holder.txtAniType.setText(animalsList.get(position).getAni_type());
        holder.txtAniAge.setText(String.valueOf(animalsList.get(position).getAni_age()));
        holder.txtAniSex.setText(animalsList.get(position).getAni_sex());
    }

    @Override
    public int getItemCount() {
        return animalsList.size();
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

    public static class AnimalViewHolder extends RecyclerView.ViewHolder {

        TextView txtAniID, txtAniName, txtAniType, txtAniAge, txtAniSex;

        public AnimalViewHolder(View itemView) {
            super(itemView);
            txtAniID = (TextView) itemView.findViewById(R.id.txtAniID);
            txtAniName = (TextView) itemView.findViewById(R.id.txtAniName);
            txtAniType = (TextView) itemView.findViewById(R.id.txtAniType);
            txtAniAge = (TextView) itemView.findViewById(R.id.txtAniAge);
            txtAniSex = (TextView) itemView.findViewById(R.id.txtAniSex);
        }
    }

}
