package app.arxivorg.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Athors class containing a list of author, constructor and methods
 * @author Tom
 */

public class Authors {
    private ArrayList<String> data;

    /**
     * Constructor to create Authors
     * @param input         List of String
     */

    public Authors(List<String> input) {
        if(input.isEmpty()) throw new IllegalArgumentException("there must at least one authors");
        this.data = new ArrayList<>();
        for(String s:input){
            this.add(s);
        }
    }

    /**
     * Get the author at the specified index, throw Exception when index is out of bound
     * @param i             int index
     * @return (String)
     */

    public String get(int i){
        if(i>=data.size() || i<0) throw new IllegalArgumentException(("index out bound [ 0 : "+ (data.size()-1) +"] for authors : index ="+ i));
        return data.get(i);
    }

    /**
     * Get the list of authors
     * @return (List of String)
     */

    public ArrayList<String> getList() {
        return data;
    }

    /**
     * Add an author to the list of author,
     * return true if the author is added,
     * else return false (when the author is already in the list)
     * @param author        String author
     * @return  (boolean)
     */

    public boolean add(String author){
        if(!data.contains(author)){
            data.add(author);
            return true;
        }
        return false;
    }

    /**
     * Get the list of author in a String,
     * separated with comma(s)
     * @return (String)
     */

    public String toString() {
        return String.join(", ", data);
    }
}
