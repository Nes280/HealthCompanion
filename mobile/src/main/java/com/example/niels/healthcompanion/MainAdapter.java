package com.example.niels.healthcompanion;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by niels on 31/12/2016.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private ArrayList<Triple> characters;

    MainAdapter(ArrayList characters)
    {
        this.characters = characters;
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Triple triple = characters.get(position);
        holder.display(triple);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView description;

        private Triple currentTriple;

        public MyViewHolder(final View itemView) {
            super(itemView);

            name = ((TextView) itemView.findViewById(R.id.name));
            description = ((TextView) itemView.findViewById(R.id.description));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (currentTriple.getTroisieme())
                    {
                        case 2:
                            itemView.getContext().startActivity(new Intent(itemView.getContext(),PoulsActivity.class));
                         break;
                        case 3:
                            itemView.getContext().startActivity(new Intent(itemView.getContext(),AideActivity.class));
                            break;
                        case 4:
                            itemView.getContext().startActivity(new Intent(itemView.getContext(),NotificationActivity.class));
                            break;
                        case 5:
                            itemView.getContext().startActivity(new Intent(itemView.getContext(),ParametreActivity.class));
                            break;
                        default:
                            new AlertDialog.Builder(itemView.getContext())
                                    .setTitle(currentTriple.getPremier()+":")
                                    .setMessage("Unavailable :(")
                                    .show();
                         break;
                    }
                }
            });
        }

        public void display(Triple triple) {
            currentTriple = triple;
            name.setText(triple.getPremier());
            description.setText(triple.getDeuxieme());
        }

    }

}