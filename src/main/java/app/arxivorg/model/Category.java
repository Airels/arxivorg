package app.arxivorg.model;

public enum Category {

    ALL("All", "Toutes"),
    Physics("physics", "Physiques"),
    Mathematics("math", "Mathématiques"),
    Quantitative_Biology("q-bio", "Biologie Quantitative"),
    Computer_Science("cs", "Informatique"),
    Quantitative_Finance("q_fin", "Finance Quantitative"),
    Statistics("stat", "Statistiques"),
    Electrical_Engineering_and_Systems_Science("eess", "Ingénierie Électrique et Sciences Des Systèmes"),
    Economics("econ", "Économie");

    private String name = "";
    private String frName;
    Category(String name, String frName){
        this.name = name;
        this.frName = frName;
    }

    public String getName(){
        return name;
    }

    public String getFrName() {
        return frName;
    }
}
