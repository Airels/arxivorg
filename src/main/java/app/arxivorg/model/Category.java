package app.arxivorg.model;

import javafx.scene.chart.CategoryAxis;

public enum Category {

    All("All", "Toutes"),
    Astrophysics ("astro-ph", "astrophysique"),
    Physics("physics", "Physiques"),
    Mathematics("math", "Mathématiques"),
    Mathematical_Physics("math-ph", "Physique Matématique"),
    Quantitative_Biology("q-bio", "Biologie Quantitative"),
    Computer_Science("cs", "Informatique"),
    Quantitative_Finance("q-fin", "Finance Quantitative"),
    Statistics("stat", "Statistiques"),
    Electrical_Engineering_and_Systems_Science("eess", "Ingénierie Électrique et Sciences Des Systèmes"),
    Economics("econ", "Économie"),
    General_Relativity_and_Quantum_Cosmology("gr-qp", "Relativité géneral et Cosmologie Quantique"),
    High_Energy_Physics("hep","Physique de haute énergie"),
    Cellular_Automata("nlin", "Automate cellulaire"),
    Nuclear("nucl", "Nucléaire"),
    Cond_Math("cond-mat", "Physique des solides"),
    Quantum_Physics("quant-ph", "Physique quantique"),
    CMP_LG("cmp-lg","cpm-lg");

    private String name, frName;

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
            case "astro-ph":
                return Astrophysics;
            case "math":
                return Mathematics;
            case "math-ph":
                return Mathematical_Physics;
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
            case "gr-qp":
                return General_Relativity_and_Quantum_Cosmology;
            case "hep":
                return High_Energy_Physics;
            case "nlin":
                return Cellular_Automata;
            case "nucl":
                return Nuclear;
            case "cond-mat":
                return Cond_Math;
            case "quant-ph":
                return Quantum_Physics;
            case "cmp-lg":
                return CMP_LG;
            default:
                throw new IllegalArgumentException("Unknown category '" + name + "' !");
        }
    }
}
