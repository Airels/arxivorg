package app.arxivorg.model;

public enum Category {

    ALL("ALL"),
    Physics("physics"),
    Mathematics("math"),
    Quantitative_Biology("q-bio"),
    Computer_Science("cs"),
    Quantitative_Finance("q_fin"),
    Statistics("stat"),
    Electrical_Engineering_and_Systems_Science("eess"),
    Economics("econ"),
    quant_ph("quant-ph"),
    hep_th("hep-th");

    private String name = "";
    Category(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
