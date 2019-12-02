package com.example.petfriend.Model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PetLoseFireDBHelper{
        private FirebaseDatabase mDatabase;
        private DatabaseReference mReferncePet;
        private ArrayList<PetLose> petlist = new ArrayList<>();


public  interface DataStatus{
    void DataIsLoaded(ArrayList<PetLose> petlist, ArrayList<String> keys);
    void DataIsInserted();
    void DataIsUpdated();
    void DataIsDeleted();
}

    public PetLoseFireDBHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        mReferncePet = mDatabase.getReference("PetLose");
    }

    public void readPetLose(final PetLoseFireDBHelper.DataStatus dataStatus){
        mReferncePet.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                petlist.clear();
                ArrayList<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    PetLose pet = keyNode.getValue(PetLose.class);
                    petlist.add(pet);
                }
                dataStatus.DataIsLoaded(petlist,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addPetLose(PetLose pet, final PetLoseFireDBHelper.DataStatus dataStatus){
        String key = mReferncePet.push().getKey();
        mReferncePet.child(key).setValue(pet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });

    }
    public void updatePetLose(String key, PetLose pet, final PetLoseFireDBHelper.DataStatus dataStatus){
        mReferncePet.child(key).setValue(pet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                    }
                });
    }
    public void deletePetLose(String key, final PetLoseFireDBHelper.DataStatus dataStatus){
        mReferncePet.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }
}
