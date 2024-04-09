package varyagin.lab06;

import static varyagin.lab06.AdapterHolder.adp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    EditText txt_title;
    EditText txt_content;
    int pos;

    boolean isForEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        txt_title = findViewById(R.id.TxtTitle);
        txt_content = findViewById(R.id.TxtContent);

        Intent i = getIntent();
        pos = i.getIntExtra("my note index", -1);
        txt_title.setText(i.getStringExtra("my note title"));
        txt_content.setText(i.getStringExtra("my note content"));

        isForEdit = i.getBooleanExtra("is for edit", false);
    }

    public void on_cancel_click(View v){

        if (!isForEdit) {
            adp.remove(adp.getItem(adp.getCount() - 1));
            adp.notifyDataSetChanged();
        }
        finish();

    }

    public void on_ok_click(View v) {
        String titleStr = txt_title.getText().toString();
        if (titleStr.equals("")) {
            Toast.makeText(this, "Заголовок не может быть пустым", Toast.LENGTH_LONG).show();
        }
        else {
            Intent i = new Intent();
            i.putExtra("my note index", pos);
            i.putExtra("my note title", txt_title.getText().toString());
            i.putExtra("my note content", txt_content.getText().toString());
            setResult(RESULT_OK, i);
            finish();
        }
    }
}