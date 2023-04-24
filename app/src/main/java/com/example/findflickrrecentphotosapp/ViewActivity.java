package com.example.findflickrrecentphotosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.findflickrrecentphotosapp.utilities.DownloadImageFromInternet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ViewActivity";
    FirebaseDatabase database;
     DatabaseReference myRef;
    Button btnSave;

    String url;
    String uniq_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        new DownloadImageFromInternet((ImageView) findViewById(R.id.image_view)).execute(url);
        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        uniq_id=intent.getStringExtra("uniq_id");
        myRef = database.getReference(uniq_id);

    }
    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: clicked ");
        myRef.setValue(url).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess: done");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: error");

            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete: finish");
                Toast.makeText(ViewActivity.this, R.string.saved, Toast.LENGTH_SHORT).show();
            }
        });

    }

}