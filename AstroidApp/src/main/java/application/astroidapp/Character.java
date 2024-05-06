package application.astroidapp;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * Character Abstract Class
 */
public abstract class Character {
    // Polygon shape
    private Polygon character;
    // Defines a point representing a location in (x,y) coordinate space.
    private Point2D movement;

    /**
     * Constructor for Character class
     * @param polygon Polygon that will be used for drawing
     * @param x Location along x-axis
     * @param y Location along y-axis
     */
    public Character(Polygon polygon, int x, int y) {
        this.character = polygon;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);

        this.movement = new Point2D(0, 0);
    }

    /**
     * Gets the polygon used for this character
     * @return Polygon object
     */
    public Polygon getCharacter() {
        return character;
    }

    /**
     * Rotates this character to the left
     */
    public void turnLeft() {
        this.character.setRotate(this.character.getRotate() - 5);
    }

    /**
     * Rotates this character to the right
     */
    public void turnRight() {
        this.character.setRotate(this.character.getRotate() + 5);
    }

    /**
     * Moves this character along the x and y axes
     */
    public void move() {
        double newX = this.character.getTranslateX() + this.movement.getX();
        double newY = this.character.getTranslateY() + this.movement.getY();

        // Get the bounds of the character in the parent's coordinate system
        Bounds bounds = this.character.getBoundsInParent();
        double minX = bounds.getMinX();
        double minY = bounds.getMinY();
        double maxX = bounds.getMaxX();
        double maxY = bounds.getMaxY();

        // Check if the new position is outside the screen bounds and adjust if necessary
        if (newX < 0) {
            newX = 0; // Keep the character at the left edge of the screen
        } else if (newX > GameWindow.width - (maxX - minX)) {
            newX = GameWindow.width - (maxX - minX); // Adjust for character width
        }

        if (newY < 0) {
            newY = 0; // Keep the character at the top edge of the screen
        } else if (newY > GameWindow.height - (maxY - minY)) {
            newY = GameWindow.height - (maxY - minY); // Adjust for character height
        }

        // Update the character's position
        this.character.setTranslateX(newX);
        this.character.setTranslateY(newY);
    }


    /**
     * Accelerates the speed at which this character moves
     */
    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));

        changeX *= 0.05;
        changeY *= 0.05;

        this.movement = this.movement.add(changeX, changeY);
    }

    /**
     * Check for collision between characters
     * @param other The other character that is collided with
     * @return If the intersection is 0, there is no collision and False is outputted
     */
    public boolean collide(Character other) {
        Shape collisionArea = Shape.intersect(this.character, other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
}
