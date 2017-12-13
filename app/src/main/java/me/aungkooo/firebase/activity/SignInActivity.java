package me.aungkooo.firebase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import me.aungkooo.firebase.R;
import me.aungkooo.firebase.Utility;
import me.aungkooo.firebase.model.User;


public class SignInActivity extends FirebaseAuthActivity implements FirebaseAuthActivity.GoogleSignInListener,
        FirebaseAuthActivity.EmailSignInListener
{
    private EditText editEmail, editPassword;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getGoogleApiClient();
        getFirebaseAuth();
        setGoogleSignInListener(this);
        setEmailSignInListener(this);
        setSupportActionBarTitle(R.string.sign_in);

        userRef = FirebaseDatabase.getInstance().getReference(User.USERS);

        editEmail = findById(R.id.edit_email_sign_in);
        editPassword = findById(R.id.edit_password_sign_in);
    }

    public void changeSignUp(View v)
    {
        changeActivityAndFinish(SignUpActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(getFirebaseAuth().getCurrentUser() != null)
        {
            // TODO something with current user
            changeActivityAndFinish(MainActivity.class);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQUEST_GOOGLE_SIGN_IN:
                    GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    if(signInResult.isSuccess())
                    {
                        GoogleSignInAccount googleAccount = signInResult.getSignInAccount();

                        showProgressDialog("Signing in...");
                        firebaseSignInWithGoogle(googleAccount);
                    }
                    break;
            }
        }
        else if(resultCode == RESULT_CANCELED)
        {
            // TODO something
        }
    }

    public void signInWithGoogle(View v)
    {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(getGoogleApiClient());
        startActivityForResult(intent, REQUEST_GOOGLE_SIGN_IN);
    }

    @Override
    public void onGoogleSignInCompleted(@NonNull Task<AuthResult> task)
    {
        stopProgressDialog();

        if(task.isSuccessful())
        {
            FirebaseUser fUser = task.getResult().getUser();
            addNewUser(fUser);

            changeActivityAndFinish(MainActivity.class);
        }
        else {
            Auth.GoogleSignInApi.signOut(getGoogleApiClient());

            String message = task.getException().getMessage();
            makeLongToast(message);
        }
    }

    @Override
    public void onGoogleSignInFailed(@NonNull Exception e) {

    }

    public void signInWithEmail(View v)
    {
        if(!Utility.isValidForm(editEmail, editPassword))
        {
            return;
        }

        showProgressDialog("Signing in...");

        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        firebaseSignInWithEmailAndPassword(email, password);
    }

    private void addNewUser(FirebaseUser fUser)
    {
        String username = fUser.getDisplayName();
        String email = fUser.getEmail();
        String userId = fUser.getUid();

        User user = new User(username, email);

        userRef.child(userId).setValue(user);
    }

    @Override
    public void onEmailSignInCompleted(@NonNull Task<AuthResult> task)
    {
        stopProgressDialog();

        if(task.isSuccessful())
        {
            changeActivityAndFinish(MainActivity.class);
        }
        else{
            String message = task.getException().getMessage();
            makeLongToast(message);
        }
    }

    @Override
    public void onEmailSignInFailed(@NonNull Exception e) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
