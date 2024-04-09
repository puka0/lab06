package varyagin.lab06;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {
    TextView txt_title;
    TextView txt_content;
    int pos;

    SpannableString title_span;
    SpannableString content_span;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        txt_title = findViewById(R.id.title_third);
        txt_content = findViewById(R.id.content_third);

        Intent i = getIntent();

        title_span = new SpannableString(i.getStringExtra("my note title"));
        content_span = new SpannableString(i.getStringExtra("my note content"));

        title_span.setSpan(new UnderlineSpan(), 0, title_span.length(), 0);
        content_span.setSpan(new UnderlineSpan(), 0, content_span.length(), 0);

        pos = i.getIntExtra("my note index", -1);
        txt_title.setText(title_span);
        txt_content.setText(content_span);
    }

    public void on_close_click(View v) {
        finish();
    }
}