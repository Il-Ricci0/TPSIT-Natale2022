import javax.swing.*;
import java.awt.event.*;

public class Game {
    public static Integer worldDimensionX = 50;
    public static Integer worldDimensionY = 40;
    public static Boolean end = false;
    public static Integer[][] world = generateEmptyWorld(worldDimensionX, worldDimensionY);
    public static Player player = new Player("Giocatore 1", 0, 0);
    static JPanel panel = createPanel();
    // martrix[row][column]

    public static void main(String[] args) throws Exception {
        JPanel panel = new JPanel();
        // Add a key listener to the panel
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        if(player.getRow() - 1 >= 0){
                            world[player.getRow()][player.getColumn()] = 0;
                            player.setRow(player.getRow() - 1);
                            world[player.getRow()][player.getColumn()] = 1;
                        }
                        break;

                    case KeyEvent.VK_A:
                        if(player.getColumn() - 1 >= 0){
                            world[player.getRow()][player.getColumn()] = 0;
                            player.setColumn(player.getColumn() - 1);
                            world[player.getRow()][player.getColumn()] = 1;
                        }
                        break;

                    case KeyEvent.VK_S:
                        if(player.getRow() + 1 < worldDimensionX){
                            world[player.getRow()][player.getColumn()] = 0;
                            player.setRow(player.getRow() + 1);
                            world[player.getRow()][player.getColumn()] = 1;
                        }
                        break;

                    case KeyEvent.VK_D:
                        if(player.getColumn() + 1 < worldDimensionY){
                            world[player.getRow()][player.getColumn()] = 0;
                            player.setColumn(player.getColumn() + 1);
                            world[player.getRow()][player.getColumn()] = 1;
                        }
                        break;
                }
            }
        });
        JLabel label = new JLabel();
        // Make the panel focusable so it can receive key events
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        panel.add(label);

        // Add the panel to a frame and display it
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setSize(800, 800);  // Set the size of the frame
        frame.setVisible(true);  // Make the frame visible

        world[player.getRow()][player.getColumn()] = 1;

         while (true) {
            label.setText(printWorld(world));
 
             // Update the panel to show the new label color
             panel.revalidate();
             panel.repaint();
         }
    }

    static Integer[][] generateEmptyWorld(Integer worldDimensionX, Integer worldDimensionY) {
        Integer[][] world = new Integer[worldDimensionX][worldDimensionY];

        for (int i = 0; i < worldDimensionX; i++) {
            for (int j = 0; j < worldDimensionY; j++) {
                world[i][j] = 0;
            }
        }

        return world;
    }

    static public String printWorld(Integer[][] world) {
        String output = "<html>";
        for (int i = 0; i < worldDimensionX; i++) {
            for (int j = 0; j < worldDimensionY; j++) {
                output += world[i][j] + " ";
            }
            output += "<br>";
        }
        output += "</html>";
        return output.replaceAll("0", ".").replaceAll("1", "@");
    }

    public Integer[] findPlayer(Integer[][] world, Integer worldDimensionX, Integer worldDimensionY) {
        Integer[] playerPosition = new Integer[2];

        for (int columnIndex = 0; columnIndex < worldDimensionX; columnIndex++) {
            for (int rowIndex = 0; rowIndex < worldDimensionY; rowIndex++) {
                if (world[columnIndex][rowIndex] == 1) {
                    playerPosition[0] = columnIndex;
                    playerPosition[1] = rowIndex;
                }
            }
        }

        return playerPosition;
    }

    static JPanel createPanel() {
        JPanel panel = new JPanel();
        return panel;
    }

    public void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (Exception e) {
        }
    }

    public class GameLoop implements Runnable {
        private void gameLoop() {
            while (!end) {
                refreshScreen();
            }
        }

        private void refreshScreen() {
            clearConsole();
            printWorld(world);
        }

        public void run() {
            gameLoop();
        }
    }
}