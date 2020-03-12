package app.arxivorg.model;

import java.util.ArrayList;
import java.util.List;

public class Authors {
    private ArrayList<String> data;

    public Authors(List<String> input) {
        if(input.isEmpty()) throw new IllegalArgumentException("there must at least one authors");
        this.data = new ArrayList<>();
        for(String s:input){
            this.add(s);
        }
    }

    public ArrayList<String> get() {
        return data;
    }

    public boolean add(String author){
        if(!data.contains(author)){
            data.add(author);
            return true;
        }
        return false;
    }

    public String toString() {
        return String.join(", ", data);
    }
}
