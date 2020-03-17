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

    public static void main (String[] args)
    {
        try {
            File inputFile = new File("1.atom");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("entry");
            NodeList primarylist = doc.getElementsByTagName("arxiv:primary_category");
            NodeList secondlist = doc.getElementsByTagName("category");


            //ArrayList<Article> articles = new ArrayList<Article>();


            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Node primaryNode = primarylist.item(temp);
                Node secondNode = primarylist.item(temp);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Element aElement = (Element) primaryNode;
                    Element yElement = (Element) secondNode;

                    int lenghtauthor = eElement.getElementsByTagName("author").getLength();
                    //ArrayList<String> templist = new ArrayList<String>();

                    for (int index = 0 ; index > lenghtauthor ; index ++ ){
                        //templist.add(eElement.getElementsByTagName("name").item(index).getTextContent());

                    }

                    //Authors tempauthors = new Authors(templist);

                    //String temptitle = eElement.getElementsByTagName("title").item(0).getTextContent();

                    //String tempcomtent = eElement.getElementsByTagName("summary").item(0).getTextContent();




                    System.out.println("test = "
                            + aElement
                            .getAttribute("term"));

                    System.out.println("test second  = "
                            + yElement
                            .getAttribute("term"));

                    System.out.println(" ");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
