package uk.co.asepstrath.bank;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import uk.co.asepstrath.bank.transactions.TransactionsAPI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

public class TransactionsAPITest {

        @Test
        void testConstructor() throws Exception {

            HttpURLConnection urlConnection = mock(HttpURLConnection.class);
            InputStream inputStream = mock(InputStream.class);
            when(urlConnection.getInputStream()).thenReturn(inputStream);

            DocumentBuilder documentBuilder = mock(DocumentBuilder.class);
            when(documentBuilder.parse(Mockito.any(InputSource.class))).thenReturn(mock(Document.class));


            DocumentBuilderFactory documentBuilderFactory = mock(DocumentBuilderFactory.class);
            when(documentBuilderFactory.newDocumentBuilder()).thenReturn(documentBuilder);

            URL url = mock(URL.class);
            when(url.openConnection()).thenReturn(urlConnection);

            AtomicReference<TransactionsAPI> transactionsAPI = new AtomicReference<>(spy(new TransactionsAPI()));

            // Call the constructor
            assertDoesNotThrow(() -> {
                transactionsAPI.set(new TransactionsAPI());
            });

            verify(url, atLeastOnce()).openConnection();
            verify(urlConnection).getInputStream();
            verify(documentBuilder, atLeastOnce()).parse(Mockito.any(InputSource.class));
        } // Imcomplete tests.
    }

