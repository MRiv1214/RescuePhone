package com.example.rescuephone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private EditText loginUser, loginPass;

    private TextInputLayout tillEmail, tillPass;

    private Button login_but;

    private ProgressBar progressBar;

    private TextView reg_act, rescontra;



    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        tillEmail = findViewById(R.id.Till_LogMail);
        tillPass = findViewById(R.id.Till_PassLog);
        loginUser = findViewById(R.id.ETLog_mail);
        loginPass = findViewById(R.id.ETLog_pass);
        rescontra = findViewById(R.id.Log_RESContra);
        login_but = findViewById(R.id.btn_login);
        reg_act = findViewById(R.id.Reg_Act);
        reg_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
                finish();
            }
        });

        login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginUser.getText().toString().trim();
                String pass = loginPass.getText().toString().trim();

                if(email.isEmpty() || pass.isEmpty()){
                    Toast.makeText(Login.this, "Por favor ingrese los datos", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email, pass);
                }
            }
        });


        /*login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(loginUser.getText());
                password = String.valueOf(loginPass.getText());

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Ingresa tu correo", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.VISIBLE);
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Se ha iniciado sesion correctamente",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else{
                                    Toast.makeText(Login.this, "Los datos ingresaods sin incorrectos",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });*/
    }
    private void loginUser(String user, String pass){
        mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(Login.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "Hubo un error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


}