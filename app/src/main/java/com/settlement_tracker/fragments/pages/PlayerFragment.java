package com.settlement_tracker.fragments.pages;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.john.kingdomdeath.R;
import com.settlement_tracker.TrackerInterfaces.OnDeletePopup;
import com.settlement_tracker.fragments.popups.CreatePlayerAlert;
import com.settlement_tracker.adapters.PlayerAdapter;
import com.settlement_tracker.fragments.popups.DeleteAlert;
import com.settlement_tracker.interfaces.IModifyItem;
import com.settlement_tracker.sqlite.model.Player;
import com.settlement_tracker.sqlite.model.Settlement;
import com.settlement_tracker.sqllite.dao.PlayerDAO;
import com.settlement_tracker.sqllite.dao.SettlementDAO;

import java.util.List;


/**
 * Created by John on 8/1/2017.
 */

public class PlayerFragment extends FragmentActivity implements CreatePlayerAlert.Communicator, IModifyItem, OnDeletePopup {
    PlayerDAO playerDAO;
    List<Player> characters;
    Settlement settlement;
    RecyclerView playerRecycler;
    PlayerAdapter adapter;
    BottomNavigationView mBottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerDAO = new PlayerDAO(this.getApplicationContext());
        getBundle();
        setContentView(R.layout.popup_characters);
        setWindowDimensions();
        initBottomBar();
        getPlayerData();
    }

    private void initBottomBar() {
        mBottomBar = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.new_player:
                        createNewPlayer();
                        return true;
                }
                return false;
            }
        });
    }

    private void initRecycler() {
        playerRecycler = (RecyclerView) findViewById(R.id.character_recycler);
        adapter = new PlayerAdapter(characters, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        playerRecycler.setLayoutManager(mLayoutManager);
        playerRecycler.setItemAnimator(new DefaultItemAnimator());
        playerRecycler.setAdapter(adapter);

    }

    private void getPlayerData() {
        if (characters == null || characters.isEmpty()) {
            createNewPlayer();
        } else {
            initRecycler();
            adapter.notifyDataSetChanged();
        }
    }

    private void createNewPlayer() {
        FragmentManager fm = getFragmentManager();
        CreatePlayerAlert dialog = CreatePlayerAlert.newInstance();
        dialog.show(fm, "Create Player");
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            settlement = bundle.getParcelable(SettlementDAO.TABLE);
            characters = settlement.getCharacters();
        }
    }

    @Override
    public void onDialogMessage(String name, int gender) {
        Player p = new Player(settlement.getId(), name, gender, "''");
        playerDAO.insert(p);
        addToSettlement(name);
        getPlayerData();
    }

    private void addToSettlement(String name) {
        Player p = playerDAO.getPlayer(name);
        characters = playerDAO.listPlayers(settlement);
        settlement.addCharacter(p);
    }

    private DisplayMetrics setWindowDimensions() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.8));
        return dm;
    }

    @Override
    public void itemSelect(View view, int position) {
        String name = ((TextView) view).getText().toString();
        Player p = playerDAO.getPlayer(name);
        Intent i = new Intent(PlayerFragment.this, SheetPager.class);
        i.putExtra(PlayerDAO.TABLE, p);
        startActivity(i);
    }

    @Override
    public void itemDelete(View view, int position){
        FragmentManager fm = getFragmentManager();
        DeleteAlert dialog = DeleteAlert.newInstance();
        String settlementName = ((TextView) view).getText().toString();
        dialog.setArgs(settlementName, "settlement", view, position);
        dialog.show(fm, "Create Settlement");
    }

    @Override
    public void delete(String name, View view, int pos) {
        Player player = playerDAO.getPlayer(name);
        playerDAO.delete(player);
        characters.remove(pos);
        adapter.notifyItemRemoved(pos);
        adapter.notifyItemRangeChanged(pos, characters.size());
    }
}
