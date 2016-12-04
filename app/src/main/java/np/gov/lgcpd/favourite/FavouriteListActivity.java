package np.gov.lgcpd.favourite;

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
import android.widget.Toast;

import java.util.List;

import np.gov.lgcpd.AdaptersAndModel.CardViewAdapterForSMAndLSPList;
import np.gov.lgcpd.AdaptersAndModel.SM;
import np.gov.lgcpd.Helper.Constants;
import np.gov.lgcpd.R;
import np.gov.lgcpd.database.DatabaseHandler;

/**
 * Created by asis on 12/4/16.
 */
public class FavouriteListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private CardViewAdapterForSMAndLSPList adapter;

    private boolean isSM;
    private String value;

    MenuItem lsp,sm;

    public FavouriteListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sm_list);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Favourite -SM");

        setRecycleView();

        isSM = true;
    }

    public void setRecycleView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        populateTheList();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }

    private void populateTheList(){
        new GetSM().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favourite_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        lsp = menu.findItem(R.id.action_lsp);
        sm = menu.findItem(R.id.action_sm);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }

        if (id == R.id.action_lsp){
            item.setVisible(false);
            sm.setVisible(true);
            isSM = false;
            getSupportActionBar().setTitle("Favourite -LSP");
            new GetSM().execute();

        }

        if( id == R.id.action_sm){
            item.setVisible(false);
            lsp.setVisible(true);
            isSM = true;
            getSupportActionBar().setTitle("Favourite -SM");
            new GetSM().execute();
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onQueryTextChange(String query) {
        adapter.filterRecyclerView(query);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetSM().execute();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private class GetSM extends AsyncTask<String, Integer, List<SM> > {

        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(FavouriteListActivity.this);
            pd.setTitle("Populating the Favourites");
            pd.setMessage("Fetching data from database");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected List<SM> doInBackground(String... params) {
            DatabaseHandler handler = new DatabaseHandler(getApplicationContext(), Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);

            if(isSM)
                return handler.getAllSM();
            return handler.getAllLSP();
        }

        @Override
        protected void onPostExecute(List<SM> list) {
            super.onPostExecute(list);

            if (list.isEmpty()){
                Toast.makeText(getApplicationContext(),"Sorry no favourite right now",Toast.LENGTH_SHORT).show();
            }
            else {
                adapter = new CardViewAdapterForSMAndLSPList(getApplicationContext(), list);
                adapter.setFromFavourite(true);
                adapter.isSM(isSM);
                recyclerView.setAdapter(adapter);
            }
            pd.cancel();
        }
    }

}
