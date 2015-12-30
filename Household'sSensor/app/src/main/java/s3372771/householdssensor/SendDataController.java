package s3372771.householdssensor;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Tan on 14/12/15.
 */
public class SendDataController extends AsyncTask<Void, Void, Void> {
    String inputString;
    Context context;
    boolean fail = false;

    public SendDataController(String inputString, Context context){
        this.inputString = inputString;
        this.context = context;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(fail) {
            CharSequence text = "Cannot connect to the Server!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL(inputString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine = in.readLine();

            if(!inputLine.equals("acknowledged")){
                throw new IOException();
            } else {
                System.out.println("Success");
            }

            in.close();
//            // optional default is GET
//
//            con.setRequestMethod("GET");
//
//            System.out.println(con.getURL());
//
//            int responseCode = con.getResponseCode();
//            System.out.println("\nSending 'GET' request to URL : " + url);
//            System.out.println("Response Code : " + responseCode);
//
//            System.out.println("goto: " + inputString);
        } catch (IOException e) {
            fail = true;
            e.printStackTrace();
        }



        return null;
    }

}
