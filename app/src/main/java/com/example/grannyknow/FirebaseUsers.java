package com.example.grannyknow;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.grannyknow.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.firestore.auth.User;

import java.util.HashMap;

public class FirebaseUsers {

    public static void addUserToDB(String User_ID, String firstName, String lastName,String nickname, String phoneNumber, boolean isPremium, String email) {

        User user = new User(User_ID, firstName, lastName,nickname, phoneNumber, isPremium, 0, 0, email);
        FirebaseBaseModel.getRef().child("Users").child(User_ID).setValue(user);

    }

    public static void changeUserByID(User user) {

        FirebaseBaseModel.getRef().child("Users").child(user.getUser_ID()).setValue(user);
    }

    public static DatabaseReference getAllusers() {

        return FirebaseBaseModel.getRef().child("Users");
    }

    public static DatabaseReference getUserByID(String id) {

        return FirebaseBaseModel.getRef().child("Users").child(id);
    }
    public static DatabaseReference getUserByNickname(String nickname) {

        return FirebaseBaseModel.getRef().child("Users").child(nickname);
    }

    public static DatabaseReference getTreatments() {

        return FirebaseBaseModel.getRef().child("Treatments");
    }


    public static void addTreatmentToDB(Treatment treatment) {
        FirebaseBaseModel.getRef().child("Users").child(treatment.getTreatUser_ID()).child("Treatments").child(treatment.getTreatName()).setValue(treatment);
        FirebaseBaseModel.getRef().child("Treatments").child(treatment.getTreatName()).setValue(treatment);

    }

    public static void addTipToDB(String name[], String description[]) {
        for (int i = 0; i < name.length; i++) {
            FirebaseBaseModel.getRef().child("Tips").child(name[i]).setValue(description[i]);
        }
    }

}