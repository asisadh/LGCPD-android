package np.gov.lgcpd.offline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import np.gov.lgcpd.AdaptersAndModel.CardViewAdapterForLSPList;
import np.gov.lgcpd.AdaptersAndModel.SM;
import np.gov.lgcpd.AdaptersAndModel.SMDetails;
import np.gov.lgcpd.Helper.Constants;
import np.gov.lgcpd.R;
import np.gov.lgcpd.database.DatabaseHandler;

/**
 * Created by asis on 12/2/16.
 */
public class Favourite extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private CardViewAdapterForLSPList adapter;
    private List<SMDetails> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sm_list);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setRecycleView();

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

    public  void populateTheList(){
        list = new ArrayList<>();

        DatabaseHandler dbh = new DatabaseHandler(this, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);

        List<SMDetails> list = dbh.getAllSM();


    }


}
