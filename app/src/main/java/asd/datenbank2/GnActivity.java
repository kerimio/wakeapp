package asd.datenbank2;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

public class GnActivity extends AppCompatActivity {

    DatabaseHelper db;
    TextView ShowText;
    Button btnUhrzeit;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gn);

        db = new DatabaseHelper(getApplicationContext());
        btnUhrzeit = (Button)findViewById(R.id.home);
        ShowText = (TextView)findViewById(R.id.gn_text);
        image = (ImageView)findViewById(R.id.imageView3);

        bildae();
        show();
        Continue();
    }

    public  void bildae() {

        Wuser x = new Wuser();
        x = db.getUserData();
        StringBuffer buffer = new StringBuffer();

        if (x.getPunkte()>=10){
            image.setImageResource(R.drawable.eule_junior_schlafend);
        }
        if (x.getPunkte()>=20){
            image.setImageResource(R.drawable.eule_senior_schlafend);
        }
    }

    public  void show() {

        Wuser x = new Wuser();
        x = db.getUserData();
        StringBuffer buffer = new StringBuffer();
        ShowText.setText("Gute Nacht "+x.getName()+"!");
    }

    public  void Continue() {

        btnUhrzeit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(GnActivity.this, WeckerActivity.class));
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
