package asd.datenbank2;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

public class NameActivity extends AppCompatActivity  {

    DatabaseHelper db;
    EditText editName;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        db = new DatabaseHelper(getApplicationContext());
        editName = (EditText)findViewById(R.id.name);
        btnContinue = (Button)findViewById(R.id.button_continue);
        AddData();
    }

    public  void AddData() {
        btnContinue.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteUserData();
                        Wuser wuser = new Wuser(1, editName.getText().toString(),0,1);

                        long isInserted = db.insertUserData(wuser);

                        if(isInserted == 1){
                            Toast.makeText(NameActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(NameActivity.this, StudiengangActivity.class));
                        }
                        else{
                            Toast.makeText(NameActivity.this,"ERROR!",Toast.LENGTH_LONG).show();
                        }
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
