package app.arxivorg.model;

/**
 * enumeration of all categories used for all sort of articles
 * @author Tom David--Broglio , VIZCAINO Yohan , Sibiak Aymeric
 */

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

    /**
     * Constructor for a category
     * @param name          String name(english)
     * @param frName        String name(French)
     */

    Category(String name, String frName){
        this.name = name;
        this.frName = frName;
    }

    /**
     * Get the name(english)
     * @return (String)
     */

    public String getName(){
        return name;
    }

    /**
     * Get the name(French)
     * @return (String)
     */

    public String toString() {
        return frName;
    }

    /**
     * Get a category from it's name
     * @param name                          String name
     * @return                              (Category)
     * @throws IllegalArgumentException     throws Exception if the name does not exist
     */

    public static Category getCategory(String name) throws IllegalArgumentException {
        for (Category category : Category.values()) {
            if (name.equals(category.getName()))
                return category;
        }

        throw new IllegalArgumentException("Unknown category '" + name + "' !");
    }
}
