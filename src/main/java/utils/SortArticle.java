package utils;

import app.arxivorg.model.Article;
import app.arxivorg.model.Authors;
import app.arxivorg.model.Category;

import java.time.LocalDate;
import java.util.*;

import static app.arxivorg.model.Category.*;

public class SortArticle {

   public static ArrayList<String> getAllSubCategories (Category category) {

       ArrayList<String> subCategories = new ArrayList<>();

       if (category.getName().equals("astro-ph")) {
           subCategories.add(".CO");
           subCategories.add(".EP");
           subCategories.add(".GA");
           subCategories.add(".HE");
           subCategories.add(".IM");
           subCategories.add(".SR");
       }

       if (category.getName().equals("cond-mat")) {
           subCategories.add(".dis-nn");
           subCategories.add(".mes-hall");
           subCategories.add(".mtrl-sci");
           subCategories.add(".other");
           subCategories.add(".quant-gas");
           subCategories.add(".soft");
           subCategories.add(".stat-mech");
           subCategories.add(".str-el");
           subCategories.add(".supr-con");
       }

       if (category.getName().equals("cs")) {
           subCategories.add(".AI");
           subCategories.add(".AR");
           subCategories.add(".CC");
           subCategories.add(".CE");
           subCategories.add(".CG");
           subCategories.add(".CL");
           subCategories.add(".CR");
           subCategories.add(".CV");
           subCategories.add(".CY");
           subCategories.add(".DB");
           subCategories.add(".DC");
           subCategories.add(".DL");
           subCategories.add(".DM");
           subCategories.add(".DS");
           subCategories.add(".ET");
           subCategories.add(".FL");
           subCategories.add(".GL");
           subCategories.add(".GR");
           subCategories.add(".GT");
           subCategories.add(".HC");
           subCategories.add(".IR");
           subCategories.add(".IT");
           subCategories.add(".LG");
           subCategories.add(".LO");
           subCategories.add(".MA");
           subCategories.add(".MM");
           subCategories.add(".MS");
           subCategories.add(".NA");
           subCategories.add(".NE");
           subCategories.add(".NI");
           subCategories.add(".OH");
           subCategories.add(".OS");
           subCategories.add(".PF");
           subCategories.add(".PL");
           subCategories.add(".RO");
           subCategories.add(".SC");
           subCategories.add(".SD");
           subCategories.add(".SE");
           subCategories.add(".SI");
           subCategories.add(".SY");
       }
       if (category.getName().equals("econ")) subCategories.add(".EM");
       if (category.getName().equals("eess")) {
           subCategories.add(".AS");
           subCategories.add(".IV");
           subCategories.add(".SP");
       }
       if (category.getName().equals("hep")){
           subCategories.add("-ex");
           subCategories.add("-lat");
           subCategories.add("-ph");
           subCategories.add("-th");
        }
       if (category.getName().equals("math")) {
           subCategories.add(".AC");
           subCategories.add(".AG");
           subCategories.add(".AP");
           subCategories.add(".AT");
           subCategories.add(".CA");
           subCategories.add(".CO");
           subCategories.add(".CT");
           subCategories.add(".CV");
           subCategories.add(".DG");
           subCategories.add(".DS");
           subCategories.add(".FA");
           subCategories.add(".GM");
           subCategories.add(".GN");
           subCategories.add(".GR");
           subCategories.add(".GT");
           subCategories.add(".HO");
           subCategories.add(".IT");
           subCategories.add(".KT");
           subCategories.add(".LO");
           subCategories.add(".MG");
           subCategories.add(".MP");
           subCategories.add(".NA");
           subCategories.add(".NT");
           subCategories.add(".OA");
           subCategories.add(".OC");
           subCategories.add(".PR");
           subCategories.add(".QA");
           subCategories.add(".RA");
           subCategories.add(".RT");
           subCategories.add(".SG");
           subCategories.add(".SP");
           subCategories.add(".ST");
       }
       if (category.getName().equals("nlin")) {
           subCategories.add(".AO");
           subCategories.add(".CD");
           subCategories.add(".CG");
           subCategories.add(".PS");
           subCategories.add(".SI");
       }
       if (category.getName().equals("nucl")) {
           subCategories.add("-ex");
           subCategories.add("-th");
       }
       if (category.getName().equals("physics")) {
           subCategories.add(".acc-ph");
           subCategories.add(".ao-ph");
           subCategories.add(".app-ph");
           subCategories.add(".atm-clus");
           subCategories.add(".atom-ph");
           subCategories.add(".bio-ph");
           subCategories.add(".chem-ph");
           subCategories.add(".class-ph");
           subCategories.add(".comp-ph");
           subCategories.add(".data-an");
           subCategories.add(".ed-ph");
           subCategories.add(".flu-dyn");
           subCategories.add(".gen-ph");
           subCategories.add(".geo-ph");
           subCategories.add(".hist-ph");
           subCategories.add(".ins-det");
           subCategories.add(".med-ph");
           subCategories.add(".optics");
           subCategories.add(".plasm-ph");
           subCategories.add(".pop-ph");
           subCategories.add(".soc-ph");
           subCategories.add(".space-ph");
       }
       if (category.getName().equals("q-bio")) {
           subCategories.add(".BM");
           subCategories.add(".CB");
           subCategories.add(".GN");
           subCategories.add(".MN");
           subCategories.add(".NC");
           subCategories.add(".OT");
           subCategories.add(".PE");
           subCategories.add(".QM");
           subCategories.add(".SC");
           subCategories.add(".TO");
       }
       if (category.getName().equals("q-fin")) {
           subCategories.add(".CP");
           subCategories.add(".EC");
           subCategories.add(".GN");
           subCategories.add(".MF");
           subCategories.add(".PM");
           subCategories.add(".PR");
           subCategories.add(".RM");
           subCategories.add(".ST");
           subCategories.add(".TR");
       }
       if (category.getName().equals("stat")) {
           subCategories.add(".AP");
           subCategories.add(".CO");
           subCategories.add(".ME");
           subCategories.add(".ML");
           subCategories.add(".OT");
           subCategories.add(".TH");
       }

       return subCategories;
    }

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

