package com.settlement_tracker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.john.kingdomdeath.R;
import com.settlement_tracker.interfaces.IModifyItem;
import com.settlement_tracker.sqlite.model.Settlement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 5/31/2017.
 */

public class SettlementAdapter extends RecyclerView.Adapter<SettlementAdapter.SettlementViewHolder> implements View.OnClickListener {
    private List<Settlement> settlementList = new ArrayList<>();
    private Context context;
    private RecyclerView.ViewHolder holder;
    private IModifyItem listener;

    public SettlementAdapter(List<Settlement> settlementList, IModifyItem listener) {
        this.settlementList = settlementList;
        this.listener = listener;
    }

    public class SettlementViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView numPlayers;
        private TextView notes;

        public SettlementViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            numPlayers = (TextView) itemView.findViewById(R.id.num_players);
            notes = (TextView) itemView.findViewById(R.id.notes);

        }
    }

    @Override
    public SettlementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.settlement_row, parent, false);
        return new SettlementViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SettlementViewHolder holder, int position) {
        this.holder = holder;
        Settlement settlementRow = settlementList.get(position);
        holder.name.setText(settlementRow.getName());
        holder.numPlayers.setText(Integer.toString(settlementRow.getCharacters().size()));
        holder.notes.setText("Delete");
        holder.name.setOnClickListener(this);
        holder.notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                listener.itemDelete(holder.name, pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return settlementList.size();
    }

    @Override
    public void onClick(View v) {
        listener.itemSelect(v, holder.getAdapterPosition());
    }
}
