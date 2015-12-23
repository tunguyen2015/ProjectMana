#include <SPI.h>
#include <Ethernet.h>
#include <Servo.h> 

/*
  Blink
  Turns on an LED on for one second, then off for one second, repeatedly.

  Most Arduinos have an on-board LED you can control. On the Uno and
  Leonardo, it is attached to digital pin 13. If you're unsure what
  pin the on-board LED is connected to on your Arduino model, check
  the documentation at http://www.arduino.cc

  This example code is in the public domain.

  modified 8 May 2014
  by Scott Fitzgerald
 */

int amount = 0;

// light Pin
int LED1 = 13; // Use the onboard Uno LED
int LED2 = 12;

// sensors Pin and boolean
int isObstaclePin2 = 1; // This is our input pin
int isObstaclePin1 = 0; // This is our input pin 1
int isObstacle2 = HIGH;  // HIGH MEANS NO OBSTACLE
int isObstacle1 = HIGH;

// sound Pin
int buzzerPin = 9;

// Boolean state
bool fullLight = true;
bool dimLight = false;
bool alert = true;
bool off = false;
int thresh;

Servo myservo; // create servo object to control a servo
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED }; //physical mac address
byte ip[] = { 192, 168, 100, 20 }; // fixed IP addr in LAN
byte gateway[] = { 192, 168, 100, 1 }; // internet access via router
byte subnet[] = { 255, 255, 255, 0 }; //subnet mask
EthernetServer server(80); //server port
String readString;

void alertOn(){
  alert = true;
}

void alertOff(){
  alert = false;
}

void fullLightOn(){

}

void fullLightOff(){
  fullLight = false;
}

void dimLightPress(){
   fullLight = false;
   dimLight = true;
  }

void offPress(){
  fullLight = false;
  dimLight = false;
  alert =false;
  }

void setup()
{
  pinMode(LED1, OUTPUT);
  pinMode(LED2, OUTPUT);
  pinMode(buzzerPin, OUTPUT);
  
  Ethernet.begin(mac, ip, gateway, subnet);
  server.begin();
  Serial.begin(9600);
}

void loop(){
  getData(); 

  // Execute
  if(!off){
    isObstacle1 = analogRead(isObstaclePin1);
    isObstacle2= analogRead(isObstaclePin2);
    thresh = 500;

    if(isObstacle1<thresh && isObstacle2>thresh)
    {
      while(isObstacle1 < thresh){
            getData();
            updatePin();
            isObstacle1 = analogRead(isObstaclePin1);
            isObstacle2= analogRead(isObstaclePin2);
        if(isObstacle2 < thresh && isObstacle1 > thresh){
          amount++;
          Serial.println("added");
          Serial.println(amount);
          delay(700);
          break;
        }
      }
    } else if(isObstacle1>thresh && isObstacle2<thresh)
    {
      while(isObstacle2 < thresh){
            getData();
            updatePin();
            isObstacle1 = analogRead(isObstaclePin1);
            isObstacle2= analogRead(isObstaclePin2);
        if(isObstacle1 < thresh && isObstacle2 > thresh){
          if(amount != 0){
          amount--;
          }
          Serial.println("deleted");
          Serial.println(amount);
          delay(700);
          break;          
        }
      }
    }

    updatePin();

// end if(off)
  }else {
    digitalWrite(buzzerPin, LOW);
    digitalWrite(LED1, LOW);
    digitalWrite(LED2, LOW);
    fullLight = false;
    dimLight = false;
    alert =false;
  }
}

void getData(){
  EthernetClient client = server.available();
  if (client) {
    if (client.available()) {
      char c = client.read();
      
      if (readString.length() < 100) {
      readString += c;
      }
      
      if (c == '\n') {
        client.stop();

      if (readString.indexOf( "?fullLightOn") > 0){
        fullLight = true;
        dimLight = false;
        Serial.println("fullLightOn");
      }
  
      if (readString.indexOf( "?fullLightOff") > 0){
        fullLight = false;
        Serial.println("fullLightOff");
      }
  
      // Dim light case
      if (readString.indexOf( "?dimLightOn") > 0){
        fullLight = false;
        dimLight = true;
        Serial.println("dimLightOn");
      }
  
      if (readString.indexOf( "?dimLightOff") > 0){
        dimLight = false;
        Serial.println("dimLightOff");
      }
  
      // Alert case
      if (readString.indexOf( "?alertOn") > 0){
        alert = true;
        Serial.println("alertOn");
      }
      if (readString.indexOf( "?alertOff") > 0){
        alert = false;
        Serial.println("alertOff");
      }
  
      // System on/off case
      if (readString.indexOf( "?turnOff") > 0){
        fullLight = false;
        dimLight = false;
        alert =false;
        Serial.println("turnOff");
      }
        readString=""; //clearing string for next read
      }
    }
  }
}

void updatePin(){
  if(alert){
      if(isObstacle1<thresh || isObstacle2<thresh){        
        digitalWrite(buzzerPin, HIGH);
      }else{
      digitalWrite(buzzerPin, LOW);
      }
   }else{
        digitalWrite(buzzerPin, LOW);
   }

   if(amount == 0){
      digitalWrite(LED1, LOW);
      digitalWrite(LED2, LOW);
   } else {

   if(fullLight || amount >= 5){
        digitalWrite(LED1, HIGH);
        digitalWrite(LED2, HIGH);
   } else if(dimLight) {
        digitalWrite(LED1, LOW);
        digitalWrite(LED2, HIGH);
   } else {
        digitalWrite(LED1, LOW);
        digitalWrite(LED2, LOW);
   }

    }
}


