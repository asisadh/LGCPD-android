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

import np.gov.lgcpd.R;
import np.gov.lgcpd.SM.SMDetailActivity;

/**
 * Created by asis on 11/30/16.
 */
public class CardViewAdapterForSMList extends RecyclerView.Adapter<CardViewAdapterForSMList.ViewHolder> {

    private Context context;
    private List<SM> list;
    private List<SM> originalList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView vdc_ward;
        private TextView address;
        private TextView phone_number;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            vdc_ward = (TextView) view.findViewById(R.id.vdc_ward);
            address = (TextView) view.findViewById(R.id.address);
            phone_number = (TextView) view.findViewById(R.id.phone_number);

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Toast.makeText(context,"View Clicked = " + list.get(getAdapterPosition()).getName(),Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(context, SMDetailActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("id",list.get(getAdapterPosition()).getId())
                    );
                    //context.startActivity(new Intent(context,SMListActivity.class));
                }
            });
        }

    }

    public CardViewAdapterForSMList(Context context, List<SM> list) {
        this.context = context;
        this.list = list;
        this.originalList = new ArrayList<SM>();

        originalList.addAll(this.list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_for_sm, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SM modal = list.get(position);
        holder.name.setText(modal.getName());
        holder.phone_number.setText(modal.getPhone());
        holder.address.setText(modal.getAddress());
        holder.vdc_ward.setText(modal.getVdc_ward());
    }

    public void filterRecyclerView(String charText){

        charText = charText.toLowerCase();

        list.clear();

        if (charText.equals("")) {
            list.addAll(originalList);
        } else {
            for (SM data : originalList) {
                if (data.getName().toLowerCase().contains(charText) || data.getAddress().toLowerCase().contains(charText) || data.getVdc_ward().toLowerCase().contains(charText)) {
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
