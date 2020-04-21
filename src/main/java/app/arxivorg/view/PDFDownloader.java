package app.arxivorg.view;

import app.arxivorg.model.Article;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PDFDownloader {

    public static boolean downloadFile(Article article, File file) {
        boolean isDownloaded = false;
        try (InputStream in = new URL("https" + article.getLink().substring(4)).openStream()) {
            Files.copy(in,
                    Paths.get(file.getAbsolutePath() + '/'+ article.getTitle() + ".pdf"),
                    StandardCopyOption.REPLACE_EXISTING);
            isDownloaded = true;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return isDownloaded;
    }

}
