package com.example.rafa.appcomfirebase;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rafa.appcomfirebase.Listas.ListaCarros;
import com.example.rafa.appcomfirebase.Listas.ListaUsuarios;
import com.example.rafa.appcomfirebase.Model.Usuario;
import com.example.rafa.appcomfirebase.Persistencia.AppComFirebase;

public class TelaPosLoginActivity extends AppCompatActivity {

    private ImageView carro, usuario, relatorio, hln;
    private TextView txtuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pos_login);

        reconheceComponentes();
        carregaUsuario();
        vaiParaListaCarros();

        vaiParaListaUsuarios();

        vaiParaRelatorio();



    }

    private void carregaUsuario() {
        Intent intent = getIntent();
        Usuario usuario = (Usuario) intent.getSerializableExtra("Usuario");
        txtuser = findViewById(R.id.nome_usuario);
        txtuser.setText("Usuário: " + usuario.getNome());
    }

    private void vaiParaRelatorio() {
        relatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPosLoginActivity.this, ListaCarros.class);
                startActivity(intent);
            }
        });
    }

    private void vaiParaListaUsuarios() {
        usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPosLoginActivity.this, ListaUsuarios.class);
                startActivity(intent);
            }
        });
    }

    private void vaiParaListaCarros() {
        carro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPosLoginActivity.this, ListaCarros.class);
                startActivity(intent);
            }
        });
    }

    private void reconheceComponentes() {
        carro = findViewById(R.id.posLogin_carro);
        usuario = findViewById(R.id.posLogin_usuario);
        relatorio = findViewById(R.id.posLogin_relatorio);
        hln = findViewById(R.id.posLogin_img);
    }
}
