package asd.datenbank2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());
        springi();
    }

    public void springi() {   // Überpruft ob der user schon in der Datenbank ist

        Wuser x = new Wuser();
        x = db.getUserData();

        if(x.getId()!= 1 ){
            startActivity(new Intent(MainActivity.this, Anleitung.class));}
        else{
            startActivity(new Intent(MainActivity.this, WeckerActivity.class));}
    }
}