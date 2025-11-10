package org.project;

import javafx.collections.ObservableList;
import javafx.scene.shape.Polygon;

import java.util.Random;

public class PolygonFactory {

    private static final Random RND = new Random();

    private static final double C1 = Math.cos(Math.PI * 2 / 5);
    private static final double C2 = Math.cos(Math.PI / 5);
    private static final double S1 = Math.sin(Math.PI * 2 / 5);
    private static final double S2 = Math.sin(Math.PI * 4 / 5);

    private static final int JITTER_AMOUNT = 5;
    private static final int JITTER_OFFSET = 2;

    public Polygon createPolygon () {

        double size = 10 + RND.nextInt(10);

        Polygon polygon = new Polygon();

        polygon.getPoints().addAll(
                size, 0.0,
                size * C1, -1 * size * S1,
                -1 * size * C2, -1 * size * S2,
                -1 * size * C2, size * S2,
                size * C1, size * S1);

        ObservableList<Double> points = polygon.getPoints();

        for (int i = 0; i < points.size(); i++) {
            int change = RND.nextInt(JITTER_AMOUNT) - JITTER_OFFSET;
            points.set(i, points.get(i) + change);
        }
        return polygon;
    }
}
