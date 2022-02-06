package ui;

import dungeon.ReadOnlyDungeonModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

class GameDetailsPanel extends JPanel {

  private static final int OFFSET = 15;
  private static final int FONT_SIZE = 15;
  private static final int WIDTH = 450;
  private static final int HEIGHT = 150;

  private final ReadOnlyDungeonModel roModel;

  GameDetailsPanel(ReadOnlyDungeonModel model) {
    this.roModel = model;
    setLayout(null);
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setBackground(Color.DARK_GRAY);
    setVisible(true);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);


    setForeground(Color.WHITE);

    if (roModel.isGameOver()) {
      g.setFont(new Font("Times Roman", Font.BOLD, FONT_SIZE * 2));
      g.drawString("GAME OVER", OFFSET + 140, OFFSET + 90);
      if (roModel.getEaten()) {
        g.drawString("Eaten by Otyugh :(", OFFSET + 95, OFFSET + 130);
      } else {
        g.setFont(new Font("TimesRoman", Font.BOLD, FONT_SIZE * 2));
        g.drawString("You escaped out of the dungeon :)", OFFSET + 20, OFFSET + 130);
      }
    } else {
      g.setFont(new Font("TimesRoman", Font.PLAIN, FONT_SIZE));
      g.drawString("LOCATION INFORMATION", OFFSET, OFFSET + 20);
      String[] details = roModel.getGameDetails().split("\n");
      int position = 3;
      for (String detail: details) {
        g.drawString(detail, OFFSET + 15, position * OFFSET + 20);
        position ++;
      }

      g.drawString("PLAYER INFORMATION", OFFSET, (position + 1) * OFFSET + 20);
      position += 2;
      details = roModel.getPlayerDetails().split("\n");
      for (String detail : details) {
        g.drawString(detail, OFFSET + 15, position * OFFSET + 25);
        position ++;
      }
    }
  }

}
