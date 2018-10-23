package com.example.rafa.appcomfirebase.Cadastros;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rafa.appcomfirebase.Model.Inspecao;
import com.example.rafa.appcomfirebase.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class InspecaoDataHoraKm extends AppCompatActivity {

    private EditText edthora,km,data, obs;
    private TextView id, check;
    private Button salvar;
    private DatePickerDialog datePickerDialogData;
    private FirebaseDatabase firebaseDataBase;
    private DatabaseReference databaseReference;
    private String codCarro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspecao_data_hora_km);

        Intent it = getIntent();
        codCarro = it.getStringExtra("id");

        setTitle("");

        check = findViewById(R.id.check);
        check.setText("Check-IN");

        obs = findViewById(R.id.obs);
        obs.setVisibility(View.INVISIBLE);
        km = findViewById(R.id.km);
        km.setHint("KM Inicial");

        inicializarFirebase();
        carregaHora();
        mostraHora();
        carregaCalendario();



        salvarInsp();
    }

    private void salvarInsp() {
        salvar = findViewById(R.id.inspecao_salvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = findViewById(R.id.idC);
                data = findViewById(R.id.data);
                edthora = findViewById(R.id.hora);
                km = findViewById(R.id.km);

                Inspecao i = new Inspecao();
                i.setId(UUID.randomUUID().toString());
                i.setIdCarro(codCarro);
                i.setDataSaida(data.getText().toString());
                i.setHoraSaida(edthora.getText().toString());

                i.setKmSaida(km.getText().toString());

                databaseReference.child("Inspecao").child(i.getId()).setValue(i);

                Intent intent = new Intent(InspecaoDataHoraKm.this, FormularioActivity.class);
                intent.putExtra("id", codCarro);
                startActivity(intent);
                finish();
            }
        });
    }


    private void mostraHora() {
        data = findViewById(R.id.data);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialogData.show();
            }
        });
    }

    private void carregaCalendario() {
        final Calendar calendarDataAtual = Calendar.getInstance();
        int anoAtual   = calendarDataAtual.get(Calendar.YEAR);
        int mesAtual   = calendarDataAtual.get(Calendar.MONTH);
        int diaAtual   = calendarDataAtual.get(Calendar.DAY_OF_MONTH);
        datePickerDialogData = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {
                String mes = (String.valueOf((mesSelecionado + 1)).length() == 1 ? "0" + (mesSelecionado + 1 ): String.valueOf(mesSelecionado));
                data = findViewById(R.id.data);
                data.setText(diaSelecionado + "/" + mes + "/" + anoSelecionado);
            }

        }, anoAtual, mesAtual, diaAtual);
    }

    private void carregaHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date hora = Calendar.getInstance().getTime();
        String dataFormatada = sdf.format(hora);
        edthora = findViewById(R.id.hora);
        edthora.setText(dataFormatada);
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(InspecaoDataHoraKm.this);
        firebaseDataBase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDataBase.getReference();

    }
}
