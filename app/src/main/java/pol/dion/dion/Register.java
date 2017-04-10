package pol.dion.dion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends Activity implements View.OnClickListener {

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
            Toast.makeText(this, "Please Enter a Valid Email", Toast.LENGTH_SHORT).show();

            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter a Valid Password", Toast.LENGTH_SHORT).show();

            return;
        }

        //using the sequence above to validate email and password

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //TODO Start Profile activity here
                    progressDialog.dismiss();
                    Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Register.this, "Please Try Again", Toast.LENGTH_LONG).show();
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
            //TODO login
        }
    }

}
