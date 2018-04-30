package it.ncorti.emgvisualizer.model;

public class ImuDataPoint extends RawDataPoint{

    private static final double MYOHW_ORIENTATION_SCALE = 16384.0f; ///< See myohw_imu_data_t::orientation
    private static final double MYOHW_ACCELEROMETER_SCALE = 2048.0f; ///< See myohw_imu_data_t::accelerometer
    private static final double MYOHW_ACCEMYOHW_GYROSCOPE_SCALELEROMETER_SCALE = 16.0f; ///< See myohw_imu_data_t::gyroscope
    private final double[] mOrientationData;
    private final double[] mAccelerometerData;
    private final double[] mGyroData;
    private long timestamp;


    public ImuDataPoint( byte[] imu_data,long timestamp) {

        this.timestamp = timestamp;
        ByteHelper byteHelper = new ByteHelper(imu_data);
        mOrientationData = new double[4];
        for (int i = 0; i < 4; i++)
            mOrientationData[i] = byteHelper.getUInt16() / MYOHW_ORIENTATION_SCALE;

        mAccelerometerData = new double[3];
        for (int i = 0; i < 3; i++)
            mAccelerometerData[i] = byteHelper.getUInt16() / MYOHW_ACCELEROMETER_SCALE;

        mGyroData = new double[3];
        for (int i = 0; i < 3; i++)
            mGyroData[i] = byteHelper.getUInt16() / MYOHW_ACCEMYOHW_GYROSCOPE_SCALELEROMETER_SCALE;
    }

    /**
     * Values range form -1.0 to 1.0<br>
     * Format: [w,x,y,z]
     *
     * @see <a href="https://github.com/thalmiclabs/myo-bluetooth/blob/master/myohw.h">Myo protocol specification</a>
     */
    public double[] getOrientationData() {
        return mOrientationData;
    }

    /**
     * Values range from -1.0 to 1.0 <br>
     * Format: [?,?,?]
     *
     * @see <a href="https://github.com/thalmiclabs/myo-bluetooth/blob/master/myohw.h">Myo protocol specification</a>
     */
    public double[] getAccelerometerData() {
        return mAccelerometerData;
    }

    /**
     * Values range from -? to ? <br>
     * Format: [?,?,?]
     *
     * @see <a href="https://github.com/thalmiclabs/myo-bluetooth/blob/master/myohw.h">Myo protocol specification</a>
     */
    public double[] getGyroData() {
        return mGyroData;
    }

    public static String format(double[] data) {
        StringBuilder builder = new StringBuilder();
        for (double d : data)
            builder.append(String.format("%+.3f", d)).append(" ");
        return builder.toString();
    }

}