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

    public BusinessesAPI() {

        Business businessResponse = Unirest.get("https://api.asep-strath.co.uk/api/businesses").asObject(Business.class).getBody();
        String csvData = businessResponse.toString();

        List<Business> businessList = parseCsvData(csvData);
    }

        private static List<Business> parseCsvData (String csvData) {
            try (CSVReader csvReader = new CSVReaderBuilder(new StringReader(csvData)).build()) {
                List<Business> businessList = new ArrayList<>();

                csvReader.readNext();

                String[] nextLine;

                while((nextLine = csvReader.readNext()) != null) {
                    Business business = new Business();

                    for (int i = 0; i < nextLine.length; i++) {


                    }
                    businessList.add(business);
                }
                return businessList;
        } catch (CsvValidationException | IOException e) {
                throw new RuntimeException(e);
            }


        }
}
