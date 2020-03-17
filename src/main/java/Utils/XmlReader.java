package Utils;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import app.arxivorg.model.Article;
import app.arxivorg.model.Authors;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class XmlReader
{

    public static ArrayList<Article> ArticlefromXml (File file)
    {
        try {
            File inputFile = new File(String.valueOf(file));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("entry");
            ArrayList<Article> articles = new ArrayList<Article>();


            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    int lenghtauthor = eElement.getElementsByTagName("author").getLength();
                    ArrayList<String> templist = new ArrayList<String>();

                    for (int index = 0 ; index > lenghtauthor ; index ++ ){
                        templist.add(eElement.getElementsByTagName("name").item(index).getTextContent());

                    }

                    Authors tempauthors = new Authors(templist);

                    String temptitle = eElement.getElementsByTagName("title").item(0).getTextContent();

                    String tempcomtent = eElement.getElementsByTagName("summary").item(0).getTextContent();




                    System.out.println("First Name : "
                            + eElement
                            .getElementsByTagName("firstname")
                            .item(0)
                            .getTextContent());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
