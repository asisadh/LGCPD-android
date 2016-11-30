package np.gov.lgcpd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import np.gov.lgcpd.AdaptersAndModel.CardViewAdapterForDistrictAndMunicipality;
import np.gov.lgcpd.Helper.Constants;
import np.gov.lgcpd.Helper.JSONParser;
import np.gov.lgcpd.AdaptersAndModel.ModelForCardDistrictMunicipality;
import np.gov.lgcpd.Login.LoginActivity;

/**
 * Created by asis on 11/6/16.
 */
public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    private RecyclerView recyclerView;
    private CardViewAdapterForDistrictAndMunicipality adapter;
    private List<ModelForCardDistrictMunicipality> list;

    DrawerLayout drawerRight;
    DrawerLayout drawerLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        setRightDrawerLayout();

        setLeftDrawerLayout();

        setRecycleLayout();
    }

    public void setRightDrawerLayout(){
        drawerLeft = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLeft, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLeft.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView leftNavigationView = (NavigationView) findViewById(R.id.nav_left_view);
        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle Left navigation view item clicks here.
                int id = item.getItemId();
                switch (id){

                    case R.id.nav_login:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        break;
                    default:
                        break;
                }
                drawerLeft.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public void setLeftDrawerLayout(){
        drawerRight = (DrawerLayout)findViewById(R.id.drawer_layout_right) ;

        NavigationView rightNavigationView = (NavigationView) findViewById(R.id.nav_right_view);
        rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle Right navigation view item clicks here.
                int id = item.getItemId();

                switch (id){
                    case R.id.nav_district:
                        populateTheList(Constants.DISTRICT_VALUE);
                        break;
                    case R.id.nav_municipality:
                        populateTheList(Constants.MUNICIPALITY_VALUE);
                        break;
                    case R.id.nav_regional:
                        break;

                }

                drawerRight.closeDrawer(GravityCompat.END); /*Important Line*/
                return true;
            }
        });
    }

    public void setRecycleLayout(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        populateTheList(Constants.DISTRICT_VALUE);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }

    public void populateTheList(String value){
        list = new ArrayList<>();

        String URL = Constants.BASE_URL + value;

        new GetDistrict().execute(URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_openRight) {
            drawerRight.openDrawer(GravityCompat.END); /*Opens the Right Drawer*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLeft.isDrawerOpen(GravityCompat.START)) {
            drawerLeft.closeDrawer(GravityCompat.START);
        } else if (drawerRight.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/
            drawerRight.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
            System.exit(0);
        }
    }

    private class GetDistrict extends AsyncTask<String, Integer, JSONArray>{

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Getting data from Server");
            pd.setMessage("Connecting to server");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            return JSONParser.getJSONFromUrlUsingGet(params[0]);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);

            for(int i = 0; i < jsonArray.length(); i++){
                try {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    String type = jo.getString("type");
                    String region_id = jo.getString("region_id");
                    String name = jo.getString("name_en");

                    ModelForCardDistrictMunicipality m = new ModelForCardDistrictMunicipality(name,type,region_id);
                    list.add(m);

                }catch (JSONException ex){ ex.printStackTrace();}
            }

            adapter = new CardViewAdapterForDistrictAndMunicipality(getApplicationContext(), list);
            recyclerView.setAdapter(adapter);

            pd.cancel();
        }
    }
}
