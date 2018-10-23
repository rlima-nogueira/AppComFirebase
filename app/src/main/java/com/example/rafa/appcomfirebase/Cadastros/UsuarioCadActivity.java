package com.example.rafa.appcomfirebase.Cadastros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rafa.appcomfirebase.Model.Usuario;
import com.example.rafa.appcomfirebase.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class UsuarioCadActivity extends AppCompatActivity {

    private EditText nome, telefone, email, senha, confirmaSenha;
    private Button botao;
    private FirebaseDatabase firebaseDataBase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_cad);

        setTitle("");

        criaComponentes();
        inicializarFirebase();

        botaoSalvar();

    }

    private void botaoSalvar() {
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario u = new Usuario();
                    u.setId(UUID.randomUUID().toString());
                    u.setNome(nome.getText().toString());
                    u.setEmail(email.getText().toString());
                    u.setTelefone(telefone.getText().toString());
                    u.setSenha(senha.getText().toString());
                    u.setConfirmaSenha(confirmaSenha.getText().toString());

                 if(nome.getText().toString().trim().equals("")||telefone.getText().toString().trim().equals("")
                         || email.getText().toString().trim().equals("")){
                     Toast.makeText(UsuarioCadActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                     if(nome.getText().toString().equals("")){
                         nome.requestFocus();
                     }else if(email.getText().toString().equals("")){
                         email.requestFocus();
                     }else{
                         telefone.requestFocus();
                     }
                 }else if(u.getSenha().toString().equals(u.getConfirmaSenha().toString())){
                     databaseReference.child("Usuarios").child(u.getId()).setValue(u);
                     Toast.makeText(UsuarioCadActivity.this, "Usuário " +u.getNome() + " salvo com sucesso!", Toast.LENGTH_SHORT).show();
                     finish();
                 }else{
                     Toast.makeText(UsuarioCadActivity.this, "Verifique sua senha", Toast.LENGTH_SHORT).show();
                     senha.requestFocus();
                 }
            }
        });
    }

    private void criaComponentes() {
        nome = findViewById(R.id.cadUsuario_nome);
        telefone = findViewById(R.id.cadUsuario_telefone);
        email= findViewById(R.id.cadUsuario_email);
        senha = findViewById(R.id.cadUsuario_senha);
        confirmaSenha = findViewById(R.id.cadUsuario_ConfirmaSenha);
        botao = findViewById(R.id.cadUsuario_botao);
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(UsuarioCadActivity.this);
        firebaseDataBase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDataBase.getReference();
    }
}
