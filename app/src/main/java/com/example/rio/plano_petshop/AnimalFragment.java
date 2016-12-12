package com.example.rio.plano_petshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by almanalfaruq on 22/11/2016.
 */

public class AnimalFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private static List<Animal> animalList = new ArrayList<>();
    private AnimalAdapter adapter;
    private ActionMode mActionMode;
    private DatabaseHelper databaseHelper;
    Bundle bundle;
    String phone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recycler_view, container, false);
        databaseHelper = new DatabaseHelper(getActivity());
        bundle = this.getArguments();
        if (bundle!=null) {
            phone = bundle.getString("phone_no");
        }
        populateRecyclerView();
        implementRecyclerViewClickListeners();
        return view;
    }

    //Populate ListView with dummy data
    private void populateRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d("Phone", phone);
        int cust_id = databaseHelper.getIdCust(phone);
        animalList = databaseHelper.getAnimal(cust_id);
        adapter = new AnimalAdapter(getActivity(), animalList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //Implement item click and long click over recycler view
    private void implementRecyclerViewClickListeners() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //If ActionMode not null select item
                if (mActionMode != null)
                    onListItemSelect(position);
                Animal animal = animalList.get(position);
                String ani_id = String.valueOf(animal.getAni_id());
                String ani_name = animal.getAni_name();
                String ani_type = animal.getAni_type();
                String ani_age = String.valueOf(animal.getAni_age());
                String ani_sex = animal.getAni_sex();
                Intent intent = new Intent(getActivity(), AnimalDetail.class);
                intent.putExtra("phone_no", phone);
                intent.putExtra("ani_id", ani_id);
                intent.putExtra("ani_name", ani_name);
                intent.putExtra("ani_type", ani_type);
                intent.putExtra("ani_age", ani_age);
                intent.putExtra("ani_sex", ani_sex);
                startActivity(intent);
//                Toast.makeText(getActivity(), "Anda memilih " + nama, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                //Select item on long click
                onListItemSelect(position);
            }
        }));
    }

    //List item select method
    private void onListItemSelect(int position) {
        adapter.toggleSelection(position);//Toggle the selection

        boolean hasCheckedItems = adapter.getSelectedCount() > 0;//Check if any items are already selected or not


        if (hasCheckedItems && mActionMode == null)
            // there are some selected items, start the actionMode
            mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new ToolbarAnimal(getActivity(), adapter, animalList));
        else if (!hasCheckedItems && mActionMode != null)
            // there no selected items, finish the actionMode
            mActionMode.finish();

        if (mActionMode != null)
            //set action mode title on item selection
            mActionMode.setTitle(String.valueOf(adapter
                    .getSelectedCount()) + " selected");
        adapter.getItemId(position);

    }

    //Set action mode null after use
    public void setNullToActionMode() {
        if (mActionMode != null)
            mActionMode = null;
    }

    //Delete selected rows
    public void deleteRows() {
        SparseBooleanArray selected = adapter.getSelectedIds();//Get selected ids


        //Loop all selected ids
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item via key
                animalList.remove(selected.keyAt(i));

                adapter.notifyDataSetChanged();//notify adapter

            }
        }
        Toast.makeText(getActivity(), selected.size() + " item deleted.", Toast.LENGTH_SHORT).show();//Show Toast
        mActionMode.finish();//Finish action mode after use

    }
}
