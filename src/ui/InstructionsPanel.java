package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class InstructionsPanel extends JPanel {
  private static final int OFFSET = 15;
  private static final int FONT_SIZE = 15;
  private static final int WIDTH = 450;
  private static final int HEIGHT = 50;

  private static final BufferedImage arrowKeys;
  private static final BufferedImage numKey;
  private static final BufferedImage keyE;
  private static final BufferedImage keyS;

  static {
    try {
      ClassLoader loader = Class.forName("ui.InstructionsPanel").getClassLoader();
      arrowKeys = ImageIO.read(
              Objects.requireNonNull(loader.getResource("icons/move.png")));
      numKey = ImageIO.read(
              Objects.requireNonNull(loader.getResource("icons/num_keys.png")));
      keyE = ImageIO.read(Objects.requireNonNull(loader.getResource("icons/pick.png")));
      keyS = ImageIO.read(Objects.requireNonNull(loader.getResource("icons/shoot.png")));

    } catch (IOException ioe) {
      throw new IllegalArgumentException("File not found.");
    } catch (ClassNotFoundException cnfe) {
      throw new IllegalArgumentException("Class not found - Instructions Panel");
    }
  }

  InstructionsPanel() {
    setLayout(null);
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setVisible(true);
    setBackground(Color.BLACK);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    setFont(new Font("TimesRoman", Font.PLAIN, FONT_SIZE));
    setForeground(Color.WHITE);

    g.drawString("How To Play The Game?", OFFSET + 140, OFFSET + 10);

    g.drawImage(arrowKeys, OFFSET, OFFSET - 5, 75, 75, null);
    g.drawString(" --- Move Player", OFFSET + 65, OFFSET + 40);

    g.drawImage(keyE, OFFSET + 30, OFFSET + 62, 15, 15, null);
    g.drawString(" --- Pickup Treasure and/or Arrows", OFFSET + 65, OFFSET + 73);

    g.drawImage(keyS, OFFSET, OFFSET + 99, 15, 15, null);
    g.drawString("+", OFFSET + 25, OFFSET + 109);
    g.drawImage(arrowKeys, OFFSET + 30, OFFSET + 65, 75, 75, null);
    g.drawString("+", OFFSET + 100, OFFSET + 109);
    g.drawImage(numKey, OFFSET + 120, OFFSET + 98, 15, 15, null);
    g.drawString("--- Shoot", OFFSET + 145, OFFSET + 110);
  }

}
