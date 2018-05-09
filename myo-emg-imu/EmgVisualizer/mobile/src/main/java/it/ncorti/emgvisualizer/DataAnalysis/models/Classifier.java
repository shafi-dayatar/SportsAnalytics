package it.ncorti.emgvisualizer.DataAnalysis.models;


public interface Classifier {
    String name();

    Classification recognize(final float[] pixels);

}