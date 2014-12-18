package news.core.repository;

import news.core.domain.Comment;

import java.util.List;

public interface CommentRepository {

    public Comment findComment(Integer commentId);

    public List<Comment> findAllCommentsByArticle(Integer articleId);

    public Integer insertComment(Comment comment);

    public void updateComment(Comment comment);

    public void deleteComment(Integer commentId);

    public void deleteAllCommentsByArticle(Integer articleId);
}
