//package com.example.chris.coshare;
//
///**
// * Created by edmund on 12/12/17.
// */
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.drawable.Drawable;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.chris.coshare.SampleData.DataModel;
//import com.example.chris.coshare.SampleData.UltimateDataModel;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//
//public class ViewBookingAdapter extends RecyclerView.Adapter<ViewBookingAdapter.ViewHolder> {
//
//    //declare
//    public static final String ITEM_ID_KEY = "item_id_key";
//    private List<UltimateDataModel> mItems;
//    private Context mContext;
//
//    public ViewBookingAdapter(Context context, List<UltimateDataModel> items) {
//        this.mContext = context;
//        this.mItems = items;
//    }
////public AddBookingAdapter(AllBookingsFragment frag, List<DataItem> items) {
////    this.mContext = frag.getActivity();
////    this.mItems = items;
////}
//
//    // creates a view and returns it. Boilerplate.
//    @Override
//    public ViewBookingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        //this.mContext=parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(mContext);                //??? can be placed at constructor
//        View itemView = inflater.inflate(R.layout.list_item, parent, false);    //boilerplate
//        ViewHolder viewHolder = new ViewHolder(itemView);                       //boilerplate
//        return viewHolder;
//    }
//
//    // associates the data with the view holder for a given position in the RecyclerView.
//    // supplies actual data to view holder.
//    @Override
//    public void onBindViewHolder(ViewBookingAdapter.ViewHolder holder, int position) {
//        final UltimateDataModel item = mItems.get(position);                                   //surprisingly also a boilerplate
//        holder.tvName.setText(item.getLocationName());
////        holder.imgName.setImageDrawable();
//        Context ctx = holder.itemView.getContext();
//
//        try {
//            String imageFile = item.getImage();
//            InputStream inputStream = ctx.getAssets().open(imageFile);
//            Drawable d = Drawable.createFromStream(inputStream, null);
//            holder.imgName.setImageDrawable(d);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        holder.mView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                //Toast.makeText(mContext, "Clicked data: " + item.getLocationName(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, ViewBookingPage.class);
//                String itemId = item.getTableID();
//                intent.putExtra(ITEM_ID_KEY,itemId);
//                mContext.startActivity(intent);
//            }
//        });
//    }
//
//    // returns to number of data items available for displaying. Boilerplate.
//    @Override
//    public int getItemCount() {
//        return mItems.size();
//    }
//
//    // create a view holder for item layout
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        //Declare UI widgets here
//        public TextView tvName;
//        public ImageView imgName;
//        public Button addButton;
//        public View mView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            //Bind UI
//            tvName = (TextView) itemView.findViewById(R.id.locationName);
//            imgName = (ImageView) itemView.findViewById(R.id.locationImage);
//            addButton = (Button) itemView.findViewById(R.id.addLocation);
//            mView = itemView;
//
//        }
//    }
//}
