package mazesolver;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class View extends JFrame {
 /**
     * Conventions:
     * 
    
     * 
     * Values: 0 = not-visited node
     *         1 = wall (blocked)
     *         2 = visited node
     *         9 = target node
     */
    private int [][] maze = 
        { {1,1,1,1,1,1,1,1,1,1,1,1,1},
          {1,0,1,0,1,0,1,0,0,0,0,0,1},
          {1,0,1,0,0,0,1,0,1,1,1,0,1},
          {1,0,0,0,1,1,1,0,0,0,0,0,1},
          {1,0,1,0,0,0,0,0,1,1,1,0,1},
          {1,0,1,0,1,1,1,0,1,0,0,0,1},
          {1,0,1,0,1,0,0,0,1,1,1,0,1},
          {1,0,1,0,1,1,1,0,1,0,1,0,1},
          {1,0,0,0,0,0,0,0,0,0,1,9,1},
          {1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
    
    private final List<Integer> path = new ArrayList<Integer>();
    private int pathIndex;
    
    public View() {
        setTitle("Maze Solver");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //DepthFirst2.searchPath(maze, 1, 1, path); width and bredth
        DepthFirst.searchPath(maze, 1, 1, path);
        pathIndex = path.size() - 2;

        // game loop each 0.5 seconds
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                update();
//                repaint();
//            }
//        }, 100, 500);        
    }
    
    public void update() {
        pathIndex -= 2;
        if (pathIndex < 0) {
            pathIndex = 0;
        }
    }
    
    @Override
    //inbuilt=>paint
    public void paint(Graphics g) {
        super.paint(g);
        
        g.translate(50, 50);
        
        // draw the maze
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                Color color;
                switch (maze[row][col]) {
                    case 1 : color = Color.BLACK; break;
                    case 9 : color = Color.RED; break;
                    default : color = Color.WHITE;
                }
                g.setColor(color);
                g.fillRect(30 * col, 30 * row,30, 30); //coordinate and width and bredth
                g.setColor(Color.RED);// for boundary
                g.drawRect(30 * col, 30 * row, 30, 30);
            }
        }
        
        // draw the path list
        for (int p = 0; p < path.size(); p += 2) {//i+2=> added both cooardinate x and y then 1st (x coordinate) and then y
            int pathX = path.get(p);
            int pathY = path.get(p + 1);
            g.setColor(Color.GREEN);
            g.fillRect(pathX * 30, pathY * 30, 30, 30);
        }

        // draw the ball on path
        int pathX = path.get(pathIndex);
        int pathY = path.get(pathIndex + 1);
        g.setColor(Color.RED);
        g.fillOval(pathX * 30, pathY * 30, 30, 30);
    }
    
    @Override
    protected void processKeyEvent(KeyEvent ke) {
        if (ke.getID() != KeyEvent.KEY_PRESSED) {
            return;
        }
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            pathIndex -= 2;
            if (pathIndex < 0) {
                pathIndex = 0;
            }
        }
        else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            pathIndex += 2;
            if (pathIndex > path.size() - 2) {
                pathIndex = path.size() - 2;
            }
        }
        repaint(); 
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View();
                view.setVisible(true);
            }
        });
    }
    
}

