package com.example.rio.plano_petshop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by almanalfaruq on 19/11/2016.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Customer> customerList = new ArrayList<>();
    private Context context;
    private SparseBooleanArray mSelectedItemsIds;
    private DatabaseHelper databaseHelper;

    public CustomerAdapter(Context context, List<Customer> customerList) {
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
        this.customerList = customerList;
        mSelectedItemsIds = new SparseBooleanArray();

    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.customer_row, parent, false);
        return new CustomerViewHolder(mainGroup);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        holder.txtName.setText(customerList.get(position).getName());
        holder.txtPhone.setText(customerList.get(position).getPhone_no());
        holder.txtAnimal.setText(customerList.get(position).getAni_type());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
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

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtPhone, txtAnimal;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtPhone = (TextView) itemView.findViewById(R.id.txtPhone);
            txtAnimal = (TextView) itemView.findViewById(R.id.txtAnimal);
        }
    }
}
