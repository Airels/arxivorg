package utils;

import app.arxivorg.model.Article;
import app.arxivorg.model.Category;

import java.time.LocalDate;
import java.util.*;

import static app.arxivorg.model.Category.All;

public class SortArticle {

    public static ArrayList<Article> byAuthors(ArrayList<Article> list , String name) {

        if (list.isEmpty()) return list;

        if (name.equals("") || name.equals(" ")) return list;

        ArrayList<Article> result = new ArrayList<>();

        if (list.size() != 1){

            int mid = list.size()/2;

            ArrayList<Article> leftPart = new ArrayList<>();
            ArrayList<Article> rightPart = new ArrayList<>();

            for (int index = 0 ; index < list.size() ; index++){

                if ( index < mid) leftPart.add(list.get(index));

                else rightPart.add(list.get(index));

            }

            ArrayList<Article> resultLeft = byAuthors(leftPart , name);
            ArrayList<Article> resultRight = byAuthors(rightPart, name);

            result.addAll(resultLeft);
            result.addAll(resultRight);
        }

        else {
            for (int index2 = 0 ; index2 < list.get(0).getAuthors().getList().size(); index2 ++){
                if (name.toLowerCase().equals(list.get(0).getAuthors().getList().get(index2).toLowerCase())) result.add(list.get(0));
            }
        }

        return result;
    }


    public static ArrayList<Article> byCategory (ArrayList<Article> list , Category type ) {

        if (list.isEmpty()) return list;


        if (type == All)
            return list;

        ArrayList<Article> result = new ArrayList<>();

        if (list.size() != 1){

            int mid = list.size()/2;

            ArrayList<Article> leftPart = new ArrayList<>();
            ArrayList<Article> rightPart = new ArrayList<>();

            for (int index = 0 ; index < list.size() ; index++){

                if ( index < mid) leftPart.add(list.get(index));

                else rightPart.add(list.get(index));

            }


            ArrayList<Article> resultLeft = byCategory(leftPart , type);
            ArrayList<Article> resultRight = byCategory(rightPart, type);

            result.addAll(resultLeft);
            result.addAll(resultRight);
        }

        else {
                if (type.equals(list.get(0).getCategory())) result.add(list.get(0));
            }

        return result;
    }

    public static ArrayList<Article> byKeyword (ArrayList<Article> list , String sub) {

        if (list.isEmpty()) return list;


        ArrayList<Article> result = new ArrayList<>();

        if (list.size() != 1){

            int mid = list.size()/2;

            ArrayList<Article> leftPart = new ArrayList<>();
            ArrayList<Article> rightPart = new ArrayList<>();

            for (int index = 0 ; index < list.size() ; index++){

                if ( index < mid) leftPart.add(list.get(index));

                else rightPart.add(list.get(index));

            }


            ArrayList<Article> resultLeft = byKeyword(leftPart , sub);
            ArrayList<Article> resultRight = byKeyword(rightPart, sub);

            result.addAll(resultLeft);
            result.addAll(resultRight);
        }

        else {
            if (list.get(0).getTitle().contains(sub)) result.add(list.get(0));

            else if (list.get(0).getContent().contains(sub)) result.add(list.get(0));

            else {
                for (int index2 = 0 ; index2 < list.get(0).getSubCategories().getList().size(); index2 ++){
                if (sub.equals(list.get(0).getSubCategories().getList().get(index2))) result.add(list.get(0));
                }
            }
        }

        return result;
    }

    public static ArrayList<Article> byDate(ArrayList<Article> list , LocalDate dateMin , LocalDate dateMax ) {

        if (list.isEmpty()) return list;


        ArrayList<Article> result = new ArrayList<>();

        if (list.size() != 1){

            int mid = list.size()/2;

            ArrayList<Article> leftPart = new ArrayList<>();
            ArrayList<Article> rightPart = new ArrayList<>();

            for (int index = 0 ; index < list.size() ; index++){

                if ( index < mid) leftPart.add(list.get(index));

                else rightPart.add(list.get(index));

            }


            ArrayList<Article> resultLeft = byDate(leftPart , dateMin, dateMax);
            ArrayList<Article> resultRight = byDate(rightPart, dateMin , dateMax);

            result.addAll(resultLeft);
            result.addAll(resultRight);
        }



        else {
            LocalDate dateToCompare = list.get(0).getDate();

            if (dateMin.compareTo(dateToCompare) <= 0 && dateMax.compareTo(dateToCompare) >= 0) result.add(list.get(0));
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

        if (list.isEmpty()) return list;

        list.sort(Comparator.comparing(Article::getTitle));
        return list;
    }

}
