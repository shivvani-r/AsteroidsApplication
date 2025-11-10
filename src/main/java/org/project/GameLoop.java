package org.project;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;


import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GameLoop extends AnimationTimer {

    private Pane pane;
    private Ship ship;
    private List<Asteroid> asteroids;
    private List<Projectile> projectiles;
    private Map<KeyCode, Boolean> pressedKeys;
    private Text pointsText;
    private AtomicInteger points;


    public GameLoop (Pane pane, Ship ship, List<Asteroid> asteroids,
                     List<Projectile> projectiles, Map<KeyCode, Boolean> pressedKeys,
                     Text pointsText, AtomicInteger points) {
        this.pane = pane;
        this.ship = ship;
        this.asteroids = asteroids;
        this.projectiles = projectiles;
        this.pressedKeys = pressedKeys;
        this.pointsText = pointsText;
        this.points = points;
    }


    @Override
    public void handle(long now) {
        if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
            ship.turnLeft();
        }
        if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
            ship.turnRight();
        }
        if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
            ship.accelerate();
        }

        ship.move();
        asteroids.forEach(Character::move);
        projectiles.forEach(Character::move);

        asteroids.forEach(asteroid -> {
            if (ship.collide(asteroid)) {
                stop();
            }
        });

        projectiles.forEach(projectile -> {
            for (Asteroid asteroid: asteroids) {
                if (projectile.collide(asteroid)) {
                    projectile.setAlive(false);
                    asteroid.setAlive(false);

                    pointsText.setText("Points: " + points.addAndGet(AsteroidsApplication.POINTS_PER_ASTEROID));
                    break;
                }
            }
        });

        projectiles.removeIf(projectile ->  {
            if (!projectile.isAlive()) {
                pane.getChildren().remove(projectile.getCharacter());
                return true;
            }
            double x = projectile.getCharacter().getTranslateX();
            double y = projectile.getCharacter().getTranslateY();

            if (x < -20 || x > AsteroidsApplication.WIDTH + 20 || y < -20 || y > AsteroidsApplication.HEIGHT + 20) {
                pane.getChildren().remove(projectile.getCharacter());
                return true; // Remove from list
            }
            return false;
        });

        asteroids.removeIf(asteroid ->  {
            if (!asteroid.isAlive()) {
                pane.getChildren().remove(asteroid.getCharacter());
                return true;
            }
            return false;
        });

        if (AsteroidsApplication.RND.nextDouble() < AsteroidsApplication.ASTEROID_SPAWN_CHANCE) {

            int edge = AsteroidsApplication.RND.nextInt(4); // 0=top, 1=right, 2=bottom, 3=left
            int x = 0;
            int y = 0;

            switch (edge) {
                case 0: // Top edge
                    x = AsteroidsApplication.RND.nextInt(AsteroidsApplication.WIDTH);
                    y = 0;
                    break;
                case 1: // Right edge
                    x = AsteroidsApplication.WIDTH;
                    y = AsteroidsApplication.RND.nextInt(AsteroidsApplication.HEIGHT);
                    break;
                case 2: // Bottom edge
                    x = AsteroidsApplication.RND.nextInt(AsteroidsApplication.WIDTH);
                    y = AsteroidsApplication.HEIGHT;
                    break;
                case 3: // Left edge
                    x = 0;
                    y = AsteroidsApplication.RND.nextInt(AsteroidsApplication.HEIGHT);
                    break;
            }

            Asteroid asteroid = new Asteroid(x, y);

            if (!asteroid.collide(ship)) {
                asteroids.add(asteroid);
                pane.getChildren().add(asteroid.getCharacter());
            }
        }


    }
}
