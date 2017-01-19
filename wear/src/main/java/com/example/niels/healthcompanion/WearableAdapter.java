package com.example.niels.healthcompanion;

/**
 * Created by niels on 17/01/2017.
 */

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class WearableAdapter extends WearableListView.Adapter {
    private ArrayList<Pair<Integer, String>> mItems;
    private final LayoutInflater mInflater;

    public WearableAdapter(Context context, ArrayList<Pair<Integer, String>> items) {
        mInflater = LayoutInflater.from(context);
        mItems = items;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(
            ViewGroup viewGroup, int i) {
        return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder,
                                 int position) {
        Pair<Integer, String> ligne = mItems.get(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        CircledImageView circledView = itemViewHolder.mCircledImageView;
        circledView.setImageResource(ligne.first);
        TextView textView = itemViewHolder.mItemTextView;
        textView.setText(String.format(ligne.second, position + 1));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private static class ItemViewHolder extends WearableListView.ViewHolder {
        private CircledImageView mCircledImageView;
        private TextView mItemTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mCircledImageView = (CircledImageView)
                    itemView.findViewById(R.id.circle);
            mItemTextView = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
