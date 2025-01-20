package com.example.sgundot_di;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.registerButton).setOnClickListener(v -> registerUser());
        findViewById(R.id.loginButton).setOnClickListener(v -> loginUser());

    }

    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");

    ValueEventListener userListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                String userName = userSnapshot.child("name").getValue(String.class);
                Log.d("Firebase", "Nombre del usuario: " + userName);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.w("Firebase", "Error al leer datos", databaseError.toException());
        }
    };
    

    private void loginUser() {
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Error en autenticación.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

        private void registerUser() {
            String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
            String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Usuario registrado correctamente.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Error en el registro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
}
