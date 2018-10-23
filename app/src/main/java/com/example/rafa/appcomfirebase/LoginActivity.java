package com.example.rafa.appcomfirebase;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rafa.appcomfirebase.Cadastros.CadCarroActivity;
import com.example.rafa.appcomfirebase.Listas.ListaCarros;
import com.example.rafa.appcomfirebase.Model.Usuario;
import com.example.rafa.appcomfirebase.Persistencia.AppComFirebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDataBase;
    private DatabaseReference databaseReference;
    private EditText usuario, senha;
    private Button botao;
    private List<Usuario> usuarios = new ArrayList<Usuario>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("");
        criaComponentes();
        inicializarFirebase();

        botaoLogin();

    }

    private void botaoLogin() {
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Usuarios").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Usuario u = null;
                        boolean var = false;
                        for(DataSnapshot objetoSnapShot:dataSnapshot.getChildren()){
                            u = objetoSnapShot.getValue(Usuario.class);
                            usuarios.add(u);
                            Intent intent = new Intent(LoginActivity.this, TelaPosLoginActivity.class);
                            if(u.getNome().toString().equals(usuario.getText().toString())&&
                                    u.getSenha().toString().equals(senha.getText().toString())){
                                intent.putExtra("Usuario", u);
                                var=true;
                                startActivity(intent);
                            }
                        }
                        if(var==false){
                            Toast.makeText(LoginActivity.this, "Verifique login e senha!", Toast.LENGTH_SHORT).show();
                            usuario.requestFocus();
                        }
                    //    if(u!=null) {


                     //   }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    private void criaComponentes() {
        usuario = findViewById(R.id.login_usuario);
        senha = findViewById(R.id.login_senha);
        botao = findViewById(R.id.login_botao);
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(LoginActivity.this);
        firebaseDataBase = FirebaseDatabase.getInstance("https://appcomfirebase.firebaseio.com/");
        databaseReference = firebaseDataBase.getReference();

    }

}
