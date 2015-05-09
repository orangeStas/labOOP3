import org.mozilla.universalchardet.UniversalDetector;

import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by OrangeUser on 4/28/2015.
 */
public class Archiver {

    private File zipFile;
    private File file;

    public Archiver(File file){
        this.file = file;
    }

    public void zipFile() throws IOException {
        FileOutputStream fileOutputStream = null;
        ZipOutputStream zipOutputStream = null;
        FileInputStream fileInputStream = null;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int ret = fileChooser.showDialog(null, "Choose file");
        if (ret == JFileChooser.APPROVE_OPTION) {
            zipFile = fileChooser.getSelectedFile();
        }

        try {

            byte[] buffer = new byte[1024];

            fileOutputStream = new FileOutputStream(zipFile);
            zipOutputStream = new ZipOutputStream(fileOutputStream);

            fileInputStream = new FileInputStream(file);

            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));

            int length;

            while ((length = fileInputStream.read(buffer)) > 0)
                zipOutputStream.write(buffer, 0 , length);


        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fileInputStream != null)
                fileInputStream.close();
            if (zipOutputStream != null)
                zipOutputStream.close();
            if (fileOutputStream != null)
                fileOutputStream.close();
        }
    }

    public String unzipFile() throws IOException {
        ZipInputStream zipInputStream = null;
        FileInputStream fileInputStream = null;
        String result = "";
        try {
            fileInputStream = new FileInputStream(file);
            zipInputStream = new ZipInputStream(fileInputStream);
            ZipEntry zipEntry;
            BufferedReader reader = null;

            String charSet = getEncoding(zipInputStream);

            fileInputStream = new FileInputStream(file);
            zipInputStream = new ZipInputStream(fileInputStream);

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (charSet == null)
                    reader = new BufferedReader(new InputStreamReader(zipInputStream));
                else
                    reader = new BufferedReader(new InputStreamReader(zipInputStream, Charset.forName("UTF-16")));
                String line;


                while ((line = reader.readLine()) != null){
                    result += line + System.getProperty("line.separator");
                }
            }

            if (reader != null)
                reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (fileInputStream != null)
                fileInputStream.close();
            if (zipInputStream != null)
                zipInputStream.close();
        }

        return result;

    }

    public String getEncoding(ZipInputStream zipInputStream) throws IOException {
        byte[] buf = new byte[4096];

        UniversalDetector detector = new UniversalDetector(null);

        ZipEntry zipEntry;
        BufferedReader reader = null;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {

            reader = new BufferedReader(new InputStreamReader(zipInputStream));

            int nread;
            while ((nread = zipInputStream.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
        }

        detector.dataEnd();
        String encoding = detector.getDetectedCharset();

        detector.reset();

        if (reader != null)
            reader.close();
        zipInputStream.close();

        return encoding;
    }

}
