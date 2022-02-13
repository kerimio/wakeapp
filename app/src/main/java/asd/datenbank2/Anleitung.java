package asd.datenbank2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Anleitung extends AppCompatActivity {

    Button homeButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anleitung);


        homeButton = (Button)findViewById(R.id.home);
        homeButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        springi2();
                    }
                }
        );

        db = new DatabaseHelper(getApplicationContext());
    }

    public void springi2() {   // Überpruft ob der user schon in der Datenbank ist

        Wuser x = new Wuser();
        x = db.getUserData();

        if(x.getId()!= 1 ){
            startActivity(new Intent(Anleitung.this, NameActivity.class));}
        else{
            startActivity(new Intent(Anleitung.this, WeckerActivity.class));}
    }
}
