package com.sgp.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class modify_notes extends Activity implements View.OnClickListener {

    private EditText title,description;
    private Button update_button,delete_button;

    private long _id;
    private SqlManager sqlManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("MODIFY NOTES");
        setContentView(R.layout.activity_modify_notes);

        sqlManager=new SqlManager(this);
        sqlManager.openDataBase();

        title=findViewById(R.id.title_editText);
        description=findViewById(R.id.description_editText);
        update_button=findViewById(R.id.updateButton);
        delete_button=findViewById(R.id.deleteButton);

        Intent intent=getIntent();
        String id=intent.getStringExtra("id");
        String titleString=intent.getStringExtra("title");
        String descriptionString=intent.getStringExtra("description");

        _id=Long.parseLong(id);

        title.setText(titleString);
        description.setText(descriptionString);

        update_button.setOnClickListener(this);
        delete_button.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.updateButton:
                String titleSet= title.getText().toString();
                String descriptionSet=description.getText().toString();

                sqlManager.update(_id,titleSet,descriptionSet);

                this.returnHome();
                break;

            case R.id.deleteButton:
                sqlManager.delete(_id);
                this.returnHome();
                break;
        }

    }

    private void returnHome() {
        Intent home=new Intent(getApplicationContext(),Note_Taker.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home);


    }
}