package com.github.florent37.materialviewpager.sample.fragment;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.TestRecyclerViewAdapter;
import com.github.florent37.materialviewpager.sample.model.Word;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment implements TestRecyclerViewAdapter.ItemListener {

    private static final String ARG_TYPE = "type";
    private static final String COLLECTION_NAME = "words";

    public static final int TYPE_YESTERDAY = 0;
    public static final int TYPE_TODAY = 1;
    public static final int TYPE_LEARNED = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_YESTERDAY, TYPE_TODAY, TYPE_LEARNED})
    public @interface Type {}

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private FirebaseFirestore mDatabase;
    private TestRecyclerViewAdapter adapter;

    @Type
    private int type;

    public static RecyclerViewFragment newInstance(@Type int type) {
        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);

        recyclerViewFragment.setArguments(args);

        return recyclerViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        type = getArguments().getInt(ARG_TYPE, 0);
        mDatabase = FirebaseFirestore.getInstance();

        Log.d("taaag", String.valueOf(type));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        adapter = new TestRecyclerViewAdapter();
        adapter.setItemListener(this);

        mRecyclerView.setAdapter(adapter);

        loadWordsFromDataStore();
    }

    private void loadWordsFromDataStore() {
        final List<Word> words = new ArrayList<>();
        if (type == TYPE_TODAY) {
            mDatabase.collection(COLLECTION_NAME).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot querySnapshot: task.getResult()) {
                                    words.add(querySnapshot.toObject(Word.class));
                                }

                                adapter.setWords(words);
                            }
                        }
                    });
        } else if (type == TYPE_LEARNED) {
            mDatabase.collection(COLLECTION_NAME).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot querySnapshot: task.getResult()) {
                                    words.add(querySnapshot.toObject(Word.class));
                                }

                                adapter.setWords(words);
                            }
                        }
                    });
        }
    }

    @Override
    public void onOtherWordClicked(Word word) {
        Toast.makeText(getContext(),  "Other word: " + word.getWord(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLeanedClicked(final Word word) {
        mDatabase.collection(COLLECTION_NAME).document(word.getWord()).update("learned", true)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), word.getWord() + " is learned", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
