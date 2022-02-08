package br.ufc.dspm.abilio.drapp.model;

import java.io.Serializable;

public class Users implements Serializable {
    private String username;
    private String password;
    private String _class;

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
