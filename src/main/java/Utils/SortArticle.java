package Utils;

import app.arxivorg.model.Article;
import app.arxivorg.model.Category;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class SortArticle {

    public static ArrayList<Article> byAuthors(ArrayList<Article> list , String name) {

        if (name.equals(" ")) return list;

        ArrayList<Article> result = new ArrayList<>();

        if (list.size() != 1){

            int mid = list.size()/2;

            ArrayList<Article> leftpart = new ArrayList<>();
            ArrayList<Article> rightpart = new ArrayList<>();

            for (int index = 0 ; index < list.size() ; index++){

                if ( index < mid) leftpart.add(list.get(index));

                else rightpart.add(list.get(index));

            }

            ArrayList<Article> resultleft = byAuthors(leftpart , name);
            ArrayList<Article> resultright = byAuthors(rightpart, name);

            result.addAll(resultleft);
            result.addAll(resultright);
        }

        else {
            for (int index2 = 0 ; index2 < list.get(0).getAuthors().getList().size(); index2 ++){
                if (name.equals(list.get(0).getAuthors().getList().get(index2))) result.add(list.get(0));
            }
        }

        return result;
    }


    public static ArrayList<Article> byCategory (ArrayList<Article> list , Category type ) {

        ArrayList<Article> result = new ArrayList<>();

        if (list.size() != 1){

            int mid = list.size()/2;

            ArrayList<Article> leftpart = new ArrayList<>();
            ArrayList<Article> rightpart = new ArrayList<>();

            for (int index = 0 ; index < list.size() ; index++){

                if ( index < mid) leftpart.add(list.get(index));

                else rightpart.add(list.get(index));

            }


            ArrayList<Article> resultleft = byCategory(leftpart , type);
            ArrayList<Article> resultright = byCategory(rightpart, type);

            result.addAll(resultleft);
            result.addAll(resultright);
        }

        else {
                if (type.equals(list.get(0).getCategory())) result.add(list.get(0));
            }

        return result;
    }

    public static ArrayList<Article> bySubCategories (ArrayList<Article> list , String sub) {

        ArrayList<Article> result = new ArrayList<>();

        if (list.size() != 1){

            int mid = list.size()/2;

            ArrayList<Article> leftpart = new ArrayList<>();
            ArrayList<Article> rightpart = new ArrayList<>();

            for (int index = 0 ; index < list.size() ; index++){

                if ( index < mid) leftpart.add(list.get(index));

                else rightpart.add(list.get(index));

            }


            ArrayList<Article> resultleft = bySubCategories(leftpart , sub);
            ArrayList<Article> resultright = bySubCategories(rightpart, sub);

            result.addAll(resultleft);
            result.addAll(resultright);
        }

        else {
            for (int index2 = 0 ; index2 < list.get(0).getSubCategories().getList().size(); index2 ++){
                if (sub.equals(list.get(0).getSubCategories().getList().get(index2))) result.add(list.get(0));
            }
        }

        return result;
    }

    public static ArrayList<Article> byDate(ArrayList<Article> list , LocalDate datemin , LocalDate datemax ) throws ParseException {

        ArrayList<Article> result = new ArrayList<>();

        if (list.size() != 1){

            int mid = list.size()/2;

            ArrayList<Article> leftpart = new ArrayList<>();
            ArrayList<Article> rightpart = new ArrayList<>();

            for (int index = 0 ; index < list.size() ; index++){

                if ( index < mid) leftpart.add(list.get(index));

                else rightpart.add(list.get(index));

            }


            ArrayList<Article> resultleft = byDate(leftpart , datemin, datemax);
            ArrayList<Article> resultright = byDate(rightpart, datemin , datemax);

            result.addAll(resultleft);
            result.addAll(resultright);
        }



        else {
            LocalDate datetocompar = list.get(0).getDate();

            if (datemin.compareTo(datetocompar) >= 0 && datemax.compareTo(datetocompar) <= 0) result.add(list.get(0));
        }

        return result;
    }

    private static int Compare ( String title1, String title2, int nb)
    {
        if (title1.equals(title2)) return 0 ;

        if (title1.charAt(nb) == title2.charAt(nb)) return Compare(title1,title2,nb+1);

        return title1.charAt(nb) - title2.charAt(nb);

    }

    public static ArrayList<Article> byTitle (ArrayList<Article> list) {

        list.sort(Comparator.comparing(Article::getTitle));
        return list;
    }




    public static void main(String[] args) {
        ArrayList<Article> authors = XmlReader.read("1.atom");
        ArrayList<Article> test = byAuthors(authors,"");



        for (int index = 0 ; index < test.size(); index ++) {
            System.out.println(test.get(index).getTitle());
            System.out.println(" ");
        }
    }
}
