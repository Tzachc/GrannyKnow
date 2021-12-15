package com.example.grannyknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView MyProf;
    private ImageView granny;
    private TextView fname;
    private TextView lname;
    private TextView nickname;
    private TextView email;
    private TextView phone;
    private TextView rating;
    private FirebaseAuth mAuth;
    private ImageButton logoff;
    private ImageButton back;
    private ImageButton myTreats;
    private TextView myTreatsTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        activateText();
        init();
    }

    @Override
    public void onClick(View v) {
    if (logoff.equals(v))
    {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(ProfileActivity.this, "Logged Out!",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ProfileActivity.this, StartActivity.class));
    }
    if(back.equals(v)){
        ProfileActivity.super.onBackPressed();
    }
    if(myTreats.equals(v)){
        Intent i = new Intent(ProfileActivity.this,AllTreatmentsActivity.class);
        i.putExtra("personal",true);
        startActivity(i);
    }
    }
    private void activateText()
    {
        fname = findViewById(R.id.firstName);
        lname = findViewById(R.id.lastName);
        nickname = findViewById(R.id.nickName);
        email = findViewById(R.id.email_prof);
        phone = findViewById(R.id.phone_num);
        rating = findViewById(R.id.rate);
        MyProf = findViewById(R.id.myProfile);
        granny = findViewById(R.id.grannyImg);
        logoff = findViewById(R.id.logoffB);
        logoff.setOnClickListener(this);
        back = findViewById(R.id.backButton);
        back.setOnClickListener(this);
        myTreats = findViewById(R.id.treats);
        myTreats.setOnClickListener(this);
        myTreatsTxt = findViewById(R.id.myTreats);
    }
    private void init(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String user_email = user.getEmail();
        String id = user.getUid();
        DatabaseReference DR = FirebaseUsers.getUserByID(id);
        DR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                fname.setText("First name: " + u.getFirstName());
                lname.setText("Last name: " + u.getLastName());
                nickname.setText("Nickname: " + u.getNickname());
                email.setText("Email: " + u.getEmail());
                phone.setText("Phone number: " + u.getPhoneNumber());
                rating.setText("Rates: " + u.getRate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}