#include <SoftwareSerial.h>

int RFIDResetPin = 13;

SoftwareSerial mySerial(8, 9); // RX TX

void setup(){
  
Serial.begin(9600); // Bluetooth
mySerial.begin(9600); // RFID
pinMode(RFIDResetPin, OUTPUT);
digitalWrite(RFIDResetPin, HIGH);      
}


void loop(){
  char tagString[13];
  int index = 0;
  boolean reading = false;
  boolean flag = false;

  while(mySerial.available()){ // My RFID is available 
   
    flag = true;
    int readByte = mySerial.read(); // read from my RFID
    if(readByte == 2) reading = true;
    if(readByte == 3) reading = false; 
    if(reading && readByte != 2 && readByte != 10 && readByte != 13){
      
      tagString[index] = readByte;
      index ++;
    }
  }
  if(flag == true)
    Serial.println(tagString); // Send it through the BT
                                                                       
  clearTag(tagString); 
  resetReader(); 
}
void resetReader(){
  
  digitalWrite(RFIDResetPin, LOW);
  digitalWrite(RFIDResetPin, HIGH);
  delay(150);
  
}

void clearTag(char one[]){
  
  for(int i = 0; i < strlen(one); i++){
    
    one[i] = 0;
  }
}
