package com.example.rafa.appcomfirebase.Cadastros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.appcomfirebase.Model.Pergunta;
import com.example.rafa.appcomfirebase.Model.Resposta;
import com.example.rafa.appcomfirebase.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FormularioActivity extends AppCompatActivity {

    private EditText obs;
    private RadioButton conforme, nconforme, nseaplica;
    private TextView pergunta, idPergunta;
    private String codCarro;
    private Button botao;
    private List<Pergunta> perguntas = new ArrayList<Pergunta>();
    private int indicePerguntaAtual = 0;
    private FirebaseDatabase firebaseDataBase;
    private DatabaseReference databaseReference;
    private Pergunta perguntaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        inicializarFirebase();
        carregaComponentes();

        carregaIdCarro();

        atualizaFormPerguntaAtual();
        botaoProximo();

    }

    private void carregaIdCarro() {
        Intent it = getIntent();
        codCarro = it.getStringExtra("id");
    }

    private void atualizaFormPerguntaAtual() {
        databaseReference.child("Perguntas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                perguntas.clear();
                for(DataSnapshot objetoSnapShot:dataSnapshot.getChildren()){
                    Pergunta p = objetoSnapShot.getValue(Pergunta.class);
                    perguntas.add(p);
                }
                if(indicePerguntaAtual<perguntas.size()){
                    perguntaAtual = perguntas.get(indicePerguntaAtual);
                    pergunta.setText(perguntaAtual.getPergunta());
                    idPergunta.setText(perguntaAtual.getId());

                }else{
                    Toast.makeText(FormularioActivity.this, "Salvo com Sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void botaoProximo() {
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicePerguntaAtual++;
                responde();
            }
        });
    }

    private void carregaComponentes() {
        idPergunta = findViewById(R.id.idPergunta);
        obs = findViewById(R.id.form_obs);
        conforme = findViewById(R.id.form_conforme);
        nconforme = findViewById(R.id.form_nconforme);
        nseaplica = findViewById(R.id.form_nseaplica);
        pergunta = findViewById(R.id.form_pergunta);
        botao = findViewById(R.id.form_botao);
    }

    private void responde() {
        Resposta resposta = new Resposta();
            resposta.setId(UUID.randomUUID().toString());
            resposta.setIdCarro(codCarro);
            resposta.setIdPergunta(idPergunta.getText().toString());
            resposta.setObs(obs.getText().toString());

            if(conforme.isChecked()){
                resposta.setResposta(conforme.getText().toString());
            }else if (nconforme.isChecked()){
                resposta.setResposta(nconforme.getText().toString());
            }else{
                resposta.setResposta(nseaplica.getText().toString());
            }
        databaseReference.child("Resposta").child(resposta.getId()).setValue(resposta);
        atualizaFormPerguntaAtual();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(FormularioActivity.this);
        firebaseDataBase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDataBase.getReference();
    }
}
