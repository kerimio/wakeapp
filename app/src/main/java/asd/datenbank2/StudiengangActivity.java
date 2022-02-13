package asd.datenbank2;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import static java.lang.System.in;

public class StudiengangActivity extends AppCompatActivity {

    //-------------------------------------------------------------------------------------------------alle benötigten Variablen--------------------------------------------------------------------------------------
    Button itd_btn;
    Button homeButton;
    Button eigeneFrageButton;
    TextView tv;
    ArrayList<String> items;
    ArrayList<String> onlineFragen;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    TextView tv2;
    DatabaseHelper db;

    final String scripturlstring = "http://finimento.de/connect_wakeapp.php";

    //-------------------------------------------------------------------------------------------------onCreate------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studiengang);

        tv = (TextView) findViewById(R.id.AntwortText);
        itd_btn = (Button) findViewById(R.id.itemtodo_button);
        homeButton = (Button)findViewById(R.id.home);
        homeButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent I = new Intent(StudiengangActivity.this, WeckerActivity.class);
                        startActivity(I);
                    }
                }
        );
        eigeneFrageButton = (Button)findViewById(R.id.eigene_frage);
        eigeneFrageButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent I = new Intent(StudiengangActivity.this, CustomfActivity.class);
                        startActivity(I);
                    }
                }
        );

        if (internetAvailable()) {
            final String event = "zeigeAllePackete"; //Das Event für das PHP Script
            sendToServer(event);  //Http-Request vollziehen
        } else {
            Toast.makeText(getApplicationContext(), "Internet ist nicht verfügbar.", Toast.LENGTH_SHORT).show();
        }
    }

    //-------------------------------------------------------------------------------------------------sendToServer-Methode----------------------------------------------------------------------------------------------------------------
    public void sendToServer(final String event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String textparam = "event=" + URLEncoder.encode(event, "UTF-8");   //unser Text für den http-Request

                    db = new DatabaseHelper(getApplicationContext());  //DatabaseHelper Objekt erzeugen
                    URL scripturl = new URL(scripturlstring);   //neues URL Object aus der Web-Server-Domain erstellen
                    HttpURLConnection connection = (HttpURLConnection) scripturl.openConnection();   //ein HttpURLConnection Objekt zum Senden und Empfangen der Daten aus dem URL-Objekt erstellen
                    connection.setDoOutput(true);   //Das HttpURLConnection Objekt auf Post und Ausgabe auf True stellen
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");   //Die Funktion setRequestProperty() wird als Accept-Encoding Request Header verwendet, um eine automatische Dekomprimierung zu deaktivieren. Ganz nach dem Motto 'client.setRequestProperty(“Key”,”Value”)'   Versteh ich nicht wirklich
                    connection.setFixedLengthStreamingMode(textparam.getBytes().length);  // Informiert den Server darüber wie groß die gesendete Datei ist. (PerformanceGründe: ansonsten würde das HttpURLConnection Objekt den kompletten Body im Speicher puffert, bevor er übermittelt wird)
                    //sendenAnfang
                    OutputStreamWriter contentWriter = new OutputStreamWriter(connection.getOutputStream());  // mit QutputStreamWriter können wir den Inhalt an die URL-Connection(connection) senden
                    contentWriter.write(textparam);   //unseren zusammengebauten Text für die PHP (textparam) in den OutputStreamWriter schreiben
                    contentWriter.flush();   //Sicherstellen das der OutputStreamWriter nun leer (spülen) ist um Ihn daraufhin schließen zu können.
                    contentWriter.close();   //OutputStreamWriter schlie0en
                    //sendenEnde
                    InputStream answerInputStream = connection.getInputStream();   //InputStream aus der Verbindung entnehmen und in Variable speichern
                    final String answer = getTextFromInputStream(answerInputStream);  //InputStream mit einer Methode zu einem String umwandeln ----answer---- ist unser AntwortString
                    if(event=="zeigeAllePackete") {
                        updateUI(answer);
                        //db.gebeStudiengangArray();
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                db.updateStudiengang(event, answer); //innere Datenbank mit neuem Studiengang updaten
                                // tv.setText(getStudiengang());      //für den Request-Test. Gibt den Heruntergeladenen String aus
                            }
                        });
                    }
                    answerInputStream.close();  //InputStream Schließen
                    connection.disconnect();   //Serververbindung schließen

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //-------------------------------------------------------------------------------------------------Input Stream zu String umwandeln----------------------------------------------------------------------------------------------------------------
    public String getTextFromInputStream(InputStream input) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));   //input Stream einlesen
        StringBuilder stringBuilder = new StringBuilder();

        String aktuelleZeile;
        try {       //tryblock zum berücksichtigen mehrerer Zeilen
            while ((aktuelleZeile = reader.readLine()) != null) {
                stringBuilder.append(aktuelleZeile);
                stringBuilder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString().trim();
    }

    //------------------------------------------------------------------------------------------------- ListView mit der Input-String füllen---------------------------------------------------------------------------------
    public void updateUI(String input){
        lvItems = (ListView) findViewById(R.id.lvItemsUI);                                                    // neues ListView Objekt von dem ListView (ID: lvItemsUI) aus der activity_main.xml erstellen
        items = new ArrayList<String>(Arrays.asList(input.split(",")));                                      // ArrayList (items) aus den Zeilen der todo.txt erstellen
        itemsAdapter = new ArrayAdapter<String>(this, R.layout.item_studiengang, R.id.itemtodo_textView, items);    // ArrayList in die einzelnen textViews (itemtodo_textView (itemtodo.xml)) verteilen und aus denen ein ArrayAdapterObjekt erstellen
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lvItems.setAdapter(itemsAdapter);                                                                   // Die ListView mit den Teilen (ArrayList-Teile) aus dem ArrayAdapter Objekt füllen
            }
        });
    }

    //------------------------------------------------------------------------------------------------- gewähltes Packet Laden----------------------------------------------------------------------------------
    public void loadFromUI(View view){
        View parentRow = (View) view.getParent();
        ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        String[] geladeneStudiengaenge = db.gebeStudiengangArray();
        if(Arrays.asList(geladeneStudiengaenge).contains(items.get(position)) ){
            Toast.makeText(getApplicationContext(), "Dieses Packet ist bereits geladen.", Toast.LENGTH_LONG).show();
        }else{
            if (internetAvailable()) {
                final String et = items.get(position);
                sendToServer(et);   //Request vollziehen. Zum server wird der angeklickte Studiengang versendet und als Antwort erhalten wir Alle Fragen zu dem Studiengang
                Toast.makeText(getApplicationContext(), "Data inserted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Internet ist nicht verfügbar.", Toast.LENGTH_SHORT).show();
            }
        }


    }
    //-------------------------------------------------------------------------------------------------Verbindung bzw. Verbindungsaufbau abfragen----------------------------------------------------------------------------------------------------
    public boolean internetAvailable() {   //Diese Funktion fragt, ob wir aktuell Internet haben bzw. das Netz grad am aufbauen ist. Und gibt dementsprechen True oder False zurück
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}