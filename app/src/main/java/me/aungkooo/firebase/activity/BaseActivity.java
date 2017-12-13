package me.aungkooo.firebase.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import me.aungkooo.firebase.R;


public abstract class BaseActivity extends AppCompatActivity
{
    public final String TAG = getClass().getSimpleName();
    private ProgressDialog progressDialog;

    public void showProgressDialog(String message)
    {
        if(progressDialog == null)
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(message);
            progressDialog.setIndeterminate(true);
        }

        progressDialog.show();
    }

    public void stopProgressDialog()
    {
        if(progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }

    public void makeLongToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void makeShortToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setSupportActionBarTitle(@StringRes int title)
    {
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(title);
        }
    }

    public void changeActivityAndFinish(Class to)
    {
        Intent intent = new Intent(this, to);
        if(intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
        finish();
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V findById(@IdRes int id)
    {
        return (V) findViewById(id);
    }
}
