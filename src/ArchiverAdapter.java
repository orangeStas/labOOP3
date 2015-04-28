import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

/**
 * Created by OrangeUser on 4/28/2015.
 */
public class ArchiverAdapter implements IFormatter {

    Archiver archiver;
    private File file;

    public ArchiverAdapter(File file) {
        this.file = file;
    }

    @Override
    public void formatXML() throws TransformerException {
        try {
            archiver.zipFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String deformatXML() throws TransformerException, IOException {
        return archiver.unzipFile(file);
    }
}
