package news.core.service;

import news.core.domain.Comment;

import java.util.List;

public interface CommentService {

    public List<Comment> getCommentsByArticle(Integer articleId);

    public Comment getCommentById(Integer commentId);

    public Integer addComment(Integer articleId, Comment comment);

    public void editComment(Integer commentId, Comment comment);

    public void deleteComment(Integer commentId);
}