    public static ArrayList<Article> byAuthors ( Authors searchsubjet){
        String tempAuthors = searchsubjet.toString();
        ArrayList<Article> articlesByAuthor = APICall.requestApi("au", tempAuthors);

        return articlesByAuthor;
    }

    public static ArrayList<Article> byTitle ( String searchsubjet){
        ArrayList<Article> articlesByTitle = APICall.requestApi("ti", searchsubjet);

        return articlesByTitle;

    }

    public static ArrayList<Article> byCategory (Category searchcategory){
        ArrayList<String> subcat = getAllSubCategories(searchcategory);
        System.out.println(subcat.size());
        ArrayList<Article> articlesByCategory = new ArrayList<>();

        if (searchcategory.getName().equals("gr-qp") || searchcategory.getName().equals("math-ph") ||
                searchcategory.getName().equals("quant-ph") || searchcategory.getName().equals("cmp-lg")) {
            articlesByCategory = APICall.requestApi("cat", searchcategory.getName());
        }

        else for (int index = 0 ; index < subcat.size(); index++){
            String looking = searchcategory.getName()+subcat.get(index);
            System.out.println(looking);
            articlesByCategory.addAll(APICall.requestApi("cat", looking ));
            System.out.println(articlesByCategory.size()+ "\n");
        }

        return articlesByCategory;
    }

    public static ArrayList<Article> byAuthorsFromTo ( Authors searchsubjet, int start, int to){
        String tempAuthors = searchsubjet.toString();
        ArrayList<Article> articlesByAuthor = APICall.requestApiFromTo("au", tempAuthors, start, to);

        return articlesByAuthor;
    }

    public static ArrayList<Article> byTitleFromTo ( String searchsubjet, int start, int to){
        ArrayList<Article> articlesByTitle = APICall.requestApiFromTo("ti", searchsubjet, start, to);

        return articlesByTitle;

    }

    public static ArrayList<Article> byCategoryFromTo (Category searchcategory, int start , int to){
        String tempcategory = searchcategory.getName();

        ArrayList<Article> articlesByCategory = APICall.requestApiFromTo("cat", tempcategory, start, to);

        return articlesByCategory;
    }

    public static ArrayList<Article> byKeyword (String searchsubjet){
        ArrayList<Article> allArticle = APICall.requestApi("all:",  searchsubjet);

        return allArticle;
    }

    public static ArrayList<Article> byKeywordFromTo (String searchsubjet, int start , int to){
        ArrayList<Article> allArticle = APICall.requestApiFromTo("all:",  searchsubjet, start , to);

        return allArticle;
    }

    public static ArrayList<Article> All (){
        ArrayList<Article> allArticle = APICall.requestApi("all:","" );

        return allArticle;
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
