package unisul.br.trabalho.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import unisul.br.trabalho.ListaAluno;
import unisul.br.trabalho.R;
import unisul.br.trabalho.dao.TurmaDAO;
import unisul.br.trabalho.model.Turma;

public class RecyclerViewAdapterTurma extends RecyclerView.Adapter<RecyclerViewAdapterTurma.ViewHolder>{
    private ArrayList<Turma> listaTurma;
    private Context context;

    public RecyclerViewAdapterTurma(Context context, ArrayList<Turma> listaTurma) {
        this.setListaTurma(listaTurma);
        this.context = context;
    }


    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_turma_view, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int id = listaTurma.get(position).getId();
        String nome = listaTurma.get(position).getNome();
        holder.id.setText(String.valueOf(id));
        holder.nome.setText(nome);

        holder.btnTelaAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avancarTelaAluno(id);
            }
        });

        holder.btnDelTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTurma(position);
            }
        });
    }

    public int getItemCount() {
        return listaTurma.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView id;
        private TextView nome;
        private ImageButton btnTelaAluno;
        private ImageButton btnDelTurma;

        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.textViewAlunoID);
            nome = itemView.findViewById(R.id.textViewNomeAluno);
            btnTelaAluno = itemView.findViewById(R.id.btnDelAluno);
            btnDelTurma = itemView.findViewById(R.id.btnDelTurma);
        }
    }

    public ArrayList<Turma> getListaTurma() {
        return listaTurma;
    }

    public void setListaTurma(ArrayList<Turma> listaTurma) {
        this.listaTurma = listaTurma;
    }

    public void avancarTelaAluno(int idTurma) {
        Intent intent = new Intent(this.context, ListaAluno.class);
        intent.putExtra("idTurma", idTurma);
        this.context.startActivity(intent);
    }

    public void updateListaTurma(ArrayList<Turma> listaTurma) {
        this.listaTurma.clear();
        this.listaTurma.addAll(listaTurma);
        notifyDataSetChanged();
    }

    public void removeTurma(int position) {
        TurmaDAO turmadb = new TurmaDAO(this.context);
        if (turmadb.remove(this.listaTurma.get(position).getId())) {
            Toast.makeText(this.context, "Turma deletada!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.context, "Turma não pode ser deletada!", Toast.LENGTH_LONG).show();
        }
        notifyDataSetChanged();
    }
}
