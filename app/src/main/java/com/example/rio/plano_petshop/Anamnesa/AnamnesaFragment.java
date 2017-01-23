package com.example.rio.plano_petshop.Anamnesa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rio.plano_petshop.DatabaseHelper;
import com.example.rio.plano_petshop.Model.Anamnesa;
import com.example.rio.plano_petshop.R;
import com.example.rio.plano_petshop.RecyclerItemClickListener;
import com.example.rio.plano_petshop.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by almanalfaruq on 22/01/2017.
 */

public class AnamnesaFragment extends Fragment {

    private static View view;
    private static RecyclerView recyclerView;
    private static List<Anamnesa> anamnesaList = new ArrayList<>();
    private static AnamAdapter adapter;
    private ActionMode mActionMode;
    private DatabaseHelper databaseHelper;
    private int id, anam_id, ani_id;
    Bundle bundle;
    private String phone;

    public AnamnesaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedBundleInstance) {
        view = inflater.inflate(R.layout.recycler_view, container, false);
        databaseHelper = new DatabaseHelper(getActivity());
        bundle = this.getArguments();
        phone = bundle.getString("phone_no");
        ani_id = bundle.getInt("ani_id");
        anam_id = bundle.getInt("anam_id");
        populateRecyclerView();
        implementRecyclerViewClickListeners();
        return view;
    }

    //Populate ListView with dummy data
    private void populateRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        anamnesaList = databaseHelper.getAnamnesa(ani_id);
        adapter = new AnamAdapter(getActivity(), anamnesaList);
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
                else {
                    Anamnesa anamnesa = anamnesaList.get(position);
                    anam_id = anamnesa.getAnam_id();
                    String stAniID = String.valueOf(ani_id);
                    String stAnamID = String.valueOf(anam_id);
                    Intent intent = new Intent(getContext(), AnamnesaDetail.class);
                    intent.putExtra("phone_no", phone);
                    intent.putExtra("ani_id", stAniID);
                    intent.putExtra("anam_id", stAnamID);
                    startActivity(intent);
                }
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
            mActionMode = ((AppCompatActivity) getActivity())
                    .startSupportActionMode(new ToolbarAnamnesa(getActivity(),adapter, anamnesaList));
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
                Anamnesa anamnesa = anamnesaList.get(selected.keyAt(i));
                int anam_id = anamnesa.getAnam_id();
                databaseHelper.deletAnam(anam_id);
                anamnesaList.remove(selected.keyAt(i));

                adapter.notifyDataSetChanged();//notify adapter

            }
        }
        Toast.makeText(getActivity(), selected.size() + " item deleted.", Toast.LENGTH_SHORT).show();//Show Toast
        mActionMode.finish();//Finish action mode after use
    }

}
