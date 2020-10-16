import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(21,GPIO.OUT)
GPIO.output(21,GPIO.LOW)

while (1):
	time.sleep(2)
	f = open("Status.txt", "r")
	status = f.read()
	if(status == "on"):
		GPIO.output(21,GPIO.HIGH)
	else:
		GPIO.output(21,GPIO.LOW)
