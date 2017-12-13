package me.aungkooo.firebase.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;

import me.aungkooo.firebase.R;


public class MainActivity extends FirebaseAuthActivity implements FirebaseAuthActivity.GoogleSignOutListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getGoogleApiClient();
        getFirebaseAuth();
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

            default:
                return false;
        }
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
