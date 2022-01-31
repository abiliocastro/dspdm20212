package br.ufc.dspm.abilio.drapp.model;

public class Patient extends Users {
    private String nome;

    public Patient(String nome, String username, String password) {
        super(username, password);
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
