# Project Idea

This project uses Deep Learning and AI to improve a player's game for racket sports, by providing constructive feedback and notifying the mistakes of players by comparing their swings with other professional players. For now we will be developing deep learning models on Tennis sports, as it is a very dynamic game having significant amount of difficulty in it's strokes,swings and body movement as compared to any other racket sports. 

## [MYO Device](https://www.myo.com/)

[MYO](https://www.myo.com/) is a wearable technology, which is worn on a persons forearm. It consists of nine axis IMU (accelerometer for direction, a gyroscope for rotation) sensor, and it also has an EMG(electro Myogragpy) sensor which captures the muscle information of your forearm, generated through hand movements.


## Basic workflow of the developement:

## Data Gathering/Collection

I and my team tried to contact the researchers to obtain the "IMU data" for stroke identication but were unable to obtain it due to limited time and resources. This lead us to generate our own data by developing web and android application which fetches raw data from MYO. To accomplish this we are using API's like [Nicola Corti](https://github.com/cortinico/) [github](https://github.com/cortinico/myonnaise).

The web and android app is used extensively while we play tennis wearing MYO on our forearm. For this we joined [AVAC tennis club](http://www.avac.us/) in San Jose.

Following is the image of the data that we are getting from accelerometer and gyroscope:

![backhand accelerometer](data_analysis/acc-backand.png)

![forehand accelerometer](data_analysis/acc-forhand.png)


![backhand gyro](data_analysis/gyro-topspin-backhand.png)

![forehand gyro](data_analysis/gyro-topspin-forehand.png)

## Data Understanding

## Data Preparation

## Implementing Deep Learning Models

## Mobile App Working
