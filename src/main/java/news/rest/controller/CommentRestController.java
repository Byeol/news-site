package news.rest.controller;

import news.core.domain.Comment;
import news.core.service.CommentService;
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
public class CommentRestController {

    @Autowired
    private CommentService commentService;

    // GET /articles/:id/comments
    @RequestMapping(
            value = IConstants.ARTICLES_PATH + "/{articleId}" + IConstants.COMMENTS_PATH,
            method = RequestMethod.GET
    )
    public List<Comment> getComments(
            @PathVariable Integer articleId) {
        return commentService.getCommentsByArticle(articleId);
    }

    // POST /articles/:id/comments
    @RequestMapping(
            value = IConstants.ARTICLES_PATH + "/{articleId}" + IConstants.COMMENTS_PATH,
            method = RequestMethod.POST
    )
    public ResponseEntity postComment(
            @PathVariable Integer articleId,
            @RequestBody Comment comment,
            UriComponentsBuilder uriComponentsBuilder) {

        Integer commentId = commentService.addComment(articleId, comment);

        UriComponents uriComponents = uriComponentsBuilder.path(IConstants.COMMENTS_PATH + "/{commentId}").buildAndExpand(commentId);
        URI commentUri = uriComponents.toUri();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(commentUri);

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    // GET /comments/:id
    @RequestMapping(
            value = IConstants.COMMENTS_PATH + "/{commentId}",
            method = RequestMethod.GET
    )
    public ResponseEntity<Comment> getComment(
            @PathVariable Integer commentId) {
        Comment comment = commentService.getCommentById(commentId);

        if (comment == null) {
            return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }

    // PATCH /comments/:id
    @RequestMapping(
            value = IConstants.COMMENTS_PATH + "/{commentId}",
            method = RequestMethod.PATCH
    )
    public ResponseEntity patchComment(
            @PathVariable Integer commentId,
            @RequestBody Comment comment) {
        commentService.editComment(commentId, comment);
        return new ResponseEntity(HttpStatus.OK);
    }

    // DELETE /comments/:id
    @RequestMapping(
            value = IConstants.COMMENTS_PATH + "/{commentId}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity deleteComment(
            @PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
