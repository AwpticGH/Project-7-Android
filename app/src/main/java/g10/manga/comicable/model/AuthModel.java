package g10.manga.comicable.model;

import java.io.Serializable;

public class AuthModel implements Serializable {

    private String id;
    private String email;
    private String password;
    private String name;

    public AuthModel() {}

    public AuthModel(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public AuthModel(String id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
