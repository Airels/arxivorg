package app.arxivorg.utils;

import app.arxivorg.model.Article;

import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import java.io.File;


public class APICall {
    /**
     * Classe to Call the Arxiv Api and get some Article
     * @author Sibiak Aymeric , Guillaume Nicolai
     */

    /**
     * Builder for the Api Call
     */
    private static HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();


    /**
     * Create the call for the Api of a specific research and specific index
     * @param typesearch the type of search you want to made into arxiv database
     * @param searchsubjet what you want to get into arxiv database
     * @param start index of the first article you want
     * @param to index of the last
     * @return A request for requestApi
     */
    private static HttpRequest createGetFromToAsk(String typesearch, String searchsubjet, int start, int to){

        String whithourspace = searchsubjet.replace(" ", "+");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://export.arxiv.org/api/query?search_query=" + typesearch + ":" + whithourspace + "&start=" + start + "&max_results=" + to + "&sortBy=lastUpdatedDate&sortOrder=descending"))
                .GET()
                .build();

        return request;

    }

    /**
     * Return the 10 first Article of the specific request
     * @see XmlReader
     * @param typesearch how you want to Look into arxiv database
     * @param searchsubjet what you want to search
     * @return List of Article
     */

    protected static ArrayList<Article> requestApi (String typesearch, String searchsubjet) {

        return requestApiFromTo(typesearch, searchsubjet, 0 , 10);
    }

    protected static ArrayList<Article> requestApiFromTo (String typesearch, String searchsubjet,int start, int to) {
        ArrayList<Article> requestArticles;
        try {

            HttpResponse<String> response =
                    httpClient.send(createGetFromToAsk(typesearch, searchsubjet, start , to), HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 400) throw new RuntimeException("Commande mal formuler");

            if (response.statusCode() == 200) {

                File requestfile = new File("temp.atom");
                FileWriter writer = new FileWriter(requestfile);
                writer.write(response.body());
                writer.flush();

                requestArticles = XmlReader.read("temp.atom");

                requestfile.delete();

            }

            else requestArticles = new ArrayList<>();

        } catch (Exception e) {
            System.out.println("Request failed");

            requestArticles = new ArrayList<>();
        }

        return requestArticles;
    }



}



