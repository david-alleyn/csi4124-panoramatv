package simModel;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;
import java.lang.Object;
import cern.colt.PersistentObject;

import cern.jet.random.Empirical;
import cern.jet.random.engine.MersenneTwister;

/**
 *
 * @author ODUNADE SEGUN
 */
public class Emp {

    public static void main(String[] args) {
        double randomValue;
        double[] histogram = {
           10, 25, 20, 7, 5, 17, 15
        };
        double scaleFactor = 10.0; // Width of the histogram bin
        double xMax = 70.0; // maximum data value
// Create Empirical Object
        Empirical empDM = new Empirical(histogram, Empirical.LINEAR_INTERPOLATION, new MersenneTwister());
// Lets get defining points on the CDF from empDM
        for (int i = 0; i <= histogram.length; i++) {
            System.out.println(i + ", " + (i * scaleFactor) + ", " + empDM.cdf(i));
        }
// Get empDM to generate 10 random numbers
        for (int i = 0; i < 20; i++) {
            randomValue = xMax * empDM.nextDouble();
            System.out.println(randomValue);
        }
    }
}
