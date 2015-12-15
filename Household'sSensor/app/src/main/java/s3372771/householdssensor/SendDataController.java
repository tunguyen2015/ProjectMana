package s3372771.householdssensor;

import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tan on 14/12/15.
 */
public class SendDataController extends AsyncTask<Void, Void, Void> {
    String inputString;

    public SendDataController(String inputString){
        this.inputString = inputString;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL(inputString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            System.out.println("goto: " + inputString);
        } catch (IOException e) {

        }

        return null;
    }

}
