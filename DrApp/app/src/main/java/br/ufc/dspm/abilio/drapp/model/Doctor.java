package br.ufc.dspm.abilio.drapp.model;

public class Doctor extends Users {
    private String nome;
    private String especialidade;

    public Doctor(String nome, String especialidade, String username, String password) {
        super(username, password);
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
