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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRiskImage extends Fragment {

    /***declaring variables***/
    ArrayList<String> arrListImagesUrl;
    //views
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    //firebase
    DatabaseReference mRootRef;
    DatabaseReference mImagesRef;
    ChildEventListener childEventListener;

    public FragmentRiskImage() {
        // Required empty public constructor
        Log.d("xyz", "initializing list in risk images");
        arrListImagesUrl = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_risk_image, container, false);
        /*** initializing components ***/
        //views
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_at_risk_images);
        adapter = new RvImagesAdapter(getContext(), arrListImagesUrl);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        //firebase
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mImagesRef = mRootRef.child(MyConstants.DB_REF_AT_RISK).child(MyConstants.DB_REF_IMAGES);
        Log.d("xyz", "at_risk-images ref :" + mImagesRef.toString());      //logging path

        //defining listener for db
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String url = dataSnapshot.child("url").getValue(String.class);
                Log.d("xyz", "url risk images :" + url);
                arrListImagesUrl.add(url);

                Log.d("xyz", arrListImagesUrl.size() + " images in at_risk");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("xyz", "inside onStart of risk images fragment");
        arrListImagesUrl.clear();
        mImagesRef.addChildEventListener(childEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mImagesRef.removeEventListener(childEventListener);
    }
}
