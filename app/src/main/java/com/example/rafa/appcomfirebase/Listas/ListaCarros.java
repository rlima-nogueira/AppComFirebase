package com.example.rafa.appcomfirebase.Listas;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.rafa.appcomfirebase.Cadastros.CadCarroActivity;
import com.example.rafa.appcomfirebase.Model.Carro;
import com.example.rafa.appcomfirebase.Model.Inspecao;
import com.example.rafa.appcomfirebase.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaCarros extends AppCompatActivity {

    private ListView lista_carros;
    private Button novo_carro;
    private List<Carro> listaCarro = new ArrayList<Carro>();
    private ArrayAdapter<Carro> adapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Lista de Carros");
        inicializaFirebase();
        carregaLista();
        novoCarro();
        vaiParaListaInspecao();

        lista_carros = findViewById(R.id.carro_lista);
        registerForContextMenu(lista_carros);

    }

    @Override
    public void onCreateContextMenu(final ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                new AlertDialog.Builder(ListaCarros.this)
                        .setTitle("Deletando Carro")
                        .setMessage("Tem certeza que deseja deletar esse carro?")
                        .setPositiveButton("sim",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        adapter.notifyDataSetChanged();
                                        listaCarro.remove(item);
                                        Carro c = (Carro) lista_carros.getItemAtPosition(info.position);
                                        databaseReference.child("Carro").child(c.getId()).removeValue();
                                    }
                                })
                        .setNegativeButton("não", null)
                        .show();


                return false;
            }
        });

    }

    private void vaiParaListaInspecao() {
        lista_carros = findViewById(R.id.carro_lista);
        lista_carros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListaCarros.this, ListaInspecao.class);
                Carro carro = (Carro) lista_carros.getItemAtPosition(position);
                intent.putExtra("Carro", carro);
                startActivity(intent);
            }
        });
    }

    private void novoCarro() {
        novo_carro = findViewById(R.id.carro_novo);
        novo_carro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaCarros.this, CadCarroActivity.class);
                startActivity(intent);
            }
        });
    }

    private void inicializaFirebase() {
        FirebaseApp.initializeApp(ListaCarros.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void carregaLista() {
        databaseReference.child("Carro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaCarro.clear();
                for(DataSnapshot objetoSnapShot:dataSnapshot.getChildren()){
                    Carro c = objetoSnapShot.getValue(Carro.class);
                        listaCarro.add(c);
                }
                adapter = new ArrayAdapter<Carro>(ListaCarros.this, android.R.layout.simple_list_item_1, listaCarro);
                lista_carros = findViewById(R.id.carro_lista);
                lista_carros.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }
}
