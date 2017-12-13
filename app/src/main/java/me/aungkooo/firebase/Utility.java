package me.aungkooo.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.widget.EditText;


public class Utility
{
    private static final String SIGNED_IN = "LoggedIn";

    public static boolean isSignedIn(Context context)
    {
        String prefKey = context.getString(R.string.pref_key);
        SharedPreferences pref = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE);

        return pref.getBoolean(SIGNED_IN, false);
    }

    public static void signOut(Context context)
    {
        String prefKey = context.getString(R.string.pref_key);
        SharedPreferences pref = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(SIGNED_IN, false);
        editor.apply();
    }

    public static boolean isValidForm(EditText editEmail, EditText editPassword)
    {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        boolean valid = false;

        if(email.isEmpty())
        {
            editEmail.setError("Required");
            editEmail.requestFocus();
        }
        else if(password.isEmpty())
        {
            editPassword.setError("Required");
            editPassword.requestFocus();
        }
        else if(!isValidEmail(email))
        {
            editEmail.setError("Invalid");
            editEmail.requestFocus();
        }
        else if(!isValidPassword(password))
        {
            editPassword.setError("At least 6 characters");
            editPassword.requestFocus();
        }
        else {
            valid = true;
        }

        return valid;
    }

    public static boolean isValidForm(EditText editUsername, EditText editEmail, EditText editPassword)
    {
        String username = editUsername.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        boolean valid = false;

        if(username.isEmpty())
        {
            editUsername.setError("Required");
            editUsername.requestFocus();
        }
        else if(email.isEmpty())
        {
            editEmail.setError("Required");
            editEmail.requestFocus();
        }
        else if(password.isEmpty())
        {
            editPassword.setError("Required");
            editPassword.requestFocus();
        }
        else if(!isValidEmail(email))
        {
            editEmail.setError("Invalid");
            editEmail.requestFocus();
        }
        else if(!isValidPassword(password))
        {
            editPassword.setError("At least 6 characters");
            editPassword.requestFocus();
        }
        else {
            valid = true;
        }

        return valid;
    }

    private static boolean isValidEmail(String email)
    {
        return email.contains("@") && email.contains(".com");
    }

    private static boolean isValidPassword(String password)
    {
        return password.length() >= 6;
    }

    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
