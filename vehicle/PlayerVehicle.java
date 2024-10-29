package vehicle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class PlayerVehicle {
    private int x, y;
    private int width, height;
    private int xVelocity, yVelocity;
    private BufferedImage sliderImage;

    public PlayerVehicle(String imagePath, int x, int y, int width, int height, int xVelocity, int yVelocity) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        try {
            // Tải hình ảnh từ tệp tin
            sliderImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveUp() {
        if (y > 0) {
            y -= yVelocity;
        }
    }

    public void moveDown() {
        if (y + height < 600) {
            y += yVelocity;
        }
    }

    public void moveRight() {
        if (x < 760) {
            x += xVelocity;
        }
    }

    public void moveLeft() {
        if (x > 0) {
            x -= xVelocity;
        }
    }

    // Phương thức kiểm tra va chạm với xe của máy
    public boolean move(LinkedList<ComputerVehicle> comVehicle) {
        // Kiểm tra va chạm với xe của người chơi (playerVehicle)
        for (int i = 0; i < comVehicle.size(); i++) {
            if (x < comVehicle.get(i).getX() + comVehicle.get(i).getWidth() &&
                    x + width > comVehicle.get(i).getX() &&
                    y < comVehicle.get(i).getY() + comVehicle.get(i).getHeight() &&
                    y + height > comVehicle.get(i).getY()) {
                return false;
            }
        }
        return true;
    }

    // Vẽ xe người chơi
    public void draw(Graphics g) {
        if (sliderImage != null) {
            // Vẽ hình ảnh tại vị trí (x, y) và thay đổi kích thước theo width, height
            g.drawImage(sliderImage, x, y, width, height, null);
        } else {
            // Nếu không tải được hình ảnh, vẽ một hình chữ nhật tạm
            g.setColor(Color.WHITE);
            g.fillRect(x, y, width, height);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
