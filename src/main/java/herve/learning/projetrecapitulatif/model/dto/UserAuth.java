package herve.learning.projetrecapitulatif.model.dto;

public class UserAuth {

    private Long id;
    private String username;
    private String token;

    public UserAuth() {
    }

    public UserAuth(Long id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
