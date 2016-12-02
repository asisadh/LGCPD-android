package np.gov.lgcpd.LSP;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import np.gov.lgcpd.AdaptersAndModel.LSPDetail;
import np.gov.lgcpd.AdaptersAndModel.SMDetails;
import np.gov.lgcpd.Helper.Constants;
import np.gov.lgcpd.Helper.NetworkHelper;
import np.gov.lgcpd.R;

/**
 * Created by asis on 12/2/16.
 */
public class LSPDetailActivity extends AppCompatActivity {

    Toolbar toolbar;

    private String id;

    private TextView name, address, officePhone,
            contactPerson, chairman,
            contactEmail, contactPhone,
            contactMobile, chairmanMobile,
            chairmanEmail,
            remarks;
//
//    name
//    address
//    officePhone,
//    contactPerson,
//    chairman,
//    contactEmail,
//    contactPhone,
//    contactMobile,
//    chairmanMobile,
//    chairmanEmail,
//    remark

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsp_detail);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        String URL = Constants.LSP_DETAIL_API + id ;

        new GetDetailsOfSM().execute(URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sm_detail_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);

    }

    private class GetDetailsOfSM extends AsyncTask<String, Void, LSPDetail> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(LSPDetailActivity.this);
            pd.setTitle("Getting data from Server");
            pd.setMessage("Connecting to server");
            pd.setCancelable(false);
            pd.show();
        }

        private LSPDetail createLSPDetailObject(JSONObject jo){
            try {
                String id = jo.getString("id");
                String name = jo.getString("name");
                String address = jo.getString("address");
                String officePhone = jo.getString("office_phone");
                String contactPerson = jo.getString("contact_person");
                String chairman = jo.getString("chairman");
                String contactEmail = jo.getString("contact_email");
                String contactPhone = jo.getString("contact_phone");
                String contactMobile = jo.getString("contact_mobile");
                String chairmanMobile = jo.getString("chairman_mobile");
                String chairmanEmail = jo.getString("chairman_email");
                String remark = jo.getString("remarks");


                return new LSPDetail(id,
                        name,
                        address,
                        officePhone,
                        contactPerson,
                        chairman,
                        contactEmail,
                        contactPhone,
                        contactMobile,
                        chairmanMobile,
                        chairmanEmail,
                        remark
                );

            }catch (JSONException ex){ex.printStackTrace();}

            return null;
        }

        @Override
        protected LSPDetail doInBackground(String... params) {
            return createLSPDetailObject(NetworkHelper.getJSONObjectFromUrlUsingGet(params[0]));
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
