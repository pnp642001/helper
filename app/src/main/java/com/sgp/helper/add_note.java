package com.sgp.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class add_note extends Activity implements View.OnClickListener {

    private Button add;
    private EditText title,description;
    private SqlManager sqlManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Note!");

        setContentView(R.layout.activity_add_note);

        add=findViewById(R.id.addButton);
        title=findViewById(R.id.title_editText);
        description=findViewById(R.id.description_editText);

        sqlManager=new SqlManager(this);

        sqlManager.openDataBase();

        add.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            default:
                final String titleText=title.getText().toString();
                final String descriptionText=description.getText().toString();

                sqlManager.insert(titleText,descriptionText);

                Intent main =new Intent(add_note.this,Note_Taker.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);

                break;

        }
    }
}