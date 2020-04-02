package utils;

import app.arxivorg.model.Article;

import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class APICall {

    private static HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();


    private static HttpRequest createGet(String typesearch, String searchsubjet){

        String whithourspace = searchsubjet.replace(" ", "+");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://export.arxiv.org/api/query?search_query=" + typesearch + ":" + whithourspace))
                .GET()
                .build();

        return request;

    }

    private static HttpRequest createGetFromToAsk(String typesearch, String searchsubjet, int start, int to){

        String whithourspace = searchsubjet.replace(" ", "+");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://export.arxiv.org/api/query?search_query=" + typesearch + ":" + whithourspace +"&start=" + start + "max_results=" + to))
                .GET()
                .build();

        return request;

    }

    public static ArrayList<Article> requestApi (String typesearch, String searchsubjet) {

        try {

            HttpResponse<String> response =
                    httpClient.send(createGet(typesearch, searchsubjet), HttpResponse.BodyHandlers.ofString());


            if (response.statusCode() == 200) {

                File requestfile = new File("temp.atom");
                FileWriter writer = new FileWriter(requestfile);
                writer.write(response.body());
                writer.flush();

                ArrayList<Article> requestArticles = XmlReader.read("temp.atom");

                requestfile.delete();

                return requestArticles;
            }
        } catch (Exception e) {
            System.out.println("Request failed");
        }

        return new ArrayList<Article>();
    }

    public static ArrayList<Article> requestApiFromTo (String typesearch, String searchsubjet,int start, int to) {

        try {

            HttpResponse<String> response =
                    httpClient.send(createGetFromToAsk(typesearch, searchsubjet, start , to), HttpResponse.BodyHandlers.ofString());


            if (response.statusCode() == 200) {

                File requestfile = new File("temp.atom");
                FileWriter writer = new FileWriter(requestfile);
                writer.write(response.body());
                writer.flush();

                ArrayList<Article> requestArticles = XmlReader.read("temp.atom");

                requestfile.delete();

                return requestArticles;
            }
        } catch (Exception e) {
            System.out.println("Request failed");
        }

        return new ArrayList<Article>();
    }



}



