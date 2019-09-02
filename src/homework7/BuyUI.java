/*
 * Created by JFormDesigner on Wed Jan 03 12:07:37 CST 2018
 */

package homework7;

import javax.swing.*;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

class BuyUI extends JDialog {

  private Controller controller;

  BuyUI() {
    initComponents();

    controller = Controller.getInstance();

    // start loading book cat items
    ArrayList<BookSpecification> bookArrayList = controller.getBookCatalog().getBookSpecifications();
    DefaultTableModel model = new DefaultTableModel(new String[] { "ISBN", "Title", "Price" }, 0) {
      // editing is not allowed
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    if (bookArrayList.isEmpty()) {
      model.addRow(new Object[] { "Empty", "Empty", 0 });
    } else {
      for (BookSpecification book : bookArrayList) {
        model.addRow(new Object[] { book.getIsbn(), book.getTitle(), book.getPrice() });
      }
    }

    tableBookCat.setModel(model);
    tableBookCat.setRowHeight(50);
    tableBookCat.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public int getHorizontalAlignment() {
        return SwingConstants.CENTER;
      }
    });
    // end loading book cat items

    pack();
    setModal(true);
    setVisible(true);
  }

  private void buttonViewCarActionPerformed(ActionEvent e) {
    new ShoppingCarUI();
  }

  private void buttonEmptyCarActionPerformed(ActionEvent e) {
    try {
      controller.getSale().resetSale();
      JOptionPane.showMessageDialog(null, "Successfully emptied car!", "Empty Car", JOptionPane.INFORMATION_MESSAGE);
    } catch (NumberFormatException nfe) {
      nfe.printStackTrace();
    }
    textFieldISBN.setText(null);
    spinnerAmount.setValue(0);
  }

  private void buttonAddToCarActionPerformed(ActionEvent e) {
    String isbn = textFieldISBN.getText();
    if (controller.getBookCatalog().getBookSpecification(isbn) == null) {
      JOptionPane.showMessageDialog(null, "isbn not found!");
      return;
    }

    int amount = (Integer) spinnerAmount.getValue();
    if (amount <= 0) {
      JOptionPane.showMessageDialog(null, "Amount illegal!");
      return;
    }

    try {
      controller.buyBook(isbn, amount);
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e1) {
      e1.printStackTrace();
    }

    textFieldISBN.setText(null);
    spinnerAmount.setValue(0);

    new ShoppingCarUI();
  }

  private void tableBookCatMouseClicked(MouseEvent e) {
    int row = tableBookCat.getSelectedRow();
    textFieldISBN.setText(tableBookCat.getValueAt(row, 0).toString());
    spinnerAmount.setValue(1);
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY
    // //GEN-BEGIN:initComponents
    scrollPaneForTable = new JScrollPane();
    tableBookCat = new JTable();
    panelInput = new JPanel();
    labelISBN = new JLabel();
    textFieldISBN = new JTextField();
    labelAmount = new JLabel();
    spinnerAmount = new JSpinner();
    panelButtons = new JPanel();
    buttonViewCar = new JButton();
    buttonEmptyCar = new JButton();
    hSpacerBottom = new Spacer();
    buttonAddToCar = new JButton();

    // ======== this ========
    setMinimumSize(new Dimension(400, 300));
    setTitle("Browse Catalog & Buy");
    setResizable(false);
    Container contentPane = getContentPane();
    contentPane.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));

    // ======== scrollPaneForTable ========
    {

      // ---- tableBookCat ----
      tableBookCat.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          tableBookCatMouseClicked(e);
        }
      });
      scrollPaneForTable.setViewportView(tableBookCat);
    }
    contentPane.add(scrollPaneForTable,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

    // ======== panelInput ========
    {
      panelInput.setPreferredSize(new Dimension(200, 68));
      panelInput
          .setLayout(new FormLayout("center:default:grow, $lcgap, left:[100px,min]:grow", "default, $lgap, default"));

      // ---- labelISBN ----
      labelISBN.setText("ISBN");
      panelInput.add(labelISBN, CC.xy(1, 1));

      // ---- textFieldISBN ----
      textFieldISBN.setMinimumSize(new Dimension(200, 30));
      panelInput.add(textFieldISBN, CC.xy(3, 1, CC.FILL, CC.DEFAULT));

      // ---- labelAmount ----
      labelAmount.setText("Amount");
      panelInput.add(labelAmount, CC.xy(1, 3));

      // ---- spinnerAmount ----
      spinnerAmount.setMinimumSize(new Dimension(200, 32));
      panelInput.add(spinnerAmount, CC.xy(3, 3, CC.FILL, CC.DEFAULT));
    }
    contentPane.add(panelInput,
        new GridConstraints(1, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

    // ======== panelButtons ========
    {
      panelButtons.setPreferredSize(new Dimension(0, 40));
      panelButtons.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), 0, 0));

      // ---- buttonViewCar ----
      buttonViewCar.setText("View Car");
      buttonViewCar.addActionListener(e -> buttonViewCarActionPerformed(e));
      panelButtons.add(buttonViewCar,
          new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_FIXED,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- buttonEmptyCar ----
      buttonEmptyCar.setText("Empty Car");
      buttonEmptyCar.addActionListener(e -> buttonEmptyCarActionPerformed(e));
      panelButtons.add(buttonEmptyCar,
          new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_FIXED,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- hSpacerBottom ----
      hSpacerBottom.setMinimumSize(new Dimension(100, 12));
      panelButtons.add(hSpacerBottom,
          new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW
                  | GridConstraints.SIZEPOLICY_WANT_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK, new Dimension(50, 1), new Dimension(100, 1), null));

      // ---- buttonAddToCar ----
      buttonAddToCar.setText("Add to car");
      buttonAddToCar.setMinimumSize(new Dimension(200, 40));
      buttonAddToCar.setMaximumSize(null);
      buttonAddToCar.setBackground(Color.orange);
      buttonAddToCar.setBorder(null);
      buttonAddToCar.setBorderPainted(false);
      buttonAddToCar.setForeground(Color.black);
      buttonAddToCar.setFont(buttonAddToCar.getFont().deriveFont(Font.BOLD, buttonAddToCar.getFont().getSize() + 5f));
      buttonAddToCar.addActionListener(e -> buttonAddToCarActionPerformed(e));
      panelButtons.add(buttonAddToCar,
          new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
    }
    contentPane.add(panelButtons,
        new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
    pack();
    setLocationRelativeTo(getOwner());
    // JFormDesigner - End of component initialization //GEN-END:initComponents
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY //GEN-BEGIN:variables
  private JScrollPane scrollPaneForTable;
  private JTable tableBookCat;
  private JPanel panelInput;
  private JLabel labelISBN;
  private JTextField textFieldISBN;
  private JLabel labelAmount;
  private JSpinner spinnerAmount;
  private JPanel panelButtons;
  private JButton buttonViewCar;
  private JButton buttonEmptyCar;
  private Spacer hSpacerBottom;
  private JButton buttonAddToCar;
  // JFormDesigner - End of variables declaration //GEN-END:variables
}
