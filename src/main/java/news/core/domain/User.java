package news.core.domain;

public class User {
    private final Integer id;
    private String loginId;
    private String nickname;

    public User() {
        this.id = null;
    }

    public User(Integer id, String loginId, String nickname) {
        this.id = id;
        this.loginId = loginId;
        this.nickname = nickname;
    }

    public Integer getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
