package com.example.chris.coshare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.coshare.SampleData.DataModel;

import java.util.List;

public class AddBookingAdapter extends RecyclerView.Adapter<AddBookingAdapter.ViewHolder> {

    //declare
    public static final String ITEM_ID_KEY = "item_id_key";
    private List<DataModel> mItems;
    private Context mContext;

    public AddBookingAdapter(Context context, List<DataModel> items) {
        this.mContext = context;
        this.mItems = items;
    }
//public AddBookingAdapter(AllBookingsFragment frag, List<DataItem> items) {
//    this.mContext = frag.getActivity();
//    this.mItems = items;
//}

    // creates a view and returns it. Boilerplate.
    @Override
    public AddBookingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //this.mContext=parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);                //??? can be placed at constructor
        View itemView = inflater.inflate(R.layout.list_item, parent, false);    //boilerplate
        ViewHolder viewHolder = new ViewHolder(itemView);                       //boilerplate
        return viewHolder;
    }

    // associates the data with the view holder for a given position in the RecyclerView.
    // supplies actual data to view holder.
    @Override
    public void onBindViewHolder(AddBookingAdapter.ViewHolder holder, int position) {
        final DataModel item = mItems.get(position);                                   //surprisingly also a boilerplate
        holder.tvName.setText(item.getLocationName());
//        holder.imgName.setImageDrawable();


//        holder.mView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
////                Toast.makeText(mContext, "Clicked data: " + item.getLocationName(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, AddBookingPage.class);
//                String itemId = item.getTableID();
//                intent.putExtra(ITEM_ID_KEY,itemId);
//                mContext.startActivity(intent);
//            }
//        });
    }

    // returns to number of data items available for displaying. Boilerplate.
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    // create a view holder for item layout
    public static class ViewHolder extends RecyclerView.ViewHolder {

        //Declare UI widgets here
        public TextView tvName;
//        public ImageView imgName;
        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            //Bind UI
            tvName = (TextView) itemView.findViewById(R.id.itemName);
//            imgName = (ImageView) itemView.findViewById(R.id.imageView2);
            mView = itemView;

        }
    }
}
