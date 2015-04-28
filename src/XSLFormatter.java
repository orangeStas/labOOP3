import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by OrangeUser on 4/21/2015.
 */
public class XSLFormatter implements IFormatter {

    private File file;
    private File[] fileStylesArray;
    private Transformer transformer;
    private String choosenStyle;
    private boolean deformatFlag = false;

    public XSLFormatter(File file, String choosenStyle, File[] fileStylesArray) throws TransformerConfigurationException, IOException {
        this.file = file;
        this.choosenStyle = choosenStyle;
        this.fileStylesArray = fileStylesArray;
        setupTransformer();
    }

    public XSLFormatter(File file, File[] fileStylesArray) throws TransformerConfigurationException, IOException {
        this.file = file;
        this.fileStylesArray = fileStylesArray;
        deformatFlag = true;
        setupTransformer();
    }

    private void setupTransformer() throws TransformerConfigurationException, IOException {
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setAttribute("indent-number", 2);
        StreamSource xslStream = new StreamSource(getStyleFile());
        transformer = factory.newTransformer(xslStream);
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-16");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    }

    public File getStyleFile() throws IOException {
        if (deformatFlag){
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-16")));
            String currentLine = "";
            while ((currentLine = reader.readLine()) != null)
                choosenStyle = currentLine;
            choosenStyle = choosenStyle.replaceAll("(<!--|-->)", "");
            reader.close();
        }
        for (File file1 : fileStylesArray){
            if (file1.getName().equals(choosenStyle))
                return file1;
        }
        return null;
    }

    @Override
    public void formatXML() throws TransformerException {
        StringWriter outWriter = new StringWriter();

        StreamSource in = new StreamSource(file);
        StreamResult out = new StreamResult(outWriter);

        transformer.transform(in, out);

        StringBuffer buffer = outWriter.getBuffer();
        String result = prettyPrint(buffer.toString());


        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file, "UTF-16");
            writer.println(result);
            writer.println("<!--" + main.tableStyles.get(choosenStyle) + "-->");

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    public String prettyPrint(String xmlString) {
        try {
            final InputSource src = new InputSource(new StringReader(xmlString));
            final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
            final Boolean keepDeclaration = Boolean.valueOf(xmlString.startsWith("<?xml"));

            System.setProperty(DOMImplementationRegistry.PROPERTY,"com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");


            final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            final LSSerializer writer = impl.createLSSerializer();

            writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
            writer.getDomConfig().setParameter("xml-declaration", keepDeclaration);

            return writer.writeToString(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deformatXML() throws TransformerException, IOException {
        StringWriter outWriter = new StringWriter();
        StringReader xmlString = new StringReader(getXMLWithoutStyle(file, choosenStyle));

        StreamSource in = new StreamSource(xmlString);
        StreamResult out = new StreamResult(outWriter);

        transformer.transform(in, out);

        StringBuffer buffer = outWriter.getBuffer();
        String result = buffer.toString();
        return result;
    }

    public String getXMLWithoutStyle(File file, String lineToRemove) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-16")));
        String currentLine;
        String result = "";

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            result += (currentLine + System.getProperty("line.separator"));
        }
        reader.close();
        return result;
    }

}
