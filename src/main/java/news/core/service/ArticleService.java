package news.core.service;

import news.core.domain.Article;

import java.util.List;

public interface ArticleService {

    public List<Article> getArticles();

    public Article getArticleById(Integer articleId);

    public Integer addArticle(Article article);

    public void editArticle(Integer articleId, Article article);

    public void deleteArticle(Integer articleId);
}
