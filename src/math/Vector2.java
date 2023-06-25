package math;

public class Vector2 {
    private double x;
    private double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2 setDirection(double angle) {
        double magnitude = getMagnitude();
        return new Vector2(Math.cos(angle) * magnitude, Math.sin(angle) * magnitude);
    }

    public Vector2() {
        this(0, 0);
    }

    public Vector2 limit(double value) {
        if (getMagnitude() > value) {
           return this.normalize().scale(value);
        }
        return this;
    }

    public Vector2 normalize() {
        double magnitude = getMagnitude();
        return new Vector2(x / magnitude, y / magnitude);
    }

    public Vector2 scale(double value) {
        return new Vector2(x * value, y * value);
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.getX(), y + other.getY());
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(x - other.getX(), y - other.getY());
    }

    public double getAngle() {
        return Math.asin(y/getMagnitude());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
}
