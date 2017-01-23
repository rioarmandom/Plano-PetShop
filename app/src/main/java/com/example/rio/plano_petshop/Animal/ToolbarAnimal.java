package com.example.rio.plano_petshop.Animal;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rio.plano_petshop.Model.Animal;
import com.example.rio.plano_petshop.R;

import java.util.List;

/**
 * Created by almanalfaruq on 22/11/2016.
 */

public class ToolbarAnimal implements ActionMode.Callback {

    private Context context;
    private AnimalAdapter animalAdapter;
    private List<Animal> animals;

    public ToolbarAnimal(Context context, AnimalAdapter animalAdapter, List<Animal> animalList) {
        this.context = context;
        this.animalAdapter = animalAdapter;
        this.animals = animalList;
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
                Fragment recyclerFragment = new AnimalMenu().getFragment(0);//Get recycler view fragment
                if (recyclerFragment != null)
                    //If recycler fragment not null
                    ((AnimalFragment) recyclerFragment).deleteRows();//delete selected rows
                break;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        //When action mode destroyed remove selected selections and set action mode to null
        //First check current fragment action mode
        animalAdapter.removeSelection();  // remove selection
        Fragment recyclerFragment = new AnimalMenu().getFragment(0);//Get recycler fragment
        if (recyclerFragment != null)
            ((AnimalFragment) recyclerFragment).setNullToActionMode();//Set action mode null
    }
}