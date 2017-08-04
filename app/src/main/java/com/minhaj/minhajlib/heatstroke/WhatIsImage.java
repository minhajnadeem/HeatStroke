package com.minhaj.minhajlib.heatstroke;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minhaj.minhajlib.heatstroke.constants.MyConstants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WhatIsImage extends Fragment {

    /***declaring variables***/
    ArrayList<String> arrListImagesUrl;
    //views
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    //firebase
    DatabaseReference mRootRef;
    DatabaseReference mImagesRef;
    ValueEventListener valueEventListener;

    public WhatIsImage() {
        // Required empty public constructor
        arrListImagesUrl = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_what_is_image, container, false);

        /*** initializing components ***/
        //views
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_what_is_images);
        adapter = new RvImagesAdapter(getContext(),arrListImagesUrl);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        //firebase
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mImagesRef = mRootRef.child(MyConstants.DB_REF_WHAT_IS).child(MyConstants.DB_REF_IMAGES);
        Log.d("xyz","what is-images ref :"+mImagesRef.toString());      //logging path

        //defining listener for db
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arrListImagesUrl.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    String url = data.child("url").getValue(String.class);
                    arrListImagesUrl.add(url);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        return view;
    }

    //attaching listener to db ref
    @Override
    public void onStart() {
        super.onStart();
        mImagesRef.addValueEventListener(valueEventListener);
    }

    //removing listener
    @Override
    public void onPause() {
        super.onPause();
        mImagesRef.removeEventListener(valueEventListener);
    }
}
