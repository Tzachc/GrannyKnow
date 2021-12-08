package com.example.grannyknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button logout;
    private Button upload;
    private Button allTreatments;
    private EditText first_name;
    private TextView welcomeText;
    private FirebaseAuth auth;
    //private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout = findViewById(R.id.logout);
        upload = findViewById(R.id.uploadButton);
        allTreatments = findViewById(R.id.allTreatmentsButton);

        welcomeText = findViewById(R.id.WelcomeText);
        first_name = findViewById(R.id.user_firstName);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "check this!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, TreatmentActivity.class));
            }
        });

        allTreatments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "all treatments!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, AllTreatmentsActivity.class));
            }
        });

    }

//    @Override
//    public void onClick(View ClickedButton) {
//        // if(backButton.equals(ClickedButton)){
//        //    LoginActivity.super.onBackPressed();
//        // }
//        if (logout.equals(ClickedButton)) {
//            System.out.println("HERE IN logout");
//            FirebaseAuth.getInstance().signOut();
//            Toast.makeText(MainActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MainActivity.this, StartActivity.class));
//
//        } else if (upload.equals(ClickedButton)) {
//            Toast.makeText(MainActivity.this, "check this!", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MainActivity.this, TreatmentActivity.class));
//        }
//    }
    @Override
    protected void onResume()
    {
        super.onResume();
        loggedInWelcome();
    }
    private void loggedInWelcome(){
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String id = user.getUid();
        DatabaseReference DR = FirebaseUsers.getUserByID(id);
        DR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                String name = u.getFirstName();
                welcomeText.setText("Hello, " + name + "!");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(user!=null){
            String userName = user.getUid();
            String real = FirebaseBaseModel.getRef().child("firstName").child(userName).getKey();
            //welcomeText.setText("Hello," + real + "!");
        }
    }

}