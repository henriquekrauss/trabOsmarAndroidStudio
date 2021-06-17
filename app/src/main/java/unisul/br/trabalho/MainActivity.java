package unisul.br.trabalho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import unisul.br.trabalho.dao.TurmaDAO;
import unisul.br.trabalho.recyclerview.RecyclerViewAdapterTurma;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapterTurma adapterTurma;
    private TurmaDAO turmaDB;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshTurma);
        recyclerView = findViewById(R.id.recyclerViewTurma);
        turmaDB = new TurmaDAO(getApplicationContext());
        adapterTurma = new RecyclerViewAdapterTurma(this, turmaDB.getAll());

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterTurma);

        setSwipeRefresh();
    }

    public void onClickBtnTelaAddTurma(View v) {
        Intent intent = new Intent(this, AddTurma.class);
        startActivity(intent);
    }

    private void setSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapterTurma.updateListaTurma(turmaDB.getAll());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}