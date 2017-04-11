package pol.dion.dion;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class test extends Activity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView tvEmail;
    private Button bLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvEmail.setText(test.this.getString(R.string.Welcome)+ user.getEmail());

        tvEmail = (TextView) findViewById(R.id.tvEmail);
        bLogout = (Button) findViewById(R.id.bLogout);

        bLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view == bLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

    }
}
