package ui;

import controller.AdvDungeonViewController;
import dungeon.ReadOnlyDungeonModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

class OptionsMenu extends JMenuBar implements ActionListener {

  private final JMenuItem resetGame;
  private final NewGameFrame ngp;

  OptionsMenu(ReadOnlyDungeonModel roModel) {

    ngp = new NewGameFrame();
    ngp.setVisible(false);

    // Menu - 1: Settings
    // Restart the game.
    JMenu restartGame = new JMenu("Restart");
    // Create a New game.
    JMenuItem newGame = new JMenuItem("New");
    restartGame.add(newGame);
    newGame.addActionListener(this);
    // Reset the existing game.
    resetGame = new JMenuItem("Reset");
    restartGame.add(resetGame);

    JMenu settings = new JMenu("Settings");
    settings.add(restartGame);
    settings.addSeparator();

    // Quit the game.
    JMenuItem quitGame = new JMenuItem("Quit");
    quitGame.setMnemonic(KeyEvent.VK_Q);
    quitGame.addActionListener(this);
    settings.add(quitGame);
    add(settings);

    // Menu - 2: Dungeon Information.
    JMenu dungeonInformation = new JMenu("Dungeon Info");
    dungeonInformation.add(new JMenuItem("Rows - " + roModel.getWidth()));
    dungeonInformation.add(new JMenuItem("Columns - " + roModel.getHeight()));
    dungeonInformation.add(new JMenuItem("Wrapping - " + roModel.getWrapping()));
    dungeonInformation.add(new JMenuItem("Interconnectivity - "
            + roModel.getInterconnectivity()));
    dungeonInformation.add(new JMenuItem("Treasure/Arrow % - " + roModel.getPercentage()));
    dungeonInformation.add(new JMenuItem("Otyugh - " + roModel.getOtyughNum()));
    dungeonInformation.add(new JMenuItem("Thieves - " + roModel.getThiefNum()));
    add(dungeonInformation);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    if (e.getActionCommand().equals("New")) {
      // new game
      ngp.setVisible(true);

    } else if (e.getActionCommand().equals("Quit")) {
      // quit game
      System.exit(0);
    }
  }

  void addActionListener(AdvDungeonViewController listener) {
    resetGame.addActionListener(listener);
  }

  void addClickListener(AdvDungeonViewController listener) {
    ngp.addClickListener(listener);
  }
  // end of class
}
