//package com.example.chris.coshare;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.chris.coshare.R;
//import com.example.chris.coshare.SampleData.DataModel;
//
//import java.util.List;
//
///**
// * Created by edmund on 8/12/17.
// */
//
//public class CancelBookingAdapter extends RecyclerView.Adapter<CancelBookingAdapter.ViewHolder> {
//
//
//    // Declare variables
//    private List<DataModel> mItems; //TODO: Replace this to firebase current bookings list
//    private Context mContext;
//
//    @Override //boilerplate
//    public CancelBookingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(mContext);                //??? can be placed at constructor
//        View itemView = inflater.inflate(R.layout.list_item, parent, false);    //boilerplate
//        ViewHolder viewHolder = new ViewHolder(itemView);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        final DataModel item = mItems.get(position);                                   //surprisingly also a boilerplate
//        holder.tvName.setText(item.getLocationName());
////        holder.imgName.setImageDrawable();
//
//
//        holder.mView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Toast.makeText(mContext, "Clicked data: " + item.getLocationName(), Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(mContext, AddBookingPage.class);
////                String itemId = item.getTableID();
////                intent.putExtra(ITEM_ID_KEY,itemId);
////                mContext.startActivity(intent);
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
//
//
//
//
//    public CancelBookingAdapter(Context context, List<DataModel> items) {
//        this.mContext = context;
//        this.mItems = items;
//    }
//
//    // create a view holder for item layout
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        //Declare UI widgets here
//        public TextView tvName;
//        public View mView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            //Bind UI
//            tvName = (TextView) itemView.findViewById(R.id.itemName);
//            mView = itemView;
//
//        }
//    }
//}


package com.example.chris.coshare;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.coshare.SampleData.DataModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CancelBookingAdapter extends RecyclerView.Adapter<CancelBookingAdapter.ViewHolder> {

    private List<DataModel> mItems;
    private Context mContext;

    public CancelBookingAdapter(Context context, List<DataModel> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public CancelBookingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CancelBookingAdapter.ViewHolder holder, int position) {
        DataModel item = mItems.get(position);

        holder.tvName.setText(item.getLocationName());
//        try {
//            String imageFile = item.getImage();
//            InputStream inputStream = mContext.getAssets().open(imageFile);
//            Drawable d = Drawable.createFromStream(inputStream, null);
//            holder.imageView.setImageDrawable(d);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
//        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.itemName);
        }
    }
}