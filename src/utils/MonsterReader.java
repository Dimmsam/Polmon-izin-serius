package utils;

import model.Monster;
import model.EvolutionStage;
import model.SpeciesType;
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
            if (!version.equals("2.0")) {
                System.out.println("Old save version detected, creating new monster");
                return null;
            }

            Node monsterNode = doc.getElementsByTagName("Monster").item(0);

            if (monsterNode.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) monsterNode;

                int ID = Integer.parseInt(getElementValue(el, "ID"));
                String name = getElementValue(el, "Name");
                String birthday = getElementValue(el, "Birthday");
                String speciesStr = getElementValue(el, "Species");
                String stageStr = getElementValue(el, "Stage");
                int ageSeconds = Integer.parseInt(getElementValue(el, "AgeSeconds"));

                Element stats = (Element) el.getElementsByTagName("Stats").item(0);
                Element health = (Element) stats.getElementsByTagName("Health").item(0);
                Element damage = (Element) stats.getElementsByTagName("Damage").item(0);
                Element attributes = (Element) stats.getElementsByTagName("Attributes").item(0);
                Element energyEl = (Element) stats.getElementsByTagName("Energy").item(0);

                int hp = Integer.parseInt(getElementValue(health, "Current"));
                int maxHP = Integer.parseInt(getElementValue(health, "Max"));
                long lastFedTimestamp = Long.parseLong(getElementValue(health, "FeedTime"));
                long lastCareTimestamp = Long.parseLong(getElementValue(health, "CareTime"));

                int minDmg = Integer.parseInt(getElementValue(damage, "Min"));
                int maxDmg = Integer.parseInt(getElementValue(damage, "Max"));

                int happiness = Integer.parseInt(getElementValue(attributes, "Happiness"));
                int hunger = Integer.parseInt(getElementValue(attributes, "Hunger"));

                int energy = Integer.parseInt(getElementValue(energyEl, "Current"));
                int maxEnergy = Integer.parseInt(getElementValue(energyEl, "Max"));

                Monster monster = new Monster(ID, birthday, name, lastFedTimestamp, lastCareTimestamp,
                        hp, maxHP, minDmg, maxDmg, happiness, energy, maxEnergy);

                EvolutionStage stage = EvolutionStage.valueOf(stageStr);
                monster.setStage(stage);
                monster.setName(name);

                for (int i = 0; i < ageSeconds; i++) {
                    monster.addAgeSeconds(1);
                }

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