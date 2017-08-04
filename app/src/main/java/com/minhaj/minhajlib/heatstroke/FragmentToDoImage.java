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
public class FragmentToDoImage extends Fragment {

    /***declaring variables***/
    ArrayList<String> arrListImagesUrl;
    //views
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    //firebase
    DatabaseReference mRootRef;
    DatabaseReference mImagesRef;
    ChildEventListener childEventListener;

    public FragmentToDoImage() {
        // Required empty public constructor
        arrListImagesUrl = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_do_image, container, false);

        /*** initializing components ***/
        //views
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_images);
        adapter = new RvImagesAdapter(getContext(), arrListImagesUrl);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        //firebase
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mImagesRef = mRootRef.child(MyConstants.DB_REF_TO_DO).child(MyConstants.DB_REF_IMAGES);
        Log.d("xyz", "to do-images ref :" + mImagesRef.toString());      //logging path

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String url = dataSnapshot.child("url").getValue(String.class);
                arrListImagesUrl.add(url);
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
        //clear list before attaching listener
        arrListImagesUrl.clear();
        mImagesRef.addChildEventListener(childEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mImagesRef.removeEventListener(childEventListener);
    }
}
