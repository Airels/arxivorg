package app.arxivorg.model;

import java.util.List;

public class Article {
    private String title;
    private Authors authors;
    private String content;
    private Category category;
    private SubCategories subCategories;

    public Article(String title, Authors authors, String content, Category category, SubCategories subCategories) throws Exception {
        if(title.isEmpty()) throw new IllegalAccessException("title is Empty");
        if(authors.get().isEmpty()) throw new IllegalAccessException("there must be at least one author");
        if(content.isEmpty()) throw new IllegalAccessException("content is empty");
        if(category==null) throw new IllegalAccessException("category must be defined");
        this.title = title;
        this.authors = authors;
        this.content = content;
        this.category = category;
        this.subCategories = subCategories;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(Authors authors) {
        this.authors = authors;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSubCategories(SubCategories subCategories) {
        this.subCategories = subCategories;
    }

    public String getTitle() {
        return title;
    }

    public Authors getAuthors() {
        return authors;
    }

    public String getContent() {
        return content;
    }

    public Category getCategory() {
        return category;
    }

    public SubCategories getSubCategories() {
        return subCategories;
    }
}
