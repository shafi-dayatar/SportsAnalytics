package com.example.rsk54.strokeanalysis.models;


public interface Classifier {
    String name();

    Classification recognize(final float[] pixels);

}