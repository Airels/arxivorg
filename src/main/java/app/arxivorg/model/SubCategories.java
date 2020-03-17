package app.arxivorg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SubCategories {
    private ArrayList<String> subCategories;

    public SubCategories (){
        this.subCategories = new ArrayList<>();
    }

    public SubCategories (List<String> list){
        this.subCategories = new ArrayList<>();
        for(String s:list){
            this.add(s);
        }
    }

    public boolean add(String category){
        if (!subCategories.contains(category)){
            subCategories.add(category);
            return true;
        }
        return false;
    }

    public String get(int i){
        if(i>subCategories.size()-1 || i<0) throw new IllegalArgumentException(("index out bound [ 0 : "+ (subCategories.size()-1) +"] for authors : index ="+ i));
        return subCategories.get(i);
    }

    public List<String> getList(){
        return subCategories;
    }


}
