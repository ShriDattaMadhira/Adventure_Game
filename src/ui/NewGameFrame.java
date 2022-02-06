package ui;

import controller.AdvDungeonViewController;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


class NewGameFrame extends JFrame {

  private final JTextField width;
  private final JTextField height;
  private final JTextField wrapping;
  private final JTextField interconnectivity;
  private final JTextField taPercentage;
  private final JTextField numOtyughs;

  private final JButton startGameButton;

  NewGameFrame() {

    // frame = new JFrame();
    setBounds(100, 100, 400, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setLayout(null);
    setVisible(true);
    getContentPane().setBackground(Color.DARK_GRAY);

    JLabel widthLabel = new JLabel("Width");
    widthLabel.setBounds(50, 31, 200, 14);
    widthLabel.setForeground(Color.WHITE);
    getContentPane().add(widthLabel);
    width = new JTextField();
    width.setBounds(201, 28, 150, 20);
    getContentPane().add(width);
    width.setColumns(10);

    JLabel heightLabel = new JLabel("Height");
    heightLabel.setBounds(50, 68, 200, 14);
    heightLabel.setForeground(Color.WHITE);
    getContentPane().add(heightLabel);
    height = new JTextField();
    height.setBounds(201, 65, 150, 20);
    getContentPane().add(height);
    height.setColumns(10);

    JLabel wrapLabel = new JLabel("Wrapping (true/false)");
    wrapLabel.setBounds(50, 105, 200, 14);
    wrapLabel.setForeground(Color.WHITE);
    getContentPane().add(wrapLabel);
    wrapping = new JTextField();
    wrapping.setBounds(201, 102, 150, 20);
    getContentPane().add(wrapping);
    wrapping.setColumns(10);

    JLabel interConLabel = new JLabel("Interconnectivity");
    interConLabel.setBounds(50, 142, 200, 14);
    interConLabel.setForeground(Color.WHITE);
    getContentPane().add(interConLabel);
    interconnectivity = new JTextField();
    interconnectivity.setBounds(201, 139,150, 20);
    getContentPane().add(interconnectivity);
    interconnectivity.setColumns(10);

    JLabel percentageLabel = new JLabel("Treasure & Arrow %");
    percentageLabel.setBounds(50, 179, 200, 14);
    percentageLabel.setForeground(Color.WHITE);
    getContentPane().add(percentageLabel);
    taPercentage = new JTextField();
    taPercentage.setBounds(201, 176, 150, 20);
    getContentPane().add(taPercentage);
    taPercentage.setColumns(10);

    JLabel otyughNumLabel = new JLabel("Number of monsters");
    otyughNumLabel.setBounds(50, 216, 200, 14);
    otyughNumLabel.setForeground(Color.WHITE);
    getContentPane().add(otyughNumLabel);
    numOtyughs = new JTextField();
    numOtyughs.setBounds(201, 213, 150, 20);
    getContentPane().add(numOtyughs);
    numOtyughs.setColumns(10);

    startGameButton = new JButton("Start Game");
    startGameButton.setBounds(100, 250, 175, 23);
    getContentPane().add(startGameButton);

  }


  void addClickListener(AdvDungeonViewController listener) {
    MouseAdapter newGameButtonClickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        try {
          setVisible(false);
          listener.handleMouseInputs("new " + width.getText() + " " + height.getText() + " "
                  + wrapping.getText() + " " + interconnectivity.getText() + " "
                  + taPercentage.getText() + " " + numOtyughs.getText());
        } catch (Exception exception) {
          JOptionPane.showMessageDialog(startGameButton, exception.getMessage(),
              "Data Missing/Wrong", JOptionPane.ERROR_MESSAGE);
        }
      }
    };
    startGameButton.addMouseListener(newGameButtonClickAdapter);
  }
  // end of class.
}
