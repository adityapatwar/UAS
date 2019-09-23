package com.example.uas;

import android.os.Parcel;
import android.os.Parcelable;

public class Notes implements Parcelable {
    String NotesId;
    String Time;
    String Judul;
    String Deskripsi;

    public Notes() {

    }

    public Notes(String notesId, String time, String judul, String deskripsi) {
        NotesId = notesId;
        Time = time;
        Judul = judul;
        Deskripsi = deskripsi;
    }

    public String getNotesId() {
        return NotesId;
    }

    public String getTime() {
        return Time;
    }

    public String getJudul() {
        return Judul;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setNotesId(String notesId) {
        NotesId = notesId;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public void setDeskripsi(String deskripsi) {
        Deskripsi = deskripsi;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString (this.NotesId);
        dest.writeString (this.Time);
        dest.writeString (this.Judul);
        dest.writeString (this.Deskripsi);
    }

    protected Notes(Parcel in) {
        this.NotesId = in.readString ();
        this.Time = in.readString ();
        this.Judul = in.readString ();
        this.Deskripsi = in.readString ();
    }

    public static final Parcelable.Creator<Notes> CREATOR = new Parcelable.Creator<Notes> () {
        @Override
        public Notes createFromParcel(Parcel source) {
            return new Notes (source);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };
}
