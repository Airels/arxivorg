package Utils;

import app.arxivorg.model.Article;
import app.arxivorg.model.Category;
import app.arxivorg.model.SubCategories;

import java.util.ArrayList;

import static app.arxivorg.model.Category.Computer_Science;

public class SortArticle {

    public static ArrayList<Article> SortbyAuthors(ArrayList<Article> list , String name) {

        ArrayList<Article> result = new ArrayList<>();

        if (list.size() != 1){

            int mid = list.size()/2;

            ArrayList<Article> leftpart = new ArrayList<>();
            ArrayList<Article> rightpart = new ArrayList<>();

            for (int index = 0 ; index < list.size() ; index++){

                if ( index < mid) leftpart.add(list.get(index));

                else rightpart.add(list.get(index));

            }


            ArrayList<Article> resultleft = SortbyAuthors(leftpart , name);
            ArrayList<Article> resultright = SortbyAuthors(rightpart, name);

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


    public static ArrayList<Article> SortbyCategory (ArrayList<Article> list , Category type ) {

        ArrayList<Article> result = new ArrayList<>();

        if (list.size() != 1){

            int mid = list.size()/2;

            ArrayList<Article> leftpart = new ArrayList<>();
            ArrayList<Article> rightpart = new ArrayList<>();

            for (int index = 0 ; index < list.size() ; index++){

                if ( index < mid) leftpart.add(list.get(index));

                else rightpart.add(list.get(index));

            }


            ArrayList<Article> resultleft = SortbyCategory(leftpart , type);
            ArrayList<Article> resultright = SortbyCategory(rightpart, type);

            result.addAll(resultleft);
            result.addAll(resultright);
        }

        else {
                if (type.equals(list.get(0).getCategory())) result.add(list.get(0));
            }

        return result;
    }

    public static ArrayList<Article> SortbySubCategories (ArrayList<Article> list , String sub) {

        ArrayList<Article> result = new ArrayList<>();

        if (list.size() != 1){

            int mid = list.size()/2;

            ArrayList<Article> leftpart = new ArrayList<>();
            ArrayList<Article> rightpart = new ArrayList<>();

            for (int index = 0 ; index < list.size() ; index++){

                if ( index < mid) leftpart.add(list.get(index));

                else rightpart.add(list.get(index));

            }


            ArrayList<Article> resultleft = SortbySubCategories(leftpart , sub);
            ArrayList<Article> resultright = SortbySubCategories(rightpart, sub);

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




    public static void main(String[] args) {
        ArrayList<Article> authors = XmlReader.read("1.atom");
        ArrayList<Article> test = SortbySubCategories(authors , "cs.LG");



        for (int index = 0 ; index < test.size(); index ++) {
            System.out.println(test.get(index).getTitle());
        }
    }
}
