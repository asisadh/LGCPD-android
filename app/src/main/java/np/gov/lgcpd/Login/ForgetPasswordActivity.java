package np.gov.lgcpd.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import np.gov.lgcpd.R;

/**
 * Created by asis on 9/11/16.
 */
public class ForgetPasswordActivity extends AppCompatActivity{

    private EditText email;
    private Button recover;
    private TextView backToSignIn,information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email = (EditText) findViewById(R.id.email);
        recover = (Button) findViewById(R.id.recover);
        information = (TextView) findViewById(R.id.information);
        backToSignIn = (TextView) findViewById(R.id.sign_in);

        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a function to send email to retrieve the password of the user
                if(!email.getText().equals("")) {
                    email.setText("");
                    information.setVisibility(View.VISIBLE);
                    information.setText("Check your email to recover your password");
                    information.setTextColor(getResources().getColor(R.color.md_green_500));
                    Toast.makeText(getApplication(), "Check your email to recover your password.", Toast.LENGTH_LONG).show();
                }else{
                    email.setText("");
                    information.setVisibility(View.VISIBLE);
                    information.setText("Enter your email");
                    Toast.makeText(getApplication(), "Enter your email", Toast.LENGTH_LONG).show();
                }
            }
        });

        backToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
