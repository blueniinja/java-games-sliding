package com.jakobniinja;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class SlidingTiles extends JFrame {

  private static final long serialVersionUID = 1L;

  private static final String FILENAME = "chalmander.jpg";

  private static final int UP = 0;

  private static final int DOWN = 1;

  private static final int LEFT = 2;

  private static final int RIGHT = 3;

  private static final int TILE_SIZE = 50;

  private static final int GRID_SIZE = 4;

  private final TileButton[][] tile = new TileButton[GRID_SIZE][GRID_SIZE];

  private final JPanel centerPanel = new JPanel();

  private final Random random = new Random();

  private transient BufferedImage image;

  public SlidingTiles() {
    try {
      image = ImageIO.read(new File(FILENAME));
      TileButton.setTileMaxTiles(TILE_SIZE, GRID_SIZE * GRID_SIZE);
      initGUI();

      setTitle("Sliding Tiles");
      setResizable(false);
      pack();
      setLocationRelativeTo(null);
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
    } catch (IOException e) {
      String message = "The image file " + FILENAME + " could not be opened";
      JOptionPane.showMessageDialog(this, message);
    }
  }

  private void initGUI() {
    // main panel
    divideImage();

    // button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.BLUE);
    add(buttonPanel, BorderLayout.PAGE_END);

    JButton scrambleButton = new JButton("Scramble");
    scrambleButton.addActionListener(e -> newGame());
    buttonPanel.add(scrambleButton);
  }

  private void newGame() {
    int imageId = 0;
    for (int row = 0; row < GRID_SIZE; row++) {
      for (int col = 0; col < GRID_SIZE; col++) {
        int x = col * TILE_SIZE;
        int y = row * TILE_SIZE;
        BufferedImage subimage = image.getSubimage(x, y, TILE_SIZE, TILE_SIZE);
        ImageIcon imageIcon = new ImageIcon(subimage);
        tile[row][col].setImage(imageIcon, imageId);
        imageId++;
      }
    }
    scramble();
  }

  private void divideImage() {
    centerPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
    add(centerPanel, BorderLayout.CENTER);

    int imageId = 0;
    for (int row = 0; row < GRID_SIZE; row++) {
      for (int col = 0; col < GRID_SIZE; col++) {
        int x = col * TILE_SIZE;
        int y = row * TILE_SIZE;

        BufferedImage subImage = image.getSubimage(x, y, TILE_SIZE, TILE_SIZE);
        ImageIcon imageIcon = new ImageIcon(subImage);
        tile[row][col] = new TileButton(imageIcon, imageId, row, col);
        tile[row][col].addActionListener(e -> {
          TileButton button = (TileButton) e.getSource();
          clickedTile(button);
        });

        centerPanel.add(tile[row][col]);
        imageId++;
      }
    }
    centerPanel.revalidate();

    // scramble();
  }

  private void scramble() {
    int openRow = GRID_SIZE - 1;
    int openCol = GRID_SIZE - 1;

    for (int i = 0; i < 25 * GRID_SIZE; i++) {
      int direction = random.nextInt(GRID_SIZE);
      switch (direction) {
        case UP:
          if (openRow > 0) {
            tile[openRow][openCol].swap(tile[openRow - 1][openCol]);
            openRow--;
          }
          break;

        case DOWN:
          if (openRow < GRID_SIZE - 1) {
            tile[openRow][openCol].swap(tile[openRow + 1][openCol]);
            openRow++;
          }
          break;
        case LEFT:
          if (openCol > 0) {
            tile[openRow][openCol].swap(tile[openRow][openCol - 1]);
            openCol--;
          }
          break;

        case RIGHT:
          if (openCol < GRID_SIZE - 1) {
            tile[openRow][openCol].swap(tile[openRow][openCol + 1]);
            openCol++;
          }
          break;
      }
    }
  }

  private void clickedTile(TileButton clickedTile) {
    int row = clickedTile.getRow();
    int col = clickedTile.getCol();

    if (row > 0 && tile[row - 1][col].hasNoImage()) {
      clickedTile.swap(tile[row - 1][col]);
    } else if (row < GRID_SIZE - 1 && tile[row + 1][col].hasNoImage()) {
      clickedTile.swap(tile[row + 1][col]);
    } else if (col > 0 && tile[row][col - 1].hasNoImage()) {
      clickedTile.swap(tile[row][col - 1]);
    } else if (col < GRID_SIZE - 1 && tile[row][col + 1].hasNoImage()) {
      clickedTile.swap(tile[row][col + 1]);
    }

    if (imageInOrder()) {
      tile[GRID_SIZE - 1][GRID_SIZE - 1].showImage();
    }
  }

  public boolean imageInOrder() {
    int id = 0;
    boolean inOrder = true;
    for (int row = 0; row < GRID_SIZE && inOrder; row++) {
      for (int col = 0; col < GRID_SIZE && inOrder; col++) {
        int currentId = tile[row][col].getImageId();
        if (currentId != id) {
          inOrder = false;
        }
        id++;
      }
    }
    return inOrder;
  }

  public static void main(String[] args) {
    try {
      String className = UIManager.getCrossPlatformLookAndFeelClassName();
      UIManager.setLookAndFeel(className);
    } catch (Exception e) {
      //
    }
    EventQueue.invokeLater(SlidingTiles::new);
  }
}
