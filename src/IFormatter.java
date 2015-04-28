import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * Created by OrangeUser on 4/28/2015.
 */
public interface IFormatter {


    void formatXML() throws TransformerException;

    String deformatXML() throws TransformerException, IOException;


}
