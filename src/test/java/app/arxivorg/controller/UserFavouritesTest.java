package app.arxivorg.controller;

import app.arxivorg.model.Article;
import app.arxivorg.model.Authors;
import app.arxivorg.model.Category;
import app.arxivorg.utils.FileManager;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class UserFavouritesTest {
    private final static Article article1 = new Article("ABC",
            new Authors(new ArrayList<String>(Arrays.asList("Ben", "Solo"))),
            "I love content",
            Category.Computer_Science,
            null,
            "https://google.fr",
            LocalDate.now()
        );

    private final static Article article2 = new Article("DEF",
            new Authors(new ArrayList<>(Arrays.asList("Henri", "Des"))),
            "It just works",
            Category.Computer_Science,
            null,
            "https://facebook.com",
            LocalDate.now()
    );

    @Test
    public void testCheckUserFavouritesFile() {
        FileManager fm = new FileManager(UserFavourites.fileName);
        fm.wipeFile();

        UserFavourites.checkUserFavouriteFile();
        assert(new File(UserFavourites.fileName).exists());
    }

    @Test
    public void testSetFavourite() {
        FileManager fm = new FileManager(UserFavourites.fileName);
        fm.wipeFile();

        UserFavourites.setFavourite(article1);

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(UserFavourites.fileName));

            JSONObject jsonObj = (JSONObject) obj;
            JSONArray articlesList = (JSONArray) jsonObj.get("Articles");

            assert(articlesList.size() == 1);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsFavourite() {
        FileManager fm = new FileManager(UserFavourites.fileName);
        fm.wipeFile();

        UserFavourites.setFavourite(article1);

        assert(UserFavourites.isFavourite(article1));
        assert(!UserFavourites.isFavourite(article2));
    }

    @Test
    public void testGetFavourites() {
        FileManager fm = new FileManager(UserFavourites.fileName);
        fm.wipeFile();

        UserFavourites.setFavourite(article1);
        UserFavourites.setFavourite(article2);

        List<Article> articlesFavourites = UserFavourites.getFavourites();

        assert articlesFavourites != null;
        assert(articlesFavourites.contains(article1));
        assert(articlesFavourites.contains(article2));
    }
}
