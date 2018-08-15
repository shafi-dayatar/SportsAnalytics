

This is one of my best projects so far and great application for AI and Deep Learning.

So our goal is to identify what kind of swing a player has played from the real-time data collected from MYO armband(https://www.myo.com/). This device is a wearable technology, and to be worn on the forearm. The IMU(accelerometer for direction, a gyroscope for rotation) sensors in the device are used to identify the stroke. It also gives an EMG(electro Myogragpy) data which is used to identify the grip for holding the racquet by a player.

As this is completely new data, I wasn't able to manage to get data from the internet. I had to generate data on my own. So for this, I created a simple data collection tools which is used to gather the data using the api developed by Nicola Corti(https://github.com/cortinico/) github(https://github.com/cortinico/myonnaise), while I play tennis wearing this device on my hand. Now I went to one of the open tennis courts nearby to get the swing data.


I tried to figure what kind of data am I getting, do I see any correlation between data. 
Following is the image of the data that I am getting from accelerometer and gyroscope

![backhand accelerometer](data_analysis/acc-backand.png)

![forehand accelerometer](data_analysis/acc-forhand.png)


![backhand gyro](data_analysis/gyro-topspin-backhand.png)

![forehand gyro](data_analysis/gyro-topspin-forehand.png)

