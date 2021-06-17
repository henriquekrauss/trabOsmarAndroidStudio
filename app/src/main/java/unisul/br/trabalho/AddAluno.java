package unisul.br.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import unisul.br.trabalho.dao.AlunoDAO;
import unisul.br.trabalho.model.Aluno;

public class AddAluno extends AppCompatActivity {
    private EditText inputID;
    private int idTurma;
    private EditText inputNome;
    private EditText inputIdade;
    private EditText inputEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aluno);
        inputID = findViewById(R.id.inputIDAluno);
        inputNome = findViewById(R.id.inputNomeAluno);
        inputIdade = findViewById(R.id.inputIdade);
        inputEndereco = findViewById(R.id.inputEndereco);
        Intent intent = getIntent();
        idTurma = intent.getIntExtra("idTurma", -1);

    }

    public void onClickBtnAddAluno(View v) {
        AlunoDAO aluno = new AlunoDAO(this);
        int id = Integer.parseInt(inputID.getText().toString());
        String nome = inputNome.getText().toString();
        int idade = Integer.parseInt(inputIdade.getText().toString());
        String endereco = inputEndereco.getText().toString();

        if (aluno.add(new Aluno(id, nome, idade, endereco), idTurma)) {
            Toast.makeText(getApplicationContext(), "Cadastro do aluno foi feito!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Cadastro do aluno não rolou!", Toast.LENGTH_LONG).show();
        }
    }
}