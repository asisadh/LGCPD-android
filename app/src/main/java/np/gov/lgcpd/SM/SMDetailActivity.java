package np.gov.lgcpd.SM;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import np.gov.lgcpd.AdaptersAndModel.SMDetails;
import np.gov.lgcpd.Helper.Constants;
import np.gov.lgcpd.Helper.NetworkHelper;
import np.gov.lgcpd.R;
import np.gov.lgcpd.database.DatabaseHandler;

/**
 * Created by asis on 12/2/16.
 */
public class SMDetailActivity extends AppCompatActivity {

    Toolbar toolbar;

    private String id;

    private TextView name, email, img_name, phone, address,
            lsp_id, hired, vdc, sex, dalit, janajati, dag,  education,
            work_experience, belong_to,  training,  entry_date,  last_date_modify,
            remarks;

    private SMDetails details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sm_detail);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("SM");

        id = getIntent().getStringExtra("id");

        setView();

        filTheInformation();
    }

    public void setView(){
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        //img_name = (TextView) findViewById(R.id.image);
        phone = (TextView) findViewById(R.id.phone);
        address = (TextView) findViewById(R.id.address);
        lsp_id = (TextView)findViewById(R.id.lsp);
        hired = (TextView) findViewById(R.id.hired);
        vdc = (TextView) findViewById(R.id.vdc);
        sex = (TextView) findViewById(R.id.sex);
        dalit = (TextView) findViewById(R.id.dalit);
        janajati = (TextView) findViewById(R.id.janajati);
        dag = (TextView) findViewById(R.id.dag);
        education = (TextView) findViewById(R.id.eduaction);
        work_experience = (TextView) findViewById(R.id.work_experience);
        belong_to = (TextView) findViewById(R.id.belong_to);
        training = (TextView) findViewById(R.id.training);
        remarks = (TextView) findViewById(R.id.remarks);
    }

    public void filTheInformation(){
        String URL = Constants.SM_DETAIL_API + id ;

        if(NetworkHelper.isNetworkAvailable(getApplicationContext())){
            new GetDetailsOfSM().execute(URL);
        }else{
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }

        if (id == R.id.action_favourite){
            DatabaseHandler handler = new DatabaseHandler(getApplicationContext(), Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
            if(handler.addSM(details))
                Toast.makeText(getApplicationContext(),"Added to favourite.",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(),"Already in database",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);

    }

    private class GetDetailsOfSM extends AsyncTask<String, Void, SMDetails>{

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SMDetailActivity.this);
            pd.setTitle("Getting data from Server");
            pd.setMessage("Connecting to server");
            pd.setCancelable(false);
            pd.show();
        }

        private SMDetails createSMDetailObject(JSONObject jo){
            try {
                String id = jo.getString("id");
                String name = jo.getString("name");
                String email = jo.getString("email");
                String img_name = jo.getString("img_name");
                String phone = jo.getString("phone");
                String address = jo.getString("address");
                String lsp_id = jo.getString("lsp_id");
                String hired = jo.getString("hired");
                String vdc = jo.getString("vdc");
                String sex = jo.getString("sex");
                String dalit = jo.getString("dalit");
                String janajati = jo.getString("janajati");
                String dag = jo.getString("dag");
                String education = jo.getString("education");
                String work_experience = jo.getString("work_experience");
                String belong_to = jo.getString("belong_to");
                String training = jo.getString("training");
                String entry_date = jo.getString("entry_date");
                String last_date_modify = jo.getString("last_date_modify");
                String remarks = jo.getString("remarks");
                String district_id = jo.getString("district_id");
                String type = jo.getString("type");

                details =  new SMDetails(id, name, email, img_name, phone, address,
                        lsp_id, hired, vdc, sex, dalit, janajati, dag,  education,
                        work_experience, belong_to,  training,  entry_date,  last_date_modify,
                        remarks, district_id, type
                );

                return details;

                }catch (JSONException ex){ex.printStackTrace();}

            return null;
        }

        @Override
        protected SMDetails doInBackground(String... params) {
            return createSMDetailObject(NetworkHelper.getJSONObjectFromUrlUsingGet(params[0]));
        }

        @Override
        protected void onPostExecute(SMDetails details) {

            name.setText(details.getName());
            email.setText(details.getEmail());
            //img_name
            phone.setText(details.getPhone());
            address.setText(details.getAddress());
            lsp_id.setText(details.getLsp_id());
            hired.setText(details.getHired());
            vdc.setText(details.getVdc());
            sex.setText(details.getSex());
            dalit.setText(details.getDalit());
            janajati.setText(details.getJanajati());
            dag.setText(details.getDag());
            education.setText(details.getEducation());
            work_experience.setText(details.getWork_experience());
            belong_to.setText(details.getBelong_to());
            training.setText(details.getTraining());
            remarks.setText(details.getRemarks());

            pd.cancel();
        }
    }

}
