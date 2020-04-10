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
        try (InputStream in = new URL("https" + article.getLink().substring(4)).openStream()) {
            Files.copy(in,
                    Paths.get(file.getAbsolutePath() + '/'+ article.getTitle() + ".pdf"),
                    StandardCopyOption.REPLACE_EXISTING);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
