package Utils;

import app.arxivorg.model.Article;
import java.util.ArrayList;

public class SortArticle {

    public static ArrayList<Article> Sortbyauthors (ArrayList<Article> list , String name) {

        ArrayList<Article> result = new ArrayList<>();

        if (list.size() != 1){

            int mid = list.size()/2;

            ArrayList<Article> leftpart = new ArrayList<>();
            ArrayList<Article> rightpart = new ArrayList<>();

            for (int index = 0 ; index < list.size() ; index++){

                if ( index < mid) leftpart.add(list.get(index));

                else rightpart.add(list.get(index));

            }


            ArrayList<Article> resultleft = Sortbyauthors(leftpart , name);
            ArrayList<Article> resultright = Sortbyauthors(rightpart, name);

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


    public static void main(String[] args) {
        ArrayList<Article> authors = XmlReader.read("1.atom");
        ArrayList<Article> test = Sortbyauthors(authors , "Piji Li");


        System.out.println(test.get(0).getTitle());

    }
}
