package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

//Die `MainActivity.java` ist eine Java-Datei in Android Studio, die eine Aktivitätsklasse
// repräsentiert. Eine Aktivität ist ein grundlegender Bestandteil einer Android-App und
// repräsentiert normalerweise ein Fenster, das dem Benutzer angezeigt wird

public class MainActivity extends AppCompatActivity {
    //Die `MainActivity`-Klasse erweitert die Klasse `AppCompatActivity`, die eine von
    //Android bereitgestellte Basisaktivitätsklasse ist

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    @Override
    // "@Override" ein Hinweis für den Compiler, dass die folgende Methode
    // eine Methode aus der Basisklasse überschreibt oder implementiert
    protected void onCreate(Bundle savedInstanceState) {
    //`onCreate`-Methode wird automatisch aufgerufen, wenn die Aktivität erstellt wird.
    //Diese Methode ist dafür verantwortlich, das Layout der Aktivität zu setzen
    //(in diesem Fall wird das Layout `activity_main` verwendet) und andere Initialisierungen
    //durchzuführen

    //Methode "onCreate(Bundle savedInstanceState)" überschreibt die Methode "onCreate
    //(Bundle savedInstanceState)" aus der Basisklasse "AppCompatActivity"
        super.onCreate(savedInstanceState);
        //super = ruft die Methode "onCreate" der Basisklasse "AppCompatActivity" auf
        setContentView(R.layout.activity_main);
        //setzt das Layout für die MainActivity. Hier wird das Layout mit dem Namen
        // "activity_main"

        //verwendet, das in der Verzeichnisstruktur des Projekts unter "res/layout/"
        // zu finden ist

        //list = findViewById(R.id.list);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);

        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapter,
                                               View item, int pos, long id) {
                    // Remove the item within array at position
                    items.remove(pos);

                    // Refresh the adapter
                    itemsAdapter.notifyDataSetChanged();
                    // Return true consumes the long click event (marks it handled)
                    writeItems();
                    return true;
                }

            });
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    public void onDeleteItem(View v) {

    }
}