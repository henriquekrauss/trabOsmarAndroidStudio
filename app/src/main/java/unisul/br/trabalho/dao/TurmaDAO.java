package unisul.br.trabalho.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import unisul.br.trabalho.model.Turma;

public class TurmaDAO {
    private SQLiteHelper dbHelper;
    private SQLiteDatabase db;

    public TurmaDAO (Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public boolean add(Turma turma) {
        boolean status = false;
        if (turma != null) {
            try {
                db = dbHelper.getWritableDatabase();
                ContentValues dados = new ContentValues();
                dados.put("id", turma.getId());
                dados.put("nome", turma.getNome());
                db.insert("turma", null, dados);
                status = true;
                db.close();
                db = null;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (db != null) {
                    try {
                        db.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    db = null;
                }
            }
        }
        return status;
    }

    public ArrayList<Turma> getAll() {
        ArrayList<Turma> listaTurma = new ArrayList<>();
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM turma", null);
            if (cursor.moveToFirst()) {
                do {
                    listaTurma.add(new Turma(cursor.getInt(0), cursor.getString(1)));
                } while (cursor.moveToNext());
            }
            db.close();
            db = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                try {
                    db.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                db = null;
            }
        }
        return listaTurma;
    }

    public boolean remove(int id) {
        boolean status = false;
        try {
            db = dbHelper.getWritableDatabase();
            db.delete("turma", "id=" + id, null);
            db.delete("re_turma_aluno", "idTurma=" + id, null);
            status = true;
            db.close();
            db = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                try {
                    db.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                db = null;
            }
        }
        return status;
    }
}
