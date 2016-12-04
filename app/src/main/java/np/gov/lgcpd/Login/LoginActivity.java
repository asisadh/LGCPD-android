package np.gov.lgcpd.Login;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import np.gov.lgcpd.Encryption.SecurePreferences;
import np.gov.lgcpd.Helper.Constants;
import np.gov.lgcpd.Helper.NetworkHelper;
import np.gov.lgcpd.R;

/**
 * Created by asis on 11/26/16.
 */
public class LoginActivity extends AppCompatActivity{

    /**
     * Variables to refrence from xml file
     */
    private EditText email, password;
    private Button login;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeTheViews();
    }

    private void initializeTheViews(){
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkHelper.isNetworkAvailable(getApplicationContext())){
                    startLogin();
                }else{
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startLogin(){

        pd = new ProgressDialog(LoginActivity.this);
        pd.setTitle("Login in Progress");
        pd.setMessage("Connecting to server");
        pd.setCancelable(false);
        pd.show();

        String txt_email = email.getText().toString();
        String txt_password = password.getText().toString();
        if(!txt_email.equals("") && !txt_password.equals("") ){
            new LoginAsyncTask().execute(Constants.LOGIN_URL,txt_email, txt_password);
        }else{
            Toast.makeText(getApplicationContext(),"Empty Field",Toast.LENGTH_SHORT).show();
        }
    }

    private void onSuccess(){
        finish();
    }

    private void onFailed(){
        Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
    }


    private class LoginAsyncTask extends AsyncTask<String, Void, String> {

        String code,status,data ;
        String id, username, name, address, contact, auth;
        SharedPreferences prefs;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd.setTitle("Login in Progress");
            pd.setMessage("Waiting For Server Response");

            prefs = new SecurePreferences(LoginActivity.this, Constants.SHARED_PREFRENCE_PASSWORD, Constants.SHARED_PREFRENCE_LOGIN_INFORMATION);
        }

        private String parseJSONObjectAndStorePreference(JSONObject jo){
            status = "false";

            if (jo != null){
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
                       // auth = information.getString("auth");
                    }

                }catch (JSONException ex){ex.printStackTrace();}

                if(status.equals("true") && code.equals("1")){

                    prefs.edit().putBoolean("is_already_login",true).apply();
                    prefs.edit().putString("id", id).apply();
                    prefs.edit().putString("name", name).apply();
                    prefs.edit().putString("username", username).apply();
                    prefs.edit().putString("address", address).apply();
                    prefs.edit().putString("contact", contact).apply();
                    prefs.edit().putString("auth", auth).apply();
                }
            }

            return status;
        }

        @Override
        protected String doInBackground(String... params) {

            Map map = new HashMap<String,String>();
            map.put("email",params[1]);
            map.put("password",params[2]);

            JSONObject jo = NetworkHelper.getJSONFromUrlUsingPost(params[0],map);

            String status = parseJSONObjectAndStorePreference(jo);

            return status;
        }

        @Override
        protected void onPostExecute(String status) {
            super.onPostExecute(status);
            pd.setMessage("Almost done.");

            pd.cancel();

            if (status.equals("true"))
                onSuccess();
            else
                onFailed();
        }
    }

}
