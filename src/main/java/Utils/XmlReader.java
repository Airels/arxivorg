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
            NodeList secondList = doc.getElementsByTagName("category");





            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Node secondNode = secondList.item(temp);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element entryNode = (Element) nNode;
                    Element secondCategoryNode = (Element) secondNode;

                    int lengthAuthor = entryNode.getElementsByTagName("author").getLength();
                    ArrayList<String> tempList = new ArrayList<String>();

                    for (int index = 0 ; index > lengthAuthor ; index ++ ){
                        tempList.add(entryNode.getElementsByTagName("name").item(index).getTextContent());

                    }


                    Authors tempAuthors = new Authors(tempList);

                    String tempTitle = entryNode.getElementsByTagName("title").item(0).getTextContent();

                    String tempContent = entryNode.getElementsByTagName("summary").item(0).getTextContent();

                    ArrayList subcategories = new ArrayList<String>();

                    subcategories.add(secondCategoryNode
                            .getAttribute("term"));

                    SubCategories tempSub = new SubCategories(subcategories);



                    Category primalCategory = Category.Computer_Science;


                    Article tempArticle = new Article(tempTitle, tempAuthors, tempContent, primalCategory, tempSub );
                    articles.add(tempArticle);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return articles;

    }
}
