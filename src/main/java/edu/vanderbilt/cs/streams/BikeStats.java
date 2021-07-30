package edu.vanderbilt.cs.streams;

import edu.vanderbilt.cs.streams.BikeRide.LatLng;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BikeStats {

    private BikeRide ride;

    public BikeStats(BikeRide ride) {
        this.ride = ride;
    }


    /**
     * @ToDo:
     *
     * Create a stream of DataFrames representing the average of the
     * sliding windows generated from the given window size.
     *
     * For example, if a windowSize of 3 was provided, the BikeRide.DataFrames
     * would be fetched with the BikeRide.fusedFramesStream() method. These
     * frames would be divided into sliding windows of size 3 using the
     * StreamUtils.slidingWindow() method. Each sliding window would be a
     * list of 3 DataFrame objects. You would produce a new DataFrame for
     * each window by averaging the grade, altitude, velocity, and heart
     * rate for the 3 DataFrame objects.
     *
     * For each window, you should use the coordinate of the first DataFrame in the window
     * for the location.
     *
     * @param windowSize
     * @return
     */

    public Stream<BikeRide.DataFrame> averagedDataFrameStream(int windowSize){

        List<BikeRide.DataFrame> dfs = this.ride.fusedFramesStream().collect(Collectors.toList());

        return StreamUtils.slidingWindow(dfs, windowSize).map(df -> new BikeRide.DataFrame(getMyCoord(df.get(0)),
                StreamUtils.averageOfProperty(BikeRide.DataFrame::getGrade).apply(df),
                StreamUtils.averageOfProperty(BikeRide.DataFrame::getAltitude).apply(df),
                StreamUtils.averageOfProperty(BikeRide.DataFrame::getVelocity).apply(df),
                StreamUtils.averageOfProperty(BikeRide.DataFrame::getHeartRate).apply(df)));
    }

    public static LatLng getMyCoord(BikeRide.DataFrame df){
        return df.coordinate;
    }

    // @ToDo:
    //
    // Determine the stream of unique locations that the
    // rider stopped. A location is unique if there are no
    // other stops at the same latitude / longitude.
    // The rider is stopped if velocity = 0.
    //
    // For the purposes of this assignment, you should use
    // LatLng.equals() to determine if two locations are
    // the same.
    //
    public Stream<LatLng> locationsOfStops() {
        Stream<LatLng>  coords = this.ride.fusedFramesStream()
                .filter(v -> v.velocity == 0).map(dataFrame -> dataFrame.coordinate);
        return coords;
    }

}
