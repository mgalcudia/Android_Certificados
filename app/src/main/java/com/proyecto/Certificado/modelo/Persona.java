package com.proyecto.Certificado.modelo;

import java.io.Serializable;

public class Persona implements Serializable {

    private String idUSer;
    private String email;
    private String name;
    private String password;

    public Persona() {
    }

    @Override

    public String toString() {
        return "Persona{" +
                "idUSer='" + idUSer + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getIdUSer() {
        return idUSer;
    }

    public void setIdUSer(String idUSer) {
        this.idUSer = idUSer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
