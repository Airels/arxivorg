package app.arxivorg.model;

import org.junit.jupiter.api.Test;

public class SubCategoriesTest {
    private SubCategories subCategories = new SubCategories();

    @Test
    public void addTest(){
        assert(subCategories.add("test"));
        assert(!subCategories.add("test"));
    }
}
