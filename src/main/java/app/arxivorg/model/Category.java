package app.arxivorg.model;

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
    General_Relativity_and_Quantum_Cosmology("gr-qc", "Relativité géneral et Cosmologie Quantique"),
    High_Energy_Physics("hep","Physique de haute énergie"),
    Cellular_Automata("nlin", "Automate cellulaire"),
    Nuclear("nucl", "Nucléaire"),
    Cond_Math("cond-mat", "Physique des solides"),
    Quantum_Physics("quant-ph", "Physique quantique"),
    CMP_LG("cmp-lg","cpm-lg"),
    Favourites("fav", "Favoris");

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
        for (Category category : Category.values()) {
            if (name.equals(category.getName()))
                return category;
        }

        throw new IllegalArgumentException("Unknown category '" + name + "' !");
    }
}
