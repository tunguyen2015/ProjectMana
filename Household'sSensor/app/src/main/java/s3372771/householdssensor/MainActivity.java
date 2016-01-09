package s3372771.householdssensor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    Switch fullLightSwitch;
    Switch dimLightSwitch;
    Switch alertSwitch;
    Switch offSwitch;
    String ip = "";
    String port = "";
    String ipText;
    AlertDialog alertDialog;
    Button ipButton;
    TextView ipTitle;
    Button portButton;
    TextView portTitle;

    protected ParseApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullLightSwitch = (Switch) findViewById(R.id.fullLightButton);
        dimLightSwitch = (Switch) findViewById(R.id.dimLightButton);
        alertSwitch = (Switch) findViewById(R.id.alertButton);
        offSwitch = (Switch) findViewById(R.id.offButton);
        ipButton = (Button) findViewById(R.id.ipButton);
        ipTitle = (TextView) findViewById(R.id.ipTitle);
        portButton = (Button) findViewById(R.id.portButton);
        portTitle = (TextView) findViewById(R.id.portTitle);

        readFromfile();
        ipText =  "http://" + ip + ":" + port + "/";
        ipTitle.setText(ip);
        portTitle.setText(port);
        fullLightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    try {
                        dimLightSwitch.setChecked(false);
                        String temp = ipText + "?fullLightOn";
                        commandArduino(temp);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        String temp = ipText + "?fullLightOff";
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
                        String temp = ipText + "?dimLightOn";
                        commandArduino(temp);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        String temp = ipText + "?dimLightOff";
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
                        String temp = ipText + "?alertOn";
                        commandArduino(temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        String temp = ipText + "?alertOff";
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
                        String temp = ipText + "?turnOff";
                        commandArduino(temp);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
//                    try {
                        fullLightSwitch.setEnabled(true);
                        dimLightSwitch.setEnabled(true);
                        alertSwitch.setEnabled(true);
//                        String temp = ipText + "?cmd=turnOn";
//                        commandArduino(temp);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        });

        ipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIPPopUp();
            }
        });

        portButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPortPopUp();
            }
        });
    }

    public void showIPPopUp() {

        final EditText input = new EditText(this);
        System.out.println(ip);
        input.setText(ip);

        alertDialog = new AlertDialog.Builder(this)
                .setView(input)
                .setTitle("New IP")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newIp = input.getText().toString();
                        if (!ip.equals(newIp)) {
                            if (!newIp.isEmpty()) {
                                ip = newIp;
                                ipText =  "http://" + ip + ":" + port + "/";
                                ipTitle.setText(ip);
                                System.out.println(ipText);
                                writeIPAndPort();
                            }
                        }
                    }
                })
                .show();
    }

    public void showPortPopUp() {

        final EditText input = new EditText(this);
        System.out.println(port);
        input.setText(port);

        alertDialog = new AlertDialog.Builder(this)
                .setView(input)
                .setTitle("New Port")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPort = input.getText().toString();
                        if (!ip.equals(newPort)) {
                            if (!newPort.isEmpty()) {
                                port = newPort;
                                ipText = "http://" + ip + ":" + port + "/";
                                portTitle.setText(port);
                                System.out.println(ipText);
                                writeIPAndPort();
                            }
                        }
                    }
                })
                .show();
    }

    public void commandArduino(String inputURL) throws IOException {
        SendDataController sendData = new SendDataController(inputURL, this);
        sendData.execute();
    }

    public void writeIPAndPort() {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("ip.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(ip);
            outputStreamWriter.write("\n");
            outputStreamWriter.write(port);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromfile() {
        try {
            InputStream inputStream = openFileInput("ip.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedString = new BufferedReader(inputStreamReader);

                ip = bufferedString.readLine();;
                port = bufferedString.readLine();

                if(ip == null){
                    ip = "14.186.175.175";
                }

                if(port == null){
                    port = "800";
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            ip = "14.186.175.175";
            port = "800";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
