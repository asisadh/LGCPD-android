package np.gov.lgcpd.AdaptersAndModel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import np.gov.lgcpd.Helper.Constants;
import np.gov.lgcpd.LSP.LSPListActivity;
import np.gov.lgcpd.R;
import np.gov.lgcpd.SM.SMListActivity;

/**
 * Created by asis on 11/29/16.
 */
public class CardViewAdapterForDistrictAndMunicipality extends RecyclerView.Adapter<CardViewAdapterForDistrictAndMunicipality.ViewHolder>{
    private Context context;
    private List<ModelForCardDistrictMunicipality> list;
    private List<ModelForCardDistrictMunicipality> originalList;

    private String area;
    private String sm_lsp;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                    Intent i = null;

                    if(sm_lsp.equals(Constants.SM)) {
                        i = new Intent(context, SMListActivity.class);
                    }else{
                        i = new Intent(context, LSPListActivity.class);
                    }
                    context.startActivity(i
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("value", area)
                            .putExtra("name", list.get(getAdapterPosition()).getName()));
                }
            });
        }
    }

    public void setArea(String s){
        area = s;
    }

    public void setSM_LSP(String s){
        sm_lsp = s;
    }

    public CardViewAdapterForDistrictAndMunicipality(Context context, List<ModelForCardDistrictMunicipality> list) {
        this.context = context;
        this.list = list;
        this.originalList = new ArrayList<ModelForCardDistrictMunicipality>();

        originalList.addAll(this.list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_district_municapility, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ModelForCardDistrictMunicipality modal = list.get(position);
        holder.name.setText(modal.getName());
    }

    public void filterRecyclerView(String charText){

        charText = charText.toLowerCase();

        list.clear();

        if (charText.equals("")) {
            list.addAll(originalList);
        } else {
            for (ModelForCardDistrictMunicipality data : originalList) {
                if (data.getName().toLowerCase().contains(charText)) {
                    list.add(data);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
