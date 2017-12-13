package me.aungkooo.firebase.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.aungkooo.firebase.R;
import me.aungkooo.firebase.model.User;
import me.aungkooo.firebase.viewholder.MainViewHolder;

public class MainAdapter extends RecyclerAdapter<MainViewHolder, User>
{

    public MainAdapter(Context context, ArrayList<User> itemList) {
        super(context, itemList);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.user, parent, false);

        return new MainViewHolder(view, getContext());
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position)
    {
        User item = getItemList().get(holder.getAdapterPosition());
        holder.onBind(item);
    }
}
