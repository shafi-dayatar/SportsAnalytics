package com.example.rsk54.strokeanalysis.models;

/**
 * Created by darshanbagul on 14/07/17.
 */

public interface Classifier {
    String name();

    Classification recognize(final float[] pixels);
}