package news.core.service.impl;

import news.core.domain.Article;
import news.core.repository.ArticleRepository;
import news.core.repository.CommentRepository;
import news.core.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Article> getArticles() {
        return articleRepository.findAllArticles();
    }

    @Override
    public Article getArticleById(Integer articleId) {
        return articleRepository.findArticle(articleId);
    }

    @Override
    public Integer addArticle(Article article) {
        Article newArticle = new Article();
        newArticle.setTitle(article.getTitle());
        newArticle.setContent(article.getContent());

        Integer articleId = articleRepository.insertArticle(newArticle);
        return articleId;
    }

    @Override
    public void editArticle(Integer articleId, Article article) {
        Article newArticle = new Article(articleId);
        newArticle.setTitle(article.getTitle());
        newArticle.setContent(article.getContent());

        articleRepository.updateArticle(newArticle);
    }

    @Override
    public void deleteArticle(Integer articleId) {
        commentRepository.deleteAllCommentsByArticle(articleId);
        articleRepository.deleteArticle(articleId);
    }
}
