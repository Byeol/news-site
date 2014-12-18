package news.core.service.impl;

import news.core.domain.Comment;
import news.core.repository.CommentRepository;
import news.core.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getCommentsByArticle(Integer articleId) {
        return commentRepository.findAllCommentsByArticle(articleId);
    }

    @Override
    public Comment getCommentById(Integer commentId) {
        return commentRepository.findComment(commentId);
    }

    @Override
    public Integer addComment(Integer articleId, Comment comment) {
        Comment newComment = new Comment(articleId);
        newComment.setContent(comment.getContent());

        Integer commentId = commentRepository.insertComment(newComment);
        return commentId;
    }

    @Override
    public void editComment(Integer commentId, Comment comment) {
        Comment newComment = new Comment(commentId, comment.getArticleId());
        newComment.setContent(comment.getContent());

        commentRepository.updateComment(newComment);
    }

    @Override
    public void deleteComment(Integer commentId) {
        commentRepository.deleteComment(commentId);
    }
}
