package com.example.dominic.memo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends ActionBarActivity {

    SQLiteDatabase db;
    DbHelper mDbHelper;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.commentslist);
        mDbHelper = new DbHelper(this);
        db= mDbHelper.getWritableDatabase();

        final ImageView alarmImage = (ImageView) findViewById(R.id.alarmImage);

        String[] from = {mDbHelper.TITLE, mDbHelper.DETAIL, mDbHelper.TYPE, mDbHelper.TIME, mDbHelper.DATE};
        final String[] column = {mDbHelper.C_ID, mDbHelper.TITLE, mDbHelper.DETAIL, mDbHelper.TYPE, mDbHelper.TIME, mDbHelper.DATE};
        int[] to = {R.id.title, R.id.Detail, R.id.type, R.id.time, R.id.date};

        final Cursor cursor = db.query(mDbHelper.TABLE_NAME, column, null, null ,null, null, null);
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_entry, cursor, from, to, 0);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView, View view, int position,
            long id){
                Intent intent = new Intent(MainActivity.this, View_Note.class);
                intent.putExtra(getString(R.string.rodId), id);
                startActivity(intent);
            }

        });

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_new:
                Intent openCreateNote = new Intent(MainActivity.this, CreateNote.class);
                startActivity(openCreateNote);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
