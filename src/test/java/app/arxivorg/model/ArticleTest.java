package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import javax.swing.plaf.SeparatorUI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArticleTest {

    @Test
    public void ConstructorTest(){
        List<String> auth = new ArrayList<>();
        auth.add("test");
        Authors authors = new Authors(auth);
        //No Title :
        assertThrows(IllegalArgumentException.class,()-> new Article("",authors,"content",Category.Economics,new SubCategories()));
        //No Content
        assertThrows(IllegalArgumentException.class,()-> new Article("title",authors,"",Category.Economics,new SubCategories()));
        //No authors
        assertThrows(IllegalArgumentException.class,()-> new Article("title",new Authors(new ArrayList<>()),"content",Category.Economics,new SubCategories()));
        //Null Category
        assertThrows(IllegalArgumentException.class,()-> new Article("title",authors,"content",null,new SubCategories()));
    }

    @Test
    public void GetterTest(){
        List<String> auth = new ArrayList<>();
        auth.add("test");
        Authors authors = new Authors(auth);

        List<String> subC = new ArrayList<>();
        subC.add("Covid-19");
        SubCategories subCategories = new SubCategories(subC);

        Article article = new Article("title",authors,"content",Category.Physics,subCategories);

        assert (article.getAuthors() == authors);
        assert (article.getCategory() == Category.Physics);
        assert (article.getContent().equals("content"));
        assert (article.getSubCategories() == subCategories);
        assert (article.getTitle().equals("title"));

    }

    @Test
    public void SetterTest(){
        List<String> auth = new ArrayList<>();
        auth.add("test_false");
        Authors authors_false = new Authors(auth);
        List<String> subC = new ArrayList<>();
        subC.add("Covid-19_false");
        SubCategories subCategories_false = new SubCategories(subC);

        List<String> autha = new ArrayList<>();
        autha.add("test");
        Authors authors = new Authors(autha);
        List<String> subCat = new ArrayList<>();
        subCat.add("Covid-19");
        SubCategories subCategories = new SubCategories(subCat);


        Article article = new Article("title_false",authors_false,"content_false",Category.Economics,subCategories_false);

        article.setAuthors(authors);
        article.setCategory(Category.Physics);
        article.setContent("content");
        article.setTitle("title");
        article.setSubCategories(subCategories);

        assert (article.getAuthors() == authors);
        assert (article.getCategory() == Category.Physics);
        assert (article.getContent().equals("content"));
        assert (article.getSubCategories() == subCategories);
        assert (article.getTitle().equals("title"));

    }
}
