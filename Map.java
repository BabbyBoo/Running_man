import java.awt.*;

public class Map {
    protected int x, y;
    protected int width, height;
    protected int velocity;

    public Map(int x, int y) {
        this.x = x;
        this.y = y;
        width = 10;
        height = 60;
        velocity = 2;
    }

    public void move() {
        y += velocity;
        if (y > 1000)
            y = -100;
    }

    // Phương thức vẽ hcn (xe)
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }
}
