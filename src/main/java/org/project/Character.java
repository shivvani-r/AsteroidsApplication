package org.project;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;



public abstract class Character {

    private Polygon character;
    private Point2D movement;
    private boolean alive;

    private static final double TURN_RATE = 5.0;
    private static final double THRUST_AMOUNT = 0.05;

    public Character (Polygon polygon, int x, int y) {
        this.character = polygon;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);

        this.movement = Point2D.ZERO;
        this.alive = true;
    }

    public Polygon getCharacter() {
        return this.character;
    }
    public void turnLeft() {
        this.character.setRotate(this.character.getRotate() - TURN_RATE);
    }
    public void turnRight() {
        this.character.setRotate(this.character.getRotate() + TURN_RATE);
    }

    public void move() {
        double newX = this.character.getTranslateX() + this.movement.getX();
        double newY = this.character.getTranslateY() + this.movement.getY();
        newX = (newX % AsteroidsApplication.WIDTH + AsteroidsApplication.WIDTH) % AsteroidsApplication.WIDTH;
        newY = (newY % AsteroidsApplication.HEIGHT + AsteroidsApplication.HEIGHT) % AsteroidsApplication.HEIGHT;

        this.character.setTranslateX(newX);
        this.character.setTranslateY(newY);
    }

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));
        changeX *= THRUST_AMOUNT;
        changeY *= THRUST_AMOUNT;

        this.movement = this.movement.add(changeX, changeY);
    }

    public boolean collide (Character other) {
        return this.character.getBoundsInParent().intersects(other.getCharacter().getBoundsInParent());
    }

    public Point2D getMovement() {
        return this.movement;
    }

    public void setMovement(Point2D point) {
        this.movement = point;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return this.alive;
    }
}
