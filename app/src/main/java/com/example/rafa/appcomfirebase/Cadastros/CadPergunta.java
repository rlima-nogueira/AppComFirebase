package com.example.rafa.appcomfirebase.Cadastros;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.appcomfirebase.Model.Pergunta;
import com.example.rafa.appcomfirebase.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class CadPergunta extends AppCompatActivity {

    private EditText pergunta, campo;
    private Button botao;
    private FirebaseDatabase firebaseDataBase;
    private DatabaseReference databaseReference;
    private TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_carro);

        setTitle("");
        inicializarFirebase();

        titulo = findViewById(R.id.carro_cadastro);
        titulo.setText("Cadastro de Perguntas");

        campo = findViewById(R.id.carro_nome);
        campo.setVisibility(View.INVISIBLE);

        pergunta = findViewById(R.id.carro_placa);
        pergunta.setHint("Pergunta");

        botao = findViewById(R.id.carro_salvar);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pergunta p = new Pergunta();
                p.setId(UUID.randomUUID().toString());
                p.setPergunta(pergunta.getText().toString());
                databaseReference.child("Perguntas").child(p.getId()).setValue(p);

                Toast.makeText(CadPergunta.this, "Pergunta salva!", Toast.LENGTH_SHORT).show();
                pergunta.setText("");

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(CadPergunta.this);
        firebaseDataBase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDataBase.getReference();
    }
}
