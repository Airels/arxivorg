package app.arxivorg;

import app.arxivorg.utils.XmlReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class ArxivOrgCliTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    public void testHelp()  {
        final String testString = "help\nexit\n";

        provideInput(testString);

        ArxivOrgCLI.main(new String[0]);

        assert(getOutput().equals(ArxivOrgCLI.firstLine+ArxivOrgCLI.manual));
    }
    @Test
    public void testListbyAuthor(){
        final String testString = "list -a Thomas_Bachlechner 1.atom\nexit\n";

        provideInput(testString);

        ArxivOrgCLI.main(new String[0]);

        assert(getOutput().equals(ArxivOrgCLI.firstLine+ ArxivOrgCLI.articleToString(0, XmlReader.read("1.atom").get(0))));
    }

    @Test
    public void testListbyCategory() {
        final String testString = "list -c cond-mat 1.atom\nexit\n";

        provideInput(testString);

        ArxivOrgCLI.main(new String[0]);

        assert(getOutput().equals(ArxivOrgCLI.firstLine+ ArxivOrgCLI.articleToString(0,XmlReader.read("1.atom").get(4))));
    }

    @Test
    public void testListbyKeyword(){
        final String testString = "list -k ReZero 1.atom\nexit\n";

        provideInput(testString);

        ArxivOrgCLI.main(new String[0]);

        assert(getOutput().equals(ArxivOrgCLI.firstLine+ ArxivOrgCLI.articleToString(0,XmlReader.read("1.atom").get(0))));
    }

    @Test
    public void testListbyPeriod(){
        final String testString = "list -p 2019-03-10,2020-03-09 1.atom\nexit\n";

        provideInput(testString);

        ArxivOrgCLI.main(new String[0]);

        assert(getOutput().equals(ArxivOrgCLI.firstLine+ ArxivOrgCLI.articleToString(0,XmlReader.read("1.atom").get(8))+
                ArxivOrgCLI.articleToString(1,XmlReader.read("1.atom").get(9))));
    }

    @Test
    public void testSelect() {
        final String testString = "list -p 2019-03-10,2020-03-09 1.atom\nselect 1\nexit\n";

        provideInput(testString);

        ArxivOrgCLI.main(new String[0]);

        assert(getOutput().equals(ArxivOrgCLI.firstLine+ ArxivOrgCLI.articleToString(0,XmlReader.read("1.atom").get(8))+
                ArxivOrgCLI.articleToString(1,XmlReader.read("1.atom").get(9))
                +ArxivOrgCLI.articleToString(0,XmlReader.read("1.atom").get(8))));
    }







}
