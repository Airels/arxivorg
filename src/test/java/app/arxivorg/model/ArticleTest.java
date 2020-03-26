package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import javax.swing.plaf.SeparatorUI;
import java.time.LocalDate;
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
        LocalDate date = LocalDate.of(2020,1,1);
        //No Title :
        assertThrows(IllegalArgumentException.class,()-> new Article("",authors,"content",Category.Economics,new SubCategories(),"link",date));
        //No Content
        assertThrows(IllegalArgumentException.class,()-> new Article("title",authors,"",Category.Economics,new SubCategories(),"link",date));
        //No authors
        assertThrows(IllegalArgumentException.class,()-> new Article("title",new Authors(new ArrayList<>()),"content",Category.Economics,new SubCategories(),"link",date));
        //Null Category
        assertThrows(IllegalArgumentException.class,()-> new Article("title",authors,"content",null,new SubCategories(),"link",date));
        //Null link
        assertThrows(IllegalArgumentException.class,()-> new Article("title",authors,"content",Category.Physics,new SubCategories(),null,date));
        //Null date
        assertThrows(IllegalArgumentException.class,()-> new Article("title",authors,"content",Category.Physics,new SubCategories(),"link",null));

    }

    @Test
    public void GetterTest(){

        List<String> auth = new ArrayList<>();
        auth.add("test");
        Authors authors = new Authors(auth);

        List<String> subC = new ArrayList<>();
        subC.add("Covid-19");
        SubCategories subCategories = new SubCategories(subC);
        LocalDate date = LocalDate.of(2020,1,1);

        Article article = new Article("title",authors,"content",Category.Physics,subCategories,"link",date);

        assert (article.getAuthors() == authors);
        assert (article.getCategory() == Category.Physics);
        assert (article.getContent().equals("content"));
        assert (article.getSubCategories() == subCategories);
        assert (article.getTitle().equals("title"));
        assert (article.getLink().equals("link"));
        assert (article.getDate().equals(date));

    }

    @Test
    public void SetterTest(){

        LocalDate date = LocalDate.of(2020,1,1);
        LocalDate dateFalse = LocalDate.of(2020,1,2);
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


        Article article = new Article("title_false",authors_false,"content_false",Category.Economics,subCategories_false,"link_false",dateFalse);

        article.setAuthors(authors);
        article.setCategory(Category.Physics);
        article.setContent("content");
        article.setTitle("title");
        article.setSubCategories(subCategories);
        article.setLink("link");
        article.setDate(date);

        assert (article.getAuthors() == authors);
        assert (article.getCategory() == Category.Physics);
        assert (article.getContent().equals("content"));
        assert (article.getSubCategories() == subCategories);
        assert (article.getTitle().equals("title"));
        assert (article.getLink().equals("link"));
        assert (article.getDate().equals(date));

    }
}
