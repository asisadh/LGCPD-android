package np.gov.lgcpd.AdaptersAndModel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import np.gov.lgcpd.R;

/**
 * Created by asis on 11/29/16.
 */
public class CardViewAdapterForDistrictAndMunicipality extends RecyclerView.Adapter<CardViewAdapterForDistrictAndMunicipality.ViewHolder>{
    private Context context;
    private List<ModelForCardDistrictMunicipality> list;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Toast.makeText(context,"View Clicked = " + list.get(getAdapterPosition()).getName(),Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    public CardViewAdapterForDistrictAndMunicipality(Context context, List<ModelForCardDistrictMunicipality> list) {
        this.context = context;
        this.list = list;
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
       // holder.name.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
