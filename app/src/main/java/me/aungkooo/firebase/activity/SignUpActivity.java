package me.aungkooo.firebase.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import me.aungkooo.firebase.R;
import me.aungkooo.firebase.Utility;
import me.aungkooo.firebase.model.User;


public class SignUpActivity extends FirebaseAuthActivity implements FirebaseAuthActivity.EmailSignUpListener
{
    private EditText editEmail, editPassword, editUsername;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getFirebaseAuth();
        setEmailSignUpListener(this);
        setSupportActionBarTitle(R.string.sign_up);

        userRef = FirebaseDatabase.getInstance().getReference(User.USERS);

        editEmail = findById(R.id.edit_email_sign_up);
        editPassword = findById(R.id.edit_password_sign_up);
        editUsername = findById(R.id.edit_username_sign_up);
    }

    @Override
    public void onBackPressed()
    {
        changeActivityAndFinish(SignInActivity.class);
    }

    public void signUpWithEmail(View v)
    {
        if(!Utility.isValidForm(editUsername, editEmail, editPassword))
        {
            return;
        }

        showProgressDialog("Signing up...");

        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        firebaseSignUpWithEmailAndPassword(email, password);
    }

    @Override
    public void onEmailSignUpCompleted(@NonNull Task<AuthResult> task)
    {
        stopProgressDialog();

        if(task.isSuccessful())
        {
            FirebaseUser fUser = task.getResult().getUser();

            addNewUser(fUser);
            changeActivityAndFinish(MainActivity.class);
        }
        else {
            getFirebaseAuth().signOut();

            String message = task.getException().getMessage();
            makeLongToast(message);
        }
    }

    @Override
    public void onEmailSignUpFailed(@NonNull Exception e) {

    }

    private void addNewUser(FirebaseUser fUser)
    {
        String username = editUsername.getText().toString();
        String email = fUser.getEmail();
        String password = editPassword.getText().toString();
        String userId = fUser.getUid();
        String photoUrl = null;
        if(fUser.getPhotoUrl() != null)
        {
             photoUrl = fUser.getPhotoUrl().toString();
        }

        User user = new User(username, email, password, photoUrl);

        userRef.child(userId).setValue(user);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
