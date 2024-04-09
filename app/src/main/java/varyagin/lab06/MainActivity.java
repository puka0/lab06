package varyagin.lab06;

import static varyagin.lab06.AdapterHolder.adp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // ArrayAdapter <Note> adp;

    int sel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adp = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1);
        ListView lst = findViewById(R.id.list_notes);
        lst.setAdapter(adp);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // System.out.println("");
                sel = position;
                TextView deb = findViewById(R.id.debugSelect);
                deb.setText("Выбрано: " + String.valueOf(sel));
            }
        });

        lst.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //----------------
                sel = position;
                TextView deb = findViewById(R.id.debugSelect);
                deb.setText("Выбрано: " + String.valueOf(sel));
                //----------------
                longClick();
                return true;
            }
        });
    }


    // ----------------------
    public void longClick() {

        Note curNote = adp.getItem(sel);

//        Toast.makeText(this, curNote.content, Toast.LENGTH_LONG).show();

        Intent i = new Intent(this, ThirdActivity.class);
        i.putExtra("my note index", sel);
        i.putExtra("my note title", curNote.title);
        i.putExtra("my note content", curNote.content);
        startActivityForResult(i, 333);
    }
    // ----------------------

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if (data != null){
            int pos = data.getIntExtra("my note index", 1);
            String title = data.getStringExtra("my note title");
            String content = data.getStringExtra("my note content");
            Note note = adp.getItem(pos);
            note.title = title;
            note.content = content;
            adp.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    public void on_new_click(View v){
        Note n = new Note();
        n.title = "New note";
        n.content = "Some content";
        adp.add(n);
        int pos = adp.getPosition(n);
        Intent i = new Intent(this, SecondActivity.class);
        i.putExtra("my note index", pos);
        i.putExtra("my note title", n.title);
        i.putExtra("my note content", n.content);
        startActivityForResult(i, 12345);
    }
    public void on_edit_click(View v){

        if (checkIsZeroNotes("редактирования")) {
            return;
        }

        if (sel != AdapterView.INVALID_POSITION) {
            Note selectedNote = adp.getItem(sel);
            Intent i = new Intent(this, SecondActivity.class);
            i.putExtra("my note index", sel);
            i.putExtra("my note title", selectedNote.title);
            i.putExtra("my note content", selectedNote.content);

            i.putExtra("is for edit", true);

            startActivityForResult(i, 12345);
        }
    }
    public void on_delete_click(View v){

        if (checkIsZeroNotes("удаления")) {
            return;
        }

        if (sel != AdapterView.INVALID_POSITION) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Вы уверены в удалении?")
                    .setMessage("Заметку будет невозможно восстановить")

                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            adp.remove(adp.getItem(sel));
                            adp.notifyDataSetChanged();
                        }
                    })

                    .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "Nothing Happened", Toast.LENGTH_LONG).show();
                        }
                    })
                    .show();

        }
    }


    private boolean checkIsZeroNotes(String action) {
        if (adp.getCount() == 0) {
            Toast.makeText(this, "Нет заметок для " + action, Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
}