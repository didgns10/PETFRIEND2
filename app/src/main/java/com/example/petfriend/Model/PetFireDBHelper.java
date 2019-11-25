package com.example.petfriend.Model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PetFireDBHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferncePet;
    private ArrayList<Pet> petlist = new ArrayList<>();


    public  interface DataStatus{
        void DataIsLoaded(ArrayList<Pet> petlist, ArrayList<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public PetFireDBHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        mReferncePet = mDatabase.getReference("Pet");
    }

    public void readPet(final DataStatus dataStatus){
        mReferncePet.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                petlist.clear();
                ArrayList<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Pet pet = keyNode.getValue(Pet.class);
                    petlist.add(pet);
                }
                dataStatus.DataIsLoaded(petlist,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addPet(Pet pet, final DataStatus dataStatus){
        String key = mReferncePet.push().getKey();
        mReferncePet.child(key).setValue(pet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });

    }
    public void updatePet(String key, Pet pet, final DataStatus dataStatus){
        mReferncePet.child(key).setValue(pet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                    }
                });
    }
    public void deletePet(String key, final DataStatus dataStatus){
        mReferncePet.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }
}
