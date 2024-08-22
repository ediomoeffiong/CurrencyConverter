package comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;



public class CurrencyConverter {
    // essential URL structure is built using constants
    public static final String ACCESS_KEY = "0df65045a27bf335360acc146714239e";
    public static final String BASE_URL = "http://api.exchangerate.host/";
    public static final String ENDPOINT = "live";
    public static String base, notBase, baseNot;
    public static int quantity;

    // this object is used for executing requests to the (REST) API
    static CloseableHttpClient httpClient = HttpClients.createDefault();

    // sendLiveRequest() function is created to request and retrieve the data
    public static void sendLiveRequest(){

        // The following line initializes the HttpGet Object with the URL in order to send a request
        HttpGet get = new HttpGet(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY);

        try {
            CloseableHttpResponse response =  httpClient.execute(get);
            HttpEntity entity = response.getEntity();

            // the following line converts the JSON Response to an equivalent Java Object
            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

            System.out.println("Live Currency Exchange Rates");

            // Parsed JSON Objects are accessed according to the JSON response's hierarchy, output strings are built
            Date timeStampDate = new Date((long)(exchangeRates.getLong("timestamp")*1000));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
            String formattedDate = dateFormat.format(timeStampDate);
            System.out.println(quantity + " " + exchangeRates.getString("source") + " in " + notBase + " : " + exchangeRates.getJSONObject("quotes").getDouble(baseNot) + " (Date: " + formattedDate + ")");
            System.out.println("\n");
            response.close();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("-----------Welcome to Currency Converter by Covenant-----------\n\n");
        System.out.println("Please input the correct details below to ensure proper conversion");
        System.out.println("Note: Base currency is the currency you want to convert from");

        Scanner scan = new Scanner(System.in);
        System.out.print("Input base currency: ");
        base = scan.nextLine();
        System.out.print("Input the currency you want to convert to: ");
        notBase = scan.nextLine();
        System.out.print("Input quantity: ");
        quantity = scan.nextInt();
        baseNot = base.concat(notBase);


        sendLiveRequest();
        httpClient.close();
        new BufferedReader(new InputStreamReader(System.in)).readLine();
    }
}