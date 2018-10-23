package com.example.rafa.appcomfirebase.Cadastros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rafa.appcomfirebase.Model.Carro;
import com.example.rafa.appcomfirebase.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class CadCarroActivity extends AppCompatActivity {
    private EditText nome, placa;
    private Button botao;
    private FirebaseDatabase firebaseDataBase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_carro);

        setTitle("");

        inicializarFirebase();

        botao = findViewById(R.id.carro_salvar);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome = findViewById(R.id.carro_nome);
                placa = findViewById(R.id.carro_placa);
                Carro c = new Carro();
                c.setId(UUID.randomUUID().toString());
                c.setNome(nome.getText().toString());
                c.setPlaca(placa.getText().toString());
                databaseReference.child("Carro").child((c.getId())).setValue(c);

                Toast.makeText(CadCarroActivity.this, "Carro " + c.getNome() + " salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(CadCarroActivity.this);
        firebaseDataBase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDataBase.getReference();
    }
}
