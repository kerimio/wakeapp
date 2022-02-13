package asd.datenbank2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class WeckerActivity extends AppCompatActivity {

    //Deklaration der Variabeln

    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;
    ImageButton btnA;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wecker);

        // Initialiationen der Variabeln,
        this.context = this;
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);         // alarm_manager = Zuständig für den Alarm Service, löst den Alarm aus

        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);         // alarm_timepicker = Dient um den User eine Weckzeit auswählen zu lassen

        update_text = (TextView) findViewById(R.id.update_text);                // Zeigt die Gesetzte Alarmzeit oder ob Wecker deaktiviert ist
        btnA = (ImageButton) findViewById(R.id.button_options);
        final Calendar calendar = Calendar.getInstance();                       // Greift auf die Uhrzeit zu
        final Intent intent = new Intent(this.context, Reciever.class);

        alarm_timepicker.setIs24HourView(true);                         // stellt von AM/PM auf 24 Stundensystem
        alarm_timepicker.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));  // Greift mit dem Timepicker durch den Kalendar auf die Uhrzeit zu


        Button start_alarm = (Button) findViewById(R.id.start_alarm);
        pruef();


        // Startbutton, wird benutzt wenn die Weckzeit ausgewählt wurde um den Wecker zu stellen
        start_alarm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override


            public void onClick(View v) {

                calendar.add(java.util.Calendar.SECOND, 3);
                final int hour = alarm_timepicker.getCurrentHour();
                final int minute = alarm_timepicker.getCurrentMinute();

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);

                // Falls eine Uhrzeit ausgewählt wird die zurückliegt, wird der Wecker am nächsten Tag zu dieser Uhrzeit ausgelöst
                long timeInMillis = calendar.getTimeInMillis();
                if (timeInMillis - System.currentTimeMillis() < 0) {
                    timeInMillis += 86400000;
                }

                // Löst den Wecker aus
                intent.putExtra("extra", "on");
                pending_intent = PendingIntent.getBroadcast(WeckerActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                alarm_manager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pending_intent);
                set_alarm_text(getString(R.string.alarm_wurde_auf) + " " + hour + ":" + (String.format("%02d",minute)));

                //zur Gute Nacht Activity verweisen
                startActivity(new Intent(WeckerActivity.this, GnActivity.class));
                Toast.makeText(WeckerActivity.this,"Wecker ist gestellt!",Toast.LENGTH_LONG).show();



            }


        });
        // Deaktivieren Button, dient lediglich dazu um die Activitie zu wechseln
        Button stop_alarm = (Button) findViewById(R.id.stop_alarm);
        stop_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(WeckerActivity.this, FrageActivity.class));


            }
        });
    }


    private void set_alarm_text(String output) {
        update_text.setText(output);
    }

    public void pruef() {

        btnA.setOnClickListener(


                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(WeckerActivity.this, EditActivity.class));
                    }
                }
        );
    }
}




