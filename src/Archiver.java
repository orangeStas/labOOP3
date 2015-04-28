import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by OrangeUser on 4/28/2015.
 */
public class Archiver {

    String zipFilePath = System.getProperty("user.dir") + "\\archive.zip";

    public void zipFile(File file) throws IOException {
        FileOutputStream fileOutputStream = null;
        ZipOutputStream zipOutputStream = null;
        FileInputStream fileInputStream = null;

        try {

            byte[] buffer = new byte[1024];

            fileOutputStream = new FileOutputStream(new File(zipFilePath));
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

    public String unzipFile(File file) throws IOException {
        ZipInputStream zipInputStream = null;
        FileInputStream fileInputStream = null;
        String result = "";
        try {
            fileInputStream = new FileInputStream(file);
            zipInputStream = new ZipInputStream(fileInputStream);
            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));
                String line;
                while ((line = reader.readLine()) != null){
                    result += line;
                }
                reader.close();
            }

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

}
