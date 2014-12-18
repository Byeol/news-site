package news.core.repository.jdbc;

import news.core.domain.Article;
import news.core.repository.ArticleRepository;
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
public class JdbcArticleRepository implements ArticleRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String tableName = "article";

    private static final String columnId = "id";
    private static final String columnUserId = "user_id";
    private static final String columnTitle = "title";
    private static final String columnContent = "content";
    private static final String columnCreatedDate = "created_date";
    private static final String joinColumnUserNickname = "user_nickname";

    private static final String userTableName = "user";

    private static final String userColumnId = "id";
    private static final String userColumnNickname = "nickname";

    public Article findArticle(Integer articleId) {
        Article article;
        String sql = "SELECT " + tableName + "." + "*" + ", " + userTableName + "." + userColumnNickname + " AS " + joinColumnUserNickname + " FROM " + tableName
                + " JOIN " + userTableName + " ON " + tableName + "." + columnUserId + " = " + userTableName + "." + userColumnId
                + " WHERE " + tableName + "." + columnId + " = " + ":id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", articleId);

        try {
            article = this.namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new ArticleMapper());
        } catch (EmptyResultDataAccessException e) {
            article = null;
        }

        return article;
    }

    public List<Article> findAllArticles() {
        String sql = "SELECT " + tableName + "." + "*" + ", " + userTableName + "." + userColumnNickname + " AS " + joinColumnUserNickname + " FROM " + tableName
                + " JOIN " + userTableName + " ON " + tableName + "." + columnUserId + " = " + userTableName + "." + userColumnId;

        return this.namedParameterJdbcTemplate.query(sql, new ArticleMapper());
    }

    public Integer insertArticle(Article article) {
        String sql = "INSERT INTO " + tableName
                + " (" + columnUserId + ", " + columnTitle + ", " + columnContent + ", " + columnCreatedDate + ")"
                + " VALUES (:userId, :title, :content, :createdDate)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(article);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);
        Integer articleId = keyHolder.getKey().intValue();

        return articleId;
    }

    public void updateArticle(Article article) {
        String sql = "UPDATE " + tableName
                + " SET " + columnTitle + " = " + ":title" + ", " + columnContent + " = " + ":content"
                + " WHERE " + columnId + " = " + ":id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(article);

        this.namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    public void deleteArticle(Integer articleId) {
        String sql = "DELETE FROM " + tableName
                + " WHERE " + columnId + " = " + ":id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", articleId);

        this.namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    private static final class ArticleMapper implements RowMapper<Article> {

        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            Integer id = rs.getInt(columnId);
            Integer userId = rs.getInt(columnUserId);
            String userNickname = rs.getString(joinColumnUserNickname);
            String title = rs.getString(columnTitle);
            String content = rs.getString(columnContent);
            Date createdDate = rs.getDate(columnCreatedDate);

            return new Article(id, userId, userNickname, title, content, createdDate);
        }
    }

}
