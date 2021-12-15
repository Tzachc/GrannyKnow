package com.example.grannyknow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class TreatmentActivity extends AppCompatActivity{
    private Button uploadTreatment;
    private EditText treatName;
    private EditText ingredients;
    private EditText preparation;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_treatment);
        uploadTreatment = findViewById(R.id.uploadTreatment);
        treatName = findViewById(R.id.treatment_name);
        ingredients = findViewById(R.id.ingredients);
        preparation = findViewById(R.id.preparation);
        auth = FirebaseAuth.getInstance();
        uploadTreatment.setOnClickListener(new View.OnClickListener() {
        String nickname = "";

            @Override
            public void onClick(View v) {

                String txt_treatName = treatName.getText().toString();
                String txt_ingredients = ingredients.getText().toString();
                String txt_preparation = preparation.getText().toString();

                if (txt_treatName.isEmpty() || txt_ingredients.isEmpty() || txt_preparation.isEmpty()) {
                    Toast.makeText(TreatmentActivity.this, "Please fill all!", Toast.LENGTH_SHORT).show();
                }else {

                    FirebaseUser user = auth.getCurrentUser();
                    nickname = user.getDisplayName();
                    String userID = auth.getCurrentUser().getUid();
                    Treatment treatment = new Treatment(userID,nickname,txt_treatName,txt_ingredients,txt_preparation);
                    updateUI(user,treatment);
                    Toast.makeText(TreatmentActivity.this, "Treatment Uploaded! \nConfirmation message will be sent.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TreatmentActivity.this, MainActivity.class));
                }}
        });

    }

    void updateUI(FirebaseUser user, Treatment treatment) {
        System.err.println(user.getEmail());
        System.err.println(user.getDisplayName()); // don't display asynchronic maybe
        FirebaseUsers.addTreatmentToDB(treatment);
    }

}



