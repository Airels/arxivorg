package app.arxivorg.utils;

import app.arxivorg.model.Article;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class ArticlesStatisticsTest {

    ArrayList<Article> articles = XmlReader.read("1.atom");

    @Test
    public void statisticCategoriesTest() {
        int[] test = ArticlesStatistics.statisticCategories(articles);
        assertEquals(9 , test[5]);
        assertEquals(1, test[14]);
    }

    @Test
    public void statisticDateTest (){
        int test = ArticlesStatistics.statisticDate(articles);
        assertEquals(10, test);
    }

    @Test
    public void statisticAuthorsTest (){
        ArrayList<String> test = ArticlesStatistics.statisticAuthors(articles);
        ArrayList<String> authors = new ArrayList<>();
        authors.add("Ivan Vulić");
        authors.add("Thomas Bachlechner");
        authors.add("Bodhisattwa Prasad Majumder");
        authors.add("Huanru Henry Mao");
        authors.add("Garrison W. Cottrell");

        for (int i = 0 ; i < 5 ; i++){
            assertTrue(test.get(i).equals(authors.get(i)));
        }
    }

    @Test
    public void statisticOnTextTest (){
        ArrayList<String> mots = new ArrayList<>();
        mots.add("the");
        mots.add("other");
        mots.add("test");

        String test = ArticlesStatistics.statisticOnText(articles, mots);

        assertTrue(test.equals("the"));
    }
}
