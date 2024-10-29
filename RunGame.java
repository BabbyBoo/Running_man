import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import java.util.Random;
import java.util.LinkedList;

import vehicle.PlayerVehicle;
import vehicle.ComputerVehicle;

public class RunGame extends JFrame implements KeyListener {
    private GamePanel panel;
    private PlayerVehicle playerVehicle;
    private int numVehicle = 5;
    private LinkedList<ComputerVehicle> comVehicle = new LinkedList<>();
    private Map[] maps = new Map[22];
    private String[] imgPath = { "./images/mb.png", "./images/mb1.png", "./images/mb2.png",
            "./images/car5.png", "./images/car6.png", "./images/car1.png", "./images/car2.png",
            "./images/hat1.png", "./images/hat2.png", "./images/hat3.png", "./images/hat4.png", "./images/hat5.png" };

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private Random random = new Random();
    public int playerScore = 0;

    public RunGame() {
        setTitle("Chạy đi chờ chi");
        setSize(800, 1000);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        // Khởi tạo PlayerVehicle
        playerVehicle = new PlayerVehicle(imgPath[0], 600, 450, 40, 100, 1, 0);

        // Khởi tạo comVehicle
        addVehicle();
        for (int i = 0; i < 11; i++) {
            maps[i] = new Map(320, i * 100);
            maps[i + 11] = new Map(470, i * 100);
        }

        panel = new GamePanel(playerVehicle, comVehicle, maps, this);
        add(panel);

        addKeyListener(this);
        setFocusable(true);

        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveComputerVehicle();
                movePlayerVehicle();
                panel.repaint();
                checkScore();
            }
        });
        /////// thêm màn hình trước khi bắt đầu
        timer.start();
        /////// thay đổi hệ số của comVehicle và playervehicle

        setVisible(true);
    }

    // Phương thức tránh chồng chất các xe mới và cũ
    public boolean checkComVehicle(ComputerVehicle newCV) {
        for (int i = 0; i < comVehicle.size(); i++) {
            ComputerVehicle cV = comVehicle.get(i);
            if (cV.getX() - 10 < newCV.getX() + newCV.getWidth() &&
                    cV.getX() + cV.getWidth() + 10 > newCV.getX() &&
                    cV.getY() - 10 < newCV.getY() + newCV.getHeight() &&
                    cV.getY() + cV.getHeight() + 10 > newCV.getY()) {
                return false;
            }
        }
        return true;
    }

    // Phương thức di chuyển của comVehicle
    public void moveComputerVehicle() {
        for (int i = 0; i < comVehicle.size(); i++) {
            comVehicle.get(i).check(comVehicle, i);
            comVehicle.get(i).move();
        }
        for (int i = 0; i < 22; i++)
            maps[i].move();
    }

    // Phương thức khởi tạo lại xe
    public void addVehicle() {
        while (comVehicle.size() < numVehicle) {
            int type = random.nextInt(10);
            int k = random.nextInt(4) + 1;
            if (type < 7)
                type = 0;
            else if (type < 9) {
                type = 1;
                k = random.nextInt(2) + 5;
            } else {
                type = random.nextInt(2) + 2;
                k = random.nextInt(5) + 7;
            }
            ComputerVehicle cV = new ComputerVehicle(type, imgPath[k]);
            if (checkComVehicle(cV)) {
                comVehicle.add(cV);
            }
        }
    }

    // Phương thức kiểm tra điểm số
    public void checkScore() {
        for (int i = 0; i < comVehicle.size(); i++) {
            double y = comVehicle.get(i).getY();
            if (y < -150 || y > 1000) {
                playerScore++;
                comVehicle.remove(i);
                addVehicle();
            }
        }
        /////// có thể thay điểm chạy theo thời gian
    }

    // Phương thức di chuyển xe của người chơi dựa trên phím
    public void movePlayerVehicle() {
        if (upPressed) {
            playerVehicle.moveUp();
        }
        if (downPressed) {
            playerVehicle.moveDown();
        }
        if (leftPressed) {
            playerVehicle.moveLeft();
        }
        if (rightPressed) {
            playerVehicle.moveRight();
        }
        if (playerVehicle.move(comVehicle)) {
            System.exit(0);
            /////// thêm màn hình khi kết thúc chương trình
        }
    }

    // Phương thức điều khiển bằng bàn phím
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar(); // Lấy ký tự từ phím vừa gõ
        // Không cần thiết xử lý
        if (keyChar == 'a') {
        }
    }

    public static void main(String[] args) {
        new RunGame();
    }
}