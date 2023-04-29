package com.gui;

import com.simplicity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel {
    private static GamePanel instance = new GamePanel(null);
    int keyChar;

    public int getKeyChar() {
        return keyChar;
    }

    public static GamePanel getInstance() {
        return instance;
    }

    public void setCurrentSim(Sim currentSim) {
        this.currentSim = currentSim;
    }

    private KeyAdapter keyListener = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            SimPosition currentSimPosition = currentSim.getCurrentPosition();
            keyChar = e.getKeyCode();
            if (keyChar == KeyEvent.VK_SPACE) {
                Game.getInstance().displayGameMenu();
            } else if ((keyChar == KeyEvent.VK_W) || (keyChar == KeyEvent.VK_UP)) {
                if (currentSimPosition.getLokasi().getY() < 5) {
                    currentSimPosition.getLokasi().setY(currentSimPosition.getLokasi().getY() + 1);
                    repaint();
                }
            } else if ((keyChar == KeyEvent.VK_S) || (keyChar == KeyEvent.VK_DOWN)) {
                if (currentSimPosition.getLokasi().getY() > 0) {
                    currentSimPosition.getLokasi().setY(currentSimPosition.getLokasi().getY() - 1);
                    repaint();
                }
            } else if ((keyChar == KeyEvent.VK_A) || (keyChar == KeyEvent.VK_LEFT)) {
                if (currentSimPosition.getLokasi().getX() > 0) {
                    currentSimPosition.getLokasi().setX(currentSimPosition.getLokasi().getX() - 1);
                    repaint();
                }
            } else if ((keyChar == KeyEvent.VK_D) || (keyChar == KeyEvent.VK_RIGHT)) {
                if (currentSimPosition.getLokasi().getX() < 5) {
                    currentSimPosition.getLokasi().setX(currentSimPosition.getLokasi().getX() + 1);
                    repaint();
                }
            }
        }
    };

    private boolean displayRumah = true;
    private Sim currentSim;

    public GamePanel(Sim currentSim) {
        this.currentSim = currentSim;
        setFocusable(true);
        addKeyListener(keyListener);
        setPreferredSize(new Dimension(600, 600)); // Set the preferred size of the panel
        requestFocusInWindow(); // Request focus for the panel after it has been added to the JFrame
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (displayRumah) {
            currentSim.getCurrentPosition().getRumah().paint(g, getWidth(), getHeight());
        } else {
            BufferedImage pattern = null;
            try {
                pattern = ImageIO.read(new File("src/main/java/resources/images/sea.jpg"));
            } catch (IOException e) {
                System.out.println("Error loading background image");
            }
            if (pattern != null) {
                int patternWidth = pattern.getWidth(null);
                int patternHeight = pattern.getHeight(null);
                for (int x = 0; x < getWidth(); x += patternWidth) {
                    for (int y = 0; y < getHeight(); y += patternHeight) {
                        g.drawImage(pattern, x, y, null);
                    }
                }
            }
            World.getInstance().paint(g, getWidth(), getHeight());
        }
    }
}
