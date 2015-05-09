import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * Created by OrangeUser on 4/28/2015.
 */
public class ArchiverAdapter implements IFormatter {

    Archiver archiver;

    public ArchiverAdapter(Archiver archiver) {
        this.archiver = archiver;
    }

    @Override
    public void formatXML() throws TransformerException {
        try {
            archiver.zipFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String deformatXML() throws TransformerException, IOException {
        return archiver.unzipFile();
    }

}
