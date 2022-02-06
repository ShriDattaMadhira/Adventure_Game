package ui;

import dungeon.Cells;
import dungeon.Direction;
import dungeon.ReadOnlyDungeonModel;
import dungeon.Treasure;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JPanel;



/**
 * The Panel that displays the dungeon to the screen. This is the main part of the UI.
 */
public class DungeonPanel extends JPanel {

  private static final BufferedImage north;
  private static final BufferedImage south;
  private static final BufferedImage east;
  private static final BufferedImage west;
  private static final BufferedImage northSouth;
  private static final BufferedImage northEast;
  private static final BufferedImage northWest;
  private static final BufferedImage southEast;
  private static final BufferedImage southWest;
  private static final BufferedImage eastWest;
  private static final BufferedImage northSouthEast;
  private static final BufferedImage northSouthWest;
  private static final BufferedImage northEastWest;
  private static final BufferedImage southEastWest;
  private static final BufferedImage northSouthEastWest;
  private static final BufferedImage ARROW;
  private static final BufferedImage DIAMOND;
  private static final BufferedImage SAPPHIRE;
  private static final BufferedImage RUBY;
  private static final BufferedImage OTYUGH;
  private static final BufferedImage PLAYER;
  private static final BufferedImage THIEF;
  private static final BufferedImage FAINT;
  private static final BufferedImage PUNGENT;

  static final int CELL_SIZE = 64;

  private final ReadOnlyDungeonModel roModel;

