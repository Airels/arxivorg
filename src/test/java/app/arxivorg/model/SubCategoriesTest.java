package app.arxivorg.model;

import org.junit.jupiter.api.Test;

public class SubCategoriesTest {
    private SubCategories subCategories = new SubCategories();

    @Test
    public void addTest(){
        assert(subCategories.add("test"));
        assert(!subCategories.add("test"));
    }

    @Test
    public void getTest(){
        subCategories.add("test");
        assert (subCategories.get().get(0).equals("test"));
        assert (!subCategories.get().get(0).equals("testFalse"));
    }
}
