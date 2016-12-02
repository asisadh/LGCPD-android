package np.gov.lgcpd;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.midi.MidiOutputPort;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import np.gov.lgcpd.AdaptersAndModel.CardViewAdapterForDistrictAndMunicipality;
import np.gov.lgcpd.Encryption.SecurePreferences;
import np.gov.lgcpd.Helper.Constants;
import np.gov.lgcpd.Helper.NetworkHelper;
import np.gov.lgcpd.AdaptersAndModel.ModelForCardDistrictMunicipality;
import np.gov.lgcpd.Login.LoginActivity;

/**
 * Created by asis on 11/6/16.
 */
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    Toolbar toolbar;

    private RecyclerView recyclerView;
    private CardViewAdapterForDistrictAndMunicipality adapter;
    private List<ModelForCardDistrictMunicipality> list;

    private LinearLayout no_network_connection;
    private Button refresh;
    private String value;

    DrawerLayout drawerRight;
    DrawerLayout drawerLeft;
    NavigationView leftNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        no_network_connection = (LinearLayout) findViewById(R.id.no_network_connection);
        refresh = (Button) findViewById(R.id.refresh);
        value = Constants.DISTRICT_VALUE;

        setRightDrawerLayout();

        setLeftDrawerLayout();

        setRecycleLayout();

        checkIfUserIsAlreadyLoggedIn();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateTheList(value);
            }
        });
    }

    public void setLeftDrawerLayout(){
        drawerLeft = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLeft, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLeft.setDrawerListener(toggle);
        toggle.syncState();

        leftNavigationView = (NavigationView) findViewById(R.id.nav_left_view);
        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle Left navigation view item clicks here.
                int id = item.getItemId();
                switch (id){

                    case R.id.nav_login:
                        finish();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        //item.setVisible(false);

                       // leftNavigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                        break;
                    case R.id.nav_logout:
                        //startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        //item.setVisible(false);

                        //leftNavigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
                        break;

                    default:
                        break;
                }
                drawerLeft.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public void setRightDrawerLayout(){
        drawerRight = (DrawerLayout)findViewById(R.id.drawer_layout_right) ;

        final NavigationView rightNavigationView = (NavigationView) findViewById(R.id.nav_right_view);
        rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle Right navigation view item clicks here.
                int id = item.getItemId();

                switch (id){
                    case R.id.nav_district:
                        rightNavigationView.getMenu().findItem(R.id.right_drawer_title).setTitle("SM - " + item.getTitle());
                        chaneTheVisibilityOfSelectedField(rightNavigationView,item);
                        item.setVisible(false);
                        populateTheList(Constants.DISTRICT_VALUE);
                        break;
                    case R.id.nav_municipality:
                        rightNavigationView.getMenu().findItem(R.id.right_drawer_title).setTitle("SM - " + item.getTitle());
                        chaneTheVisibilityOfSelectedField(rightNavigationView,item);
                        item.setVisible(false);
                        populateTheList(Constants.MUNICIPALITY_VALUE);
                        break;
                    case R.id.nav_regional:
                        rightNavigationView.getMenu().findItem(R.id.right_drawer_title).setTitle("SM - " + item.getTitle());
                        chaneTheVisibilityOfSelectedField(rightNavigationView,item);
                        Toast.makeText(MainActivity.this,"Regional is not completed, Comming Soon",Toast.LENGTH_SHORT).show();
                        item.setVisible(false);
                        break;
                    case R.id.nav_other:
                        rightNavigationView.getMenu().findItem(R.id.right_drawer_title).setTitle("SM - " + item.getTitle());
                        chaneTheVisibilityOfSelectedField(rightNavigationView,item);
                        Toast.makeText(MainActivity.this,"Other is not completed, Comming Soon",Toast.LENGTH_SHORT).show();
                        item.setVisible(false);
                        break;
                }

                drawerRight.closeDrawer(GravityCompat.END); /*Important Line*/
                return true;
            }
        });
    }

    private void chaneTheVisibilityOfSelectedField(final NavigationView nv, MenuItem i){
        nv.getMenu().findItem(R.id.nav_district).setVisible(true);
        nv.getMenu().findItem(R.id.nav_municipality).setVisible(true);
        nv.getMenu().findItem(R.id.nav_regional).setVisible(true);
        nv.getMenu().findItem(R.id.nav_other).setVisible(true);
    }

    public void setRecycleLayout(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        populateTheList(value);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }

    public void populateTheList(String value){
        list = new ArrayList<ModelForCardDistrictMunicipality>();
        this.value = value;

        String URL = Constants.SM_URL_LIST + this.value;
        //Log.e("URL : " , Constants.SM_URL + this.value);

        if(NetworkHelper.isNetworkAvailable(getApplicationContext())){
            recyclerView.setVisibility(View.VISIBLE);
            no_network_connection.setVisibility(View.GONE);
            new GetDistrict().execute(URL);
        }else{
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
            no_network_connection.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    public String getValue(){
        return value;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

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

        if(id == R.id.action_search){

            return true;
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

    @Override
    public void onBackPressed() {
        if (drawerLeft.isDrawerOpen(GravityCompat.START)) {
            drawerLeft.closeDrawer(GravityCompat.START);
        } else if (drawerRight.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/
            drawerRight.closeDrawer(GravityCompat.END);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    MainActivity.this);

            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            alertDialog.setNegativeButton("No", null);

            alertDialog.setIcon(R.mipmap.ic_launcher);

            alertDialog.setMessage("Do you want to exit?");
            alertDialog.setTitle(getResources().getString(R.string.app_name));

            alertDialog.show();
        }
    }

    private void checkIfUserIsAlreadyLoggedIn(){
        SharedPreferences prefs = new SecurePreferences(MainActivity.this, Constants.SHARED_PREFRENCE_PASSWORD, Constants.SHARED_PREFRENCE_LOGIN_INFORMATION);

        if(prefs.getBoolean("is_already_login", false)){
            View v = leftNavigationView.getHeaderView(0);
            ((TextView)v.findViewById(R.id.name)).setText(prefs.getString("name","Name"));
            ((TextView)v.findViewById(R.id.email)).setText(prefs.getString("email","email@gmail.com"));
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
            return NetworkHelper.getJSONFromUrlUsingGet(params[0]);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);

            if(jsonArray != null) {
                if (jsonArray.length() == 0) {
                    Toast.makeText(getApplicationContext(), "No SM Available", Toast.LENGTH_SHORT).show();
                    Log.e("NO SM", "I m here");
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jo = jsonArray.getJSONObject(i);
                            String name = jo.getString("name_en");

                            ModelForCardDistrictMunicipality m = new ModelForCardDistrictMunicipality(name);
                            list.add(m);

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            adapter = new CardViewAdapterForDistrictAndMunicipality(getApplicationContext(), list);
            adapter.setValue(value);
            Log.e("Value :", value);
            recyclerView.setAdapter(adapter);

            pd.cancel();
        }
    }
}
