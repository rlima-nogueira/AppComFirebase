package com.example.rafa.appcomfirebase.Listas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.appcomfirebase.Cadastros.ChegadaActivity;
import com.example.rafa.appcomfirebase.Cadastros.InspecaoDataHoraKm;
import com.example.rafa.appcomfirebase.DemonstrativoInspecaoActivity;
import com.example.rafa.appcomfirebase.Model.Carro;
import com.example.rafa.appcomfirebase.Model.Inspecao;
import com.example.rafa.appcomfirebase.Model.InspecaoChegada;
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

public class ListaInspecao extends AppCompatActivity{

    private ListView lista_inspecao;
    private TextView id;
    private TextView idInspChegada;
    private Button nova_inspecao;
    private Usuario user;
    private List<Inspecao> listaInspecao = new ArrayList<Inspecao>();
    private List<InspecaoChegada> listaChegada = new ArrayList<InspecaoChegada>();
    private ArrayAdapter<Inspecao> adapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Lista de Inspeções");
        carregaComponentes();
        inicializaFirebase();
        carregaLista();
        carregaIdCarro();
        novaInspecao();

        cadastraChegada();
        registerForContextMenu(lista_inspecao);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem visualizar = menu.add("Visualizar");
        visualizar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Intent intent = new Intent(ListaInspecao.this, DemonstrativoInspecaoActivity.class);
                Inspecao inspecao= (Inspecao) lista_inspecao.getItemAtPosition(info.position);
                intent.putExtra("Inspecao", inspecao);
                startActivity(intent);
                return false;
            }
        });
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                new AlertDialog.Builder(ListaInspecao.this)
                        .setTitle("Deletando Inspeção")
                        .setMessage("Tem certeza que deseja deletar essa inspeção?")
                        .setPositiveButton("sim",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        adapter.notifyDataSetChanged();
                                        listaInspecao.remove(item);
                                        Inspecao in = (Inspecao) lista_inspecao.getItemAtPosition(info.position);
                                        databaseReference.child("Inspecao").child(in.getId()).removeValue();
                                    }
                                })
                        .setNegativeButton("não", null)
                        .show();


                return false;
            }
        });
    }

    private void carregaComponentes() {
        lista_inspecao = findViewById(R.id.carro_lista);
        idInspChegada = findViewById(R.id.idInspChegada);
        id = findViewById(R.id.id);
        nova_inspecao = findViewById(R.id.carro_novo);
    }


    private void cadastraChegada() {
        lista_inspecao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
               final Inspecao inspecao = (Inspecao) lista_inspecao.getItemAtPosition(position);

               databaseReference.child("ChegadaInspecao").addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       listaChegada.clear();
                       String idChegada = null;
                       for (DataSnapshot objetoSnapShot : dataSnapshot.getChildren()) {
                           InspecaoChegada i = objetoSnapShot.getValue(InspecaoChegada.class);

                           if (i.getIdInsp().equals(inspecao.getId())) {
                               listaChegada.add(i);
                               idInspChegada.setText(i.getId());
                           }
                           idChegada = idInspChegada.getText().toString();
                       }
                  if (!idChegada.equals("")) {
                      idInspChegada.setText("");
                      idChegada = "";
                      Toast.makeText(ListaInspecao.this, "Inspeção finalizada!", Toast.LENGTH_SHORT).show();
                   } else {
                       Intent intent = new Intent(ListaInspecao.this, ChegadaActivity.class);
                       intent.putExtra("id", inspecao);
                       startActivity(intent);
                   }

                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {
                   }
               });


           }
        });

    }

    private void novaInspecao() {
        nova_inspecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaInspecao.this, InspecaoDataHoraKm.class);
                intent.putExtra("id", id.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void carregaIdCarro() {
        Intent intent = getIntent();
        Carro carro = (Carro) intent.getSerializableExtra("Carro");

        id.setText(carro.getId());
    }

    private void carregaLista() {
        databaseReference.child("Inspecao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaInspecao.clear();
                for(DataSnapshot objetoSnapShot:dataSnapshot.getChildren()){
                    Inspecao i = objetoSnapShot.getValue(Inspecao.class);

                    if(i.getIdCarro().equals(id.getText().toString()))
                    listaInspecao.add(i);
                }
                adapter = new ArrayAdapter<Inspecao>(ListaInspecao.this, android.R.layout.simple_list_item_1, listaInspecao);
                lista_inspecao.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializaFirebase() {
        FirebaseApp.initializeApp(ListaInspecao.this);
        firebaseDatabase = FirebaseDatabase.getInstance("https://appcomfirebase.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }
}

