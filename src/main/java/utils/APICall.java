package utils;

import app.arxivorg.model.Article;

import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import java.io.File;


public class APICall {

    private static HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();




    private static HttpRequest createGetFromToAsk(String typesearch, String searchsubjet, int start, int to){

        String whithourspace = searchsubjet.replace(" ", "+");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://export.arxiv.org/api/query?search_query=" + typesearch + ":" + whithourspace + "&start=" + start + "&max_results=" + to))
                .GET()
                .build();

        return request;

    }

    protected static ArrayList<Article> requestApi (String typesearch, String searchsubjet) {

        return requestApiFromTo(typesearch, searchsubjet, 0 , 10);
    }

    protected static ArrayList<Article> requestApiFromTo (String typesearch, String searchsubjet,int start, int to) {
        ArrayList<Article> requestArticles;
        try {

            HttpResponse<String> response =
                    httpClient.send(createGetFromToAsk(typesearch, searchsubjet, start , to), HttpResponse.BodyHandlers.ofString());

            System.out.println(response.statusCode());
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

    public static void main(String[] args) {
        ArrayList<Article> test = requestApi("cat", "stat.AP");

        for (int index = 0 ; index < 9 ; index++){
            System.out.println(test.get(index).getTitle());
        }
    }



}



