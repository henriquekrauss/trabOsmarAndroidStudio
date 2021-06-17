package unisul.br.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import unisul.br.trabalho.dao.AlunoDAO;
import unisul.br.trabalho.model.Aluno;

public class EditAluno extends AppCompatActivity {
    private EditText inputNome;
    private EditText inputIdade;
    private EditText inputEndereco;
    private int idAluno;
    private AlunoDAO alunoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_aluno);

        alunoDB = new AlunoDAO(this);
        inputNome = findViewById(R.id.inputNomeAlunoEdit);
        inputIdade = findViewById(R.id.inputIdadeEdit);
        inputEndereco = findViewById(R.id.inputEnderecoEdit);
        idAluno = getIntent().getIntExtra("idAluno", -1);

        setInputValues();
    }

    public void onClickBtnSalvarAluno(View v) {
        String nome = inputNome.getText().toString();
        int idade = Integer.parseInt(inputIdade.getText().toString());
        String endereco = inputEndereco.getText().toString();

        if (alunoDB.update(new Aluno(this.idAluno, nome, idade, endereco))) {
            Toast.makeText(this, "O aluno foi atualizado!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "O aluno não pode ser atualizado!", Toast.LENGTH_LONG).show();
        }
    }

    private void setInputValues() {
        Aluno aluno = alunoDB.getAlunoByID(this.idAluno);
        if (aluno != null) {
            inputNome.setText(aluno.getNome());
            inputIdade.setText(String.valueOf(aluno.getIdade()));
            inputEndereco.setText(aluno.getEndereco());
        }
    }
}