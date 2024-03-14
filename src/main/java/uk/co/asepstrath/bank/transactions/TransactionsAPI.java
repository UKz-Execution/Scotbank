package uk.co.asepstrath.bank.transactions;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import uk.co.asepstrath.bank.database.DatabaseAPI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;
import org.slf4j.Logger;

public class TransactionsAPI {
    public TransactionsAPI() {

}

public static void loadData(Logger logger) throws ParserConfigurationException, IOException, SAXException {
        int p = 1;
        NodeList nodeList;
        ArrayList<Transaction> transactions = new ArrayList<>();
        do{
            String urlString = "https://api.asep-strath.co.uk/api/transactions?size=1000&page=" + p;
            URL url = new URL(urlString);
            DocumentBuilderFactory doc_build_factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder doc_build = doc_build_factory.newDocumentBuilder();
            Document doc = doc_build.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            nodeList = doc.getElementsByTagName("results");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Transaction transaction = new Transaction();
                Node node = nodeList.item(i).getFirstChild();
                while (node != null) {
                    switch (node.getNodeName()) {
                        case "timestamp":
                            if (node.getFirstChild() != null)
                                transaction.setTimestamp(LocalDateTime.parse(node.getFirstChild().getNodeValue(), formatter));
                            break;
                        case "amount":
                            if (node.getFirstChild() != null)
                                transaction.setAmount(Double.parseDouble(node.getFirstChild().getNodeValue()));
                            break;
                        case "from":
                            if (node.getFirstChild() != null)
                                transaction.setFrom(node.getFirstChild().getNodeValue());
                            break;
                        case "id":
                            if (node.getFirstChild() != null)
                                transaction.setId(UUID.fromString(node.getFirstChild().getNodeValue()));
                            break;
                        case "to":
                            if (node.getFirstChild() != null)
                                transaction.setTo(node.getFirstChild().getNodeValue());
                            break;
                        case "type":
                            if (node.getFirstChild() != null)
                                transaction.setType(node.getFirstChild().getNodeValue());
                            break;
                    }
                    node = node.getNextSibling();
                }
                transactions.add(transaction);
            }
            p++;
        } while (nodeList.getLength() > 0);

        try (DatabaseAPI connection = DatabaseAPI.open()) {

            for (Transaction transaction : transactions){
                connection.createTransaction(transaction);
                logger.info("Transaction created: {timestamp: " + transaction.getTimestamp() + ", amount: " + transaction.getAmount() + "id: " + transaction.getId());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


