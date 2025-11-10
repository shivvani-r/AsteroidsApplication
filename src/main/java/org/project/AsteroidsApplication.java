package org.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AsteroidsApplication extends Application {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static final Random RND = new Random();
    public static final int INITIAL_ASTEROID_COUNT = 5;
    public static final int POINTS_PER_ASTEROID = 1000;
    public static final double ASTEROID_SPAWN_CHANCE = 0.005;
    public static final double PROJECTILE_SPEED = 3.0;

    private Ship ship;
    private List<Projectile> projectiles;
    private Pane pane;

    public static void main(String[] args) {
        launch(AsteroidsApplication.class);
    }

    @Override
    public void start(Stage window) throws Exception {

        this.pane = new Pane();
        Text text = new Text(10, 20, "Points: 0");
        pane.getChildren().add(text);

        AtomicInteger points = new AtomicInteger();

        pane.setPrefSize(WIDTH, HEIGHT);

        this.ship = new Ship(WIDTH / 2, HEIGHT / 2);
        List<Asteroid> asteroids = new ArrayList<>();
        this.projectiles = new ArrayList<>();

        for (int i = 0; i < INITIAL_ASTEROID_COUNT; i++) {
            Asteroid asteroid = new Asteroid(RND.nextInt(WIDTH / 3), RND.nextInt(HEIGHT));
            asteroids.add(asteroid);
        }

        pane.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));


        Scene scene = new Scene(pane);

        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
        scene.setOnKeyPressed(event -> {
            if (pressedKeys.getOrDefault(event.getCode(), false)) {
                return;
            }
            pressedKeys.put(event.getCode(), Boolean.TRUE);

            if (event.getCode() == KeyCode.SPACE) {
                fireProjectile();
            }
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });

        GameLoop gameLoop = new GameLoop(pane, ship, asteroids, projectiles, pressedKeys, text, points);
        gameLoop.start();

        window.setTitle("Asteroids!");
        window.setScene(scene);
        window.show();

    }

    public void fireProjectile() {
        Projectile projectile = new Projectile(
                (int) ship.getCharacter().getTranslateX(),
                (int) ship.getCharacter().getTranslateY()
        );
        projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
        projectiles.add(projectile);

        projectile.accelerate();
        projectile.setMovement(projectile.getMovement().normalize().multiply(PROJECTILE_SPEED));

        pane.getChildren().add(projectile.getCharacter());
    }
}