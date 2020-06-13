
// Please insert your firebase json file i have deleted mine

package com.mt.gatepasslogin; //your package name
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //
    EditText username, password;
    Button login;
    Spinner cat;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.userid);
        password = findViewById(R.id.pass);
        cat = findViewById(R.id.category);
        login = findViewById(R.id.login);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(username.getText().toString().equals("")) && !(password.getText().toString().equals("")) && (username.getText().toString().length() > 4) && (password.getText().toString().length() > 6)) {
                    // Condition to check if the fields are not empty and greater than some value
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("cat").child(cat.getSelectedItem().toString()).hasChild(username.getText().toString()))
                            // Condition to check whether there is a username in firebase
                            {
                                if (password.getText().toString().equals(dataSnapshot.child("cat").child(cat.getSelectedItem().toString()).child(username.getText().toString()).child("password").getValue().toString()))
                                // Condition to check the password is matching with the entered password
                                {
                                    // To go to next windows based on their selection with spinner..
                                    Intent intent = new Intent(getBaseContext(), homescreen.class);
                                    intent.putExtra("user", username.getText().toString());
                                    startActivity(intent);


                                } else {
                                    Toast.makeText(MainActivity.this, "Please enter correct password", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Username does not exist.", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });


                } else {
                    if ((username.getText().toString().equals("")) || !(username.getText().toString().length() > 4)) {
                        username.setError("Enter valid username");

                    } else {
                        username.setError(null);
                    }


                    if ((password.getText().toString().equals("")) || !(password.getText().toString().length() >= 5)) {
                        password.setError("Enter valid password");

                    } else {
                        password.setError(null);
                    }
                }


            }


        });


    }


}
