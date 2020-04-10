package utils;

import app.arxivorg.model.Article;
import javafx.scene.chart.ScatterChart;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PDFDownloader {
    /**
     * @param article
     * @param file
     * @throws MalformedURLException
     */
    public static void downloadFile(Article article, File file) {

        File pdfFile = new File(file.getAbsolutePath() + "/" + article.getTitle() + ".pdf");


        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(article.getLink()).openStream());
             FileOutputStream fileOS = new FileOutputStream(pdfFile.getAbsolutePath())) {
            byte[] data = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void secondDownloader(Article article, File file) {
        try (InputStream in = new URL(article.getLink() + ".pdf").openStream()) {
            Files.copy(in, Paths.get(file.getAbsolutePath() + '/'+ article.getTitle() + ".pdf"), StandardCopyOption.REPLACE_EXISTING);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
