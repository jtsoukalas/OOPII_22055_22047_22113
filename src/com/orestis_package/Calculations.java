package com.orestis_package;

public abstract class Calculations {

    public static float normaliseFeature(float term, int mode) {
        //mode defines the type of normalisation
        int min, max;

        if (mode == 0) {       //wiki normalisation
            min = 0;
            max = 10;
        } else {
            if (mode == 1) {       //weather normalisation
                min = 184;
                max = 331;
            } else {
                min = 0;
                max = 100;
            }
        }
        return (term - min) / (max - min);
    }

    private static double geodesicDistance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            return (dist);
        }
    }


    public static double normaliseGeodesicDistance(double distance){
        //double maxDistance = geodesicDistance
        return distance;         //geodesic distance Athens Sydney
    }
}
