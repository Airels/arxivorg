package app.arxivorg.model;

public class Article {
    private String title;
    private Authors authors;
    private String content;
    private Category category;
    private SubCategories subCategories;

    public Article(String title, Authors authors, String content, Category category, SubCategories subCategories) throws Exception {
        if(title.isEmpty()) throw new IllegalAccessException("title is Empty");
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
