package me.aungkooo.firebase.activity;

/* Created by AKO on 11/12/2017 */

import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import me.aungkooo.firebase.R;

public abstract class FirebaseAuthActivity extends BaseActivity
{
    // request code can be variable
    public static final int REQUEST_GOOGLE_SIGN_IN = 600;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInListener googleSignInListener;
    private EmailSignInListener emailSignInListener;
    private EmailSignUpListener emailSignUpListener;
    private GoogleSignOutListener googleSignOutListener;

    public void setGoogleSignInListener(GoogleSignInListener googleSignInListener) {
        this.googleSignInListener = googleSignInListener;
    }

    public void setEmailSignInListener(EmailSignInListener emailSignInListener) {
        this.emailSignInListener = emailSignInListener;
    }

    public void setEmailSignUpListener(EmailSignUpListener emailSignUpListener) {
        this.emailSignUpListener = emailSignUpListener;
    }

    public void setGoogleSignOutListener(GoogleSignOutListener googleSignOutListener) {
        this.googleSignOutListener = googleSignOutListener;
    }

    public FirebaseAuth getFirebaseAuth()
    {
        if(firebaseAuth == null)
        {
            firebaseAuth = FirebaseAuth.getInstance();
        }

        return firebaseAuth;
    }

    public GoogleApiClient getGoogleApiClient()
    {
        if(googleApiClient == null)
        {
            GoogleSignInOptions googleOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            googleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
                        {
                            if(googleSignInListener != null)
                            {
                                googleSignInListener.onConnectionFailed(connectionResult);
                            }

                            if(googleSignOutListener != null)
                            {
                                googleSignOutListener.onConnectionFailed(connectionResult);
                            }
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, googleOptions)
                    .build();
        }

        return googleApiClient;
    }

    public void firebaseSignInWithGoogle(GoogleSignInAccount googleAccount)
    {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleAccount.getIdToken(), null);
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(googleSignInListener != null)
                        {
                            googleSignInListener.onGoogleSignInCompleted(task);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(googleSignInListener != null)
                        {
                            googleSignInListener.onGoogleSignInFailed(e);
                        }
                    }
                });
    }

    public void firebaseSignInWithEmailAndPassword(String email, String password)
    {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(emailSignInListener != null)
                        {
                            emailSignInListener.onEmailSignInCompleted(task);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(emailSignInListener != null)
                        {
                            emailSignInListener.onEmailSignInFailed(e);
                        }
                    }
                });
    }

    public void firebaseSignUpWithEmailAndPassword(String email, String password)
    {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(emailSignUpListener != null)
                        {
                            emailSignUpListener.onEmailSignUpCompleted(task);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(emailSignUpListener != null)
                        {
                            emailSignUpListener.onEmailSignUpFailed(e);
                        }
                    }
                });
    }

    public interface GoogleSignInListener
    {
        void onGoogleSignInCompleted(@NonNull Task<AuthResult> task);
        void onGoogleSignInFailed(@NonNull Exception e);
        void onConnectionFailed(ConnectionResult connectionResult);
    }

    public interface GoogleSignOutListener
    {
        void onConnectionFailed(ConnectionResult connectionResult);
    }

    public interface EmailSignInListener
    {
        void onEmailSignInCompleted(@NonNull Task<AuthResult> task);
        void onEmailSignInFailed(@NonNull Exception e);
    }

    public interface EmailSignUpListener
    {
        void onEmailSignUpCompleted(@NonNull Task<AuthResult> task);
        void onEmailSignUpFailed(@NonNull Exception e);
    }
}
