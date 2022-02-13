package asd.datenbank2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends Activity {

    DatabaseHelper db;
    Button btnA, btnB, btnC, bt, homeButton, anleitung;
    TextView frage, UserView, PunkteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        db = new DatabaseHelper(getApplicationContext());
        UserView = (TextView)findViewById(R.id.UserView);
        PunkteView = (TextView)findViewById(R.id.PunkteView);
        btnA = (Button)findViewById(R.id.name_reset);
        btnB = (Button)findViewById(R.id.pakete_reset);
        btnC = (Button)findViewById(R.id.custom_frage);
        frage = (TextView)findViewById(R.id.schowReset);

        homeButton = (Button)findViewById(R.id.home);
        homeButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent I = new Intent(EditActivity.this, WeckerActivity.class);
                        startActivity(I);
                    }
                }
        );

        anleitung = (Button)findViewById(R.id.anleitung);
        anleitung.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent I = new Intent(EditActivity.this, Anleitung.class);
                        startActivity(I);
                    }
                }
        );

        bt= (Button)findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = getString(R.string.share) + " "  ;
                String shareSub = "Teilen";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share using"));

            }
        });



        viewAlla();
        pruef();
    }


    public void viewAlla() {




        Wuser x = new Wuser();
        x = db.getUserData();
        StringBuffer buffer = new StringBuffer();

        UserView.setText("Name:  "+ x.getName());
        PunkteView.setText("Punkte:  "+ x.getPunkte());

    }


    public  void pruef() {                  // Click von den Buttons Werden überpruft
        btnA.setOnClickListener(
         new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            db.deleteallUserpakete();
            db.deleteallUserStudiengaenge();
            Toast.makeText(EditActivity.this,"Gelöscht!" ,Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditActivity.this, NameActivity.class));}}
        );

       btnB.setOnClickListener(
        new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(EditActivity.this,"Pakete Auswählen!" ,Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditActivity.this, StudiengangActivity.class));}}
        );

        btnC.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(EditActivity.this,"Custom Frage!" ,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditActivity.this, CustomfActivity.class));}}
        );



    }



    public void showMessage(String title,String Message){                       // Shows Toast message
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }



}