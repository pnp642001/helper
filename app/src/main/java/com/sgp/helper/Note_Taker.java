package com.sgp.helper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import android.os.Bundle;

public class Note_Taker extends AppCompatActivity {

    private SqlManager sqlManager;
    private ListView listView;
    private SimpleCursorAdapter simpleCursorAdapter;

    final String[] from=new String[] { SqlHelper.ID,SqlHelper.TITLE,SqlHelper.DESCRIPTION};
    final int[] to=new int[]{R.id.id,R.id.title,R.id.description};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taker);

        sqlManager=new SqlManager(this);
        sqlManager.openDataBase();
        Cursor cursor=sqlManager.fetchData();
        listView=findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.text));

        simpleCursorAdapter=new SimpleCursorAdapter(this,R.layout.activity_view_notes,cursor,from,to,0);
        simpleCursorAdapter.notifyDataSetChanged();

        listView.setAdapter(simpleCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewid) {
                TextView text_id=view.findViewById(R.id.id);
                TextView text_title=view.findViewById(R.id.title);
                TextView text_description=view.findViewById(R.id.description);

                String id=text_id.getText().toString();
                String title=text_title.getText().toString();
                String description=text_description.getText().toString();

                Intent modify_intent= new Intent(getApplicationContext(),modify_notes.class);

                modify_intent.putExtra("id",id);
                modify_intent.putExtra("title",title);
                modify_intent.putExtra("description",description);

                startActivity(modify_intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.add_note){

            Intent add_note=new Intent(this,add_note.class);

            startActivity(add_note);
        }

        return super.onOptionsItemSelected(item);
    }
}