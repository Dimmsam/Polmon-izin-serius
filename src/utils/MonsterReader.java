package utils;

import model.Monster;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class MonsterReader {

    public static Monster read(File dir, String path) {
        try {
            File saveFolder = new File(dir, "saves");
            File xmlFile = new File(saveFolder, path);

            if (!xmlFile.exists()) {
                return null;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            String version = doc.getDocumentElement().getAttribute("version");
            if (!version.equals("1.1")) {
                throw new Exception("Incompatible save version: " + version);
            }

            Node monsterNode = doc.getElementsByTagName("Monster").item(0);

            if (monsterNode.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) monsterNode;

                int ID = Integer.parseInt(getElementValue(el, "ID"));
                String name = getElementValue(el, "Name");
                String birthday = getElementValue(el, "Birthday");

                Element stats = (Element) el.getElementsByTagName("Stats").item(0);
                Element health = (Element) stats.getElementsByTagName("Health").item(0);
                Element damage = (Element) stats.getElementsByTagName("Damage").item(0);

                int hp = Integer.parseInt(getElementValue(health, "Current"));
                int maxHP = Integer.parseInt(getElementValue(health, "Max"));
                long lastFedTimestamp = Long.parseLong(getElementValue(health, "FeedTime"));
                long lastCareTimestamp = Long.parseLong(getElementValue(health, "CareTime"));

                int minDmg = Integer.parseInt(getElementValue(damage, "Min"));
                int maxDmg = Integer.parseInt(getElementValue(damage, "Max"));

                int happiness = Integer.parseInt(getElementValue(el, "Happiness"));

                Monster monster = new Monster(ID, birthday, name, lastFedTimestamp, lastCareTimestamp,
                        hp, maxHP, minDmg, maxDmg, happiness);

                TimeSimulator.simulateTime(monster);

                return monster;
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getElementValue(Element parent, String tagName) {
        return parent.getElementsByTagName(tagName).item(0).getTextContent();
    }
}