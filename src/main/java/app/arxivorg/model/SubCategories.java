package app.arxivorg.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Subcategories class containing properties, constructors and method needed for a list of subcategory
 * @author Tom David--Broglio
 */

public class SubCategories {
    private ArrayList<String> subCategories;

    /**
     * Constructor to make an empty list of subcategory
     */

    public SubCategories (){
        this.subCategories = new ArrayList<>();
    }

    /**
     * Constructor to make a list of subcategory
     * @param list          list of String
     */

    public SubCategories (List<String> list){
        this.subCategories = new ArrayList<>();
        for(String s:list){
            this.add(s);
        }
    }

    /**
     * Add a subcategory, return true if added properly,
     * else return false (when category already exist)
     * @param category      String subcategory
     * @return (boolean)
     */

    public boolean add(String category){
        if (!subCategories.contains(category)){
            subCategories.add(category);
            return true;
        }
        return false;
    }

    /**
     * Get the subcategory at the specified index, throw Exception when index is out of bound
     * @param i             int index
     * @return (String)
     */

    public String get(int i){
        if(i>=subCategories.size() || i<0) throw new IllegalArgumentException(("index out bound [ 0 : "+ (subCategories.size()-1) +"] for authors : index ="+ i));
        return subCategories.get(i);
    }

    /**
     * Get the list of subcategory
     * @return (List of String)
     */

    public List<String> getList(){
        return subCategories;
    }


}
