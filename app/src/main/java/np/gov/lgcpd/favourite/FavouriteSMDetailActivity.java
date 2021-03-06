package np.gov.lgcpd.favourite;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import np.gov.lgcpd.AdaptersAndModel.SMDetails;
import np.gov.lgcpd.Helper.Constants;
import np.gov.lgcpd.R;
import np.gov.lgcpd.database.DatabaseHandler;

/**
 * Created by asis on 12/4/16.
 */
public class FavouriteSMDetailActivity extends AppCompatActivity {

    Toolbar toolbar;

    private String id;

    private TextView name, email, img_name, phone, address,
            lsp_id, hired, vdc, sex, dalit, janajati, dag,  education,
            work_experience, belong_to,  training,  entry_date,  last_date_modify,
            remarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sm_detail);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Favourite -SM");

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
        new GetDetailsOfSM().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favourite_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }

        if (id == R.id.action_delete){
            //delete from database
            DatabaseHandler handler = new DatabaseHandler(getApplicationContext(), Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
            handler.deleteSM(this.id);
            finish();
        }

        return super.onOptionsItemSelected(item);

    }

    private class GetDetailsOfSM extends AsyncTask<String, Void, SMDetails > {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(FavouriteSMDetailActivity.this);
            pd.setTitle("Getting data from Database");
            pd.setMessage("Connecting to Database");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected SMDetails doInBackground(String... params) {
            DatabaseHandler handler = new DatabaseHandler(getApplicationContext(), Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
            return handler.getSM(id);
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
