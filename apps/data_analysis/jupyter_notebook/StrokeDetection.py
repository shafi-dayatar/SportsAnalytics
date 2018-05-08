import numpy as np
import scipy as sp
from scipy import signal
import pandas as pd
import matplotlib
import matplotlib.pyplot as plt

class StrokeDetection:

    @staticmethod
    def identifyStroke(time, accleration, threshold =1, hpCutOff=.001, \
        lpCutOff=5.0, sampleFrequecy = 50, number_of_pass = 1, axis=1, plot=True):
        acc_mag= np.sqrt(np.square(accleration).sum(axis=axis))
        b, a =  signal.butter(number_of_pass, 
                              (2*hpCutOff) / sampleFrequecy, 'high', 
                              analog=False)
        hp_filt = signal.filtfilt(b, a, acc_mag)
        hp_filt = np.absolute(hp_filt); 
        b, a = signal.butter(number_of_pass, ((2*lpCutOff)/sampleFrequecy), 'low')
        lp_filt = signal.filtfilt(b, a, hp_filt)
        stationary = lp_filt > threshold;
        if(plot):
            f, (ax1,  ax2, ax3, ax4) = plt.subplots(4, sharex=True, figsize=(18,20))
            ax1.plot(time, accleration,)
            ax1.legend(['X','Y','Z'],shadow=True, fancybox=True, loc="upper right");
            
            ax2.plot(time, acc_mag, 'r', label="Magnitude(sqrt(x2+y2+z2))");
            ax2.legend(shadow=True, fancybox=True, loc="upper right");
            
            ax3.plot(time, hp_filt, 'b', label="High Pass Filtered");
            ax3.legend(shadow=True, fancybox=True, loc="upper right");
            
            ax4.plot(time, lp_filt, 'r', label="Low Pass Filtered");
            ax4.plot(time, stationary, 'b', label="Swing");
            ax4.legend(shadow=True, fancybox=True, loc="upper right");
            
        return stationary

    @staticmethod
    def strokeIndexes(swing_data ):
        swing_diff = np.diff(swing_data+0)
        swingStartIndex = np.where(swing_diff == 1)[0]
        swingEndIndex = np.where(swing_diff == -1)[0]
        swingMidIndex = swingStartIndex + swingEndIndex
        swingMidIndex = np.divide(swingMidIndex,2)
        
        return  swingMidIndex,  swingStartIndex, swingEndIndex, swing_data

    @staticmethod
    def plotSwingData(time, gyr, acc, quat, swingPresent, startPos, endPos):
        fig = plt.figure('Position', figsize=(18,10));
        fig.subplots_adjust(hspace=.7)

        plt.subplot(411);
        plt.plot(time[startPos:endPos], gyr[startPos:endPos]);
        #plt.plot(time[startPos:endPos], swingPresent[startPos:endPos], ':m');
        plt.title('Gyroscope');
        plt.xlabel('Time (s)');
        plt.ylabel('Angular velocity (^\circ/s)');
        plt.legend(list(gyr),shadow=True, fancybox=True, loc="upper right");


        plt.subplot(412);
        plt.plot(time[startPos:endPos], acc[startPos:endPos]);
        plt.plot(time[startPos:endPos], swingPresent[startPos:endPos], ':m');
        plt.title('Accelerometer');
        plt.xlabel('Time (s)');
        plt.ylabel('Acceleration (g)');
        plt.legend(list(acc), shadow=True, fancybox=True, loc="upper right");


        plt.subplot(413);
        plt.plot(time[startPos:endPos], quat[startPos:endPos]);
        plt.plot(time[startPos:endPos], swingPresent[startPos:endPos], ':m',);
        plt.title('Quaternions');
        plt.xlabel('Time (s)');
        plt.ylabel('Quaternions');
        plt.legend(list(quat), shadow=True, fancybox=True, loc="upper right");

    @staticmethod
    def plot_findings(figName, x, y, xlabel, ylabel, legendlabel, figSize=(18,4)):
        plt.figure(figName, figsize=figSize);
        plt.plot(x, y)
        plt.title(figName);
        plt.xlabel(xlabel);
        plt.ylabel(ylabel);
        plt.legend(legendlabel, shadow=True, fancybox=True, loc="upper right");

    @staticmethod
    def cal_tran_acc(acc, quat):
        #quat = quat.as_matrix()
        ##Rotate body accelerations to Earth frame
        acc = Quaternion.quaternRotate( acc, Quaternion.quaternConj(quat));
        return acc

    @staticmethod
    def derive_acc_to_vel(acc, quat, swingPresent):
        trans_acc = StrokeDetection.cal_tran_acc(acc, quat)
        velocity = np.zeros(acc.shape)

        for t in range(1,len(velocity)):
            velocity[t,:] = velocity[t - 1,:] + trans_acc[t,:]# * samplePeriod;
            if(swingPresent[t] == 0):
                velocity[t,:] = [0, 0, 0]     # force zero velocity when foot stationary
        #velDrift = np.zeros(vel.shape);
        #plot_findings("Velocity", time[startPos:endPos], 
        #                      vel[startPos:endPos], "Time(ms)", "Velocity(m/s)", ('X', 'Y', 'Z'))

        #velocity_norm =  np.linalg.norm(vel, axis=1)
        #plot_findings("Racquet Velocity", time[startPos:endPos], 
        #                      velocity_norm[startPos:endPos] * 3.6, "Time(ms)", 'Velocity (km/h)', ('X', 'Y', 'Z'))
        return trans_acc, velocity

    @staticmethod
    def derive_vel_to_pos(acc, quat, swingEndIndex, swingStartIndex, swingPresent):
        trans_acc, velocity = StrokeDetection.derive_acc_to_vel(acc, quat, swingPresent)
        velDrift = np.zeros(velocity.shape)
        for i in range(0, len(swingEndIndex)):
            rateDiff = (swingEndIndex[i] - swingStartIndex[i])
            driftRate = np.divide(velocity[swingEndIndex[i] - 1, :], rateDiff)
            enum = np.arange(0, (swingEndIndex[i] - swingStartIndex[i])-1)
            enum = np.array([enum, enum, enum])
            drift = enum.T * driftRate
            velDrift[swingStartIndex[i]:swingEndIndex[i]-1, :] = drift

        velocity = velocity - velDrift    

        pos = np.zeros(velocity.shape)
        for t in range(2, len(pos)):
            pos[t] = pos[t-1] + velocity[t]   

        return trans_acc, velocity, pos
        # integrate velocity to yield position         
        #plot_findings("Velocity Drift", time[startPos:endPos], 
        #                      vel[startPos:endPos], "Time(ms)", "Velocity(m/s)", ('X', 'Y', 'Z'))



