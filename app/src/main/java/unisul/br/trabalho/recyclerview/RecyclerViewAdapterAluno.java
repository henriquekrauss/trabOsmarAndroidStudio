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

import unisul.br.trabalho.EditAluno;
import unisul.br.trabalho.R;
import unisul.br.trabalho.dao.AlunoDAO;
import unisul.br.trabalho.model.Aluno;
import unisul.br.trabalho.model.Turma;

public class RecyclerViewAdapterAluno extends RecyclerView.Adapter<RecyclerViewAdapterAluno.ViewHolder> {
    private ArrayList<Aluno> listaAluno;
    private Context context;

    public RecyclerViewAdapterAluno(Context context, ArrayList<Aluno> listaAluno) {
        this.context = context;
        this.setListaAluno(listaAluno);
    }

    public RecyclerViewAdapterAluno.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_aluno_view, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull RecyclerViewAdapterAluno.ViewHolder holder, int position) {
        int id = listaAluno.get(position).getId();
        String nome = listaAluno.get(position).getNome();
        holder.id.setText(String.valueOf(id));
        holder.nome.setText(nome);

        holder.btnDelAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAluno(position);
            }
        });

        holder.btnEditAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaEditAluno(position);
            }
        });
    }

    public int getItemCount() {
        return listaAluno.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView nome;
        private ImageButton btnDelAluno;
        private ImageButton btnEditAluno;

        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.textViewAlunoID);
            nome = itemView.findViewById(R.id.textViewNomeAluno);
            btnDelAluno = itemView.findViewById(R.id.btnDelAluno);
            btnEditAluno = itemView.findViewById(R.id.btnEditAluno);
        }
    }

    public void abrirTelaEditAluno(int position) {
        Intent intent = new Intent(this.context, EditAluno.class);
        intent.putExtra("idAluno", this.listaAluno.get(position).getId());
        this.context.startActivity(intent);
    }


    public void updateListaAluno(ArrayList<Aluno> listaAluno) {
        this.listaAluno.clear();
        this.listaAluno.addAll(listaAluno);
        notifyDataSetChanged();
    }

    public void removeAluno(int position) {
        AlunoDAO alunoDB = new AlunoDAO(this.context);
        if (alunoDB.remove(this.listaAluno.get(position).getId())) {
            Toast.makeText(this.context, "Aluno foi deletado!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.context, "Aluno não pode ser deletado!", Toast.LENGTH_LONG).show();
        }
        notifyDataSetChanged();
    }

    public void setListaAluno(ArrayList<Aluno> listaAluno) {
        this.listaAluno = listaAluno;
    }
}
