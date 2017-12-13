package me.aungkooo.firebase.viewholder;


import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.aungkooo.firebase.R;
import me.aungkooo.firebase.model.User;


public class MainViewHolder extends RecyclerViewHolder<User>
{
    private TextView txtUsername, txtEmail;
    private ImageView imgProfile;

    public MainViewHolder(View itemView, Context context)
    {
        super(itemView, context);
        txtUsername = findById(R.id.txt_username);
        txtEmail = findById(R.id.txt_email);
        imgProfile = findById(R.id.img_user_profile);
    }

    @Override
    public void onBind(User item)
    {
        if(item.getUsername() != null)
        {
            txtUsername.setText(item.getUsername());
        }

        if(item.getPhotoUrl() != null)
        {
            Picasso.with(getContext()).load(item.getPhotoUrl()).into(imgProfile);
        }

        txtEmail.setText(item.getEmail());
    }
}
