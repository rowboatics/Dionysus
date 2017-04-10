package pol.dion.dion;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends Activity implements View.OnClickListener {

    private Button bRegister;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bRegister = (Button) findViewById(R.id.bRegister);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword= (EditText) findViewById(R.id.etPassword);

        tvLogin = (TextView) findViewById(R.id.tvLogin);

        bRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

        @Override
        public void onClick(View view) {
            if (view == bRegister) {
                registerUser();
            }
            if (view == tvLogin) {
                //TODO login
            }
        }

    }
}
