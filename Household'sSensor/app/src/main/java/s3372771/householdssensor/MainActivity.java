package s3372771.householdssensor;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends ActionBarActivity {

    Switch fullLightSwitch;
    Switch dimLightSwitch;
    Switch alertSwitch;
    Switch offSwitch;
    String ip = "http://192.168.100.20/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullLightSwitch = (Switch) findViewById(R.id.fullLightButton);
        dimLightSwitch = (Switch) findViewById(R.id.dimLightButton);
        alertSwitch = (Switch) findViewById(R.id.alertButton);
        offSwitch = (Switch) findViewById(R.id.offButton);

        fullLightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    try {
                        dimLightSwitch.setChecked(false);
                        String temp = ip + "?fullLightOn";
                        commandArduino(temp);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        String temp = ip + "?fullLightOff";
                        commandArduino(temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        dimLightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    try {
                        fullLightSwitch.setChecked(false);
                        String temp = ip + "?dimLightOn";
                        commandArduino(temp);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        String temp = ip + "?dimLightOff";
                        commandArduino(temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        alertSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    try {
                        String temp = ip + "?alertOn";
                        commandArduino(temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        String temp = ip + "?alertOff";
                        commandArduino(temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        offSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    try {
                        fullLightSwitch.setChecked(false);
                        fullLightSwitch.setEnabled(false);

                        dimLightSwitch.setChecked(false);
                        dimLightSwitch.setEnabled(false);

                        alertSwitch.setChecked(false);
                        alertSwitch.setEnabled(false);
                        String temp = ip + "?turnOff";
                        commandArduino(temp);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
//                    try {
                        fullLightSwitch.setEnabled(true);
                        dimLightSwitch.setEnabled(true);
                        alertSwitch.setEnabled(true);
//                        String temp = ip + "?cmd=turnOn";
//                        commandArduino(temp);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        });
    }

    public void commandArduino(String inputURL) throws IOException {
        SendDataController sendData = new SendDataController(inputURL);
        sendData.execute();
    }
}
