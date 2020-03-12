package app.arxivorg.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class SubCategories {
    private ArrayList<String> subCategories;
    public boolean add(String categories){
        if (!subCategories.contains(categories)){
            subCategories.add(categories);
            return true;
        }
        return false;
    }

}
