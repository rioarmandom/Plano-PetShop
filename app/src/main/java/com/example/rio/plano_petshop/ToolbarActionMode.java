package com.example.rio.plano_petshop;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

/**
 * Created by almanalfaruq on 18/11/2016.
 */

public class ToolbarActionMode implements ActionMode.Callback {

    private Context context;
    private CustomerAdapter customerAdapter;
    private List<Customer> customers;

    public ToolbarActionMode(Context context, CustomerAdapter customerAdapter, List<Customer> customers) {
        this.context = context;
        this.customerAdapter = customerAdapter;
        this.customers = customers;
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
                Fragment recyclerFragment = new MainMenu().getFragment(1);//Get recycler view fragment
                if (recyclerFragment != null)
                    //If recycler fragment not null
                    ((RecyclerViewFragment) recyclerFragment).deleteRows();//delete selected rows
                break;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        //When action mode destroyed remove selected selections and set action mode to null
        //First check current fragment action mode
        customerAdapter.removeSelection();  // remove selection
        Fragment recyclerFragment = new MainMenu().getFragment(1);//Get recycler fragment
        if (recyclerFragment != null)
            ((RecyclerViewFragment) recyclerFragment).setNullToActionMode();//Set action mode null
    }
}
