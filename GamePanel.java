import java.awt.Graphics;
import java.awt.Color;
import java.util.LinkedList;
import javax.swing.JPanel;

import vehicle.PlayerVehicle;
import vehicle.ComputerVehicle;

public class GamePanel extends JPanel {
    private PlayerVehicle playerVehicle;
    private LinkedList<ComputerVehicle> comVehicle = new LinkedList<>();
    private RunGame game; // Tham chiếu đến lớp chính PongGame để lấy điểm số
    private Map[] maps;

    public GamePanel(PlayerVehicle playerVehicle, LinkedList<ComputerVehicle> comVehicle, Map[] maps, RunGame game) {
        this.playerVehicle = playerVehicle;
        this.comVehicle = comVehicle;
        this.game = game;
        this.maps = maps;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ nền
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.fillRect(170, 0, 10, 1000);
        g.fillRect(620, 0, 10, 1000);

        // vẽ vạch kẻ đường
        for (int i = 0; i < 22; i++) {
            maps[i].draw(g);
        }

        // Vẽ xe
        playerVehicle.draw(g);
        for (ComputerVehicle v : comVehicle) {
            v.draw(g);
        }

        // Hiển thị điểm số
        g.setColor(Color.WHITE);
        g.drawString("Player: " + game.playerScore, 50, 30);
    }
}
