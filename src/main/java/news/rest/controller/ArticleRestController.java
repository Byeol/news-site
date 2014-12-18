package news.rest.controller;

import news.core.domain.Article;
import news.core.service.ArticleService;
import news.util.constants.IConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(IConstants.APPLICATION_PATH)
public class ArticleRestController {

    @Autowired
    private ArticleService articleService;

    // GET /articles
    @RequestMapping(
            value = IConstants.ARTICLES_PATH,
            method = RequestMethod.GET
    )
    public List<Article> getArticleList() {
        return articleService.getArticles();
    }

    // POST /articles
    @RequestMapping(
            value = IConstants.ARTICLES_PATH,
            method = RequestMethod.POST
    )
    public ResponseEntity postArticle(
            @RequestBody Article article,
            UriComponentsBuilder uriComponentsBuilder) {

        Integer articleId = articleService.addArticle(article);

        UriComponents uriComponents = uriComponentsBuilder.path(IConstants.ARTICLES_PATH + "/{articleId}").buildAndExpand(articleId);
        URI articleUri = uriComponents.toUri();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(articleUri);

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    // GET /articles/:id
    @RequestMapping(
            value = IConstants.ARTICLES_PATH + "/{articleId}",
            method = RequestMethod.GET
    )
    public ResponseEntity<Article> getArticle(
            @PathVariable Integer articleId) {
        Article article = articleService.getArticleById(articleId);

        if (article == null) {
            return new ResponseEntity<Article>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Article>(article, HttpStatus.OK);
    }

    // PATCH /articles/:id
    @RequestMapping(
            value = IConstants.ARTICLES_PATH + "/{articleId}",
            method = RequestMethod.PATCH
    )
    public ResponseEntity patchArticle(
            @PathVariable Integer articleId,
            @RequestBody Article article) {
        articleService.editArticle(articleId, article);
        return new ResponseEntity(HttpStatus.OK);
    }

    // DELETE /articles/:id
    @RequestMapping(
            value = IConstants.ARTICLES_PATH + "/{articleId}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity deleteArticle(
            @PathVariable Integer articleId) {
        articleService.deleteArticle(articleId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
