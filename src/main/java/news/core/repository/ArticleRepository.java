package news.core.repository;

import news.core.domain.Article;

import java.util.List;

public interface ArticleRepository {

    public Article findArticle(Integer articleId);

    public List<Article> findAllArticles();

    public Integer insertArticle(Article article);

    public void updateArticle(Article article);

    public void deleteArticle(Integer articleId);
}
