package app.arxivorg.Utils;


import Utils.XmlReader;
import app.arxivorg.model.Article;
import app.arxivorg.model.Authors;
import app.arxivorg.model.Category;
import app.arxivorg.model.SubCategories;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.SeparatorUI;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class XmlReaderTest {

    List<Article> articles = XmlReader.Reader(new File("test.atom"));
    Article testedArticle = articles.get(0);


    @Test
    public void AuthorsTest(){
        List<String> auth = new ArrayList<>();
        auth.add("Guillaume Nicolai");
        auth.add("Yohan Vizcaino");
        auth.add("Tom David-Broglio");
        auth.add("Aymeric Sybiak");
        Authors authors = new Authors(auth);

        Authors testedAuthors = testedArticle.getAuthors();

        assertEquals(authors.toString(), testedAuthors.toString());

    }

    @Test
    public void TittleTest(){
        String title = "I am a test";
        String testedTitle = testedArticle.getTitle();

        assertEquals(title,testedTitle);
    }

    @Test
    public void ContentTest(){
        String articleContent = "I am working";
        String testedArticleContent = testedArticle.getContent();

        assertEquals(articleContent, testedArticleContent);
    }

    @Test
    public void CategoryTest(){
        Category articleCategory = Category.Computer_Science;
        Category testedArticleCategory = testedArticle.getCategory();

        assertEquals(articleCategory, testedArticleCategory);
    }

    @Test
    public void SubCategoryTest(){
        SubCategories subCategories = new SubCategories();
        subCategories.add("cs.LG");
        SubCategories testedSubCategories = testedArticle.getSubCategories();
        assertEquals(subCategories.get().size(), testedSubCategories.get().size());

        for (int i = 0; i <subCategories.get().size() ; i++) {

            assertEquals(subCategories.get().get(i),testedSubCategories.get().get(i));
        }
    }

}
