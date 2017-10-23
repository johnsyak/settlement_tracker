package com.settlement_tracker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.john.kingdomdeath.R;
import com.settlement_tracker.interfaces.IModifyItem;
import com.settlement_tracker.sqlite.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 7/31/2017.
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> implements View.OnClickListener{
    List<Player> players = new ArrayList<>();
    private RecyclerView.ViewHolder holder;
    private Context context;
    private IModifyItem listener;

    public PlayerAdapter(List<Player> playerList, IModifyItem listener){
        this.players = playerList;
        this.listener = listener;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.popup_characters_row, parent, false);
        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PlayerViewHolder holder, int position) {
        this.holder = holder;
        Player playerRow = players.get(position);
        holder.name.setText(playerRow.getName());
        holder.name.setOnClickListener(this);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= holder.getAdapterPosition();
                listener.itemDelete(holder.name, pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    @Override
    public void onClick(View v) {
        listener.itemSelect(v, holder.getAdapterPosition());
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView delete;
        public PlayerViewHolder(View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.character_row);
            delete = (TextView) itemView.findViewById(R.id.delete);
        }
    }
}
