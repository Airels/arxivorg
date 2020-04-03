package utils;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

import static app.arxivorg.model.Category.*;


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
            NodeList primalList = doc.getElementsByTagName("arxiv:primary_category");
            NodeList linkList = doc.getElementsByTagName("link");

            
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Node primalNode = primalList.item(temp);
                Node linkNode = linkList.item(2+2*temp);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element entryNode = (Element) nNode;
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

                    ArrayList<String> subcategories = new ArrayList<String>();

                    int nbsub = ((Element) nNode).getElementsByTagName("category").getLength();

                    for (int indexsub = 0 ; indexsub < nbsub ; indexsub++){
                        Node subitem = ((Element) nNode).getElementsByTagName("category").item(indexsub);
                        Element secondCategoryNode = (Element) subitem;
                        String sub = secondCategoryNode.getAttribute("term");

                        subcategories.add(sub);

                    }

                    SubCategories tempSub = new SubCategories(subcategories);

                    String category = primalCategoryNode.getAttribute("term");

                    if (category.contains(".")) {
                        int indexofdot = category.indexOf(".");
                        category = category.substring(0, indexofdot);
                    }

                    else if (category.contains("-")) {

                        int indexoftrai = category.indexOf("-");
                        category = category.substring(0, indexoftrai);
                    }


                    Category primalcategory = getCategory(category);


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