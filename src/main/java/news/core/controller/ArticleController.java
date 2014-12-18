package news.core.controller;

import news.core.domain.Article;
import news.core.domain.Comment;
import news.core.service.ArticleService;
import news.core.service.CommentService;
import news.util.constants.IConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(IConstants.APPLICATION_PATH)
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    // GET /articles/list
    @RequestMapping(
            value = IConstants.ARTICLES_PATH + IConstants.LIST_ACTION_PATH,
            method = RequestMethod.GET
    )
    public String getArticleList(Model model) {
        List<Article> articles = articleService.getArticles();
        model.addAttribute("articles", articles);

        return "listArticle";
    }

    // GET /articles/:id/view
    @RequestMapping(
            value = IConstants.ARTICLES_PATH + "/{articleId}" + IConstants.VIEW_ACTION_PATH,
            method = RequestMethod.GET
    )
    public String getArticleView(
            @PathVariable Integer articleId, Model model) {
        Article article = articleService.getArticleById(articleId);

        if (article == null) {
            model.addAttribute("errorDescription", "Article not found!");
            return "error";
        }

        model.addAttribute("article", article);

        List<Comment> comments = commentService.getCommentsByArticle(articleId);
        model.addAttribute("comments", comments);

        return "viewArticle";
    }

    // GET /articles/:id/edit
    @RequestMapping(
            value = IConstants.ARTICLES_PATH + "/{articleId}" + IConstants.EDIT_ACTION_PATH,
            method = RequestMethod.GET
    )
    public String editArticle(
            @PathVariable Integer articleId, Model model) {
        Article article = articleService.getArticleById(articleId);

        if (article == null) {
            model.addAttribute("errorDescription", "Article not found!");
            return "error";
        }

        model.addAttribute("article", article);

        return "editArticle";
    }

    // GET /articles/post
    @RequestMapping(
            value = IConstants.ARTICLES_PATH + IConstants.POST_ACTION_PATH,
            method = RequestMethod.GET
    )
    public String postArticle() {
        return "postArticle";
    }
}
