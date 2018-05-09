package it.ncorti.emgvisualizer.DataAnalysis;

import android.content.res.AssetManager;
import android.util.Log;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.util.Arrays;
import java.util.List;

import it.ncorti.emgvisualizer.DataAnalysis.models.Classification;
import it.ncorti.emgvisualizer.DataAnalysis.models.Classifier;
import it.ncorti.emgvisualizer.DataAnalysis.models.TensorflowClassifier;
import it.ncorti.emgvisualizer.model.ImuDataPoint;

public class AnalyseData {

    private static AnalyseData instance = null;
    private static final int FEATURES = 500;
    private Classifier mClassifier;
    private TensorFlowInferenceInterface inferenceInterface;

    private AnalyseData(AssetManager assetManager) {
        loadModel(assetManager);
    }

    private void loadModel(AssetManager assetManager) {

        //inferenceInterface = new TensorFlowInferenceInterface(getAssets(), "tensorflow_lite_stroke_prediction.pb");
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("starting load model thread");*/
                try {
                    //add 2 classifiers to our classifier arraylist
                    //the tensorflow classifier and the keras classifier
//                    mClassifiers.add(
//                            TensorflowClassifier.create(getAssets(), "TensorFlow",
//                                    "opt_mnist_convnet.pb", "labels.txt", PIXEL_WIDTH,
//                                    "input", "output", true));

                    mClassifier = TensorflowClassifier.create(assetManager, "Keras",
                            "tensorflow_lite_stroke_prediction.pb", "labels.txt", 50,
                            "input_input", "output/Softmax", false);
                    //Log.d("classifier", mClassifier.name());


                    // conv2d_11_input, dense_6/Softmax"
                } catch (final Exception e) {
                    //if they aren't found, throw an error!
                    System.out.println("excepetion while loading thread : " + e.getMessage());
                    Log.wtf("Error",e);
                    throw new RuntimeException("Error initializing classifiers!", e);
                }
            //}
       // }).start();
    }

    public static AnalyseData getInstance(AssetManager assetManager) {
        if (instance == null) {
            instance = new AnalyseData(assetManager);
        }
        return instance;
    }
    public void predictData(List<ImuDataPoint> strokeData){
        int len = 0;
        if(strokeData.size() >= 50) {
             len = strokeData.size()-50;
        } else{
            System.out.println("Less Points");
        }
        float[] data = new float[strokeData.size()*10];
        int i=0;
        for(int j = len ;j <len+50 ;j++) {
            ImuDataPoint point = strokeData.get(j);
            System.err.println(" data " + Arrays.toString(point.getAccelerometerData()));
            data[i] = (float) point.getAccelerometerData()[0];
            data[i+50] = (float) point.getAccelerometerData()[1];
            data[i+100] = (float) point.getAccelerometerData()[2];

            data[i+150] = (float) point.getGyroData()[0];
            data[i+200] = (float) point.getGyroData()[1];

            data[i+250] = (float) point.getGyroData()[2];
            data[i+300] = (float) point.getOrientationData()[0];
            data[i+350] = (float) point.getOrientationData()[1];

            data[i+400] = (float) point.getOrientationData()[2];
            data[i+450] = (float) point.getOrientationData()[3];
            i++;

        }

        System.out.println("TAG LEN"+ data.length);
        predict(data);
        strokeData.clear();
    }
    public void predict(float[] data) {
        String text = "";
        //float[] input = new float[]{1.70026f,-0.0220337f,-0.196838f,-0.471069f,0.859558f,0.412598f,0.206055f,-2.75f,0.158787f,-0.367354f};

        //Log.d( "Stack message", Arrays.toString(input));
        //Log.d("classifier", mClassifier.name());
    System.out.println("@@@@@@@@@@@@@@ Inside Predict @@@@@@@@@@@@@@@");
        final Classification res = mClassifier.recognize(data); //
        if (res.getLabel() == null) {
            text += mClassifier.name() + ": ?\n";
        } else {
            //else output its name
            text += String.format("%s: %s, %f\n", mClassifier.name(), res.getLabel(),
                    res.getConf());
        }
//            }
        System.out.println(text);
    }

}
