package unisul.br.trabalho.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import unisul.br.trabalho.model.Aluno;

public class AlunoDAO {
    private SQLiteHelper dbHelper;
    private SQLiteDatabase db;

    public AlunoDAO (Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public boolean add(Aluno aluno, int idTurma) {
        boolean status = false;
        if (aluno != null) {
            try {
                db = dbHelper.getWritableDatabase();
                ContentValues dados = new ContentValues();
                dados.put("id", aluno.getId());
                dados.put("nome", aluno.getNome());
                dados.put("idade", aluno.getIdade());
                dados.put("endereco", aluno.getEndereco());
                db.insert("aluno", null, dados);
                ContentValues dadosRelacional = new ContentValues();
                dadosRelacional.put("idTurma", idTurma);
                dadosRelacional.put("idAluno", aluno.getId());
                db.insert("re_turma_aluno", null, dadosRelacional);
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

    public ArrayList<Aluno> getAlunoByTurma(int idTurma) {
        ArrayList<Aluno> listaAluno = new ArrayList<Aluno>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT a.id, a.nome, a.idade, a.endereco FROM aluno a, re_turma_aluno re WHERE re.idAluno = a.id AND re.idTurma = " + idTurma, null);

        if (cursor.moveToFirst()) {
            do {
                listaAluno.add(new Aluno(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        return listaAluno;
    }

    public Aluno getAlunoByID(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM aluno WHERE id=" + id, null);

        if (cursor.moveToFirst()) {
            return new Aluno(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
        } else {
            return null;
        }
    }

    public boolean update(Aluno aluno) {
        boolean status = false;

        try {
            db = dbHelper.getWritableDatabase();
            ContentValues dados = new ContentValues();
            dados.put("id", aluno.getId());
            dados.put("nome", aluno.getNome());
            dados.put("idade", aluno.getIdade());
            dados.put("endereco", aluno.getEndereco());
            db.update("aluno", dados, "id=" + aluno.getId(), null);
            status = true;
            db.close();
            db = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (db != null) {
                    db.close();
                }
                db = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return status;
    }

    public boolean remove(int id) {
        boolean status = false;
        try {
            db = dbHelper.getWritableDatabase();
            db.delete("aluno", "id=" + id, null);
            db.delete("re_turma_aluno", "idAluno=" + id, null);
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
