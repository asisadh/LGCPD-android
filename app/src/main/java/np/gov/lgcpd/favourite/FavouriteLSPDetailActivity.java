package np.gov.lgcpd.favourite;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import np.gov.lgcpd.AdaptersAndModel.LSPDetail;
import np.gov.lgcpd.Helper.Constants;
import np.gov.lgcpd.Helper.NetworkHelper;
import np.gov.lgcpd.R;
import np.gov.lgcpd.database.DatabaseHandler;

/**
 * Created by asis on 12/2/16.
 */
public class FavouriteLSPDetailActivity extends AppCompatActivity {

    Toolbar toolbar;

    private String id;

    private TextView name, address, officePhone,
            contactPerson, chairman,
            contactEmail, contactPhone,
            contactMobile, chairmanMobile,
            chairmanEmail,
            remarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsp_detail);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Favourite -LSP");

        id = getIntent().getStringExtra("id");

        setView();

        filTheInformation();
    }

    public void setView(){
        name = (TextView) findViewById(R.id.name);
        address = (TextView) findViewById(R.id.address);
        officePhone = (TextView) findViewById(R.id.office_phone);
        contactPerson = (TextView) findViewById(R.id.contact_person);
        chairman = (TextView) findViewById(R.id.chairman);
        contactEmail = (TextView) findViewById(R.id.contact_email);
        contactPhone = (TextView) findViewById(R.id.contact_phone);
        contactMobile = (TextView) findViewById(R.id.contact_mobile);
        chairmanMobile = (TextView) findViewById(R.id.chairman_phone);
        chairmanEmail = (TextView) findViewById(R.id.chairman_email);
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
            DatabaseHandler handler = new DatabaseHandler(getApplicationContext(), Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
            handler.deleteLSP(this.id);
            finish();
        }

        return super.onOptionsItemSelected(item);

    }

    private class GetDetailsOfSM extends AsyncTask<String, Void, LSPDetail> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(FavouriteLSPDetailActivity.this);
            pd.setTitle("Getting data from Server");
            pd.setMessage("Connecting to server");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected LSPDetail doInBackground(String... params) {
            DatabaseHandler handler = new DatabaseHandler(getApplicationContext(), Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
            return handler.getLSP(id);
        }

        @Override
        protected void onPostExecute(LSPDetail details) {

            name.setText(details.getName());
            address.setText(details.getAddress());
            officePhone.setText(details.getOfficePhone());
            contactPerson.setText(details.getContactPerson());
            chairman.setText(details.getChairman());
            contactEmail.setText(details.getContactEmail());
            contactPhone.setText(details.getContactPhone());
            contactMobile.setText(details.getContactMobile());
            chairmanMobile.setText(details.getChairmanMobile());
            chairmanEmail.setText(details.getChairmanEmail());
            remarks.setText(details.getRemark());

            pd.cancel();
        }
    }
}
