package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArticleTest {

    @Test
    private void ConstructorTest(){
        //TODO : continue test for Article
        assertThrows(IllegalArgumentException.class,()-> new Article("",new Authors(Arrays.asList("test")),,))
    }
}
