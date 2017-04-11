package pol.dion.dion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity implements View.OnClickListener {

    private Button bLogin;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvSignUp;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), test.class));
        }

        progressDialog = new ProgressDialog(this);

        bLogin = (Button) findViewById(R.id.bLogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        tvSignUp = (TextView) findViewById(R.id.tvSignUp);

        bLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    private  void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, R.string.ValidEmail, Toast.LENGTH_SHORT).show();

            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.ValidPassword, Toast.LENGTH_SHORT).show();

            return;
        }

        //using the sequence above to validate email and password

        progressDialog.setMessage(LoginActivity.this.getString(R.string.PleaseWait));
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), test.class));
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.PleaseTryAgain, Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });


    }



    @Override
    public void onClick(View view) {

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        if (view == bLogin) {
            loginUser();
        }
        if (view == tvSignUp) {
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

}
