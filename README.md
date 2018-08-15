# Project Idea

This project is using Deep Learning and AI to improve a players game for racket sports, by providing constructive feedback and will notify the mistakes of players by comparing swings with other players. For now we will be developing deep learning models on Tennis sports, as it very dynamic game and consists of a good amount of difficulty compared to various racket sports including the number of strokes and swings possible in the game.

## [MYO Device](https://www.myo.com/)

[MYO](https://www.myo.com/) is a wearable technology. It is to be worn on the forearm. It consists of nine axis IMU (accelerometer for direction, a gyroscope for rotation) sensor, and it also has an EMG(electro Myogragpy) sensor which captures the muscle information of your forearm, generated through hand movements.


## Basic workflow of the developement:

## Data Gathering/Collection

We tried to contact the researches using the IMU data for stroke identication but were unable to get data from them so we tryied to generate data on own. To help this we created a tool/app which could get raw data from the device. To accomplish this we used api whichs [Nicola Corti](https://github.com/cortinico/) [github](https://github.com/cortinico/myonnaise).
Using the tool.

The tool was used extensively while we played tennis wearing the myo on our hand. For this we joined [AVAC tennis club](http://www.avac.us/) in San Jose.

Following is the image of the data that we are getting from accelerometer and gyroscope

![backhand accelerometer](data_analysis/acc-backand.png)

![forehand accelerometer](data_analysis/acc-forhand.png)


![backhand gyro](data_analysis/gyro-topspin-backhand.png)

![forehand gyro](data_analysis/gyro-topspin-forehand.png)

## Data Understanding

## Data Preparation

## Implementing Deep Learning Models

## Mobile App Working
