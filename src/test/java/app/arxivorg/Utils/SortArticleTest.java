package app.arxivorg.Utils;


import Utils.XmlReader;
import Utils.SortArticle;
import app.arxivorg.model.Article;
import app.arxivorg.model.Authors;
import app.arxivorg.model.Category;
import app.arxivorg.model.SubCategories;
import org.assertj.core.internal.AtomicReferenceArrayElementComparisonStrategy;
import org.assertj.core.internal.bytebuddy.TypeCache;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    }

    /***
     * Test et fonction inutil si on considère une seule catégorie ...
     */
    @Test
    public void SortByCategoryTest() {
        ArrayList<Article> expected = new ArrayList<>();
        Category category = Category.Computer_Science;
        expected.add(articles.get(0));
        expected.add(articles.get(1));

        ArrayList<Article> testedSortByCategory = SortArticle.byCategory(articles,category);

        assertEquals(testedSortByCategory.size(), expected.size());
        for (int i = 0; i < testedSortByCategory.size(); i++) {
            assertEquals(category, testedSortByCategory.get(i).getCategory());
        }
    }
    @Test
    public void SortBySubCategoryTest() {
        ArrayList<Article> expected = new ArrayList<>();
        String subcategories = "I test subCategory";
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
/** Not yet implemented
    @Test
    public void SortByDateTest(){
        ArrayList<Article> expected = new ArrayList<>();
        expected.add(articles.get(0)); // article 0 plus récent que article 1
        expected.add(articles.get(1));

        ArrayList<Article> testedSortByDate = SortArticle.byDate(articles);

        assertEquals(expected.size(),testedSortByDate.size());
        for(int i = 0; i < testedSortByDate.size(); i++){
            assertEquals(expected.get(i).getDate(), testedSortByDate.get(i).getDate());
            }
    }*/
}

