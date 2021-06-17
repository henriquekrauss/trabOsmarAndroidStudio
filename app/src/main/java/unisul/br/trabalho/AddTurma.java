package unisul.br.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import unisul.br.trabalho.dao.TurmaDAO;
import unisul.br.trabalho.model.Turma;

public class AddTurma extends AppCompatActivity {
    private EditText inputID;
    private EditText inputNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_turma);

        inputID = findViewById(R.id.inputID);
        inputNome = findViewById(R.id.inputNome);
    }

    public void onClickBtnAddTurma(View v) {
        TurmaDAO turmaDB = new TurmaDAO(getApplicationContext());
        int id = Integer.parseInt(inputID.getText().toString());
        String nome = inputNome.getText().toString();
        if (turmaDB.add(new Turma(id, nome))) {
            Toast.makeText(getApplicationContext(), "Cadastro de turma foi feito!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Cadastro de turma não pode ser realizado!", Toast.LENGTH_LONG).show();
        }
    }
}