package asd.datenbank2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.Toast;


// In dieser Klasse wird der Notificationmanager erstellt und festgelegt, unter welchen Signalen bzw. Bedingungen der Wecker ausgelöst wird.

public class PlaySoundService extends Service {
    MediaPlayer media_file;
    boolean musik_loyft;
    private int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Notification, wenn der Wecker ausgelöst wird, wird dem User eine Pop-up Benachrichtigung in seinem Controll Center angezeigt
        String state = intent.getExtras().getString("extra");

        NotificationManager notify_manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent_main_activity = new Intent(this.getApplicationContext(), FrageActivity.class);
        PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0,
                intent_main_activity, 0);


        Notification notification_popup = new Notification.Builder(this)
                .setContentTitle(getString(R.string.guten_morgen))
                .setContentText(getString(R.string.klickehier))
                .setSmallIcon(R.drawable.hintergrund_wakeapp)
                .setContentIntent(pending_intent_main_activity)
                .setAutoCancel(true)
                .build();

        notify_manager.notify(0, notification_popup);




        assert state != null;
        switch (state) {
            case "on":          // Schlüssel "on" kriegt den Wert 1  -> Wecker wird aktiviert
                startId = 1;

                break;
            case "off":         // Schlüssel "off" kriegt den Wert 0 -> Wecker wird deaktiviert
                startId = 0;
                break;
            default:
                startId = 0;   // Defeault = 0 -> im nicht gesetzten Zustand ist der Wecker stets deaktiviert
                break;
        }





        // Hier wird festgelegt, wann Musik laufen (loyft) soll und wann es stoppen soll, falls die startId == 1 ist, wird die Musik gestartet. Anderfalls nicht, bzw. gestoppt.

        if (!this.musik_loyft && startId == 1) {
            media_file = MediaPlayer.create(this, R.raw.mediasound);
            media_file.start();

            musik_loyft = true;
            this.startId = 0;
        } else if (!this.musik_loyft && startId == 0) {
            this.musik_loyft = false;
            this.startId = 0;



        } else if (this.musik_loyft && startId == 1) {
            this.musik_loyft = true;
            this.startId = 0;

        } else {
            media_file.stop();
            media_file.reset();
            this.musik_loyft = false;
            this.startId = 0;
        }


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        Toast.makeText(this, "on destroy called", Toast.LENGTH_SHORT).show();
    }


}