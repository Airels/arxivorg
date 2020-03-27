package app.arxivorg.model;

import javafx.scene.chart.CategoryAxis;

public enum Category {

    All("All", "Toutes"),
    Physics("physics", "Physiques"),
    Mathematics("math", "Mathématiques"),
    Quantitative_Biology("q-bio", "Biologie Quantitative"),
    Computer_Science("cs", "Informatique"),
    Quantitative_Finance("q-fin", "Finance Quantitative"),
    Statistics("stat", "Statistiques"),
    Electrical_Engineering_and_Systems_Science("eess", "Ingénierie Électrique et Sciences Des Systèmes"),
    Economics("econ", "Économie"),
    Cond_Math("cond-mat", "Mathématique conditionel"),
    Others("others", "Autres");

    private String name = "";
    private String frName;
    Category(String name, String frName){
        this.name = name;
        this.frName = frName;
    }

    public String getName(){
        return name;
    }

    public String toString() {
        return frName;
    }

    public static Category getCategory(String name) throws IllegalArgumentException {
        switch(name){
            case "all":
                return All;
            case "physics":
               return Physics;
            case "math":
                return Mathematics;
            case "q-bio":
                return Quantitative_Biology;
            case "cs":
                return Computer_Science;
            case "q-fin":
                return Quantitative_Finance;
            case "stat":
                return Statistics;
            case "eess":
                return Electrical_Engineering_and_Systems_Science;
            case "econ":
                return Economics;
            case "cond-mat":
                return Cond_Math;
            default:
                return Others;
        }
    }
}
