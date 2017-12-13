package me.aungkooo.firebase.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import me.aungkooo.firebase.viewholder.RecyclerViewHolder;

public abstract class RecyclerAdapter<VH extends RecyclerViewHolder, OBJ> extends RecyclerView.Adapter<VH>
{
    private Context context;
    private ArrayList<OBJ> itemList;

    public RecyclerAdapter(Context context, ArrayList<OBJ> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public ArrayList<OBJ> getItemList() {
        return itemList;
    }

    public Context getContext() {
        return context;
    }
}
