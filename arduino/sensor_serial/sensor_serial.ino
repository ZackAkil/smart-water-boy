
const int sensor = A0;
int sensor_value = 0;

void setup()
{
  Serial.begin(9600); 
}


void loop()
{
  if(Serial.available())
  {
    delay(5); 
    Serial.read();
    delay(5); 

    sensor_value = analogRead(sensor);
    // add char catsing when sensor attached
    Serial.print(map(sensor, 1, 1023, 1, 255));
  }
}
