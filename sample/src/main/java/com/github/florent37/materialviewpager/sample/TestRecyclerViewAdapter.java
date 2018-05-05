package com.github.florent37.materialviewpager.sample;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.sample.databinding.ListItemCardBigBinding;
import com.github.florent37.materialviewpager.sample.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.WordViewHolder> {
    private List<Word> mWords;

    private ItemListener itemListener;

    public TestRecyclerViewAdapter() {
        mWords = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setWords(@NonNull List<Word> words) {
        mWords.clear();
        mWords.addAll(words);

        notifyDataSetChanged();
    }

    @Override
    public TestRecyclerViewAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemCardBigBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.list_item_card_big, parent, false);

        return new WordViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(TestRecyclerViewAdapter.WordViewHolder holder, int position) {
        holder.bindWord(mWords.get(position));
    }

    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ListItemCardBigBinding mBinding;

        public WordViewHolder(ListItemCardBigBinding bigBinding) {
            super(bigBinding.getRoot());
            mBinding = bigBinding;

            mBinding.otherWordButton.setOnClickListener(this);
            mBinding.learnedButton.setOnClickListener(this);
        }

        public void bindWord(@NonNull Word word) {
            mBinding.setWord(word);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.other_word_button:
                    itemListener.onOtherWordClicked(mWords.get(getAdapterPosition()));
                    break;
                case R.id.learned_button:
                    itemListener.onLeanedClicked(mWords.get(getAdapterPosition()));
            }
        }
    }

    public interface ItemListener {
        void onOtherWordClicked(Word word);
        void onLeanedClicked(Word word);
    }
}