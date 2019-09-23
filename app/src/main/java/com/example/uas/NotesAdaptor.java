package com.example.uas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class NotesAdaptor extends RecyclerView.Adapter<NotesAdaptor.UserViewHolder> {
    Context context;
    OnUserClickListener listener;
    List<Notes> listnote;

    public NotesAdaptor(Context context, List<Notes>listNote,OnUserClickListener listener) {
        this.context=context;
        this.listnote=listNote;
        this.listener=listener;
    }

    public interface OnUserClickListener{
        void onUserClick(Notes currentNote);

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notesrv,parent,false);
        UserViewHolder userViewHolder=new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final Notes currentNote = listnote.get (position);
        holder.title.setText (currentNote.getJudul ());
        holder.time.setText (currentNote.getTime ());
        holder.des.setText (currentNote.getDeskripsi ());

        holder.crdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose Option");

                String[] options = {"Edit", "Delete"};
                builder.setItems (options, new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Notes mPerson = new Notes ();
                                mPerson.setNotesId (currentNote.getNotesId ());
                                mPerson.setTime (currentNote.getTime ());
                                mPerson.setJudul (currentNote.getJudul ());
                                mPerson.setDeskripsi (currentNote.getDeskripsi ());

                                Intent update = new Intent(context, ActionActivity.class);
                                update.putExtra(ActionActivity.EXTRA_NOTE,mPerson);
                                update.putExtra("Update","Update");
                                context.startActivity (update);
                                break;
                            case 1:
                                String id = currentNote.getNotesId ();
                                DatabaseReference dbartist = FirebaseDatabase.getInstance ().getReference ("notes").child (id);

                                dbartist.removeValue ();

                                Toast.makeText (context,"Delete successfully",Toast.LENGTH_LONG).show ();

                                break;
                        }

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
       return listnote.size ();
    }



    public class UserViewHolder extends RecyclerView.ViewHolder {
        CardView crdv;
        TextView time,title,des;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            crdv = itemView.findViewById (R.id.cardview);
            title = itemView.findViewById (R.id.tvTitle);
            des = itemView.findViewById (R.id.tvDeskripsi);
            time = itemView.findViewById (R.id.tvTime);
        }
    }
}
