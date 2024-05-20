package com.jakobniinja;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class TileButton extends JButton {

  private static final long serialVersionUID = 1L;

  private static int tileSize = 0;

  private static int maxTiles = 0;

  private ImageIcon imageIcon;

  private int imageId = 0;

  private int row = 0;

  private int col = 0;

  public void setImage(ImageIcon imageIcon, int imageId) {
    this.imageId = imageId;
    this.imageIcon = imageIcon;

    setIcon(imageId == maxTiles - 1 ? null : imageIcon);
  }

  public TileButton(ImageIcon imageIcon, int imageId, int row, int col) {
    this.row = row;
    this.col = col;
    setImage(imageIcon, imageId);

    setBackground(Color.WHITE);
    setBorder(null);
    Dimension size = new Dimension(tileSize, tileSize);
    setPreferredSize(size);
    setFocusPainted(false);
  }

  public ImageIcon getImage() {
    return imageIcon;
  }

  public int getImageId() {
    return imageId;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public static void setTileSize(int tileSize) {
    TileButton.tileSize = tileSize;
  }

  public static void setMaxTiles(int maxTiles) {
    TileButton.maxTiles = maxTiles;
  }

  public void setImage(ImageIcon imageIcon) {
    this.imageIcon = imageIcon;
  }

  public void setImageId(int imageId) {
    this.imageId = imageId;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public void setCol(int col) {
    this.col = col;
  }

  public static void setTileMaxTiles(int size, int max) {
    tileSize = size;
    maxTiles = max;
  }

  public boolean hasNoImage() {
    return getIcon() == null;
  }

  public void swap(TileButton otherTile) {
    ImageIcon otherIcon = otherTile.getImage();
    int otherId = otherTile.getImageId();
    otherTile.setImage(imageIcon, imageId);
    setImage(otherIcon, otherId);
  }

  public void showImage() {
    setImage(imageIcon);
  }
}
