package unisul.br.trabalho.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private final String sqlCreateTableTurma = "CREATE TABLE IF NOT EXISTS turma (" +
            "id integer primary key," +
            "nome text)";
    private final String sqlCreateTableAluno = "CREATE TABLE IF NOT EXISTS aluno (" +
            "id integer primary key," +
            "nome text," +
            "idade integer," +
            "endereco text)";
    private final String sqlCreateTableRelacional = "CREATE TABLE IF NOT EXISTS re_turma_aluno (" +
            "id integer primary key autoincrement," +
            "idTurma integer," +
            "idAluno integer," +
            "FOREIGN KEY (idTurma) REFERENCES turma (id)," +
            "FOREIGN KEY (idAluno) REFERENCES aluno (id))";


    public SQLiteHelper(Context context) {
        super(context, "trabalho", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.sqlCreateTableAluno);
        db.execSQL(this.sqlCreateTableTurma);
        db.execSQL(this.sqlCreateTableRelacional);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS turma");
        this.onCreate(db);
    }
}
