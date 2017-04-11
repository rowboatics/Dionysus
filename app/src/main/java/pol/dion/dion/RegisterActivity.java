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

public class RegisterActivity extends Activity implements View.OnClickListener {

    private Button bRegister;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvLogin;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), test.class));
        }

        progressDialog = new ProgressDialog(this);

        bRegister = (Button) findViewById(R.id.bRegister);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        tvLogin = (TextView) findViewById(R.id.tvLogin);

        bRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    private  void registerUser() {
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

        progressDialog.setMessage(RegisterActivity.this.getString(R.string.PleaseWait));
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //TODO Start Profile activity here
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, R.string.RegisteredSuccessfully, Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    Toast.makeText(RegisterActivity.this, R.string.PleaseTryAgain, Toast.LENGTH_LONG).show();
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

        if (view == bRegister) {
            registerUser();
        }
        if (view == tvLogin) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

}
