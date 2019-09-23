package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ActionActivity extends AppCompatActivity {

    public static String EXTRA_NOTE = " extra_note";
    String NOTE_ID = "noteId";
    EditText etJudul,etDes;
    Button btnAction;
    DatabaseReference dbNotes;
    List<Notes> NotesLists;
    Button back,delete;
    TextView ActionT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_action);

        back = findViewById (R.id.btnback);
        delete = findViewById (R.id.btndelete);
        ActionT = findViewById (R.id.tvAction);


        final String Action;
        String Action1;
        btnAction = findViewById (R.id.btnAction);
        Action1 = btnAction.getText ().toString ();

        etJudul = findViewById (R.id.etJudul);
        etDes = findViewById (R.id.etDeskripsi);

        NotesLists = new ArrayList<> ();



        if (getIntent().hasExtra("Tambah")){
            Action1 = "TAMBAH";
            delete.setVisibility (View.GONE);
        }else if(getIntent().hasExtra("Update")){
            Notes note = getIntent ().getParcelableExtra (EXTRA_NOTE);
            etJudul.setText (note.getJudul ()+"");
            etDes.setText (note.getDeskripsi ()+"");
            NOTE_ID = note.getNotesId ();
            Action1 = "UPDATE";
            delete.setVisibility (View.VISIBLE);

        }
        ActionT.setText (Action1);


        Action = Action1;
        btnAction.setText (Action);

        back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(ActionActivity.this,MainActivity.class);
                startActivity (back);
                finish ();
            }
        });

        delete.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                deleteArtist(NOTE_ID);

            }
        });

        btnAction.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if(Action.equalsIgnoreCase ("tambah")){
                    addNote ();
                }else if(Action.equalsIgnoreCase ("Update")){
                    updateArtist(NOTE_ID);
                }

            }
        });


    }

    public void addNote(){
        dbNotes = FirebaseDatabase.getInstance ().getReference ("notes");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date ());

        String Time = currentDateandTime;
        String Title = etJudul.getText ().toString ().trim ();
        String Des = etDes.getText ().toString ().trim ();

        if(TextUtils.isEmpty (Time)){
            Toast.makeText (this,"Please Enter Your Title !",Toast.LENGTH_LONG).show ();
        }else {
            String id = dbNotes.push().getKey ();
            Notes note = new Notes (id,Time,Title,Des);
            dbNotes.child(id).setValue(note);
            Toast.makeText (this,"Added",Toast.LENGTH_LONG).show ();
            Intent intent2 = new Intent (ActionActivity.this,MainActivity.class);
            startActivity (intent2);
            finish ();
        }

    }

    private void deleteArtist(String id){
        DatabaseReference dbartist = FirebaseDatabase.getInstance ().getReference ("notes").child (id);

        dbartist.removeValue ();

        Toast.makeText (this,"Delete successfully",Toast.LENGTH_LONG).show ();
        Intent intent2 = new Intent (ActionActivity.this,MainActivity.class);
        startActivity (intent2);
        finish ();
    }

    private void updateArtist (String id){

        DatabaseReference db = FirebaseDatabase.getInstance ().getReference ("notes").child (id);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date ());

        String Time = currentDateandTime;
        String Title = etJudul.getText ().toString ().trim ();
        String Des = etDes.getText ().toString ().trim ();

        Notes artist = new Notes (id,Time,Title,Des);

        db.setValue (artist);
        Toast.makeText (this,"Update successfully",Toast.LENGTH_LONG).show ();
        Intent intent2 = new Intent (ActionActivity.this,MainActivity.class);
        startActivity (intent2);
        finish ();
    }




}
