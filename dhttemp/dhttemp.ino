#include <dht.h>

#include <dht.h>
#include <Wire.h>
#include <LCD.h>
#include <LiquidCrystal_I2C.h>
#define dht_dpin A1 //Pino DATA do Sensor ligado na porta Analogica A1
LiquidCrystal_I2C lcd(0x27, 2, 1, 0, 4, 5, 6, 7, 3, POSITIVE); // Addr, En, Rw, Rs, d4, d5, d6, d7, backlighpin, polarity



dht DHT; //Inicializa o sensor
unsigned int flip = 0;
const int ledAzul = 9;      //constante ledAzul refere-se ao pino digital 9.
const int ledVerde = 10;     //constante ledVerde refere-se ao pino digital 10.
const int ledVermelho = 11; //constante ledVermelho refere-se ao pino digital 11.

void setup()
{
  //Serial.begin(9600);
  pinMode(13, OUTPUT);
  pinMode(ledAzul,OUTPUT);   
  pinMode(ledVerde,OUTPUT);   
  pinMode(ledVermelho,OUTPUT);    
  delay(1000);//Aguarda 1 seg antes de acessar as informações do sensor

  lcd.begin(16,2);
  //lcd.backlight();
  lcd.setCursor(0, 0);        //First column, first row
  lcd.print("Hello world");
  //  lcd.setCursor(0, 1);        //First column, second row
  //  lcd.print("Row number: ");
  //  lcd.setCursor(12, 1);       //12th column, second row
  //  lcd.print("2");
  //  lcd.blink();                //start blinking cursor
  delay (1000);               //delay 3 seconds
}

void loop()
{
  //int r, g, b;
  //for (r = 0; r < 256; r++) { 
  //  analogWrite(ledVermelho, r);
  //  delay(10);
  //} 
  // 
  //  for (g = 0; g < 256; g++) { 
  //   analogWrite(ledVerde, g);
  //   delay(10);
  // } 
  // 
  // for (b = 0; b < 256; b++) { 
  //   analogWrite(ledAzul, b);
  //   delay(10);
  // } /
  DHT.read11(dht_dpin); //Lê as informações do sensor

  int blueTemp= 0; 
  int greenTemp= 0; 
  int redTemp= 0;
//  if(DHT.temperature<0){
//    analogWrite(ledAzul, 255);
//  }
//  else if(DHT.temperature>0&&DHT.temperature<=45){
//    blueTemp= map(DHT.temperature, 0, 45, 255, 0);
//    analogWrite(ledAzul, blueTemp);
//  }
//  else if(DHT.temperature>45){
//    analogWrite(ledAzul, 0);
//  }
//  if(DHT.temperature<15){
//    analogWrite(ledVerde, 0);
//  }
//  else if(DHT.temperature>15&&DHT.temperature<=35){
//    greenTemp = map(DHT.temperature, 15, 35, 1, 254);
//    analogWrite(ledVerde, greenTemp);
//  }
//  else if(DHT.temperature>35&&DHT.temperature<=75){
//    greenTemp = map(DHT.temperature, 35, 75, 255, 0);
//    analogWrite(ledVerde, greenTemp);
//  }
//  else if(DHT.temperature>75){
//    analogWrite(ledVerde, 0);
//  }
//  if(DHT.temperature<45){
//    analogWrite(ledVermelho, 0);
//  }
//  else if(DHT.temperature>=45){
//    redTemp= map(DHT.temperature, 45, 90, 1, 255);
//    analogWrite(ledVermelho, redTemp);
//  }
//  else if(DHT.temperature>90){
//    analogWrite(ledAzul, 255);
//        analogWrite(ledVermelho, 255);
//  }

if (DHT.temperature>28){
         analogWrite(ledVermelho, 255);
                  analogWrite(ledVerde, 0);
}else{
         analogWrite(ledVermelho, 0);
         analogWrite(ledVerde, 255);
}
  



  if ( flip & 1 )
  {
    digitalWrite(13, HIGH);
  } 
  else {
    digitalWrite(13, LOW);
  }

  flip++;
  //Serial.print("Umidade = ");
  //Serial.print(DHT.humidity);
  //Serial.print(" %  ");
  //Serial.print("Temperatura = ");
  //Serial.print(DHT.temperature); 
  //Serial.println(" Celsius  ");

  //lcd.begin(16,2);
  //lcd.backlight();
  if ((flip % 15) > 7 )
  {
    lcd.setCursor(0, 0); 
    lcd.print("Humidade     ");
    lcd.setCursor(0, 1);
    lcd.print(DHT.humidity);
  }
  else{
    lcd.setCursor(0, 0);        //First column, first row
    lcd.print("Temperatura");
    lcd.setCursor(0, 1);        //First column, second row
    lcd.print(DHT.temperature);
    //lcd.setCursor(12, 1);       //12th column, second row
    //lcd.print("2");
  }

  delay(2000);  //Não diminuir muito este valor. O ideal é a leitura a cada 2 segundos
}








