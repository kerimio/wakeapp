package asd.datenbank2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class Reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String my_string = intent.getExtras().getString("extra");
        Intent reciever_intent = new Intent(context, PlaySoundService.class);
        reciever_intent.putExtra("extra", my_string );
        context.startService(reciever_intent);

    }
}