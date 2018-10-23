package com.example.rafa.appcomfirebase.Cadastros;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.appcomfirebase.Model.Inspecao;
import com.example.rafa.appcomfirebase.Model.InspecaoChegada;
import com.example.rafa.appcomfirebase.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class ChegadaActivity extends AppCompatActivity {

    private EditText edthora,km,data,obs;
    private TextView idInspecao,check;
    private Button salvar;
    private DatePickerDialog datePickerDialogData;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspecao_data_hora_km);

        setTitle("");
        carregaIdInspecaoSaida();

        km = findViewById(R.id.km);
        km.setHint("KM Final");

        check = findViewById(R.id.check);
        check.setText("Check-OUT");

        inicializarFirebase();
        carregaHora();
        mostraHora();
        carregaCalendario();


        salvarInsp();

    }

    private void carregaIdInspecaoSaida() {
        Intent intent = getIntent();
        Inspecao inspecao = (Inspecao) intent.getSerializableExtra("id");
        idInspecao = findViewById(R.id.idC);
        idInspecao.setText(inspecao.getId());
    }

    private void salvarInsp() {
        salvar = findViewById(R.id.inspecao_salvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idInspecao = findViewById(R.id.idC);
                data = findViewById(R.id.data);
                edthora = findViewById(R.id.hora);
                km = findViewById(R.id.km);
                obs = findViewById(R.id.obs);

                InspecaoChegada i = new InspecaoChegada();
                i.setId(UUID.randomUUID().toString());
                i.setData(data.getText().toString());
                i.setHora(edthora.getText().toString());

                i.setKm(km.getText().toString());
                i.setIdInsp(idInspecao.getText().toString());
                i.setObs(obs.getText().toString());

                databaseReference.child("ChegadaInspecao").child(i.getId()).setValue(i);

                finish();
                Toast.makeText(ChegadaActivity.this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();


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
        FirebaseApp.initializeApp(ChegadaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance("https://appcomfirebase.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference();

    }
}
