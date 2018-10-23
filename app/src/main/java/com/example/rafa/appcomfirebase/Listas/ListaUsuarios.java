package com.example.rafa.appcomfirebase.Listas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.example.rafa.appcomfirebase.Cadastros.UsuarioCadActivity;
import com.example.rafa.appcomfirebase.DemonstrativoInspecaoActivity;
import com.example.rafa.appcomfirebase.Model.Usuario;
import com.example.rafa.appcomfirebase.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuarios extends AppCompatActivity {
    private ListView lista_usuarios;
    private Button novo_usuario;
    private List<Usuario> listaUsuario = new ArrayList<Usuario>();
    private ArrayAdapter<Usuario> adapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializaFirebase();
        inicializaComponentes();

        carregaListaUsuarios();

        novoUsuario();

        lista_usuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListaUsuarios.this, DemonstrativoInspecaoActivity.class);
                Usuario usuario = (Usuario) lista_usuarios.getItemAtPosition(position);
                intent.putExtra("Usuario", usuario);
                startActivity(intent);
            }
        });
    }

    private void novoUsuario() {
        novo_usuario = findViewById(R.id.carro_novo);
        novo_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaUsuarios.this, UsuarioCadActivity.class);
                startActivity(intent);
            }
        });
    }

    private void carregaListaUsuarios() {
        databaseReference.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaUsuario.clear();
                for(DataSnapshot objetoSnapShot:dataSnapshot.getChildren()){
                    Usuario u = objetoSnapShot.getValue(Usuario.class);
                    listaUsuario.add(u);
                }
                adapter = new ArrayAdapter<Usuario>(ListaUsuarios.this, android.R.layout.simple_list_item_1, listaUsuario);
                lista_usuarios.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializaComponentes() {
        lista_usuarios = findViewById(R.id.carro_lista);
        novo_usuario = findViewById(R.id.carro_novo);
    }

    private void inicializaFirebase() {
        FirebaseApp.initializeApp(ListaUsuarios.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaListaUsuarios();
    }
}
