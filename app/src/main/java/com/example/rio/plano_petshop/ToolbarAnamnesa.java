package com.example.rio.plano_petshop;

import android.content.Context;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

/**
 * Created by almanalfaruq on 13/12/2016.
 */

public class ToolbarAnamnesa implements ActionMode.Callback {

    private Context context;
    private AnamAdapter anamAdapter;
    private List<Anamnesa> anamnesaList;
    private AnimalDetail animalDetail;

    public ToolbarAnamnesa(Context context, AnamAdapter anamAdapter, List<Anamnesa> anamnesaList, AnimalDetail animalDetail) {
        this.context = context;
        this.animalDetail = animalDetail;
        this.anamAdapter = anamAdapter;
        this.anamnesaList = anamnesaList;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        menu.findItem(R.id.action_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                animalDetail.deleteRows();//delete selected rows
                break;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        //When action mode destroyed remove selected selections and set action mode to null
        //First check current fragment action mode
        anamAdapter.removeSelection();  // remove selection
        animalDetail.setNullToActionMode();//Set action mode null
    }
}
