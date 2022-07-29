package com.beca.soccernews.ui.adapters;

//Adapter: Pegar as instancias, dados dos objetos e atribui-los aos elementos visuais

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beca.soccernews.R;
import com.beca.soccernews.databinding.NewsItemBinding;
import com.beca.soccernews.domain.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    //Lista de noticias
    private final List<News> news;
    private final FavoriteListener favoriteListener;

    public NewsAdapter(List<News>news, FavoriteListener favoriteListener){
        this.news = news;
        this.favoriteListener = favoriteListener;
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
        Context context = holder.itemView.getContext();

        //Esse evento vai dar a noticia
        News news = this.news.get(position);
        holder.binding.tvTitle.setText(news.title);
        holder.binding.tvDescription.setText(news.description);
        //pega noticia (news.image), estilização, e coloca dentro do binding o imageView( ivThumbail)
        Picasso.get().load(news.image).fit().into(holder.binding.ivThumbail);
        //Ativar o botão openLink e redirecionar para o link "abrir Link" no browser
        holder.binding.btOpenLink.setOnClickListener(view ->{
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(news.link));
            context.startActivity(i);
        });
        //Implementação da funcionalidade de compartilhar
        holder.binding.ivShare.setOnClickListener(view ->{
            Intent i = new Intent(Intent.ACTION_SEND);
            //do Tipo "texto"
            i.setType("text/plain");
            //compartilha o texto do link (news.link)
            i.putExtra(Intent.EXTRA_TEXT, news.link);
            //createChooser é a função que o user irá escolher por qual meio deseja compartilhar (gmail, wpp,drive...)
            context.startActivity(Intent.createChooser(i, "share"));
        });
        //Implementação da funcionalidade de favoritar (evento será instanciado pelo Fragment)
        //A ação do click é delegado à activite/Fragmento devido a comunicação com BD
        holder.binding.ivFavorite.setOnClickListener( view -> {
            news.favorite = !news.favorite;
            this.favoriteListener.onFavorite(news);
            //Notificar a atualização dessa posição
            notifyItemChanged(position);
        });
        //mudando a cor do icone de favorito
        int favoriteColor = news.favorite ? R.color.favorite_active : R.color.favorite_inactive;
        holder.binding.ivFavorite.setColorFilter(context.getResources().getColor(favoriteColor));
    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final NewsItemBinding binding;

        //Vai manter a instancia do elemento visual
        public ViewHolder(NewsItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface FavoriteListener {
        void onFavorite(News news);
    }
}
