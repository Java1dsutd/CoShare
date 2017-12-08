//package com.example.chris.coshare;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.LinkedList;
//
///**
// * Created by edmund on 8/12/17.
// */
//
//public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
//
//    private LayoutInflater mInflater;
//    private final LinkedList<String> mWordList;
//
//    public WordListAdapter(Context context, LinkedList<String> wordList) {
//        LayoutInflater mInflater = LayoutInflater.from(context);
//        this.mWordList = wordList;
//    }
//
//    @Override
//    public WordListAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // Inflate an item view.
//        View mItemView = mInflater.inflate(R.layout.cancellayout, parent, false);
//        return new WordViewHolder(mItemView, this);
//    }
//
//    @Override
//    public void onBindViewHolder(WordListAdapter.WordViewHolder holder, int position) {
//        // Retrieve the data for that position
//        String mCurrent = mWordList.get(position);
//// Add the data to the view
//        holder.wordItemView.setText(mCurrent);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mWordList.size();
//    }
//    class WordViewHolder extends RecyclerView.ViewHolder {
//
//        public final TextView wordItemView;
//        final WordListAdapter mAdapter;
//
//        public WordViewHolder(View itemView, WordListAdapter adapter) {
//            super(itemView);
//            wordItemView = (TextView) itemView.findViewById(R.id.word);
//            this.mAdapter = adapter;
//            itemView.setOnClickListener(this);
//        }
//        @Override
//        public void onClick(View v) {
//            wordItemView.setText ("Clicked! "+ wordItemView.getText());
//        }
//    }
//}