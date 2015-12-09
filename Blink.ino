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
int LED1 = 13; // Use the onboard Uno LED
int LED2 = 12;
int isObstaclePin2 = 7; // This is our input pin
int isObstaclePin1 = 6; // This is our input pin 1
int thresh;
int isObstacle2 = HIGH;  // HIGH MEANS NO OBSTACLE
int isObstacle1 = HIGH;
bool step = false;
bool off;

void setup() {
  pinMode(LED1, OUTPUT);
  pinMode(LED2, OUTPUT);
  pinMode(isObstaclePin2, INPUT);
  pinMode(isObstaclePin1, INPUT);
  Serial.begin(9600);

}

/*
void loop() {
  isObstacle2 = HIGH;
  isObstacle1 = HIGH;
  step = false;
  isObstacle2 = digitalRead(isObstaclePin2);
  isObstacle1 = digitalRead(isObstaclePin1);

  if ((isObstacle2 == LOW))
  {
    Serial.println("OBSTACLE!! 2, OBSTACLE!! 2");
    while(isObstacle2 == LOW){

      isObstacle1 = digitalRead(isObstaclePin1);
      isObstacle2 = digitalRead(isObstaclePin2);

      if(isObstacle1 == LOW){
        while(true){
          isObstacle2 = digitalRead(isObstaclePin2);
          if(isObstacle2 == HIGH){
            step = true;
            break;
          }
          isObstacle1 = digitalRead(isObstaclePin1);
          if(isObstacle1 == HIGH){
            step = false;
            break;
          }
        }
      }
    }

    if(step){
      Serial.println("added");
      amount++;
    }
    Serial.println(amount);
  } else if ((isObstacle1 == LOW))
  {
    Serial.println("OBSTACLE!! 1, OBSTACLE!! 1");
    while(isObstacle1 == LOW) {

      isObstacle1 = digitalRead(isObstaclePin1);
      isObstacle2 = digitalRead(isObstaclePin2);

      if(isObstacle2 == LOW){
        while(true){

          isObstacle1 = digitalRead(isObstaclePin1);
          if(isObstacle1 == HIGH){
            step = true;
            break;
          }

          isObstacle2 = digitalRead(isObstaclePin2);
          if(isObstacle2 == HIGH){
            step = false;
            break;
          }
        }
      }
    }

    if(step){
        if(amount > 0){
          Serial.println("delete");
          amount--;
        }
      }
      Serial.println(amount);
    }

  if(amount == 0){
    digitalWrite(LED1, HIGH);
    digitalWrite(LED2, HIGH);
  } else {
    digitalWrite(LED1, LOW);
    digitalWrite(LED2, LOW);
  }
  delay(200);

}
*/

void loop(){
  isObstacle1 = analogRead(isObstaclePin1);
  isObstacle2 = analogRead(isObstaclePin2);
  thresh = 300;

  if(isObstacle1<thresh && isObstacle2>thresh){
    amount++;                                      //  INCREMENT
    delay(900);
  }

  if(isObstacle1>thresh && isObstacle2<thresh){
    amount--;                                       // DECREMENT
    delay(900);
  }
}
