package me.aungkooo.firebase.viewholder;


import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class RecyclerViewHolder<OBJ> extends RecyclerView.ViewHolder
{
    private Context context;
    private View view;

    public RecyclerViewHolder(View itemView, Context context) {
        super(itemView);
        this.view = itemView;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public View getView() {
        return view;
    }

    public abstract void onBind(OBJ item);

    // ButterKnife
    @SuppressWarnings("unchecked")
    public <V extends View> V findById(@IdRes int id)
    {
        return (V) view.findViewById(id);
    }
}
