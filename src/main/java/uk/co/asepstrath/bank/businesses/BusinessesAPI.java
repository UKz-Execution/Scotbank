package uk.co.asepstrath.bank.businesses;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import kong.unirest.core.Unirest;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class BusinessesAPI {

    /*public BusinessesAPI() {


        Business businessResponse = Unirest.get("https://api.asep-strath.co.uk/api/businesses").asObject(Business.class).getBody();
        String csvData = businessResponse.toString();

        List<Business> businessList = parseCsvData(csvData);
    }

        private static List<Business> parseCsvData (String csvData) {
            try (CSVReader csvReader = new CSVReader(new StringReader(csvData))) {

                ArrayList<Business> businessList = new ArrayList<>();

                String[] nextLine;

                csvReader.readNext();


                while((nextLine = csvReader.readNext()) != null) {
                    String id = nextLine[0];
                    String name = nextLine[1];
                    String category = nextLine[2];
                    String sanctioned = nextLine[3];

                    businessList.add(new Business(id,name,category,sanctioned));
                    }
                return businessList;

        } catch (CsvValidationException | IOException e) {
                throw new RuntimeException(e);
            }
        }*/
}



