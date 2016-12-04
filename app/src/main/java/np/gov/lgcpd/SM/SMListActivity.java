package np.gov.lgcpd.SM;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import np.gov.lgcpd.AdaptersAndModel.CardViewAdapterForSMAndLSPList;
import np.gov.lgcpd.AdaptersAndModel.SM;
import np.gov.lgcpd.Helper.Constants;
import np.gov.lgcpd.Helper.NetworkHelper;
import np.gov.lgcpd.R;

/**
 * Created by asis on 11/30/16.
 */
public class SMListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private Toolbar toolbar;

    private LinearLayout no_network_connection;
    private Button refresh;

    private RecyclerView recyclerView;
    private CardViewAdapterForSMAndLSPList adapter;
    private List<SM> list;

    private String value;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sm_list);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("SM");

        no_network_connection = (LinearLayout) findViewById(R.id.no_network_connection);
        refresh = (Button) findViewById(R.id.refresh);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateTheList(value);
            }
        });

        value = getIntent().getExtras().getString("value");
        name = getIntent().getExtras().getString("name");

        setRecycleView();
    }

    public void setRecycleView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        populateTheList(value);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }

    private void populateTheList(String value){
        list = new ArrayList<>();
        this.value = value;

        String URL = Constants.SM_API + this.value + "/" +  name;

        if(NetworkHelper.isNetworkAvailable(getApplicationContext())){
            recyclerView.setVisibility(View.VISIBLE);
            no_network_connection.setVisibility(View.GONE);
            new GetSM().execute(URL);
        }else{
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
            no_network_connection.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sm_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

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

    @Override
    public boolean onQueryTextChange(String query) {
        adapter.filterRecyclerView(query);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private class GetSM extends AsyncTask<String, Integer, JSONArray>{

        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SMListActivity.this);
            pd.setTitle("Getting data from Server");
            pd.setMessage("Connecting to server");
            pd.setCancelable(false);
            pd.show();
        }


        @Override
        protected JSONArray doInBackground(String... params) {
            return NetworkHelper.getJSONArrayFromUrlUsingGet(params[0]);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            if(jsonArray != null)
                for(int i = 0; i < jsonArray.length(); i++){
                    try {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        String id = jo.getString("id");
                        String name = jo.getString("name");
                        String phone = jo.getString("phone");
                        String address = jo.getString("address");
                        String vdc = jo.getString("vdc");

                        SM m = new SM(id, name, phone, address, vdc);
                        list.add(m);

                    }catch (JSONException ex){ ex.printStackTrace();}
                }

            adapter = new CardViewAdapterForSMAndLSPList(getApplicationContext(), list);
            adapter.isSM(true);
            recyclerView.setAdapter(adapter);

            pd.cancel();
        }
    }
}
