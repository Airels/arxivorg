package app.arxivorg;

import app.arxivorg.model.Article;
import app.arxivorg.model.Authors;
import app.arxivorg.model.Category;
import app.arxivorg.utils.SortArticle;
import app.arxivorg.utils.XmlReader;
import app.arxivorg.view.PDFDownloader;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * ArxivorgCli is a simple cli to search , select and download some articles
 * from an atom file or from arxivOrg directly
 * @author Tom
 */

public class ArxivOrgCLI {
    public static String firstLine = "ArxivOrgCli : faite une recherce ou tapez help pour de l'aide , tapez exit pour quitter\n";

    public static String manual = "CLI arxivorg\n" +
            "\n" +
            "list [OPTIONS]... FILE                  list the articles in a FILE of from Arxiv org with keyword 'arxiv'\n" +

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

    /**
     * Build a String containing the requested content for one article
     * @param index         index of this article
     * @param article       article to be utilized
     * @return (String)
     */

    public static String articleToString(int index,Article article){
        return index+1+" : "+article.getTitle()+"\n"+article.getAuthors().toString()+"\n";
    }

    /**
     * Print an array of article, as requested by the client
     * @param articles      arrayList of articles
     */

    public static void printArticles(ArrayList<Article> articles){
        for(int i=0;i<articles.size();i++){
            Article article = articles.get(i);
            System.out.print(articleToString(i,article));
        }
    }

    /**
     * Download articles in an Array of article to a specified path
     * @param articles      arrayList of article to download
     * @param path          Full path to the download folder
     */
    static void downloadArticles(ArrayList<Article> articles,String path){
        File file = new File(path);
        for (Article a:articles){
            PDFDownloader.downloadFile(a,file);
        }

    }

    /**
     * Select the articles shown from a call to printArticles
     * Call printArticles on the selected articles to confirm the selection
     * @param arguments     array of arguments
     */

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

    /**
     * Tell if the number or arguments in the input is correct
     * @param arguments         input arguments
     * @return (boolean)
     */
    static boolean numberOfArgument_isCorrect(String[] arguments){
        int boundEnd = arguments.length-2;
        /*
        int boundStart = 1;
        if(boundEnd<boundStart){
            System.out.println("no arguments, all articles :");
        }

         */
        if(boundEnd%2 !=0){
            System.out.println("le nombre d'argument doit etre pair : "+boundEnd + 2);
            return false;
        }
        else return true;
    }

    /**
     * process the filter contained in the request,
     * in order to modify the local array of article
     * @param articles          arrayList of articles
     * @param arguments         arguments from the request
     * @param isApiRequest      boolean to tell if it's an Api request or not
     * @return (ArrayList of Article)
     */



    static ArrayList<Article> processFilters(ArrayList<Article> articles,String[] arguments,boolean isApiRequest) {
        int boundEnd = arguments.length-2;
        int boundStart = 1;
        ArrayList<Article> result;
        result = new ArrayList<>(articles);
        if(boundEnd<boundStart){
            return articles;
        }

        for(int i=boundStart;i<boundEnd;i=i+2){
            String firstArgument = arguments[i];
            String secondArgument = arguments[i+1];
            switch (firstArgument){
                case "-p":
                    result = isApiRequest ? SortArticle.All() : result;
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

                    if(isApiRequest){
                        List<String> authors1 = new ArrayList<>();

                        for (String s : authors) {
                            String author = s.replace("_", " ");
                            authors1.add(author);
                        }
                        result = SortArticle.byAuthors(new Authors(authors1));
                    }
                    else {
                        for (String s : authors) {
                            String author = s.replace("_", " ");
                            result = SortArticle.byAuthors(result,author);
                        }
                    }




                    break;
                case "-k":

                    String[] keywords = secondArgument.split(",");
                    for (String s : keywords) {
                        String keyword = s.replace("_", " ");
                        result = isApiRequest ? SortArticle.byKeyword(keyword) : SortArticle.byKeyword(result, keyword);
                    }
                    break;
                case "-c":
                    Category c = Category.All;
                    try {
                        c = Category.getCategory(secondArgument);
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("categories indefini : "+secondArgument);
                        result = new ArrayList<>();
                    }

                    //Category.valueOf(secondArgument)
                    result = isApiRequest ? SortArticle.byCategory(c) : SortArticle.byCategory(result,c);
                    break;
                default:
                    System.out.println("Invalid argument : "+firstArgument);
            }
        }
        return result;

    }

    /**
     * Start the cli , process the first argument and uses or not other methods
     * use a loop to always ask for input, can be exited with 'exit'
     */

    static void cli () {
        System.out.print(firstLine);
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
                    if(numberOfArgument_isCorrect(arguments)){
                        if(source.equals("arxiv")) {
                            articles = processFilters(articles,arguments,true);
                        }
                        else {
                            articles = XmlReader.read(source);
                            articles = processFilters(articles,arguments,false);
                        }
                        printArticles(articles);
                    }

                    break;
                case "download":
                    String filePath = arguments[arguments.length - 1];
                    if(articles.isEmpty()){
                        source = arguments[arguments.length - 2];
                        arguments = removeLastElement(arguments);
                        if(numberOfArgument_isCorrect(arguments)) {
                            if (source.equals("arxiv")) {
                                articles = processFilters(articles,arguments,true);
                            } else {
                                articles = XmlReader.read(source);
                                System.out.println("fichier introuvable : "+source);
                                articles = processFilters(articles, arguments,false);

                            }
                            downloadArticles(articles, filePath);
                        }
                    }
                    else {
                        downloadArticles(articles,filePath);
                    }


                    break;

                case "select":
                    if(articles.isEmpty()){
                        System.out.println("make a search first with list");
                    }
                    else {
                        select(arguments);
                        printArticles(articles);
                    }
                    break;


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
        }
    }

    /**
     * Remove the last argument in an array of argument
     * @param arr       array of articles
     * @return array of String
     */

    public static String[] removeLastElement(String[] arr) {
        String[] anotherArray = new String[arr.length - 1];
        System.arraycopy(arr, 0, anotherArray, 0, anotherArray.length);
        return anotherArray;
    }



    public static void main(String[] args) {
        cli();
    }
}


