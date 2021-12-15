package com.example.grannyknow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
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

import java.util.ArrayList;
import java.util.List;



class Group {

    public String string;
    public final List<String> children = new ArrayList<String>();

    public Group(String string) {
        this.string = string;
    }

}
public class AllTreatmentsActivity extends AppCompatActivity {

    SparseArray<Group> treatsNames = new SparseArray<Group>();
    SparseArray<Group> personalTreats = new SparseArray<Group>();
    private boolean personal;
    private ExpandableListView listView;
    private Button sendMessage;

    String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_treatments);
        getSupportActionBar().hide();
        getExtra();
        listView = (ExpandableListView) findViewById(R.id.list);
        treatmentUpdate();

//        sendMessage = findViewById(R.id.button_send_uploader);
//        sendMessage.setFocusable(false);
//        sendMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String currUser = userID;
//                System.out.println("curr user id: "+ currUser);
//                Toast.makeText(AllTreatmentsActivity.this,"curr user id : "+currUser,Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(AllTreatmentsActivity.this, MessageActivity.class));
//            }
//        });

    }

    public void setAdapters(SparseArray<Group> groups){
        MyAdapter adapter = new MyAdapter(this, groups);
        listView.setAdapter(adapter);

    }

    private void treatmentUpdate(){
        DatabaseReference DR = FirebaseBaseModel.getRef().child("Treatments");

        DR.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter = 0;
                int secCounter = 0;

                for(DataSnapshot s : snapshot.getChildren()){
                    String current_user_ID = s.child("treatUser_ID").getValue().toString();
                    userID = current_user_ID;
                    String nickname = "";
                    String preparation = "";
                    String ingredients = "";
                    String rate = "";
                    String numOfRates = "";

                    for( DataSnapshot t : s.getChildren()){

                        if(t.getKey().equals("treatUser_ID")|| t.getKey().equals("isConfirmed")) {
                            continue;
                        }
                        if(t.getKey().equals("preparation")){
                            preparation = t.getValue().toString();
                        }
                        if(t.getKey().equals("ingredients")){
                            ingredients = t.getValue().toString();
                        }
                        if(t.getKey().equals("rate")){
                            rate = t.getValue().toString();
                        }
                        if(t.getKey().equals("numOfRates")){
                            numOfRates = t.getValue().toString();
                        }
                        if(t.getKey().equals("nickname")){
                            nickname = t.getValue().toString();
                        }
                        if (t.getKey().equals("treatName") && !personal) { // add &&!personal
                            Group group = new Group(t.getValue().toString());
                            group.children.add("Preparation:\n"+preparation);
                            group.children.add("Ingredients:\n"+ingredients);
                            group.children.add("Treatment Rate:\n"+rate);
                            group.children.add("Number Of Rates:\n"+numOfRates+"\n\n");
                            group.children.add("Username: "+nickname+"\nClick icon to send a message!\n\n\n");
                            treatsNames.append(counter,group);
                        }
                            else{
                                if(current_user_ID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) ){
                                    if (t.getKey().equals("treatName")) {
                                        Group personalGroup = new Group(t.getValue().toString());
                                        personalGroup.children.add("Preparation:\n"+preparation);
                                        personalGroup.children.add("Ingredients:\n"+ingredients);
                                        personalGroup.children.add("Treatment Rate:\n"+rate);
                                        personalGroup.children.add("Number Of Rates:\n"+numOfRates);
                                        personalGroup.children.add("Username: "+nickname);

                                        personalTreats.append(secCounter,personalGroup);
                                        secCounter++;
                                    }
                                }
                            }
                    }
                    counter++;

                }
                 if(!personal) {
                        setAdapters(treatsNames);
                    }
                    else{
                        setAdapters(personalTreats);
                    }
            }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    });


    }
    private void getExtra()
    {
        personal = getIntent().getBooleanExtra("personal", false);
    }
}