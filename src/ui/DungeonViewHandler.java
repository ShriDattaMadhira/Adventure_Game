package ui;

import controller.AdvDungeonViewController;
import dungeon.ReadOnlyDungeonModel;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 * This is the main base window that contains different panels and Menu bar we created.
 */
public class DungeonViewHandler extends JFrame implements DungeonView {

  private final DungeonPanel dungeonPanel;
  private final OptionsMenu menuBar;

  /**
   * Constructor for the view handler class.
   * @param roModel - Dungeon game.
   */
  public DungeonViewHandler(ReadOnlyDungeonModel roModel) {

    super("Dungeon Adventure Game");
    this.setSize(1000,500);
    setLayout(new GridLayout());

    menuBar = new OptionsMenu(roModel);
    setJMenuBar(menuBar);

    dungeonPanel = new DungeonPanel(roModel);
    dungeonPanel.setFocusable(true);
    JScrollPane dungeonPanelScroll = new JScrollPane(dungeonPanel);

    DetailsPanel detailsPanel = new DetailsPanel(roModel);
    JScrollPane detailsScroll = new JScrollPane(detailsPanel);

    add(dungeonPanelScroll);
    add(detailsScroll);

    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public void addClickListener(AdvDungeonViewController listener) {
    // Mouse input listener
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        if (e.getComponent().equals(dungeonPanel)) {
          int r = (e.getX() - dungeonPanel.getWidthOffset()) / DungeonPanel.CELL_SIZE;
          int c = (e.getY() - dungeonPanel.getHeightOffset()) / DungeonPanel.CELL_SIZE;
          listener.handleMouseInputs("move " + c + " " + r);
        }
      }
    };

    // keyboard input listener
    KeyAdapter keyAdapter = new KeyAdapter() {
      boolean containsShoot = false;

      @Override
      public void keyReleased(KeyEvent e) {
        super.keyReleased(e);

        String action = "";
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
          if (containsShoot) {
            action = "shoot north ";
          } else {
            action = "north";
          }
        } else if (key == KeyEvent.VK_DOWN) {
          if (containsShoot) {
            action = "shoot south ";
          } else {
            action = "south";
          }
        } else if (key == KeyEvent.VK_LEFT) {
          if (containsShoot) {
            action = "shoot west ";
          } else {
            action = "west";
          }
        } else if (key == KeyEvent.VK_RIGHT) {
          if (containsShoot) {
            action = "shoot east ";
          } else {
            action = "east";
          }
        } else if (key == KeyEvent.VK_E) {
          action = "pickup";
        } else if (key == KeyEvent.VK_S) {
          action = "shoot ";
          containsShoot = true;
        } else {
          containsShoot = false;
        }

        if (containsShoot) {
          if (action.split(" ").length == 2) {
            action += JOptionPane.showInputDialog("Enter Distance to shoot the arrow");
            containsShoot = false;
            listener.handleKeyboardInputs(action);
          }
        } else {
          listener.handleKeyboardInputs(action);
        }
      }
    };

    dungeonPanel.addMouseListener(clickAdapter); // dungeon panel mouse listener
    dungeonPanel.addKeyListener(keyAdapter); // dungeon panel keyboard listener
    menuBar.addActionListener(listener); // new game listener
    menuBar.addClickListener(listener); // reset game listener
    dungeonPanel.setFocusable(true);

  }

  @Override
  public void refresh() {
    repaint();
  }

  @Override
  public void makeVisible(boolean visible) {
    if (visible) {
      setVisible(true);
    } else {
      this.dispose();
    }
  }
}
