package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

// ToDo
// - Toolbar?
// - Delet Button durch swipen ersetzten
// x Erledigte durchstreichen
// x Listen an Toolbar anhängen
// x Leerer Text soll nicht hinzugefügt werden können
// x speichern der erledigten Items

//Die `MainActivity.java` ist eine Java-Datei in Android Studio, die eine Aktivitätsklasse
// repräsentiert. Eine Aktivität ist ein grundlegender Bestandteil einer Android-App und
// repräsentiert normalerweise ein Fenster, das dem Benutzer angezeigt wird

public class MainActivity extends AppCompatActivity {
    //Die `MainActivity`-Klasse erweitert die Klasse `AppCompatActivity`, die eine von
    //Android bereitgestellte Basisaktivitätsklasse ist

    private ArrayList<String> items;
    private ArrayList<String> itemsDone;
    private ArrayAdapter<String> itemsAdapter;
    private ArrayAdapter<String> itemsDoneAdapter;
    private ListView lvItems;
    private ListView lvItemsDone;

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
        lvItemsDone = (ListView) findViewById(R.id.lvItemsDone);
        items = new ArrayList<>();
        itemsDone = new ArrayList<>();

        readItems();
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, items);
        lvItems.setAdapter(itemsAdapter);

        itemsDoneAdapter = new ArrayAdapter<>(this,
                R.layout.layout_list_done, itemsDone);
        lvItemsDone.setAdapter(itemsDoneAdapter);

        setupListViewListener();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        File todoDoneFile = new File(filesDir, "todoDone.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<>();
        }try {
            itemsDone = new ArrayList<String>(FileUtils.readLines(todoDoneFile));
        } catch (IOException e) {
            itemsDone = new ArrayList<>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        File todoDoneFile = new File(filesDir, "todoDone.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }try {
            FileUtils.writeLines(todoDoneFile, itemsDone);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                (adapter, item, pos, id) -> {
                    // Remove the item within array at position
                    itemsDoneAdapter.add(items.get(pos));
                    items.remove(pos);
                    //itemsDone.add(items.get(pos));

                    // Refresh the adapter
                    itemsAdapter.notifyDataSetChanged();
                    itemsDoneAdapter.notifyDataSetChanged();
                    // Return true consumes the long click event (marks it handled)
                    writeItems();
                    return true;
                });
        lvItems.setOnItemClickListener(
                (parent, view, pos, id) -> {
                    // Remove the item within array at position
                    itemsDoneAdapter.add(items.get(pos));
                    items.remove(pos);
                    //itemsDone.add(items.get(pos));

                    // Refresh the adapter
                    itemsAdapter.notifyDataSetChanged();
                    itemsDoneAdapter.notifyDataSetChanged();
                    // Return true consumes the long click event (marks it handled)
                    writeItems();
                });
        lvItemsDone.setOnItemLongClickListener(
                (adapter, item, pos, id) -> {
                    // Remove the item within array at position
                    itemsAdapter.add(itemsDone.get(pos));
                    itemsDone.remove(pos);
                    //itemsDone.add(items.get(pos));

                    // Refresh the adapter
                    itemsAdapter.notifyDataSetChanged();
                    itemsDoneAdapter.notifyDataSetChanged();
                    // Return true consumes the long click event (marks it handled)
                    writeItems();
                    return true;
                });
        lvItemsDone.setOnItemClickListener(
                (parent, view, pos, id) -> {
                    // Remove the item within array at position
                    itemsAdapter.add(itemsDone.get(pos));
                    itemsDone.remove(pos);
                    //itemsDone.add(items.get(pos));

                    // Refresh the adapter
                    itemsAdapter.notifyDataSetChanged();
                    itemsDoneAdapter.notifyDataSetChanged();
                    // Return true consumes the long click event (marks it handled)
                    writeItems();
                });
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        if (!itemText.equals("")) {
            itemsAdapter.add(itemText);
        }
        etNewItem.setText("");
        writeItems();
    }

    public void onDeleteItem(View v) {
        // Delete Item
    }
}