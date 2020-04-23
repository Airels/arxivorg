package app.arxivorg.utils;


import app.arxivorg.model.Article;
import app.arxivorg.model.Authors;
import app.arxivorg.model.Category;
import app.arxivorg.model.SubCategories;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class XmlReaderTest {

    List<Article> articles = XmlReader.read("test.atom");
    Article testedArticle = articles.get(0);
    Article secondTestedArticle = articles.get(1);



    @Test
    public void AuthorsTest(){
        List<String> auth = new ArrayList<>();
        auth.add("Guillaume Nicolai");
        auth.add("Yohan Vizcaino");
        auth.add("Tom David-Broglio");
        auth.add("Aymeric Sybiak");
        Authors authors = new Authors(auth);

        List<String> secondAuth = new ArrayList<>();
        secondAuth.add("Guillome Nicolai");
        secondAuth.add("Yohan Vizcaino");
        secondAuth.add("Tam David-Broglio");
        secondAuth.add("Aymerikk Sybiak");
        Authors secondAuthors = new Authors(secondAuth);

        Authors testedAuthors = testedArticle.getAuthors();
        Authors secondTestedAuthors = secondTestedArticle.getAuthors();

        assertEquals(authors.toString(), testedAuthors.toString());
        assertEquals(secondAuthors.toString(), secondTestedAuthors.toString());


    }

    @Test
    public void TittleTest(){
        String title = "I am a test";
        String testedTitle = testedArticle.getTitle();

        String secondTitle = "I am a nottest";
        String secondTestedTitle = secondTestedArticle.getTitle();

        assertEquals(title,testedTitle);
        assertEquals(secondTitle,secondTestedTitle);
    }

    @Test
    public void ContentTest(){
        String articleContent = "I am working";
        String testedArticleContent = testedArticle.getContent();

        String secondArticleContent = "I am not working";
        String secondTestArticleContent = secondTestedArticle.getContent();

        assertEquals(articleContent, testedArticleContent);
        assertEquals(secondArticleContent,secondTestArticleContent);
    }

    @Test
    public void CategoryTest(){
        Category articleCategory = Category.Statistics;
        Category testedArticleCategory = testedArticle.getCategory();

        assertEquals(articleCategory, testedArticleCategory);
    }

    @Test
    public void SubCategoryTest(){
        SubCategories subCategories = new SubCategories();
        subCategories.add("I test subCategory");
        subCategories.add("cs.CL");
        subCategories.add("stat.GL");
        SubCategories testedSubCategories = testedArticle.getSubCategories();
        SubCategories secondTestSubCategory = secondTestedArticle.getSubCategories();

        SubCategories secondEntryCategories = new SubCategories();
        secondEntryCategories.add("I also test it");
        secondEntryCategories.add("cs.CL");
        secondEntryCategories.add("stat.ML");
        assertEquals(secondEntryCategories.getList().size(),secondTestSubCategory.getList().size());
        assertEquals(subCategories.getList().size(), testedSubCategories.getList().size());

        for (int i = 0; i <subCategories.getList().size() ; i++) {

            assertEquals(subCategories.getList().get(i),testedSubCategories.getList().get(i));
            assertEquals(secondTestSubCategory.getList().get(i), secondTestSubCategory.getList().get(i));
        }
    }

    @Test
    public void LinkTest(){
        String linkContent = "http://arxiv.org/pdf/2003.04887v1";
        String secondLinkContent = "http://arxiv.org/pdf/2003.04887v2";

        String testedArticleLink = testedArticle.getLink();
        String secondArticleLink = secondTestedArticle.getLink();


        assertEquals(linkContent, testedArticleLink);
        assertEquals(secondLinkContent, secondArticleLink);
    }


}
