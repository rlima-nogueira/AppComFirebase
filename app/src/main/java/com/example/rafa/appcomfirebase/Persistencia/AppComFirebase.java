package com.example.rafa.appcomfirebase.Persistencia;

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;

public class AppComFirebase extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}