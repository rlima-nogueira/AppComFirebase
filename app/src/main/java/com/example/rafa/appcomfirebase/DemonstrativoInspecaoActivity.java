package com.example.rafa.appcomfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rafa.appcomfirebase.Listas.ListaUsuarios;
import com.example.rafa.appcomfirebase.Model.Inspecao;
import com.example.rafa.appcomfirebase.Model.InspecaoChegada;
import com.example.rafa.appcomfirebase.Model.Resposta;
import com.example.rafa.appcomfirebase.Model.Usuario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DemonstrativoInspecaoActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private TextView dias, horas, kms, situs, diac, horac, kmc, situc,saida, chegada;
    private TextView chegadadia, chegadahora, chegadakm;
    private Button voltar, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demonstrativo_inspecao);

        setTitle("");
        inicializaFirebase();

        inicializaComponentes();

        Intent intent = getIntent();
        Usuario usuario = (Usuario) intent.getSerializableExtra("Usuario");
        saida.setText("Dados do Usuário");
        dias.setText(usuario.getNome());
        horas.setText(usuario.getEmail());
        kms.setText(usuario.getTelefone());
        situs.setText("Usuário ativo!");


        voltar.setText("VOLTAR");
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        diac.setVisibility(View.INVISIBLE);
        horac.setVisibility(View.INVISIBLE);
        kmc.setVisibility(View.INVISIBLE);
        situc.setVisibility(View.INVISIBLE);

        chegada.setVisibility(View.INVISIBLE);
        email.setVisibility(View.INVISIBLE);

        chegadakm.setVisibility(View.INVISIBLE);
        chegadahora.setVisibility(View.INVISIBLE);
        chegadadia.setVisibility(View.INVISIBLE);
    }

    private void inicializaComponentes() {
        dias = findViewById(R.id.saida_dia_resp);
        horas = findViewById(R.id.saida_hora_resp);
        kms = findViewById(R.id.saida_km_resp);
        situs = findViewById(R.id.situacao_saida);

        diac = findViewById(R.id.chegada_dia_resp);
        horac = findViewById(R.id.chegada_hora_resp);
        kmc = findViewById(R.id.chegada_km_resp);
        situc = findViewById(R.id.situacao_chegada);
        saida = findViewById(R.id.saida);
        chegada = findViewById(R.id.chegada);
        chegadadia = findViewById(R.id.chegada_dia);
        chegadahora = findViewById(R.id.chegada_hora);
        chegadakm = findViewById(R.id.chegada_km);

        voltar = findViewById(R.id.pdf);
        email = findViewById(R.id.email);



    }

    private void inicializaFirebase() {
        FirebaseApp.initializeApp(DemonstrativoInspecaoActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
