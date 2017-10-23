package com.settlement_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.kingdomdeath.R;
import com.settlement_tracker.TrackerInterfaces.OnDeletePopup;
import com.settlement_tracker.exceptions.InvalidName;
import com.settlement_tracker.fragments.popups.CreateSettlementAlert;
import com.settlement_tracker.fragments.pages.PlayerFragment;
import com.settlement_tracker.adapters.SettlementAdapter;
import com.settlement_tracker.fragments.popups.DeleteAlert;
import com.settlement_tracker.interfaces.IModifyItem;
import com.settlement_tracker.sqlite.model.Settlement;
import com.settlement_tracker.sqllite.dao.DBManager;
import com.settlement_tracker.sqllite.dao.SettlementDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CreateSettlementAlert.Communicator, IModifyItem, OnDeletePopup {
    private RecyclerView settlementRecycler;
    private List<Settlement> settlements;
    private SettlementAdapter namesAdapter;
    private SettlementDAO settlementDAO;
    private Settlement settlement;
    BottomNavigationView mBottomBar;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settlementDAO = new SettlementDAO(this);
        setContentView(R.layout.activity_main);
        manageDatabase();
        initBottomBar();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getSettlementData();
    }

    private void manageDatabase() {
        dbManager = new DBManager(this);
    }

    private void initRecyclerView() {
        settlementRecycler = (RecyclerView) findViewById(R.id.settlement_recycler);
        namesAdapter = new SettlementAdapter(settlements, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        settlementRecycler.setLayoutManager(mLayoutManager);
        settlementRecycler.setItemAnimator(new DefaultItemAnimator());
        settlementRecycler.setAdapter(namesAdapter);
    }

    private void getSettlementData() {
        settlements = settlementDAO.listSettlements();
        if (settlements.isEmpty()) {
            createNewSettlementAlert();
            return;
        } else {
            initRecyclerView();
            namesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override /*gets overridden from CreateSettlement.Communicator*/
    public void onDialogMessage(String name) {
        insertNewSettlement(name);
    }

    private void insertNewSettlement(String name) {
        Settlement s = new Settlement(name, 0, "");
        try {
            settlementDAO.insert(s);
        } catch (InvalidName e) {
            Toast.makeText(getApplicationContext(), SettlementDAO.ERROR_NAME_EXISTS, Toast.LENGTH_LONG).show();
            createNewSettlementAlert();
        }
        getSettlementData();
    }

    private void createNewSettlementAlert() {
        FragmentManager fm = getFragmentManager();
        CreateSettlementAlert dialog = CreateSettlementAlert.newInstance();
        dialog.show(fm, "Create Settlement");
    }

    @Override //TODO add import export functionality? Add delete all data functionality
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void initBottomBar() {
        mBottomBar = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                createNewSettlementAlert();
                return false;
            }
        });
    }

    @Override //TODO add import export functionality? Add delete all data functionality
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.del_database:
                DBManager.eraseTables(dbManager.getWritableDatabase());
                DBManager.createTables(dbManager.getWritableDatabase());
                settlements.clear();
                namesAdapter.notifyDataSetChanged();
                return true;
        }
        return false;
    }

    @Override /*gets overridden from SettlementAdapter.SettlementClickListener*/
    public void itemSelect(View view, int position) {
        String settlementName = ((TextView) view).getText().toString();
        settlement = settlementDAO.getSettlement(settlementName);
        Intent i = new Intent(MainActivity.this, PlayerFragment.class);
        i.putExtra(SettlementDAO.TABLE, settlement);
        startActivity(i);
    }

    @Override /*gets overridden from SettlementAdapter.SettlementClickListener*/
    public void itemDelete(View view, int position) {
        FragmentManager fm = getFragmentManager();
        DeleteAlert dialog = DeleteAlert.newInstance();
        String settlementName = ((TextView) view).getText().toString();
        dialog.setArgs(settlementName, "settlement", view, position);
        dialog.show(fm, "Create Settlement");
    }

    @Override
    public void delete(String name, View view, int pos) {
        settlement = settlementDAO.getSettlement(name);
        settlementDAO.delete(settlement);
        settlements.remove(pos);
        namesAdapter.notifyItemRemoved(pos);
        namesAdapter.notifyItemRangeChanged(pos, settlements.size());
    }
}