class Quaternion:
    @staticmethod
    def quaternProd(a, b):
        ab = np.zeros(a.shape);
        w, x, y, z = 0, 1, 2, 3
        ab[:,w] = a[:,w]*b[:,w] - a[:,x]*b[:,x] - a[:,y]*b[:,y] - a[:,z]*b[:,z];
        ab[:,x] = a[:,w]*b[:,x] + a[:,x]*b[:,w] + a[:,y]*b[:,z] - a[:,z]*b[:,y];
        ab[:,y] = a[:,w]*b[:,y] - a[:,x]*b[:,z] + a[:,y]*b[:,w] + a[:,z]*b[:,x];
        ab[:,z] = a[:,w]*b[:,z] + a[:,x]*b[:,y] - a[:,y]*b[:,x] + a[:,z]*b[:,w];
        return ab;

    @staticmethod
    def quaternConj(q):
        ab = np.zeros(q.shape);
        w, x, y, z = 0, 1, 2, 3
        ab[:,w] = q[:,w]
        ab[:,x] = -q[:,x]
        ab[:,y] = -q[:,y]
        ab[:,z] = -q[:,z]
        return ab

    @staticmethod
    def quaternRotate(v, q):
        (row, col) = v.shape;
        quaterPoint = np.append(np.zeros((row,1)), v, 1)
        #v0XYZ = quaternProd(quaternProd(q, acc), quaternConj(q));
        v0XYZ = Quaternion.quaternProd(q, Quaternion.quaternProd(quaterPoint, Quaternion.quaternConj(q))); 
        return v0XYZ[:, 1:4];


