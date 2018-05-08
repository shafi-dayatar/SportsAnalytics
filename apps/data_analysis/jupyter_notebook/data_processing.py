import numpy as np
import scipy as sp
from scipy import signal
import pandas as pd
import matplotlib
import matplotlib.pyplot as plt


TIMEDIFF = 10

def load_file(file_name, stroke_type):
    data = pd.read_csv(file_name, sep=',',header=0)
    data["timestamp"] = TIMEDIFF
    data["stroke"] = stroke_type
    return data

def merge_data(data_set):
    merge_data =  pd.concat(data_set, ignore_index=True)
    merge_data["timestamp"] = merge_data["timestamp"].cumsum()
    return merge_data
    
def get_gyro_data(data):
    return data[['GyroX', 'GyroY', 'GyroZ']];
    
def get_acc_data(data):
    return data[['AccX', 'AccY', 'AccZ']]

def get_orient_data(data):
    return data[['orientation_w', 'orientation_x', 'orientation_y', 'orientation_z']]
    
def get_time_data(data):
    return data['timestamp']

def get_y(data):
    return data["stroke"]

def get_lables(data):
    return get_time_data(data), get_gyro_data(data), get_acc_data(data), get_orient_data(data)

