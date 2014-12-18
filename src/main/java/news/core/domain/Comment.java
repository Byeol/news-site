package news.core.domain;

import java.util.Date;

public class Comment {
    private final Integer id;
    private final Integer articleId;
    private final Integer userId;
    private String userNickname;
    private String content;
    private final Date createdDate;

    public Comment() {
        this(null, null);
    }

    public Comment(Integer articleId) {
        this(null, articleId);
    }

    public Comment(Integer id, Integer articleId) {
        this.id = id;
        this.articleId = articleId;
        this.userId = 1;
        this.createdDate = new Date();
    }

    public Comment(Integer id, Integer articleId, Integer userId, String userNickname, String content, Date createdDate) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.userNickname = userNickname;
        this.content = content;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
