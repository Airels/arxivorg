package app.arxivorg.model;

import org.junit.jupiter.api.Test;

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
}
