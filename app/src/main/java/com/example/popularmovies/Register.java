package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView btn_already;
    private Button btn_register;
    private EditText editFirstName, editLastName, editEmail, editPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        btn_already = findViewById(R.id.alreadyAccount);
        btn_already.setOnClickListener(this);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        editFirstName = findViewById(R.id.firstName);
        editLastName = findViewById(R.id.lastName);
        editEmail = findViewById(R.id.email_register);
        editPassword = findViewById(R.id.password_register);

    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.alreadyAccount:
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            break;
        case R.id.btn_register:
            registerUser();
            break;
    }
    }

    private void registerUser() {

        String firstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(firstName.isEmpty()){
            Toast.makeText(this, "Please enter the First Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(lastName.isEmpty()){
            Toast.makeText(this, "Please enter the Last Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.isEmpty()){
            Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 6) {
            Toast.makeText(this, "The password must contain at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if(email.isEmpty()){
            Toast.makeText(this, " Please enter the email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please provide valid email");
            editEmail.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(firstName, lastName, email);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "Registration was successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Register.this, LoginActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(Register.this, "Registration was not successful", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else {
                    Toast.makeText(Register.this, "Registration was not successful!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}