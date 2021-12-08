package com.example.grannyknow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class AllTreatmentsActivity extends AppCompatActivity {

    //private FirebaseAuth auth;
    //private DatabaseReference DR;
    private ListView lv;
    List<String> treatsNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_treatments);
        lv = findViewById(R.id.list);
        treatmentUpdate();

    }

public void setAdapters(List<String> arrs){
    ArrayAdapter<String> arr;
    arr = new ArrayAdapter<String>(
            this, R.layout.support_simple_spinner_dropdown_item,
            arrs);
    lv.setAdapter(arr);

}

    private void treatmentUpdate(){
        DatabaseReference DR = FirebaseBaseModel.getRef().child("Treatments");
        DR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s : snapshot.getChildren()){
                    for( DataSnapshot t : s.getChildren()){
                        if(t.getKey().equals("treatUser_ID")|| t.getKey().equals("isConfirmed")
                                || t.getKey().equals("numOfRates")|| t.getKey().equals("rate")
                        || t.getKey().equals("preparation") || t.getKey().equals("ingredients")) {
                            continue;
                        }
                        else {
                            String treatName = "";
                            if (t.getKey().equals("treatName"))
                                //treatName = t.getKey();
                                treatsNames.add(t.getValue().toString());
                           // System.out.println(t.getValue().toString());
                        }
                    }
                    //System.out.println(s.getValue().toString());
                }

                setAdapters(treatsNames);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//        DR.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Treatment t = snapshot.getValue(Treatment.class);
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

}