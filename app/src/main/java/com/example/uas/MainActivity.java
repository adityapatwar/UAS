package com.example.uas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,NotesAdaptor.OnUserClickListener{

    public static final String NOTE_JUDUL = "notejdul";
    public static final String NOTE_ID = "noteId";

    RecyclerView rvnotes;
    DatabaseReference dbNotes;
    List<Notes> NotesLists;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        Toolbar toolbar = findViewById (R.id.toolbar);

        setSupportActionBar (toolbar);


        NotesLists = new ArrayList<> ();

        dbNotes = FirebaseDatabase.getInstance ().getReference ("notes");

        rvnotes = findViewById (R.id.rvNotes);
        layoutManager = new LinearLayoutManager (MainActivity.this);
        rvnotes.setLayoutManager (layoutManager);


        FloatingActionButton fab = findViewById (R.id.fab);
        fab.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (MainActivity.this,ActionActivity.class);
                intent.putExtra("Tambah","Tambah");
                startActivity (intent);
                finish ();

            }
        });
    }

    private void setupRecyclerView() {

        NotesAdaptor adapter = new NotesAdaptor (this,NotesLists,this);
        rvnotes.setAdapter (adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart ();

        dbNotes.addValueEventListener (new ValueEventListener () {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                NotesLists.clear ();

                for (DataSnapshot dsNote:dataSnapshot.getChildren ()){
                    Notes note = dsNote.getValue (Notes.class);

                    NotesLists.add (note);
                }
                setupRecyclerView();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate (R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId ();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected (item);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onUserClick(final Notes currentNote) {



    }
}
