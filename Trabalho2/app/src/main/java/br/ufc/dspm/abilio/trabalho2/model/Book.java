package br.ufc.dspm.abilio.trabalho2.model;

import java.io.Serializable;

public class Book implements Serializable {
    private String id;
    private String title;
    private String numberOfPages;

    private static final long serialVersionUID = 6131518723875494551L;

    public Book(String id, String title, String numberOfPages) {
        this.id = id;
        this.title = title;
        this.numberOfPages = numberOfPages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", numberOfPages='" + numberOfPages + '\'' +
                '}';
    }
}
