package asd.datenbank2;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomfActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText editFrage, editRA, editFA1, editFA2, editFA3, editHW;
    Button continueb, loeschen, packete_verwalten, homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customf);

        db = new DatabaseHelper(getApplicationContext());
        editFrage = (EditText)findViewById(R.id.txt_frage);
        editRA = (EditText)findViewById(R.id.txt_RA);
        editFA1 = (EditText)findViewById(R.id.txt_FA1);
        editFA2 = (EditText)findViewById(R.id.txt_FA2);
        editFA3 = (EditText)findViewById(R.id.txt_FA3);
        editHW = (EditText)findViewById(R.id.txt_HW);

        continueb = (Button)findViewById(R.id.create_bt2);
        loeschen = (Button)findViewById(R.id.loes_bt);

        homeButton = (Button)findViewById(R.id.home);
        homeButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent I = new Intent(CustomfActivity.this, WeckerActivity.class);
                        startActivity(I);
                    }
                }
        );


        packete_verwalten = (Button)findViewById(R.id.packete_laden);
        packete_verwalten.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent I = new Intent(CustomfActivity.this, StudiengangActivity.class);
                        startActivity(I);
                    }
                }
        );
        AddData();
        LoeschData();
    }

    public  void AddData() {   // Custom Frage Information wird gespeichert
        continueb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Frage frage = new Frage(editFrage.getText().toString(),
                                editRA.getText().toString(),
                                editFA1.getText().toString(),
                                editFA2.getText().toString(),
                                editFA3.getText().toString(),
                                editHW.getText().toString(),
                                1);

                        long isInsertedo = db.insertFrageData(frage);{
                            Toast.makeText(CustomfActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(CustomfActivity.this, WeckerActivity.class));
                        }
                    }
                }
        );
    }

    public  void LoeschData() {  //  Custom Frage Information wird gelöscht
        loeschen.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteUserCustoms();
                        Toast.makeText(CustomfActivity.this,"Data Gelöscht!",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
