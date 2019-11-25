package com.example.petfriend.Model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlaceFireDBHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferncePlace;
    private ArrayList<Place> placelist = new ArrayList<>();

    public  interface DataStatus{
        void DataIsLoaded(ArrayList<Place> placelist, ArrayList<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }
    public PlaceFireDBHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        mReferncePlace = mDatabase.getReference("Place");
    }
    public void readPlace(final PlaceFireDBHelper.DataStatus dataStatus){
        mReferncePlace.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                placelist.clear();
                ArrayList<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Place place = keyNode.getValue(Place.class);
                    placelist.add(place);
                }
                dataStatus.DataIsLoaded(placelist,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void addPlace(Place place, final PlaceFireDBHelper.DataStatus dataStatus){
        String key = mReferncePlace.push().getKey();
        mReferncePlace.child(key).setValue(place)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });

    }
    public void updatePet(String key, Place place, final PlaceFireDBHelper.DataStatus dataStatus){
        mReferncePlace.child(key).setValue(place)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                    }
                });
    }
    public void deletePet(String key, final PlaceFireDBHelper.DataStatus dataStatus){
        mReferncePlace.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }

}
