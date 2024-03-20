package uk.co.asepstrath.bank.transactions;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import uk.co.asepstrath.bank.accounts.Account;
import uk.co.asepstrath.bank.database.DatabaseAPI;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;
import org.slf4j.Logger;

public class TransactionsAPI {
    public static void loadData(Logger logger) throws ParserConfigurationException, IOException, SAXException {
        int p = 0;
        NodeList nodeList;
        ArrayList<Transaction> transactions = new ArrayList<>();
        do{
            String urlString = "https://api.asep-strath.co.uk/api/transactions?size=1000&page=" + p;
            URL url = new URL(urlString);
            DocumentBuilderFactory doc_build_factory = DocumentBuilderFactory.newInstance();
            doc_build_factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            doc_build_factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
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
                            if (node.getFirstChild() != null) {
                                double amount = Double.parseDouble(node.getFirstChild().getNodeValue());
                                transaction.setAmount(amount);
                            }
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
                if (logger != null) logger.info("Transaction created: {timestamp: " + transaction.getTimestamp() + ", amount: " + transaction.getAmount() + "id: " + transaction.getId() + "from: " + transaction.getFrom() + "to: " + transaction.getTo() + "type: " + transaction.getType());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static BigDecimal calculateCurrentAmount(Account account) {
        try (DatabaseAPI conn = DatabaseAPI.open()) {
            return calculateCurrentAmount(account, conn.getAllTransactions());
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect to the database to get transactions.");
        }
    }

    public static BigDecimal calculateCurrentAmount(Account account, ArrayList<Transaction> transactions) {
        BigDecimal currentAmount = account.getStartingBalance();
        String accountUUID = account.getId().toString();

        for (Transaction transaction : transactions) {
            String transactionType = transaction.getType();
            switch (transactionType) {
                case "PAYMENT":
                case "WITHDRAWAL":
                    if (transaction.getFrom().equals(accountUUID)) {
                        currentAmount = currentAmount.subtract(transaction.getAmount());
                    }
                    break;
                case "DEPOSIT":
                case "COLLECT_ROUNDUPS":
                    if (transaction.getTo().equals(accountUUID)) {
                        currentAmount = transaction.getAmount().add(currentAmount);
                    }
                    break;
                case "TRANSFER":
                    if (transaction.getFrom().equals(accountUUID)) {
                        currentAmount = currentAmount.subtract(transaction.getAmount());
                    } else if (transaction.getTo().equals(accountUUID)) {
                        currentAmount = transaction.getAmount().add(currentAmount);
                    }
                    break;
                default:
                    break;
            }
        }
        return currentAmount;
    }
}


