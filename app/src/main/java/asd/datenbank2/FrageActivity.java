package asd.datenbank2;

import java.util.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FrageActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button btnA, btnB, btnC, btnD;
    TextView frage;


    // Imported Items
    String richtig;
    String b;
    String c;
    String d;
    String hinweis;

    // Randomized Items
    String item1;
    String item2;
    String item3;
    String item4;

    List<String> list = new ArrayList<>();
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frage);


        db = new DatabaseHelper(this);

        btnA = (Button)findViewById(R.id.antwort_a);
        btnB = (Button)findViewById(R.id.antwort_b);
        btnC = (Button)findViewById(R.id.antwort_c);
        btnD = (Button)findViewById(R.id.antwort_d);
        frage = (TextView)findViewById(R.id.FrageView);


        importData();
        randomize();
        pruef();



    }

    public void importData() {      // Data wird importiert

        Frage x = new Frage();
        x = db.getRandomFrage(db.getIDRandomFrage());
        StringBuffer buffer = new StringBuffer();

        frage.setText(x.getFrage());
        btnA.setText(x.getRichtigeAntwort());
        btnB.setText(x.getAntwortB());
        btnC.setText(x.getAntwortC());
        btnD.setText(x.getAntwortD());

        richtig = x.getRichtigeAntwort();
        b = (x.getAntwortB());
        c = (x.getAntwortC());
        d = (x.getAntwortD());
        hinweis = x.getHinweis();
    }

    public void randomize() {    // Randomize die Frage

        Random randomGenerator = new Random();

        list.add(b);
        list.add(c);
        list.add(d);
        list.add(richtig);

        item1 = list.get(randomGenerator.nextInt(list.size()));
        btnA.setText(item1);
        list.remove(item1);

        item2 = list.get(randomGenerator.nextInt(list.size()));
        btnB.setText(item2);
        list.remove(item2);

        item3 = list.get(randomGenerator.nextInt(list.size()));
        btnC.setText(item3);
        list.remove(item3);

        item4 = list.get(randomGenerator.nextInt(list.size()));
        btnD.setText(item4);
    }

    public  void pruef() {     //Überpruft ob die richtige antwort ist


        btnA.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if( item1 == richtig){
                            richtig();}
                        else{
                            falses();
                        }
                    }
                }
        );
        btnB.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if( item2 == richtig){
                            richtig();}
                        else{
                            falses();
                        }
                    }
                }
                );
        btnC.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if( item3 == richtig){
                            richtig();}
                        else{
                            falses();
                        }
                    }
                }
        );
        btnD.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if( item4 == richtig){
                            richtig();}
                        else{
                            falses();
                        }
                    }
                }
        );
    }


    public void  richtig(){
        this.context = this;
        final Intent intent = new Intent(this.context, Reciever.class);

        Toast.makeText(FrageActivity.this,"RICHTIG!" ,Toast.LENGTH_SHORT).show();
        Wuser x = new Wuser();
        x = db.getUserData();
        String name = x.getName();
        long punkte = (x.getPunkte() + 1);
        if (x.getPunkte() >= 0){
            intent.putExtra("extra", "off");
            sendBroadcast(intent);}
        db.deleteUserData();
        Wuser wuser = new Wuser(1, name, (int) punkte,1);
        db.insertUserData(wuser);
        startActivity(new Intent(FrageActivity.this, GmActivity.class));


    }

    public  void  falses(){

        Toast.makeText(FrageActivity.this, hinweis ,Toast.LENGTH_SHORT).show();

    }

    public void showMessage(String title,String Message){                       // Shows Toast message
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}