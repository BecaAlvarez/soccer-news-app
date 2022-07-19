package com.beca.soccernews.ui.adapters;

//Adapter: Pegar as instancias, dados, dos objetos e atribui-los aos elementos visuais

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beca.soccernews.databinding.NewsItemBinding;
import com.beca.soccernews.domain.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final List<News> news;

    public NewsAdapter(List<News>news){
        this.news = news;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }
    //Fazer o que quiser com o dado
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Esse evento vai dar a noticia
        News news = this.news.get(position);
        holder.binding.tvTitle.setText(news.getTitle());
        holder.binding.tvDescription.setText(news.getDescription());
        //pega noticia (news.getImage()), estilização, e coloca dentro do binding o imageView( ivThumbail)
        Picasso.get().load(news.getImage()).fit().into(holder.binding.ivThumbail);
        //Ativar o botão openLink e redirecionar para o link "abrir Link" no browser
        holder.binding.btOpenLink.setOnClickListener(view ->{
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(news.getLink()));
            holder.itemView.getContext().startActivity(i);
        });
        //Implementação da funcionalidade de compartilhar
        holder.binding.ivShare.setOnClickListener(view ->{
            Intent i = new Intent(Intent.ACTION_SEND);
            //do Tipo "texto"
            i.setType("text/plain");
            //compartilha o texto do link (news.getLink())
            i.putExtra(Intent.EXTRA_TEXT, news.getLink());
            //createChooser é a função que o user irá escolher por qual meio deseja compartilhar (gmail, wpp,drive...)
            holder.itemView.getContext().startActivity(Intent.createChooser(i, "share"));
        });
    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final NewsItemBinding binding;

        public ViewHolder(NewsItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
