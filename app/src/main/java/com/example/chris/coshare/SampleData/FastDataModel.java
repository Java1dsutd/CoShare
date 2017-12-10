package com.example.chris.coshare.SampleData;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.coshare.R;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * Created by edmund on 10/12/17.
 */

public class FastDataModel extends AbstractItem<FastDataModel,FastDataModel.ViewHolder> {

    private String tableID;          //firebase UUID
    private String locationName;
    private String description;
    private String area;
    private String image;
    private String occupant;
    private String owner;
    private boolean currentStatus;

    public FastDataModel() {

    }


    public FastDataModel(String tableID, String locationName, String description, String area, String image) {
        this.tableID = tableID;
        this.locationName = locationName;
        this.description = description;
        this.area = area;
        this.image = image;

        //uninitialized data
        this.occupant = null;
        this.owner = null;
        this.currentStatus = false;

    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new FastDataModel.ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.item_card;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.list_item;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> list) {        //replaces onBindViewHolder
        super.bindView(holder,list);

        //get the context
        Context ctx = holder.itemView.getContext();

        holder.tvName.setText(this.getLocationName());
//        try {
//            String imageFile = this.getImage();
//            InputStream inputStream = ctx.getAssets().open(imageFile);
//            Drawable d = Drawable.createFromStream(inputStream, null);
//            holder.imageView.setImageDrawable(d);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        holder.imageView.setImageBitmap(null);
//        Glide.clear(holder.imageView);
//        Glide.with(ctx).load(this.getImage()).animate(R.anim.alpha_on).into(holder.imageView);

    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        //TODO: Declare your UI widgets here
        public TextView tvName;
        public ImageView imageView;
        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            //TODO: init UI
            tvName = (TextView) itemView.findViewById(R.id.itemName);
//            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            mView = itemView;

        }
    }



    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOccupant() {
        return occupant;
    }

    public void setOccupant(String occupant) {
        this.occupant = occupant;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(boolean currentStatus) {
        this.currentStatus = currentStatus;
    }

}

