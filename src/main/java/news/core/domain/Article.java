package news.core.domain;

import java.util.Date;

public class Article {
    private final Integer id;
    private final Integer userId;
    private String userNickname;
    private String title;
    private String content;
    private final Date createdDate;

    public Article() {
        this(null);
    }

    public Article(Integer id) {
        this.id = id;
        this.userId = 1;
        this.createdDate = new Date();
    }

    public Article(Integer id, Integer userId, String userNickname, String title, String content, Date createdDate) {
        this.id = id;
        this.userId = userId;
        this.userNickname = userNickname;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
