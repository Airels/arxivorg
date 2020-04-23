package app.arxivorg;

import app.arxivorg.model.Article;
import app.arxivorg.model.Authors;
import app.arxivorg.model.Category;
import app.arxivorg.utils.SortArticle;
import app.arxivorg.view.PDFDownloader;
import app.arxivorg.utils.XmlReader;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ArxivOrgCLI {
    public static String fisrtLine = "faite une recherce ou tapez help pour de l'aide , tapez exit pour quitter\n";
    public static String manual = "CLI arxivorg\n" +
            "\n" +
            "list [OPTIONS]... FILE                  list the articles in a FILE of from Arxiv org with keyword arxiv\n" +

            "-p, --period==PERIOD                    filter by period , specify the period : today yesterday YYYY-MM-DD,YYYY-MM-DD\n" +

            "-a, --author==AUTHOR                    filter by author(s) , put underscore instead of spaces (ex : Anita Mehta becomes Anita_Mehta )\n" +
            "                                       , if multiple, seperated with comma\n" +

            "-k, --keyword=KEYWORD                   filter by keyword(s), if multiple, seperated with comma\n" +

            "-c, --category=CATEGORY                 filter by category(ies), if multiple, seperated with comma\n" +

            "if the list is not empty :\n" +

            "Select [OPTIONS]                        select 1 2 3 to select the 3 fisrt articles in the list\n" +

            "download [OPTION]                       download to a folder-path , path MUST be a full path\n" +
            "\n" +
            "download [OPTIONS]... FILE , FOLDER     select articles in a FILE and download them to FOLDER, \nthe path to FOLDER must be the full path \n" +
            "[OPTIONS] same as list";


    static ArrayList<Article> articles = new ArrayList<>();

    public static String articleToString(int index,Article article){
        return index+1+" : "+article.getTitle()+"\n"+article.getAuthors().toString()+"\n";
    }

    public static void printArticles(ArrayList<Article> articles){
        for(int i=0;i<articles.size();i++){
            Article article = articles.get(i);
            System.out.print(articleToString(i,article));
        }
    }

    static void downloadArticles(ArrayList<Article> articles,String path){
        File file = new File(path);
        for (Article a:articles){
            PDFDownloader.downloadFile(a,file);
        }

    }

    static void select(String[] arguments){
        int boundEnd = arguments.length-1;
        int boundStart = 1;
        ArrayList<Article> result = new ArrayList<>();
        if(boundEnd<boundStart){
            System.out.print("nothing was selected :\n");
        }
        else {
            Set<Integer> articleIndexes = new HashSet<>();
            for(int i = boundStart;i<=boundEnd;i++){
                int articleIndex = Integer.parseInt(arguments[i])-1;
                if(articleIndexes.add(articleIndex)) result.add(articles.get(articleIndex));
            }

            articles = result;

        }
    }


    static ArrayList<Article> processFilters(ArrayList<Article> articles,String[] arguments) {
        int boundEnd = arguments.length-2;
        int boundStart = 1;
        ArrayList<Article> result = new ArrayList<>(articles);
        if(boundEnd<boundStart){
            return articles;
        }
        if(boundEnd%2 !=0 /*|| boundEnd==1*/){
            System.out.println("incorrect number of argument : "+boundEnd);
            return result;
        }
        for(int i=boundStart;i<boundEnd;i=i+2){
            String firstArgument = arguments[i];
            String secondArgument = arguments[i+1];
            switch (firstArgument){
                case "-p":
                    switch (secondArgument){
                        case "today":
                            LocalDate today = LocalDate.now();
                            result = SortArticle.byDate(result,today,today);
                            break;
                        case "yesterday":
                            LocalDate yesterday = LocalDate.now().minus(1,ChronoUnit.DAYS);
                            result = SortArticle.byDate(result,yesterday,yesterday);
                            break;
                        default:
                            String[] period = secondArgument.split(",");
                            if(period.length!=2) throw new IllegalArgumentException("wrong period format : "+secondArgument+" must be YYYY-MM-DD,YYYY-MM-DD");
                            LocalDate firstdate = LocalDate.parse(period[0]);
                            LocalDate lastdate = LocalDate.parse(period[1]);
                            result = SortArticle.byDate(result,firstdate,lastdate);
                    }
                    break;
                case "-a":
                    String[] authors = secondArgument.split(",");

                    for (String s : authors) {
                        String author = s.replace("_", " ");
                        result = SortArticle.byAuthors(result,author);
                    }


                    break;
                case "-k":

                    String[] keywords = secondArgument.split(",");
                    for (String s : keywords) {
                        String keyword = s.replace("_", " ");
                        result = SortArticle.byKeyword(result, keyword);
                    }
                    break;
                case "-c":
                    Category c = Category.All;
                    try {
                        c = Category.getCategory(secondArgument);
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("categories indefini : "+secondArgument);
                        result = null;
                    }

                    //Category.valueOf(secondArgument)
                    result = SortArticle.byCategory(result,c);
                    break;
                default:
                    System.out.println("Invalid argument : "+firstArgument);
            }
        }
        return result;

    }

    static ArrayList<Article> processFiltersAPI(String[] arguments) {
        int boundEnd = arguments.length-2;
        int boundStart = 1;
        ArrayList<Article> result = new ArrayList<>(articles);
        result = SortArticle.All();
        if(boundEnd<boundStart){
            return result;
        }
        if(boundEnd%2 !=0 /*|| boundEnd==1*/){
            System.out.println("incorrect number of argument : "+boundEnd);
            return result;
        }
        for(int i=boundStart;i<boundEnd;i=i+2){
            String firstArgument = arguments[i];
            String secondArgument = arguments[i+1];
            switch (firstArgument){
                case "-p":
                    System.out.println("-p not yet implemented");
                    //not yet implemented in API
                    /*
                    switch (secondArgument){
                        case "today":
                            LocalDate today = LocalDate.now();
                            result = SortArticle.byDate(result,today,today);
                            break;
                        case "yesterday":
                            LocalDate yesterday = LocalDate.now().minus(1,ChronoUnit.DAYS);
                            result = SortArticle.byDate(result,yesterday,yesterday);
                            break;
                        default:
                            String[] period = secondArgument.split(",");
                            if(period.length!=2) throw new IllegalArgumentException("wrong period format : "+secondArgument+" must be YYYY-MM-DD,YYYY-MM-DD");
                            LocalDate firstdate = LocalDate.parse(period[0]);
                            LocalDate lastdate = LocalDate.parse(period[1]);
                            result = SortArticle.byDate(result,firstdate,lastdate);


                    }
                    */

                    break;
                case "-a":
                    String[] authors = secondArgument.split(",");
                    List<String> authors1 = new ArrayList<>();

                    for (String s : authors) {
                        String author = s.replace("_", " ");
                        authors1.add(author);
                    }
                    result = SortArticle.byAuthors(new Authors(authors1));


                    break;
                case "-k":

                    String[] keywords = secondArgument.split(",");
                    for (String s : keywords) {
                        String keyword = s.replace("_", " ");
                        result = SortArticle.byKeyword(keyword);
                    }
                    break;
                case "-c":
                    Category c = Category.All;
                    try {
                        c = Category.getCategory(secondArgument);
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("categories indefini : "+secondArgument);
                        result = null;
                    }

                    //Category.valueOf(secondArgument)
                    result = SortArticle.byCategory(c);
                    break;
                default:
                    System.out.println("Invalid argument : "+firstArgument);
            }
        }
        return result;

    }

    static void cli () {
        System.out.print(fisrtLine);
        Scanner line = new Scanner(System.in);
        String input = line.nextLine();
        while (true) {
            String[] arguments = input.split(" ");

            //Pattern start = Pattern.compile(arguments[0]);
            String argument = arguments[0];
            if(argument.equals("exit") ){
                line.close();
                break;
            }

            switch (argument) {
                case "list":
                    String source = arguments[arguments.length - 1];
                    if(source.equals("arxiv")) {
                        articles = processFiltersAPI(arguments);
                        printArticles(articles);
                    }
                    else {
                        articles = XmlReader.read(source);
                        articles = processFilters(articles,arguments);
                        printArticles(articles);
                    }
                    break;
                case "download":
                    String filePath = arguments[arguments.length - 1];
                    if(articles.isEmpty()){
                        source = arguments[arguments.length - 2];
                        arguments = removeLastElement(arguments);
                        if(source.equals("fromApi")) {
                            articles = processFiltersAPI(arguments);
                            downloadArticles(articles,filePath);
                        }
                        else {
                            articles = XmlReader.read(source);
                            articles = processFilters(articles,arguments);
                            downloadArticles(articles,filePath);
                        }
                    }
                    else {
                        downloadArticles(articles,filePath);
                    }


                    break;

                case "select":
                    if(articles.isEmpty()){
                        System.out.println("make a search first with list");
                        break;
                    }
                    else {
                        select(arguments);
                        printArticles(articles);
                        break;
                    }


                case "man":
                case "help":
                    System.out.print(manual);
                    break;
                case "":
                    break;
                default:
                    System.out.println("commande non reconnue : " + argument);
            }


            input = line.nextLine();
            //System.out.print("[Arxivor]$ ");
        }
    }

    public static String[] removeLastElement(String[] arr) {
        String[] anotherArray = new String[arr.length - 1];
        for (int i = 0; i < anotherArray.length; i++) {
            anotherArray[i] = arr[i];
        }
        return anotherArray;
    }



    public static void main(String[] args) {
        cli();
    }
}


