package org.project;

import javafx.scene.shape.Polygon;

public class Projectile extends Character {

    public Projectile (int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
    }
    @Override
    public void move() {
            this.getCharacter().setTranslateX(this.getCharacter().getTranslateX() + this.getMovement().getX());
            this.getCharacter().setTranslateY(this.getCharacter().getTranslateY() + this.getMovement().getY());
    }
}
