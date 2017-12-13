package me.aungkooo.firebase.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import me.aungkooo.firebase.R;
import me.aungkooo.firebase.adapter.MainAdapter;
import me.aungkooo.firebase.model.User;


public class MainActivity extends FirebaseAuthActivity implements FirebaseAuthActivity.GoogleSignOutListener
{
    private ArrayList<User> userList;
    private MainAdapter mainAdapter;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setGoogleSignOutListener(this);
        getGoogleApiClient();
        getFirebaseAuth();

        userRef = FirebaseDatabase.getInstance().getReference(User.USERS);

        initializeActionBar();

        userList = new ArrayList<>();

        RecyclerView recyclerView = findById(R.id.recycler_main);
        mainAdapter = new MainAdapter(this, userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainAdapter);
    }

    private void initializeActionBar()
    {
        String username = null;
        String email = null;
        if (getFirebaseAuth().getCurrentUser() != null)
        {
            username = getFirebaseAuth().getCurrentUser().getDisplayName();
            email = getFirebaseAuth().getCurrentUser().getEmail();
        }

        if(username != null)
        {
            setSupportActionBarTitle(username);
        }
        else {
            setSupportActionBarTitle(R.string.username);
        }

        if(email != null)
        {
            setSupportActionBarSubTitle(email);
        }
        else {
            setSupportActionBarSubTitle(R.string.email);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(userList.size() == 0)
        {
            loadUserList();
        }
    }

    private void loadUserList()
    {
        showProgressDialog("Loading users...");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    for (DataSnapshot each: dataSnapshot.getChildren())
                    {
                        User user = each.getValue(User.class);
                        userList.add(user);
                    }

                    mainAdapter.notifyDataSetChanged();
                }

                stopProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                stopProgressDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.menu_sign_out:
                confirmSignOut();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void confirmSignOut()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning)
                .setMessage(R.string.sign_out_message)
                .setPositiveButton(R.string.sign_out, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        signOut();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    private void signOut()
    {
        getFirebaseAuth().signOut();

        if(getGoogleApiClient().hasConnectedApi(Auth.GOOGLE_SIGN_IN_API))
        {
            Auth.GoogleSignInApi.signOut(getGoogleApiClient());
        }

        changeActivityAndFinish(SignInActivity.class);
        makeLongToast("Signed out");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
