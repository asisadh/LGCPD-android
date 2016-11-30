package np.gov.lgcpd.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import np.gov.lgcpd.Encryption.SecurePreferences;
import np.gov.lgcpd.Helper.Constants;
import np.gov.lgcpd.Helper.NetworkHelper;
import np.gov.lgcpd.MainActivity;
import np.gov.lgcpd.R;

/**
 * Created by asis on 11/26/16.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_FORGET = 0;
    private static final int REQUEST_REGISTER = 1;

    /**
     * Variables to refrence from xml file
     */
    private EditText email, password;
    private Button login;
    private TextView forgetPassword, signUp;

    private String txt_email;
    private String txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkUserHasAlreadyLoggedInOrNot();

        initilizeTheViews();
    }

    public void checkUserHasAlreadyLoggedInOrNot(){
        SharedPreferences prefs = new SecurePreferences(LoginActivity.this, Constants.SHARED_PREFRENCE_PASSWORD, Constants.SHARED_PREFRENCE_LOGIN_INFORMATION);

        if(prefs.getBoolean("is_already_login", false)){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            Toast.makeText(this,"User already Logged In",Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private void initilizeTheViews(){
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        forgetPassword = (TextView) findViewById(R.id.forget_password);
        signUp = (TextView) findViewById(R.id.sign_up);


        login.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login:
                startLogin();
                break;
            case R.id.forget_password:
                startActivityForResult(new Intent(LoginActivity.this, ForgetPasswordActivity.class),REQUEST_FORGET);
                break;
            case R.id.sign_up:
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class),REQUEST_REGISTER);
                break;
            default:
                //
        }
    }

    private void startLogin(){
        if(!validate()){
            onLoginFailed();
            return;
        }

        new LoginAsyncTask().execute(Constants.LOGIN_URL,txt_email,txt_password);

    }

    public boolean validate() {
        boolean valid = true;

        txt_email = email.getText().toString();
        txt_password = password.getText().toString();

        if (txt_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (txt_password.isEmpty() || password.length() < 4 ) {
            password.setError("password should be greater that 4 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }
        return valid;
    }

    public void onLoginSuccess() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        login.setEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_REGISTER) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }

        if (requestCode == REQUEST_FORGET) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    private class LoginAsyncTask extends AsyncTask<String, Integer, JSONObject> {

        ProgressDialog pd;
        String code,status,data ;
        String id, username, name, address, contact, auth;
        SharedPreferences prefs;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(LoginActivity.this);
            pd.setTitle("Login in Progress");
            pd.setMessage("Connecting to server");
            pd.setCancelable(false);
            pd.show();

             prefs = new SecurePreferences(LoginActivity.this, Constants.SHARED_PREFRENCE_PASSWORD, Constants.SHARED_PREFRENCE_LOGIN_INFORMATION);
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            pd.setMessage("Server is finishing the login process");

            Map map = new HashMap<String,String>();
            map.put("email",params[1]);
            map.put("password",params[2]);

            return NetworkHelper.getJSONFromUrlUsingPost(params[0],map);
        }

        @Override
        protected void onPostExecute(JSONObject jo) {
            super.onPostExecute(jo);
            pd.setMessage("Almost done.");
            try{
                code = jo.getString("code");
                status = jo.getString("status");
                data = jo.getString("data");

                if (status.equals("true")) {
                    JSONObject information = jo.getJSONObject("information");
                    id = information.getString("id");
                    username = information.getString("username");
                    name = information.getString("name");
                    address = information.getString("address");
                    contact = information.getString("contact");
                    auth = information.getString("auth");
                }

            }catch (JSONException ex){ex.printStackTrace();}

            System.out.println(code + '\n'+ status + '\n'
                    +data+ '\n'+id+ '\n'+ username+ '\n'
                    +name+ '\n' + address+ '\n' + contact+ '\n'+ auth);

            if(status.equals("true") && code.equals("1")){

                prefs.edit().putBoolean("is_already_login",true).apply();
                prefs.edit().putString("id", id).apply();
                prefs.edit().putString("name", name).apply();
                prefs.edit().putString("username", username).apply();
                prefs.edit().putString("address", address).apply();
                prefs.edit().putString("contact", contact).apply();
                prefs.edit().putString("auth", auth).apply();

                onLoginSuccess();
            }
            else
                onLoginFailed();
            pd.cancel();
        }
    }

}
