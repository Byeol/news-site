package news.core.repository.jdbc;

import news.core.domain.Comment;
import news.core.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcCommentRepository implements CommentRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String tableName = "comment";

    private static final String columnId = "id";
    private static final String columnArticleId = "article_id";
    private static final String columnUserId = "user_id";
    private static final String columnContent = "content";
    private static final String columnCreatedDate = "created_date";

    public Comment findComment(Integer commentId) {
        Comment comment;
        String sql = "SELECT * FROM " + tableName
                + " WHERE " + columnId + " = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", commentId);

        try {
            comment = this.namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new CommentMapper());
        } catch(EmptyResultDataAccessException e) {
            comment = null;
        }

        return comment;
    }

    public List<Comment> findAllCommentsByArticle(Integer articleId) {
        String sql = "SELECT * FROM " + tableName
                + " WHERE " + columnArticleId + " = :articleId";
        SqlParameterSource namedParameters = new MapSqlParameterSource("articleId", articleId);

        return this.namedParameterJdbcTemplate.query(sql, namedParameters, new CommentMapper());
    }

    public Integer insertComment(Comment comment) {
        String sql = "INSERT INTO " + tableName
                + " (" + columnArticleId + ", " + columnUserId + ", " + columnContent + ", " + columnCreatedDate + ")"
                + " VALUES (:articleId, :userId, :content, :createdDate)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(comment);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);
        Integer commentId = keyHolder.getKey().intValue();

        return commentId;
    }

    public void updateComment(Comment comment) {
        String sql = "UPDATE " + tableName
                + " SET " + columnContent + " = :content"
                + " WHERE " + columnId + " = :id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(comment);

        this.namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    public void deleteComment(Integer commentId) {
        String sql = "DELETE FROM " + tableName
                + " WHERE " + columnId + " = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", commentId);

        this.namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    public void deleteAllCommentsByArticle(Integer articleId) {
        String sql = "DELETE FROM " + tableName
                + " WHERE " + columnArticleId + " = :articleId";
        SqlParameterSource namedParameters = new MapSqlParameterSource("articleId", articleId);

        this.namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    private static final class CommentMapper implements RowMapper<Comment> {

        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Integer id = rs.getInt(columnId);
            Integer articleId = rs.getInt(columnArticleId);
            Integer userId = rs.getInt(columnUserId);
            String content = rs.getString(columnContent);
            Date createdDate = rs.getDate(columnCreatedDate);

            return new Comment(id, articleId, userId, content, createdDate);
        }
    }
}
