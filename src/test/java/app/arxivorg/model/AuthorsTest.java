package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorsTest {

    private Authors authors = new Authors(Arrays.asList("Martin Dupont", "Marie Martin", "François Cordonnier"));
    private List<String> getTest = Arrays.asList("Martin Dupont", "Marie Martin", "François Cordonnier");
    private List<String> getTest2 = Arrays.asList("Martin Dupot", "Marie Martin", "François Cordonnier");

    @Test
    public void testToString() {
        String expected = "Martin Dupont, Marie Martin, François Cordonnier";
        assertEquals(expected, authors.toString());
    }
    @Test
    public void testAdd(){
        assert (authors.add("test"));
        assert (!authors.add("Martin Dupont"));
    }
    @Test
    public void TestGetList(){
        assert (authors.getList().equals(getTest));
        assert (!authors.getList().equals(getTest2));
    }

    @Test
    public void TestGet(){
        assertThrows(IllegalArgumentException.class,()-> authors.get(3));
        assertThrows(IllegalArgumentException.class,()-> authors.get(-1));
        assert (authors.get(0).equals("Martin Dupont"));
    }

    @Test
    public  void TestConstructor(){
        assertThrows(IllegalArgumentException.class, ()-> new Authors(new ArrayList<>()));
    }




}

