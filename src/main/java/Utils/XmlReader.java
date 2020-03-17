package Utils;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import app.arxivorg.model.Article;
import app.arxivorg.model.Authors;
import app.arxivorg.model.Category;
import app.arxivorg.model.SubCategories;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class XmlReader
{

    public static ArrayList<Article> Reader (File file)
    {
        ArrayList<Article> articles = new ArrayList<Article>();

        try {
            File inputFile = new File(String.valueOf(file));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("entry");
            NodeList secondlist = doc.getElementsByTagName("category");





            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Node secondNode = secondlist.item(temp);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element entryNode = (Element) nNode;
                    Element secondcategoryNode = (Element) secondNode;

                    int lenghtauthor = entryNode.getElementsByTagName("author").getLength();
                    ArrayList<String> templist = new ArrayList<String>();

                    for (int index = 0 ; index > lenghtauthor ; index ++ ){
                        templist.add(entryNode.getElementsByTagName("name").item(index).getTextContent());

                    }


                    Authors tempauthors = new Authors(templist);

                    String temptitle = entryNode.getElementsByTagName("title").item(0).getTextContent();

                    String tempcomtent = entryNode.getElementsByTagName("summary").item(0).getTextContent();

                    ArrayList subcategories = new ArrayList<String>();

                    subcategories.add(secondcategoryNode
                            .getAttribute("term"));

                    SubCategories tempsub = new SubCategories(subcategories);



                    Category primalCategory = Category.Computer_Science;


                    Article tempArticle = new Article(temptitle, tempauthors, tempcomtent, primalCategory, tempsub );
                    articles.add(tempArticle);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return articles;

    }
}
