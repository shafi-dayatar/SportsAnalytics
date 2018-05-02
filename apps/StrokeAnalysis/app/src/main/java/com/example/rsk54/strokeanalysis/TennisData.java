package com.example.rsk54.strokeanalysis;

/**
 * Created by rsk54 on 5/1/2018.
 */

class TennisData {

    private Double timstamp;
    private Double orientaion_x;
    private Double orientaion_y;
    private Double orientaion_z;
    private Double orientaion_w;
    private Double acc_x;
    private Double acc_y;
    private Double acc_z;
    private Double gyro_x;
    private Double gyro_y;
    private Double gyro_z;

    public Double getTimstamp() {
        return timstamp;
    }

    public void setTimstamp(Double timstamp) {
        this.timstamp = timstamp;
    }

    public Double getOrientaion_x() {
        return orientaion_x;
    }

    public void setOrientaion_x(Double orientaion_x) {
        this.orientaion_x = orientaion_x;
    }

    public Double getOrientaion_y() {
        return orientaion_y;
    }

    public void setOrientaion_y(Double orientaion_y) {
        this.orientaion_y = orientaion_y;
    }

    @Override
    public String toString() {
        return "TennisData{" +
                "timstamp=" + timstamp +
                ", orientaion_x=" + orientaion_x +
                ", orientaion_y=" + orientaion_y +
                ", orientaion_z=" + orientaion_z +
                ", orientaion_w=" + orientaion_w +
                ", acc_x=" + acc_x +
                ", acc_y=" + acc_y +
                ", acc_z=" + acc_z +
                ", gyro_x=" + gyro_x +
                ", gyro_y=" + gyro_y +
                ", gyro_z=" + gyro_z +
                '}';
    }

    public Double getOrientaion_z() {
        return orientaion_z;
    }

    public void setOrientaion_z(Double orientaion_z) {
        this.orientaion_z = orientaion_z;
    }

    public Double getOrientaion_w() {
        return orientaion_w;
    }

    public void setOrientaion_w(Double orientaion_w) {
        this.orientaion_w = orientaion_w;
    }

    public Double getAcc_x() {
        return acc_x;
    }

    public void setAcc_x(Double acc_x) {
        this.acc_x = acc_x;
    }

    public Double getAcc_y() {
        return acc_y;
    }

    public void setAcc_y(Double acc_y) {
        this.acc_y = acc_y;
    }

    public Double getAcc_z() {
        return acc_z;
    }

    public void setAcc_z(Double acc_z) {
        this.acc_z = acc_z;
    }

    public Double getGyro_x() {
        return gyro_x;
    }

    public void setGyro_x(Double gyro_x) {
        this.gyro_x = gyro_x;
    }

    public Double getGyro_y() {
        return gyro_y;
    }

    public void setGyro_y(Double gyro_y) {
        this.gyro_y = gyro_y;
    }

    public Double getGyro_z() {
        return gyro_z;
    }

    public void setGyro_z(Double gyro_z) {
        this.gyro_z = gyro_z;
    }
}
