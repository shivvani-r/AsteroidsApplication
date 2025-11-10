package org.project;

import javafx.geometry.Point2D;

import java.util.Random;

public class Asteroid extends Character{

    private double rotationalMovement;
    private static final Random RND = new Random();
    private static final PolygonFactory FACTORY = new PolygonFactory();

    public Asteroid (int x, int y) {
        super (FACTORY.createPolygon(), x, y);

        super.getCharacter().setRotate(RND.nextInt(360));

        double angleRad = Math.toRadians(getCharacter().getRotate());
        Point2D direction = new Point2D(Math.cos(angleRad), Math.sin(angleRad));

        int accelerationAmount = 1 + RND.nextInt(10);
        double speed = accelerationAmount * 0.05;
        super.setMovement(direction.multiply(speed));

        this.rotationalMovement = 0.5 - RND.nextDouble();
    }

    @Override
    public void move() {
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotationalMovement);
    }
}

