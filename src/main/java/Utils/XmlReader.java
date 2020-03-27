package Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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


public class XmlReader {

    public static ArrayList<Article> read(String fileName) {

        ArrayList<Article> articles = new ArrayList<Article>();

        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("entry");
            NodeList secondList = doc.getElementsByTagName("category");
            NodeList primalList = doc.getElementsByTagName("arxiv:primary_category");
            NodeList linkList = doc.getElementsByTagName("link");

            
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Node secondNode = secondList.item(temp);
                Node primalNode = primalList.item(temp);
                Node linkNode = linkList.item(2+2*temp);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element entryNode = (Element) nNode;
                    Element secondCategoryNode = (Element) secondNode;
                    Element primalCategoryNode = (Element) primalNode;
                    Element linkGetterNode = (Element) linkNode;

                    int lengthAuthor = entryNode.getElementsByTagName("author").getLength();
                    ArrayList<String> tempList = new ArrayList<String>();

                    for (int index = 0 ; index < lengthAuthor ; index ++ ){

                        tempList.add(entryNode.getElementsByTagName("name").item(index).getTextContent());
                    }

                    Authors tempAuthors = new Authors(tempList);

                    String tempfulldate = entryNode.getElementsByTagName("published").item(0).getTextContent();
                    tempfulldate = tempfulldate.substring(0,10);

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dtf = dtf.withLocale(Locale.UK);
                    LocalDate tempdate = LocalDate.parse(tempfulldate, dtf);



                    String tempTitle = entryNode.getElementsByTagName("title").item(0).getTextContent();

                    String tempContent = entryNode.getElementsByTagName("summary").item(0).getTextContent();

                    ArrayList subcategories = new ArrayList<String>();


                    /**
                     * Only print Primary SubCategory, must be changed
                     * Tests are done with this predicate.
                     */

                    subcategories.add(secondCategoryNode
                            .getAttribute("term"));

                    SubCategories tempSub = new SubCategories(subcategories);

                    String category = primalCategoryNode.getAttribute("term");
                    int indexofdot = category.indexOf(".");
                    category = category.substring(0,indexofdot);

                    Category primalcategory;

                    switch(category){
                        case "physics" : primalcategory = Category.Physics;
                            break;

                        case "math" : primalcategory = Category.Mathematics;
                            break;

                        case "q-bio": primalcategory = Category.Quantitative_Biology;
                            break;

                        case "cs" : primalcategory = Category.Computer_Science;
                            break;

                        case "q-fin" : primalcategory = Category.Quantitative_Finance;
                            break;

                        case "stat" : primalcategory = Category.Statistics;
                            break;

                        case "eess" : primalcategory = Category.Electrical_Engineering_and_Systems_Science;
                            break;

                        case "econ" : primalcategory = Category.Economics;
                            break;

                        default: primalcategory = Category.All;
                    }


                    String tempLink = linkGetterNode.getAttribute("href");

                    Article tempArticle = new Article(tempTitle, tempAuthors, tempContent, primalcategory, tempSub,tempLink, tempdate);
                    articles.add(tempArticle);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return articles;

    }



}