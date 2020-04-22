package app.arxivorg.view;

import app.arxivorg.model.Article;
import app.arxivorg.model.Authors;
import app.arxivorg.model.Category;
import app.arxivorg.model.SubCategories;
import app.arxivorg.utils.FileManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class used for managing easily favourites with a file using JSON.
 * @author VIZCAINO Yohan (Airels)
 */
public class UserFavourites {
    public final static String fileName = "UserFavourites.json";

    /**
     * Check if user favourites file exist, and initiate the file otherwise.
     */
    public static void checkUserFavouriteFile() {
        FileManager fm = new FileManager(fileName); // Creates files if doesn't exist

        JSONArray array = new JSONArray();
        fm.putLine(array.toJSONString());
    }

    /**
     * Adding an article object to the JSON file.
     * @param article Article to add
     */
    public static void addFavourite(Article article) {
        JSONObject articleJSON = new JSONObject();
        articleJSON.put("title", article.getTitle());
        articleJSON.put("authors", article.getAuthors().toString());
        articleJSON.put("content", article.getContent());
        articleJSON.put("category", article.getCategory().getName());
        articleJSON.put("link", article.getLink());
        articleJSON.put("date", article.getDate().toString());
        articleJSON.put("subCategories", article.getSubCategories().getList().toString());

        JSONArray jsonArray = getFavouritesJSONArray();
        jsonArray.add(articleJSON);

        FileManager fm = new FileManager(fileName);
        fm.wipeFile();
        fm.putLine(jsonArray.toJSONString());
    }

    /**
     * Check if article is already an user favourite
     * @param article Article to check
     * @return Boolean, TRUE if article is already a favourite, FALSE otherwise
     */
    public static boolean isFavourite(Article article) {
        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(fileName));

            for (Object articleString : jsonArray) {
                Article parsedArticle = jsonToArticle((JSONObject) articleString);

                if (article.getTitle().equals(parsedArticle.getTitle())) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get list of articles added as favourites by the user.
     * @return List of Article
     */
    public static List<Article> getFavourites() {
        List<Article> listArticles = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            JSONArray articlesArray = (JSONArray) parser.parse(new FileReader(fileName));

            for (Object articleJSON : articlesArray)
                listArticles.add(jsonToArticle((JSONObject) articleJSON));

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listArticles;
    }

    /**
     * Remove article favourite from the JSON file.
     * @param article Article to remove
     */
    public static void removeFavourite(Article article) {
        if (!isFavourite(article)) return;

        JSONArray favourites = getFavouritesJSONArray();

        for (int i = 0; i < favourites.size(); i++) {
            JSONObject favArticle = (JSONObject) favourites.get(i);
            if (article.getTitle().equals(favArticle.get("title"))) {
                favourites.remove(i);
                break;
            }
        }

        FileManager fm = new FileManager(fileName);
        fm.wipeFile();
        fm.putLine(favourites.toJSONString());
    }

    /**
     * Used locally for getting a JSONArray instead as a simple Arrray.
     * @return JSONArray of favourites
     */
    private static JSONArray getFavouritesJSONArray() {
        JSONParser parser = new JSONParser();

        try {
            return (JSONArray) parser.parse(new FileReader(fileName));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Used to convert a JSONObject to an Article.
     * @param json JSONObject to convert
     * @return Article converted
     */
    private static Article jsonToArticle(JSONObject json) {
        return new Article(
                json.get("title").toString(),
                new Authors(new ArrayList<>(Arrays.asList(json.get("authors").toString()))),
                json.get("content").toString(),
                Category.getCategory(json.get("category").toString()),
                new SubCategories(new ArrayList<>(Arrays.asList(json.get("subCategories").toString()))),
                json.get("link").toString(),
                LocalDate.parse(json.get("date").toString())
        );
    }
}
