package apps.lore_f.cloudifier;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private final static String TAG = "MainActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* inizializza il FirebaseAuth */
        firebaseAuth = FirebaseAuth.getInstance();

        /* ottiene l'user corrente */
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null) {
            // autenticazione non effettuata

            // lancia la SignInActivity e termina l'attività corrente
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainactivity_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.sign_out_menuEntry:

                // è stato selezionata l'opzione di sign out dal menu
                firebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(googleApiClient);

                //mUsername=ANONYMOUS;
                recreate();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

}
