package app.arxivorg.utils;

import app.arxivorg.model.Article;
import app.arxivorg.model.Category;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import static app.arxivorg.model.Category.*;

public class ArticlesStatistics {
    /**
     * Classe to send back statistic
     * @author Aymeric Sibiak
     */

    /**
     * Return the number of article by categories
     * @param articles List of articles
     * @return Integer tab of the number of articles by a Category
     */

    public static int[] statisticCategories (ArrayList<Article> articles) {

        if (articles.size() == 0) {
            int[] empty = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            return empty;
        }

        int nbofcategory = 17;

        Category[] tabcatgeory = {Astrophysics, Physics ,Mathematics ,Mathematical_Physics,Quantitative_Biology,Computer_Science,Quantitative_Finance,Statistics,
                Electrical_Engineering_and_Systems_Science, Economics, General_Relativity_and_Quantum_Cosmology, High_Energy_Physics, Cellular_Automata, Nuclear, Cond_Math, Quantum_Physics, CMP_LG};

        int[] nbOfArticleByCategory = new int[nbofcategory];

        for (int index = 0 ; index < nbofcategory ; index ++) {
                int tempCount = 0;
            for (int indexOfArticle = 0 ; indexOfArticle < articles.size() ; indexOfArticle++) {
                if (articles.get(indexOfArticle).getCategory().getName().equals(tabcatgeory[index].getName())) tempCount++;
            }
            nbOfArticleByCategory[index] = tempCount;
        }

        return nbOfArticleByCategory;
    }

    /**
     * Statitic of the number of article per day for a List of article
     * @param articles List of article you want to know the number of article by day 
     * @return number of article by day for the List of article given
     */
    public static int statisticDate (ArrayList<Article> articles) {
        if (articles.size() == 0) return 0;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dtf = dtf.withLocale(Locale.UK);
        LocalDate dateMin = LocalDate.parse("4000-12-24");
        LocalDate dateMax = LocalDate.parse("0000-12-24");

        for (int index = 0 ; index < articles.size(); index++){
            if (articles.get(index).getDate().isAfter(dateMax)) dateMax = articles.get(index).getDate();
            if (articles.get(index).getDate().isBefore(dateMin)) dateMin = articles.get(index).getDate();
        }
        int trucDesEnfer = ((dateMax.getYear()) - (dateMin.getYear()))*365 + (dateMax.getMonthValue()-dateMin.getMonthValue())*30 + (dateMax.getDayOfMonth() - dateMin.getDayOfMonth());


        int medium = articles.size()/trucDesEnfer;
        if (medium == 0) {
            System.out.println("il y as moins d'un article par jour pour la liste donner");
        }
        return  medium;


    }

    /**
     *Give you a String whit the 5 top authors for a certain List of Article
     * @param articles List of Article you want to check
     * @return Return a String whit the 5 top authors name
     * https://www.youtube.com/watch?v=YPN0qhSyWy8
     */
    
    public static ArrayList<String> statisticAuthors (ArrayList<Article> articles){
        ArrayList<String> authors = new ArrayList<>();

        for (int index = 0 ; index < articles.size(); index++){
            int tempAuthors = articles.get(index).getAuthors().getList().size();
            for (int indexAuthor = 0 ; indexAuthor < tempAuthors; indexAuthor++){
                if (! authors.contains(articles.get(index).getAuthors().get(indexAuthor))) authors.add(articles.get(index).getAuthors().get(indexAuthor));
            }
        }
        int[] nbOfCountForAuthors = new int[authors.size()];

        for (int index = 0 ; index < authors.size(); index++) {
            int tempcount = 0;
            for (int indexArticle = 0; indexArticle < articles.size(); indexArticle++) {
                if (articles.get(indexArticle).getAuthors().getList().contains(authors.get(index))) tempcount++;

                nbOfCountForAuthors[index] = tempcount;
            }

        }
        int indexOfFirst = 0;
        int nbCountFrist = 0;
        int indexOfSecond = 0;
        int nbCountSecond = 0;
        int indexOfThird = 0;
        int nbCountThird = 0;
        int indexOfFourth = 0;
        int nbCountFourth = 0;
        int indexOFFitth = 0;
        int nbCountFive = 0;

        for (int index = 0 ; index < authors.size(); index++){

            if (nbOfCountForAuthors[index] > nbCountFrist){
                nbCountFive = nbCountFourth;
                indexOFFitth = indexOfFourth;
                nbCountFourth = nbCountThird;
                indexOfFourth = indexOfThird;
                nbCountThird = nbCountSecond;
                indexOfThird= indexOfSecond;
                nbCountSecond = nbCountFrist;
                indexOfSecond = indexOfFirst;
                nbCountFrist = nbOfCountForAuthors[index];
                indexOfFirst = index;
            }
            else if (nbOfCountForAuthors[index] > nbCountSecond){
                nbCountFive = nbCountFourth;
                indexOFFitth = indexOfFourth;
                nbCountFourth = nbCountThird;
                indexOfFourth = indexOfThird;
                nbCountThird = nbCountSecond;
                indexOfThird= indexOfSecond;
                nbCountSecond = nbOfCountForAuthors[index];
                indexOfSecond = index;
            }
            else if (nbOfCountForAuthors[index] > nbCountThird){
                nbCountFive = nbCountFourth;
                indexOFFitth = indexOfFourth;
                nbCountFourth = nbCountThird;
                indexOfFourth = indexOfThird;
                nbCountThird = nbOfCountForAuthors[index];
                indexOfThird = index;
            }
            else if (nbOfCountForAuthors[index] > nbCountFourth){
                nbCountFive = nbCountFourth;
                indexOFFitth = indexOfFourth;
                nbCountFourth = nbOfCountForAuthors[index];
                indexOfFourth = index;
            }
            else if (nbOfCountForAuthors[index] > nbCountFive){
                nbCountFive = nbOfCountForAuthors[index];
                indexOFFitth = index;
            }

        }

        ArrayList<String> winners = new ArrayList<>();
        winners.add(authors.get(indexOfFirst));
        winners.add(authors.get(indexOfSecond));
        winners.add(authors.get(indexOfThird));
        winners.add(authors.get(indexOfFourth));
        winners.add(authors.get(indexOFFitth));

        return winners;


    }

    /**
     * Find the most used String from a given list into a given list of Article
     * @param articles List of Articles
     * @param testStrings List of String
     * @return The most used String into Article List given
     */

    public static String statisticOnTexte (ArrayList<Article> articles , ArrayList<String> testStrings){
        int  max = 0;
        int lastIndex = 0;

        for (int indexofstring = 0 ; indexofstring < testStrings.size() ; indexofstring++){
            int count = 0;
            int stringlenght = testStrings.get(indexofstring).length();
            for (int indexofarticles = 0 ; indexofarticles < articles.size() ; indexofarticles++){
                String test = articles.get(indexofarticles).getContent() + articles.get(indexofarticles).getTitle();
                for (int indexofchar = 0 ; indexofchar < test.length() ; indexofchar++){
                    if(indexofchar+stringlenght > test.length()) break;
                    if (test.substring(indexofchar, indexofchar+stringlenght).equals(testStrings.get(indexofstring))){
                        indexofchar += stringlenght-1;
                        count++;
                    }
                }



            }

            if (count > max){
                max = count;
                lastIndex = indexofstring;
            }
        }

        return testStrings.get(lastIndex);


    }


}
