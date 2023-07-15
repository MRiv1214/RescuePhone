package com.example.rescuephone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    private AppCompatButton BtRegis;

    private EditText etnomb, etcorr, etfecna, etUsuario, etContra, etcontraveri, etNumcel, etDom, etGen;

    private TextInputLayout TillNom, TillCorreo, TillFecha, TillNombUsu, TillContra, TillContra2, TillNumCel, TillDomi, TillGenero;

    private TextView loginacti;

    FirebaseFirestore mFirestore;

    DatePickerDialog.OnDateSetListener setListener;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        TillNom = findViewById(R.id.till_name_reg);
        TillCorreo = findViewById(R.id.till_email);
        TillFecha = findViewById(R.id.till_FechaNa);
        TillNombUsu = findViewById(R.id.till_Username);
        TillContra = findViewById(R.id.till_Pass1);
        TillContra2 = findViewById(R.id.till_Pass2);
        TillNumCel = findViewById(R.id.till_Numtel);
        TillDomi = findViewById(R.id.till_Dom);
        TillGenero = findViewById(R.id.till_Gen);

        etnomb = findViewById(R.id.ETReg_Nom);
        etcorr = findViewById(R.id.ETReg_Email);
        etfecna = findViewById(R.id.ETReg_FechaNa);
        etUsuario = findViewById(R.id.ETReg_Usu);
        etContra = findViewById(R.id.ETReg_Pass1);
        etcontraveri = findViewById(R.id.ETReg_Pass2);
        etNumcel = findViewById(R.id.ET_Reg_Numtel);
        etDom = findViewById(R.id.ET_Reg_Dom);
        etGen = findViewById(R.id.ETReg_Gen);

        BtRegis = findViewById(R.id.btn_reg);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR), month = calendar.get(Calendar.MONTH), day = calendar.get(Calendar.DAY_OF_MONTH);
        etfecna.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    Signup.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,setListener,year,month,day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });
        setListener = (view, year1, month1, dayOfMonth) -> {
            month1 = month1 +1;
            String date= dayOfMonth+"/"+ month1 +"/"+ year1;
            etfecna.setText(date);
        };



        loginacti = findViewById(R.id.Inicio_ses);
        loginacti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });



        BtRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etcorr.getText().toString().trim();
                String pass = etContra.getText().toString().trim();

                if(email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Signup.this, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(email, pass);
                }
            }
            //Agrego docigo a partir de aqui
        });
    }
    private void registerUser(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Map<String, Object> map = new HashMap<>();
                map.put("mail",email);
                map.put("password",pass);
                map.put("username","");
                mFirestore.collection("users").document(mAuth.getUid()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Intent intent = new Intent(Signup.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(Signup.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Signup.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(Signup.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

