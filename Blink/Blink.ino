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
#include "etherShield.h"
#include "ETHER_28J60.h"

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
int buzzerPin;

// Boolean state
bool fullLight = true;
bool dimLight = false;
bool alert = false;
bool off = false;
int thresh;

static uint8_t mac[6] = {0xAA, 0xBB, 0xCC, 0xDD, 0xBB, 0xAA};
static uint8_t ip[4] = {192, 168, 0, 15}; // need to check from router
static uint16_t port = 80;
ETHER_28J60 e;

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
  e.setup(mac, ip, port);
  pinMode(LED1, OUTPUT);
  pinMode(LED2, OUTPUT);
  Serial.begin(9600);
  pinMode(button1,INPUT);

}

void loop(){
  char* params;
  // Read from Android
  if (params = e.serviceRequest())
  {
    // Full light case
    if (strcmp(params, "?cmd=fullLightOn") == 0)
    {
      fullLight = true;
      dimLight = false;
    }

    if (strcmp(params, "?cmd=fullLightOff") == 0)
    {
      fullLight = false;
    }

    // Dim light case
    if (strcmp(params, "?cmd=dimLightOn") == 0)
    {
      fullLight = false;
      dimLight = true;
    }

    if (strcmp(params, "?cmd=dimLightOff") == 0)
    {
      dimLight = false;
    }

    // Alert case
    if (strcmp(params, "?cmd=alertOn") == 0)
    {
      alert = true;
    }
    if (strcmp(params, "?cmd=alertOff") == 0)
    {
      alert = false;
    }

    // System on/off case
    if (strcmp(params, "?cmd=turnOff") == 0)
    {
      fullLight = false;
      dimLight = false;
      alert =false;
    }
    e.respond();
  }


  // Execute
  if(!off){
    isObstacle1 = analogRead(isObstaclePin1);
    isObstacle2= analogRead(isObstaclePin2);
    thresh = 500;

    if(isObstacle1<thresh && isObstacle2>thresh)
    {
      amount++;
      Serial.println("added");
      Serial.println(amount);
      delay(900);
    }

    if(isObstacle1>thresh && isObstacle2<thresh)
    {
      if(amount != 0){
      amount--;
      }
      Serial.println("deleted");
      Serial.println(amount);
      delay(900);
    }

    if(alert){

      if(isObstacle1<thresh || isObstacle2<thresh)
      {
        digitalWrite(buzzerPin, LOW);
      }

    } else{
      digitalWrite(buzzerPin, HIGH);
    }

    if(amount == 0){
      digitalWrite(LED1, HIGH);
      digitalWrite(LED2, HIGH);
    } else {

      if(fullLight){
        digitalWrite(LED1, LOW);
        digitalWrite(LED2, LOW);
      } else if(dimLight) {
        digitalWrite(LED1, LOW);
        digitalWrite(LED2, HIGH);
      } else {
        digitalWrite(LED1, HIGH);
        digitalWrite(LED2, HIGH);
      }

    }

// end if(off)
  }else {
    digitalWrite(buzzerPin, HIGH);
    digitalWrite(LED1, HIGH);
    digitalWrite(LED2, HIGH);
    fullLight = false;
    dimLight = false;
    alert =false;
  }


}
