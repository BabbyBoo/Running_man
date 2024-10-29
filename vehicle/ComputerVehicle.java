package vehicle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.awt.Graphics;

import javax.imageio.ImageIO;

public class ComputerVehicle {
    protected double x, y;
    protected int width, height;
    protected double xVelocity, yVelocity;
    protected int type;
    private BufferedImage sliderImage;

    public ComputerVehicle(int type, String imagePath) {
        setType(type);
        try {
            // Tải hình ảnh từ tệp tin
            sliderImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức khởi tạo xe
    public void reset(double x, double y, int width, int height, double xVelocity, double yVelocity, int type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.type = type;
    }

    // Phương thức khởi tạo loại
    public void setType(int type) {
        Random random = new Random();
        double x;
        double v;
        if (type == 0) {
            // xe máy
            x = 180 + random.nextInt(10) * 44;
            v = -2 + random.nextDouble(1);
            reset(x, 1000, 50, 100, 0, v, 0);
        } else if (type == 1) {
            // oto
            x = 180 + random.nextInt(3) * 150 + random.nextInt(40);
            v = 0.3 + random.nextDouble(1) * 0.5;
            reset(x, -150, 80, 150, 0, v, 1);
        } else if (type == 2) {
            // người qua đường bên trái
            x = random.nextDouble(200) + 100;
            reset(x, -50, 30, 30, 1, 2, 2);
        } else {
            // người qua đường bên phải
            x = random.nextDouble(200) + 500;
            reset(x, 50, 30, 30, -1, 2, 3);
        }
    }

    // Phương thức di chuyển của ComputerVehicle
    public void check(LinkedList<ComputerVehicle> comVehicles, int j) {
        boolean moveLeft = false;
        boolean moveRight = false;
        boolean moveDown = false;
        if (type != 0) {
            for (int i = 0; i < comVehicles.size(); i++) {
                if (i == j)
                    continue;

                ComputerVehicle cV = comVehicles.get(i);
                if (x - 10 > cV.getX() + cV.getWidth() ||
                        x + width + 10 < cV.getX() ||
                        y - 20 > cV.getY() + cV.getHeight() ||
                        y + height + 20 < cV.getY()) {
                    if (type == 2)
                        xVelocity = 1;
                    else if (type == 3)
                        xVelocity = -1;
                    else
                        yVelocity = 1;
                    continue;
                }
                if (type >= 2 && cV.yVelocity == 2 &&
                        (y > cV.getY() + cV.getHeight() || y + height < cV.getY() ||
                                x > cV.getX() + cV.getWidth() || x + width < cV.getX())) {
                    if (type == 2)
                        xVelocity = 1;
                    else if (type == 3)
                        xVelocity = -1;
                    continue;
                }
                if (type == 1 && cV.xVelocity == 0 &&
                        (y > cV.getY() + cV.getHeight() || y + height < cV.getY() ||
                                x > cV.getX() + cV.getWidth() || x + width < cV.getX())) {
                    yVelocity = 1;
                    continue;
                }
                if (x - 10 > cV.getX() + cV.getWidth() ||
                        x + width + 10 < cV.getX() ||
                        y - 20 > cV.getY() + cV.getHeight() ||
                        y + height + 20 < cV.getY()) {
                    continue;
                }
                if (type >= 2)
                    xVelocity = 0;
                else
                    yVelocity = 2;
                return;
            }
            return;
        }
        for (int i = 0; i < comVehicles.size(); i++) {
            if (i == j)
                continue;

            ComputerVehicle cV = comVehicles.get(i);
            if (y < cV.getY() + cV.getHeight() + 100 && y > cV.getY() && type == 0) {
                if (x < cV.getX() && x + width > cV.getX() - 10) {
                    moveLeft = true;
                } else if (x < cV.getX() + cV.getWidth() + 10 && x + width > cV.getX() + cV.getWidth()) {
                    moveRight = true;
                } else if (x >= cV.getX() && x + width <= cV.getX() + cV.getWidth()) {
                    if (x > 400)
                        moveLeft = true;
                    else
                        moveRight = true;
                }
            }

            if (y < cV.getY() + cV.getHeight() + 10 &&
                    y > cV.getY() + cV.getHeight() &&
                    x < cV.getX() + cV.getWidth() &&
                    x + width > cV.getX() && type == 0) {
                moveDown = true;
            }
        }
        if (x + width > 580)
            moveLeft = true;
        if (x < 180)
            moveRight = true;

        if (moveDown || (moveLeft && moveRight)) {
            yVelocity = 1.8;
            xVelocity = 0;
        } else if (moveLeft) {
            xVelocity = -1;
            yVelocity = -0.5;
        } else if (moveRight) {
            xVelocity = 1;
            yVelocity = -0.5;
        } else {
            if (xVelocity != 0 && yVelocity >= -0.5) {
                xVelocity = 0;
                yVelocity = -1.5;
            }
        }
    }

    public void move() {

        if (type < 2) {
            if (x < 180 && x + width < 580) {
                xVelocity = 0;
                yVelocity = 1.5;
            }
        }
        x += xVelocity;
        y += yVelocity;
    }

    // Phương thức vẽ vật
    public void draw(Graphics g) {
        if (sliderImage != null) {
            // Vẽ hình ảnh tại vị trí (x, y) và thay đổi kích thước theo width, height
            g.drawImage(sliderImage, (int) x, (int) y, width, height, null);
        } else {
            // Nếu không tải được hình ảnh, vẽ một hình khác tạm
            if (getType() == 2) {
                g.setColor(Color.WHITE);
                g.fillOval((int) x + width / 2, (int) y + height / 2, width / 2, height / 2);
                return;
            }
            g.setColor(Color.WHITE);
            g.fillRect((int) x, (int) y, width, height);
        }
    }

    public void setXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getXVelocity() {
        return xVelocity;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public int getType() {
        return type;
    }
}
