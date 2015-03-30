package workbook2.workbook2Tasks.parser.blinovTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DOMParser {

    public static List<Plant> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
        docBuildFact.setIgnoringElementContentWhitespace(true);
        Document doc = docBuildFact.newDocumentBuilder().parse(fileName);
        Element element = doc.getDocumentElement();
        NodeList nodeList = element.getChildNodes();

        List<Plant> plantList = new ArrayList<Plant>();
        List<Tree> treeList = parseTree(nodeList);
        List<Bush> bushList = parseBush(nodeList);

        for (Tree tree : treeList) {
            plantList.add(tree);
        }
        for (Bush bush : bushList) {
            plantList.add(bush);
        }
        return plantList;
    }

    private static List<Bush> parseBush(NodeList nodeList) {
        List<Bush> bushList = new ArrayList<Bush>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase(Tags.BUSH.toString())) {
                String bushName = "";
                double bushAge = 0;
                double bushHeight = 0;
                boolean bushBerries = false;
                String bushInflorescence = "";
                NodeList bushNodeList = node.getChildNodes();
                for (int j = 0; j < bushNodeList.getLength(); j++) {
                    Node bushNode = bushNodeList.item(j);
                    if ( bushNode.getNodeName().equalsIgnoreCase(Tags.NAME.toString())) {
                        bushName = bushNode.getTextContent();
                    } else if (bushNode.getNodeName().equalsIgnoreCase(Tags.AGE.toString())) {
                        bushAge = Double.parseDouble(bushNode.getTextContent());
                    } else if (bushNode.getNodeName().equalsIgnoreCase(Tags.HEIGHT.toString())) {
                        bushHeight = Double.parseDouble(bushNode.getTextContent());
                    } else if (bushNode.getNodeName().equalsIgnoreCase(Tags.BERRIES.toString())) {
                        bushBerries = bushNode.getTextContent().equalsIgnoreCase("true");
                    } else if (bushNode.getNodeName().equalsIgnoreCase(Tags.INFLORESCENCE.toString())) {
                        bushInflorescence = bushNode.getTextContent();
                    }
                }
                bushList.add(new Bush(bushName, bushAge, bushHeight, bushBerries, bushInflorescence));
            }
        }
        return bushList;
    }

    private static List<Tree> parseTree(NodeList nodeList) {
        List<Tree> treeList = new ArrayList<Tree>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase(Tags.TREE.toString())) {
                String treeName = "";
                double treeAge = 0;
                double treeHeight = 0;
                NodeList treeNodeList = node.getChildNodes();
                for (int j = 0; j < treeNodeList.getLength(); j++) {
                    Node treeNode = treeNodeList.item(j);
                    if (treeNode.getNodeName().equalsIgnoreCase(Tags.NAME.toString())) {
                        treeName = treeNode.getTextContent();
                    } else if (treeNode.getNodeName().equalsIgnoreCase(Tags.AGE.toString())) {
                        treeAge = Double.parseDouble(treeNode.getTextContent());
                    } else if (treeNode.getNodeName().equalsIgnoreCase(Tags.HEIGHT.toString())) {
                        treeHeight = Double.parseDouble(treeNode.getTextContent());
                    }
                }
                treeList.add(new Tree(treeName, treeAge, treeHeight));
            }
        }
        return treeList;
    }

    public static int plantCount(List<Plant> plantList) {
        return plantList.size();
    }

    public static double plantHeightSum(List<Plant> plantList) {
        double sum = 0;
        for (Plant plant : plantList) {
            sum += plant.getHeight();
        }
        return sum;
    }

    public static List<Plant> grow(List<Plant> plantList, int monthQuantity) {

        for (int i = 0; i < plantList.size(); i++) {
            double heightIncrement = 0;
            Plant plant = plantList.get(i);
            if (plant.getClass().equals(Tree.class)){
                heightIncrement = (double)(monthQuantity) * Tree.growMetresPerYear / 12;
            } else if (plant.getClass().equals(Bush.class)){
                heightIncrement = (double)(monthQuantity) * Bush.growMetresPerYear / 12;
            }
            double newHeight = plant.getHeight() + heightIncrement ;
            plant.setHeight(newHeight);
            double newAge = plant.getAge() + monthQuantity/12;
            plant.setAge(newAge);
        }
        return plantList;
    }

    public static void convertToXML(List<Plant> plantList, String newFileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(newFileName));
        bw.write("<?xml version = \"1.0\" encoding=\"utf-8\"?>\r\n");
        bw.write("<plants>\r\n");
        for (Plant plant : plantList) {
            if (plant instanceof Tree) {
                bw.write("\t<tree>\r\n");
                bw.write("\t\t<name>" + plant.getName() + "</name>\r\n");
                bw.write("\t\t<height>" + plant.getHeight() + "</height>\r\n");
                bw.write("\t\t<age>" + plant.getAge() + "</age>\r\n");
                bw.write("\t</tree>\r\n");
            } else if (plant instanceof Bush) {
                bw.write("\t<bush>\r\n");
                bw.write("\t\t<name>" + plant.getName() + "</name>\r\n");
                bw.write("\t\t<height>" + plant.getHeight() + "</height>\r\n");
                bw.write("\t\t<age>" + plant.getAge() + "</age>\r\n");
                bw.write("\t\t<berries>" + ((Bush) plant).getBerries() + "</berries>\r\n");
                bw.write("\t\t<inflorescence>" + ((Bush) plant).getInflorescence() + "</inflorescence>\r\n");
                bw.write("\t</bush>\r\n");
            }
        }
        bw.write("</plants>\r\n");
        bw.close();
    }
}


