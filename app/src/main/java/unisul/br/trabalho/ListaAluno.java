package unisul.br.trabalho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import unisul.br.trabalho.dao.AlunoDAO;
import unisul.br.trabalho.recyclerview.RecyclerViewAdapterAluno;

public class ListaAluno extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapterAluno adapter;
    private int idTurma;
    private AlunoDAO alunoDB;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_aluno);

        Intent exIntent = getIntent();
        this.idTurma = exIntent.getIntExtra("idTurma", -1);
        recyclerView = findViewById(R.id.recyclerViewAluno);
        swipeRefresh = findViewById(R.id.swipeRefreshAluno);
        alunoDB = new AlunoDAO(this);
        adapter = new RecyclerViewAdapterAluno(this, alunoDB.getAlunoByTurma(idTurma));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        setSwipeRefresh();
    }

    public void onClickBtnTelaAddAluno (View v) {
        Intent intent = new Intent(this, AddAluno.class);
        intent.putExtra("idTurma", idTurma);
        startActivity(intent);
    }

    public int getIdTurma() {
        return idTurma;
    }

    private void setSwipeRefresh() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.updateListaAluno(alunoDB.getAlunoByTurma(getIdTurma()));
                swipeRefresh.setRefreshing(false);
            }
        });
    }
}