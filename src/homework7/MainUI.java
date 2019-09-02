package homework7;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

class MainUI extends JFrame {

  public static void main(String[] args) {
    new MainUI();
  }

  MainUI() {
    initComponents();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setPreferredSize(new Dimension(600, 400));

    setVisible(true);
  }

  private void buttonBookManActionPerformed(ActionEvent e) {
    new AddBookUI();
  }

  private void buttonShoppingCartActionPerformed(ActionEvent e) {
    new BuyUI();
  }

  private void buttonStrategyManActionPerformed(ActionEvent e) {
    new StrategiesUI();
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY
    // //GEN-BEGIN:initComponents
    JLabel labelBanner = new JLabel();
    JButton buttonBookMan = new JButton();
    JButton buttonViewBooks = new JButton();
    JButton buttonStrategyMan = new JButton();

    // ======== this ========
    setTitle("A Simple Bookstore");
    setIconImage(new ImageIcon(getClass().getResource("/homework7/icon.png")).getImage());
    setMinimumSize(new Dimension(600, 400));
    setResizable(false);
    Container contentPane = getContentPane();
    contentPane.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), 0, 0));

    // ---- labelBanner ----
    labelBanner.setIcon(new ImageIcon(getClass().getResource("/homework7/banner.jpg")));
    contentPane.add(labelBanner,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

    // ---- buttonBookMan ----
    buttonBookMan.setText("Book Management");
    buttonBookMan.addActionListener(e -> buttonBookManActionPerformed(e));
    contentPane.add(buttonBookMan,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

    // ---- buttonViewBooks ----
    buttonViewBooks.setText("Browse Catalog & Buy");
    buttonViewBooks.addActionListener(e -> buttonShoppingCartActionPerformed(e));
    contentPane.add(buttonViewBooks,
        new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

    // ---- buttonStrategyMan ----
    buttonStrategyMan.setText("Strategy Management");
    buttonStrategyMan.addActionListener(e -> buttonStrategyManActionPerformed(e));
    contentPane.add(buttonStrategyMan,
        new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
    setSize(480, 322);
    setLocationRelativeTo(getOwner());
    // JFormDesigner - End of component initialization //GEN-END:initComponents
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY //GEN-BEGIN:variables
  // JFormDesigner - End of variables declaration //GEN-END:variables
}
