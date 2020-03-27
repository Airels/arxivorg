package app.arxivorg.utils;


import utils.XmlReader;
import utils.SortArticle;
import app.arxivorg.model.Article;
import app.arxivorg.model.Category;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static app.arxivorg.model.Category.*;
import static org.junit.jupiter.api.Assertions.*;

public class SortArticleTest {

    ArrayList<Article> articles = XmlReader.read("test.atom");


    @Test
    public void SortByAuthorsTest() {
        ArrayList<Article> expected = new ArrayList<>();
        expected.add(articles.get(0));
        String name = "Guillaume Nicolai";
        ArrayList<Article> testedAuthorSort = SortArticle.byAuthors(articles, name);

        assertEquals(testedAuthorSort.size(), expected.size());
        for (int i = 0; i < testedAuthorSort.size(); i++) {
            assertEquals(expected.get(i).getAuthors(), testedAuthorSort.get(i).getAuthors());
            assert(testedAuthorSort.get(i).getAuthors().toString().contains(name));
        }

        ArrayList<Article> secondExpected = new ArrayList<>();
        secondExpected.addAll(articles);
        String noName = "";
        String spaceName = " ";

        ArrayList<Article> testedAuthorSortEmpty = SortArticle.byAuthors(articles, noName);
        ArrayList<Article> testedAuthorSortSpace = SortArticle.byAuthors(articles,spaceName);

        assertEquals(secondExpected,testedAuthorSortEmpty);
        assertEquals(secondExpected,testedAuthorSortSpace);

        for (int i = 0; i < expected.size() ; i++) {
            assertEquals(expected.get(i).getAuthors(), testedAuthorSort.get(i).getAuthors());
            assert(testedAuthorSort.get(i).getAuthors().toString().contains(name));
        }

    }

    @Test
    public void SortByCategoryTest() {
        ArrayList<Article> expected = new ArrayList<>();
        Category category = All;
        expected.add(articles.get(0));
        expected.add(articles.get(1));

        ArrayList<Article> testedSortByCategory = SortArticle.byCategory(articles,category);

        assertEquals(testedSortByCategory.size(), expected.size());

        ArrayList<Article> secondExpected = new ArrayList<>();
        Category secondCategory = Computer_Science;
        secondExpected.add(articles.get(1));
        ArrayList<Article> secondTestedSortByCategory = SortArticle.byCategory(articles,secondCategory);

        assertEquals(secondExpected.size(),secondTestedSortByCategory.size());
        for (int i = 0; i < secondExpected.size() ; i++) {
            assertEquals(secondExpected.get(i).getCategory(),secondTestedSortByCategory.get(i).getCategory());
        }

        ArrayList<Article> thirdExpected = new ArrayList<>();
        thirdExpected.add(articles.get(0));
        Category thirdCategory = Statistics;

        ArrayList<Article> thirdTestedSortByCategory = SortArticle.byCategory(articles,thirdCategory);

        assertEquals(thirdExpected.size(),thirdTestedSortByCategory.size());
        for (int i = 0; i < thirdExpected.size() ; i++) {
            assertEquals(thirdExpected.get(i).getCategory(),thirdTestedSortByCategory.get(i).getCategory());
        }

    }
    @Test
    public void SortBySubCategoryTest() {
        ArrayList<Article> expected = new ArrayList<>();
        String subcategories = "cs.LG";
        expected.add(articles.get(0));

        ArrayList<Article> testedSortSubCategory = SortArticle.bySubCategories(articles,subcategories);
        assertEquals(expected.size(),testedSortSubCategory.size());
        for (int i = 0; i < testedSortSubCategory.size(); i++) {
            assertEquals(expected.get(i).getSubCategories(), testedSortSubCategory.get(i).getSubCategories());
        }
    }

    @Test
    public void SortByTitleTest(){
        ArrayList<Article> expected = new ArrayList<>();
        expected.add(articles.get(1)); // Article 1 est avant alphabetiquement
        expected.add(articles.get(0));

        ArrayList<Article> testedSortByTitle = SortArticle.byTitle(articles);

        assertEquals(expected.size(),testedSortByTitle.size());
        for (int i = 0; i < testedSortByTitle.size(); i++) {
            assertEquals(expected.get(i).getTitle(), testedSortByTitle.get(i).getTitle());
        }
    }

    @Test
    public void SortByDateTest() {
        ArrayList<Article> expected = new ArrayList<>();
        expected.add(articles.get(0)); // article 0 plus récent que article 1
        expected.add(articles.get(1));
        LocalDate dateMin = LocalDate.of(2020, 3, 9);
        LocalDate dateMax = LocalDate.of(2020, 3, 13);

        ArrayList<Article> testedSortByDate = SortArticle.byDate(articles, dateMin, dateMax);


        assertEquals(expected.size(), testedSortByDate.size());
        for (int i = 0; i < testedSortByDate.size(); i++) {
            assertEquals(expected.get(i).getDate(), testedSortByDate.get(i).getDate());
        }

        ArrayList<Article> secondExpected = new ArrayList<>();
        secondExpected.add(articles.get(0));
        LocalDate dateMinBis = LocalDate.of(2020, 3, 11);

        ArrayList<Article> testedSortByDateSecond = SortArticle.byDate(articles, dateMinBis, dateMax);


        assertEquals(secondExpected.size(), testedSortByDateSecond.size());
        for (int i = 0; i < testedSortByDateSecond.size(); i++) {
            assertEquals(secondExpected.get(i).getDate(), testedSortByDateSecond.get(i).getDate());
        }
    }
}

