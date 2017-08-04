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
import com.minhaj.minhajlib.heatstroke.modal.VideoModal;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPreventionVideo extends Fragment {

    /*** declaring variables ***/
    ArrayList<VideoModal> arrListVideoModal;
    //views
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    //firebase
    DatabaseReference mRootRef;
    DatabaseReference mVideosRef;
    ChildEventListener childEventListener;

    public FragmentPreventionVideo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prevention_video, container, false);

        /*** initializing components ***/
        arrListVideoModal = new ArrayList<>();
        //views
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_videos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RvVideoAdapter(getContext(),arrListVideoModal);
        recyclerView.setAdapter(adapter);

        //firebase
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mVideosRef = mRootRef.child(MyConstants.DB_REF_PREVENTION).child(MyConstants.DB_REF_VIDEOS);
        Log.d("xyz","prevention-videos ref :"+mVideosRef.toString());      //logging path

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int startAt = -1,endAt = -1;
                String desc = dataSnapshot.child(MyConstants.TEXT).getValue(String.class);
                String url = dataSnapshot.child(MyConstants.URL).getValue(String.class);
                String thumbUrl = dataSnapshot.child(MyConstants.THUMB_URL).getValue(String.class);
                if(dataSnapshot.child(MyConstants.START_AT).getValue(Integer.class) != null){
                    Log.d("xyz","true start_at end_at");
                    startAt = dataSnapshot.child(MyConstants.START_AT).getValue(Integer.class);
                    //endAt = dataSnapshot.child(MyConstants.END_AT).getValue(Integer.class);
                }
                arrListVideoModal.add(new VideoModal(desc,url,thumbUrl,startAt,endAt));
                Log.d("xyz",arrListVideoModal.size()+" videos added");
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
        arrListVideoModal.clear();
        mVideosRef.addChildEventListener(childEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mVideosRef.removeEventListener(childEventListener);
    }
}