  // initializing the images in a static block.
  static {
    try {
      ClassLoader classLoader = Class.forName("ui.DungeonPanel").getClassLoader();

      north = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/color-cells/N.png")));
      south = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/color-cells/S.png")));
      east = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/color-cells/E.png")));
      west = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/color-cells/W.png")));

      northSouth = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/color-cells/NS.png")));
      northEast = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/color-cells/NE.png")));
      northWest = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/color-cells/NW.png")));
      southEast = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/color-cells/SE.png")));
      southWest = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/color-cells/SW.png")));
      eastWest = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/color-cells/EW.png")));

      northSouthEast = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/color-cells/NSE.png")));
      northSouthWest = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/color-cells/NSW.png")));
      northEastWest = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/color-cells/NEW.png")));
      southEastWest = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/color-cells/SEW.png")));

      northSouthEastWest = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/color-cells/NSEW.png")));

      ARROW = reSize(ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/arrow-white.png"))));
      DIAMOND = reSize(ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/diamond.png"))));
      SAPPHIRE = reSize(ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/emerald.png"))));
      RUBY = reSize(ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/ruby.png"))));
      OTYUGH = reSize(ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/otyugh.png"))));
      FAINT = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/stench01.png")));
      PUNGENT = ImageIO.read(Objects.requireNonNull(
              classLoader.getResource("icons/stench02.png")));

      PLAYER = reSize(reSize(reSize(reSize(ImageIO.read(
              Objects.requireNonNull(classLoader.getResource("icons/player.png")))))));
      THIEF = reSize(reSize(reSize(reSize(reSize(reSize(ImageIO.read(
              Objects.requireNonNull(classLoader.getResource("icons/thief.png")))))))));
    } catch (IOException ioe) {
      throw new IllegalArgumentException("File not found." + Arrays.toString(ioe.getStackTrace()));
    } catch (ClassNotFoundException cnfe) {
      throw new IllegalArgumentException("Class Not Found - Dungeon Panel");
    }
  }

  /**
   * Constructor for the Dungeon Panel.
   * @param model - Dungeon Game.
   */
  DungeonPanel(ReadOnlyDungeonModel model) {
    this.roModel = model;
    setBackground(Color.DARK_GRAY);
    setPreferredSize(new Dimension(roModel.getWidth() * CELL_SIZE,
            roModel.getHeight() * CELL_SIZE));
  }

  // resize the images I downloaded from the internet
  private static BufferedImage reSize(BufferedImage image) {
    BufferedImage newImage = new BufferedImage(image.getWidth() / 2,
            image.getHeight() / 2, BufferedImage.TYPE_INT_ARGB);

    Graphics2D graphics2D = newImage.createGraphics();
    graphics2D.drawImage(image, 0, 0, image.getWidth() / 2,
            image.getHeight() / 2, null);
    graphics2D.dispose();

    return newImage;
  }

  // get image of the cave/tunnel we are in.
  private BufferedImage getImage(Set<Direction> directions) {
    StringBuilder dir = new StringBuilder();

    if (directions.contains(Direction.NORTH)) {
      dir.append("North");
    }
    if (directions.contains(Direction.SOUTH)) {
      dir.append("South");
    }
    if (directions.contains(Direction.EAST)) {
      dir.append("East");
    }
    if (directions.contains(Direction.WEST)) {
      dir.append("West");
    }

    switch (dir.toString()) {
      case "North":
        return north;
      case "South":
        return south;
      case "East":
        return east;
      case "West":
        return west;

      case "NorthSouth":
        return northSouth;
      case "NorthEast":
        return northEast;
      case "NorthWest":
        return northWest;
      case "SouthEast":
        return southEast;
      case "SouthWest":
        return southWest;
      case "EastWest":
        return eastWest;
      case "NorthSouthEast":
        return northSouthEast;
      case "NorthSouthWest":
        return northSouthWest;
      case "NorthEastWest":
        return northEastWest;
      case "SouthEastWest":
        return southEastWest;
      case "NorthSouthEastWest":
        return northSouthEastWest;

      default:
        throw new IllegalArgumentException("Image Not Found.");
    }
  }

  // put one image on top of another.
  private static BufferedImage imageOverlay(BufferedImage baseImage, BufferedImage topImage,
                                            int xOffset, int yOffset) {

    // Creating the final image of width and height that will match the final image. The image
    // will be RGB + Alpha
    BufferedImage finalImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(),
            BufferedImage.TYPE_INT_ARGB);

    Graphics finalImageGraphics = finalImage.getGraphics();

    finalImageGraphics.drawImage(baseImage, 0, 0, null);
    finalImageGraphics.drawImage(topImage, xOffset, yOffset, null);
    return finalImage;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int offsetX = getWidthOffset();
    int offsetY = getHeightOffset();

    List<Cells> visited = roModel.getVisited();
    for (Cells cell: visited) {

      int[] coords = cell.getCoordinates();
      BufferedImage image = getImage(cell.getNeighborList().keySet());

      // stench
      if (Arrays.equals(cell.getCoordinates(), roModel.getCurrPlayerPosition())) {
        int stench = roModel.getStench(cell);
        if (stench == 1) {
          image = imageOverlay(image, FAINT, (CELL_SIZE / 2) - 30,
                  (CELL_SIZE / 2) - 30);
        } else if (stench == 2 || stench == -2) {
          image = imageOverlay(image, PUNGENT, (CELL_SIZE / 2) - 30,
                  (CELL_SIZE / 2) - 30);
        }
      }

      // thief
      if (cell.getThief() != null) {
        image = imageOverlay(image, THIEF, CELL_SIZE / 4, (CELL_SIZE / 4) + 3);
      }

      // player
      if (Arrays.equals(coords, roModel.getCurrPlayerPosition())) {
        image = imageOverlay(image, PLAYER, (CELL_SIZE / 3) - 8, CELL_SIZE / 3);
      }

      // monster
      int monsterHealth = roModel.getOtyughHealth(cell);
      System.out.println();
      if (monsterHealth > 0) {
        image = imageOverlay(image, OTYUGH, (CELL_SIZE / 3) - 2,
                (CELL_SIZE / 3) - 8);
      }

      // arrows
      if (roModel.getCellArrows(cell) > 0) {
        int arrowCount = roModel.getCellArrows(cell);
        for (int i = 0; i < arrowCount; i++) {
          image = imageOverlay(image, ARROW, (CELL_SIZE / 5) + 8,
                  (CELL_SIZE / 5) + 4);
        }
      }

      // treasure
      if (roModel.getCellTreasure(cell).size() > 0) {
        List<Treasure> treasureList = roModel.getCellTreasure(cell);
        if (treasureList.contains(Treasure.RUBIES)) {
          image = imageOverlay(image, RUBY, (CELL_SIZE / 4) - 6,
                  (CELL_SIZE / 4) + 16);
        }
        if (treasureList.contains(Treasure.SAPPHIRE)) {
          image = imageOverlay(image, SAPPHIRE,
                  RUBY.getWidth() + (CELL_SIZE / 4) - 5, (CELL_SIZE / 4) + 20);
        }
        if (treasureList.contains(Treasure.DIAMONDS)) {
          image = imageOverlay(image, DIAMOND,
                  RUBY.getWidth() + SAPPHIRE.getWidth() + (CELL_SIZE / 4) - 4,
                  (CELL_SIZE / 4) + 17);
        }
      }

      g.drawImage(image, offsetX + coords[1] * CELL_SIZE, offsetY + coords[0] * CELL_SIZE,
              null);
    }
  }

  /**
   * Returns the width offset.
   * @return - integer.
   */
  public int getWidthOffset() {
    return (getWidth() - CELL_SIZE * roModel.getWidth()) / 2;
  }

  /**
   * Returns the height offset.
   * @return - integer.
   */
  public int getHeightOffset() {
    return (getHeight() - CELL_SIZE * roModel.getHeight()) / 2;
  }

  // end of class
}
