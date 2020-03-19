package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SubCategoriesTest {
    private SubCategories subCategories = new SubCategories();

    @Test
    public void addTest(){
        assert(subCategories.add("test"));
        assert(!subCategories.add("test"));
    }

    @Test
    public void getListTest(){
        subCategories.add("test");
        assert (subCategories.getList().get(0).equals("test"));
        assert (!subCategories.getList().get(0).equals("testFalse"));
    }

    @Test
    public void getTest(){
        subCategories.add("test");
        assertThrows(IllegalArgumentException.class,()-> subCategories.get(-1));
        assertThrows(IllegalArgumentException.class,()-> subCategories.get(1));
        assert (subCategories.get(0).equals("test"));

    }
}
