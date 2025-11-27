package utils;

import model.Monster;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MonsterWriter {

    public static void write(Monster mon, File dir, String path) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element root = doc.createElement("eTamagotchi");
            Attr version = doc.createAttribute("version");
            version.setValue("1.1");
            root.setAttributeNode(version);
            doc.appendChild(root);

            Element monster = doc.createElement("Monster");

            appendChild(doc, monster, "ID", String.valueOf(mon.getID()));
            appendChild(doc, monster, "Birthday", mon.getBirthday());
            appendChild(doc, monster, "Name", mon.getName());

            Element stats = doc.createElement("Stats");
            Element health = doc.createElement("Health");

            appendChild(doc, health, "Max", String.valueOf(mon.getMaxHP()));
            appendChild(doc, health, "Current", String.valueOf(mon.getHP()));
            appendChild(doc, health, "FeedTime", String.valueOf(mon.getLastFedTimestamp()));
            appendChild(doc, health, "CareTime", String.valueOf(mon.getLastCareTimestamp()));

            stats.appendChild(health);

            Element damage = doc.createElement("Damage");
            appendChild(doc, damage, "Max", String.valueOf(mon.getMaxDamage()));
            appendChild(doc, damage, "Min", String.valueOf(mon.getMinDamage()));
            stats.appendChild(damage);

            monster.appendChild(stats);

            appendChild(doc, monster, "Happiness", String.valueOf(mon.getHappiness()));

            root.appendChild(monster);

            File saveFolder = new File(dir, "saves");
            if (!saveFolder.exists()) {
                saveFolder.mkdir();
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(saveFolder, path));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void appendChild(Document doc, Element parent, String tagName, String value) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(value));
        parent.appendChild(element);
    }
}