package utils;

import app.arxivorg.model.Article;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class PDFDownloader {
    /**
     *
     * @param article
     * @param file
     * @throws MalformedURLException
     */
    public static void downloadFile(Article article, File file) {

        File pdfFile = new File(file.getAbsolutePath(), article.getTitle() + ".pdf");

        try {
            URL url = new URL(article.getLink());


            try (BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
                 FileOutputStream fileOS = new FileOutputStream(pdfFile.getAbsolutePath())) {
                byte data[] = new byte[1024];
                int byteContent;
                while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                    fileOS.write(data, 0, byteContent);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch(MalformedURLException e){
            System.out.println("Le fichier n'as pas pu être télécharger");
        }


    }
}
