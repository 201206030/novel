package xyz.zinglizingli.books.vo;

import xyz.zinglizingli.books.po.Book;

public class BookVO extends Book {

    private String cateName;

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
