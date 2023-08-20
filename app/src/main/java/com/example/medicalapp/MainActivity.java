package com.example.medicalapp;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView txt1, txt2, txt3;
    EditText name, pass, email;
    private FirebaseAuth auth;
    private DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        txt3 = findViewById(R.id.txt3);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.pass);
        email = findViewById(R.id.email);
        btn = findViewById(R.id.btn);

        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = name.getText().toString();
                String upass = pass.getText().toString();
                String uemail = email.getText().toString();
                if (uname.isEmpty() || uemail.isEmpty() || upass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                } else if (upass.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password must have at least 6 digits or letters", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(uemail)) {
                    Toast.makeText(getApplicationContext(), "Invalid email format. Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                } else {
                    if (isDeviceOnline()) {
                        checkUserExistsAndRegister(uname, uemail, upass);
                        startActivity(new Intent(MainActivity.this, Login.class));
                        Toast.makeText(getApplicationContext(), "Success now you can Login", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Device is offline. Please connect to the internet and try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
    private boolean isDeviceOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static boolean isValidEmail(String email) {
        return email.contains("@") && (email.endsWith(".com") || email.endsWith(".gr"));
    }

    private void checkUserExistsAndRegister(final String name, final String email, final String password) {
        auth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            if (result != null && result.getSignInMethods() != null && result.getSignInMethods().size() > 0) {
                                Toast.makeText(MainActivity.this, "User with this email already exists. Please log in.", Toast.LENGTH_SHORT).show();
                            } else {
                                registerAndSaveData(name, email, password);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to check if user exists. Please try again later.", Toast.LENGTH_SHORT).show();
                            Log.e("FirebaseDebug", "Failed to check user existence: " + task.getException());
                        }
                    }
                });
    }

    private void registerAndSaveData(final String name, final String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendEmailVerification();
                            saveUserDataToFirebase(name, email, password);
                        } else {
                            Toast.makeText(MainActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                            Log.e("FirebaseDebug", "Registration failed: " + task.getException());
                        }
                    }
                });
    }

    private void sendEmailVerification() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Verification email sent. Please verify your email address.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                Log.e("FirebaseDebug", "Failed to send verification email: " + task.getException());
                            }
                        }
                    });
        }
    }

    private void saveUserDataToFirebase(String name, String email, String password) {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            UserModel userModel = new UserModel(name, email, password); // Update otherField with the appropriate value
            database.child("users").child(userId).setValue(userModel)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "Registration successful. Data saved successfully", Toast.LENGTH_SHORT).show();
                            Log.d("FirebaseDebug", "Registration successful. Data saved successfully");
                            fetchDataFromFirebase(userId);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String errorMessage = "Failed: " + e.getMessage();
                            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            Log.e("FirebaseDebug", errorMessage);
                        }
                    });
        }
    }

    private void fetchDataFromFirebase(String userId) {
        database.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                if (userModel != null) {
                    String name = userModel.getName();
                    String email = userModel.getEmail();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseDebug", "Failed to fetch data: " + databaseError.getMessage());
            }
        });
    }
}


