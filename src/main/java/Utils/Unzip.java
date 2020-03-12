package Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip {

    /**
     * décompresse le fichier zip dans le répertoire donné
     * @param folder le répertoire où les fichiers seront extraits
     * @param zipfile le fichier zip à décompresser
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void unzip(File zipfile, File folder) throws FileNotFoundException, IOException{

        // création de la ZipInputStream qui va servir à lire les données du fichier zip
        ZipInputStream XmlStream = new ZipInputStream(
                new BufferedInputStream(
                        new FileInputStream(zipfile.getCanonicalFile())));

        // extractions des entrées du fichiers zip (i.e. le contenu du zip)
        ZipEntry Xmlzip = null;
        try {
            while((Xmlzip = XmlStream.getNextEntry()) != null){

                // Pour chaque entrée, on crée un fichier
                // dans le répertoire de sortie "folder"
                File newFile = new File(folder.getCanonicalPath(), Xmlzip.getName());

                // Si l'entrée est un répertoire,
                // on le crée dans le répertoire de sortie
                // et on passe à l'entrée suivante (continue)
                if (Xmlzip.isDirectory()) {
                    newFile.mkdirs();
                    continue;
                }

                // L'entrée est un fichier, on crée une OutputStream
                // pour écrire le contenu du nouveau fichier
                newFile.getParentFile().mkdirs();
                OutputStream fos = new BufferedOutputStream(
                        new FileOutputStream(newFile));

                // On écrit le contenu du nouveau fichier
                // qu'on lit à partir de la ZipInputStream
                // au moyen d'un buffer (byte[])
                try {
                    try {
                        final byte[] buf = new byte[8192];
                        int bytesRead;
                        while (-1 != (bytesRead = XmlStream.read(buf)))
                            fos.write(buf, 0, bytesRead);
                    }
                    finally {
                        fos.close();
                    }
                }
                catch (final IOException ioe) {
                    // en cas d'erreur on efface le fichier
                    newFile.delete();
                    throw ioe;
                }
            }
        }
        finally {
            // fermeture de la ZipInputStream
            XmlStream.close();
        }
    }
}

    public static void main(String[] args) {
        unzip();
    }

