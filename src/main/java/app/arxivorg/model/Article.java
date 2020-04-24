package app.arxivorg.model;

import java.time.LocalDate;

/**
 * Article class containing all properties,method and constructor needed for an article
 * @author Tom
 */

public class Article {
    private String title;
    private Authors authors;
    private String content;
    private Category category;
    private SubCategories subCategories;
    private String link;
    private LocalDate date;

    /**
     * Article constructor
     * @param title             String title of the article
     * @param authors           String author of the article
     * @param content           String content of the article
     * @param category          Category of the article
     * @param subCategories     Subcategories of an article
     * @param link              String link to download the article
     * @param date              Date of publication off the article
     * @throws IllegalArgumentException     throws Exeption when wrong parameter are passed to the construcor
     */

    public Article(String title, Authors authors, String content, Category category, SubCategories subCategories, String link, LocalDate date) throws IllegalArgumentException {
        if(title.isEmpty()) throw new IllegalArgumentException("title is Empty");
        if(authors.getList().isEmpty()) throw new IllegalArgumentException("there must be at least one author");
        if(content.isEmpty()) throw new IllegalArgumentException("content is empty");
        if(category==null) throw new IllegalArgumentException("category must be defined");
        if(link == null) throw  new IllegalArgumentException("Link can't be null or null");
        if(date == null) throw new IllegalArgumentException("date can't be null");
        if(subCategories==null)subCategories = new SubCategories();

        this.title = title;
        this.authors = authors;
        this.content = content;
        this.category = category;
        this.subCategories = subCategories;
        this.link = link;
        this.date = date;
    }

    /**
     * set the link of the Article
     * @param link          String link to download the article
     */

    public void setLink(String link) {
        this.link = link;
    }

    /**
     * set the title of an article
     * @param title         String title
     */

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * set the authors
     * @param authors           String authors
     */

    public void setAuthors(Authors authors) {
        this.authors = authors;
    }

    /**
     * set the content
     * @param content           String content
     */

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * set the category
     * @param category          Category
     */

    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * set the subcategories
     * @param subCategories     Subcategories
     */

    public void setSubCategories(SubCategories subCategories) {
        this.subCategories = subCategories;
    }

    /**
     * set the date
     * @param date              LocalDate date
     */

    public void setDate(LocalDate date) { this.date = date; }

    /**
     * get the link
     * @return (String)
     */

    public String getLink() {
        return link;
    }

    /**
     * get the title
     * @return (String)
     */

    public String getTitle() {
        return title;
    }

    /**
     * get the authors
     * @return (Authors)
     */

    public Authors getAuthors() {
        return authors;
    }

    /**
     * get the content
     * @return (String)
     */

    public String getContent() {
        return content;
    }

    /**
     * get the category
     * @return (Category)
     */

    public Category getCategory() {
        return category;
    }

    /**
     * get the subcategories
     * @return (Subcategories
     */

    public SubCategories getSubCategories() {
        return subCategories;
    }

    /**
     * get the date
     * @return (LocalDate)
     */

    public LocalDate getDate() { return date; }
}
