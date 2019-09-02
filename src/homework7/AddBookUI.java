/*
 * Created by JFormDesigner on Wed Jan 03 14:22:56 CST 2018
 */

package homework7;

import javax.swing.*;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

class AddBookUI extends JDialog {

  private Controller controller;

  AddBookUI() {
    initComponents();

    controller = Controller.getInstance();

    // start loading book cat items
    ArrayList<BookSpecification> bookArrayList = controller.getBookCatalog().getBookSpecifications();
    DefaultTableModel model = new DefaultTableModel(new String[] { "ISBN", "Title", "Price", "Type" }, 0) {
      // editing is not allowed
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    if (bookArrayList.isEmpty()) {
      model.addRow(new Object[] { "Empty", "NaN", "NaN", "NaN" });
    } else {
      for (BookSpecification book : bookArrayList) {
        model.addRow(new Object[] { book.getIsbn(), book.getTitle(), book.getPrice(), book.getType() });
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

    // public static final int ComputerBook = 0;
    // public static final int TextBook = 1;
    // public static final int ComicBook = 2;
    // public static final int HealthBook = 3;
    // public static final int OtherBook = 4;
    comboBoxBT.addItem("Computer Book");
    comboBoxBT.addItem("Text Book");
    comboBoxBT.addItem("Comic Book");
    comboBoxBT.addItem("Health Book");
    comboBoxBT.addItem("Other Book");

    pack();
    setModal(true);
    setVisible(true);
  }

  private void buttonAddActionPerformed(ActionEvent e) {
    String isbn = textFieldISBN.getText();
    if (isbn.isEmpty()) {
      JOptionPane.showMessageDialog(null, "ISBN is empty!");
      return;
    }

    String title = textFieldTitle.getText();
    if (title.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Title is empty!");
      return;
    }

    String price = textFieldPrice.getText();
    if (price.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Price is empty!");
      return;
    }

    int type = comboBoxBT.getSelectedIndex();

    try {
      if (controller.addBook(isbn, title, Double.parseDouble(price), type)) {
        DefaultTableModel model = (DefaultTableModel) tableBookCat.getModel();
        model.addRow(new Object[] { isbn, title, price, type });

        textFieldISBN.setText(null);
        textFieldTitle.setText(null);
        textFieldPrice.setText(null);
        comboBoxBT.setSelectedIndex(0);

        JOptionPane.showMessageDialog(null, "Successfully added " + isbn);
      } else {
        JOptionPane.showMessageDialog(null, "Failed to add " + isbn + ". Maybe already added?");
      }
    } catch (NumberFormatException nfe) {
      nfe.printStackTrace();
      JOptionPane.showMessageDialog(null, "Price or type illegal!");
    }

  }

  private void buttonEditActionPerformed(ActionEvent e) {
    String isbn = textFieldISBN.getText();
    if (isbn.isEmpty()) {
      JOptionPane.showMessageDialog(null, "ISBN is empty!");
      return;
    }

    String title = textFieldTitle.getText();
    if (title.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Title is empty!");
      return;
    }

    String price = textFieldPrice.getText();
    if (price.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Price is empty!");
      return;
    }

    int type = comboBoxBT.getSelectedIndex();

    try {
      if (controller.updateBook(isbn, title, Double.parseDouble(price), type)) {
        // start loading book cat items
        ArrayList<BookSpecification> bookArrayList = controller.getBookCatalog().getBookSpecifications();
        DefaultTableModel model = (DefaultTableModel) tableBookCat.getModel();
        model.setRowCount(0);
        if (bookArrayList.isEmpty()) {
          model.addRow(new Object[] { "Empty", "NaN", "NaN", "NaN" });
        } else {
          for (BookSpecification book : bookArrayList) {
            model.addRow(new Object[] { book.getIsbn(), book.getTitle(), book.getPrice(), book.getType() });
          }
        }

        tableBookCat.setModel(model);
        // end loading book cat items

        textFieldISBN.setText(null);
        textFieldTitle.setText(null);
        textFieldPrice.setText(null);
        comboBoxBT.setSelectedIndex(0);

        JOptionPane.showMessageDialog(null, "Successfully edited " + isbn);
      } else {
        JOptionPane.showMessageDialog(null, "Failed to edit " + isbn + ".");
      }
    } catch (NumberFormatException nfe) {
      nfe.printStackTrace();
      JOptionPane.showMessageDialog(null, "Price or type illegal!");
    }

  }

  private void buttonDeleteActionPerformed(ActionEvent e) {
    String isbn = textFieldISBN.getText();
    if (controller.removeBook(isbn)) {
      DefaultTableModel model = (DefaultTableModel) tableBookCat.getModel();
      for (int row = 0; row < model.getRowCount(); ++row) {
        if (model.getValueAt(row, 0).toString().equals(isbn)) {
          model.removeRow(row);
          break;
        }
      }
      textFieldISBN.setText(null);
      textFieldTitle.setText(null);
      textFieldPrice.setText(null);
      comboBoxBT.setSelectedIndex(0);
      JOptionPane.showMessageDialog(null, "Successfully deleted " + isbn);
    } else {
      if (isbn.isEmpty()) {
        JOptionPane.showMessageDialog(null, "ISBN is empty!", "Delete", JOptionPane.WARNING_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(null, "Failed to delete " + isbn, "Delete", JOptionPane.WARNING_MESSAGE);
      }
    }
  }

  private void tableBookCatMouseClicked(MouseEvent e) {
    int row = tableBookCat.getSelectedRow();
    textFieldISBN.setText(tableBookCat.getValueAt(row, 0).toString());
    textFieldTitle.setText(tableBookCat.getValueAt(row, 1).toString());
    textFieldPrice.setText(tableBookCat.getValueAt(row, 2).toString());
    comboBoxBT.setSelectedIndex(Integer.parseInt(tableBookCat.getValueAt(row, 3).toString()));
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY
    // //GEN-BEGIN:initComponents
    scrollPaneForTable = new JScrollPane();
    tableBookCat = new JTable();
    panelInput = new JPanel();
    labelISBN = new JLabel();
    textFieldISBN = new JTextField();
    labelTitle = new JLabel();
    textFieldTitle = new JTextField();
    labelPrice = new JLabel();
    textFieldPrice = new JTextField();
    labelType = new JLabel();
    comboBoxBT = new JComboBox<>();
    panelButtons = new JPanel();
    buttonAdd = new JButton();
    buttonEdit = new JButton();
    buttonDelete = new JButton();

    // ======== this ========
    setTitle("Book Management");
    setResizable(false);
    Container contentPane = getContentPane();
    contentPane.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));

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
      panelInput.setLayout(new FormLayout("default, $lcgap, [200px,min]:grow", "3*(default, $lgap), default"));

      // ---- labelISBN ----
      labelISBN.setText("ISBN");
      panelInput.add(labelISBN, CC.xy(1, 1, CC.CENTER, CC.DEFAULT));
      panelInput.add(textFieldISBN, CC.xy(3, 1, CC.FILL, CC.DEFAULT));

      // ---- labelTitle ----
      labelTitle.setText("Title");
      panelInput.add(labelTitle, CC.xy(1, 3, CC.CENTER, CC.DEFAULT));
      panelInput.add(textFieldTitle, CC.xy(3, 3, CC.FILL, CC.DEFAULT));

      // ---- labelPrice ----
      labelPrice.setText("Price");
      panelInput.add(labelPrice, CC.xy(1, 5, CC.CENTER, CC.DEFAULT));
      panelInput.add(textFieldPrice, CC.xy(3, 5, CC.FILL, CC.DEFAULT));

      // ---- labelType ----
      labelType.setText("Type");
      panelInput.add(labelType, CC.xy(1, 7, CC.CENTER, CC.DEFAULT));
      panelInput.add(comboBoxBT, CC.xy(3, 7));
    }
    contentPane.add(panelInput,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

    // ======== panelButtons ========
    {
      panelButtons.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), 10, 0));

      // ---- buttonAdd ----
      buttonAdd.setText("Add");
      buttonAdd.setPreferredSize(new Dimension(100, 28));
      buttonAdd.setBackground(new Color(51, 255, 51));
      buttonAdd.addActionListener(e -> buttonAddActionPerformed(e));
      panelButtons.add(buttonAdd,
          new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- buttonEdit ----
      buttonEdit.setText("Edit");
      buttonEdit.setPreferredSize(new Dimension(100, 28));
      buttonEdit.setBackground(Color.yellow);
      buttonEdit.addActionListener(e -> buttonEditActionPerformed(e));
      panelButtons.add(buttonEdit,
          new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- buttonDelete ----
      buttonDelete.setText("Delete");
      buttonDelete.setPreferredSize(new Dimension(100, 28));
      buttonDelete.setBackground(Color.red);
      buttonDelete.setForeground(Color.white);
      buttonDelete.addActionListener(e -> buttonDeleteActionPerformed(e));
      panelButtons.add(buttonDelete,
          new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
    }
    contentPane.add(panelButtons,
        new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
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
  private JLabel labelTitle;
  private JTextField textFieldTitle;
  private JLabel labelPrice;
  private JTextField textFieldPrice;
  private JLabel labelType;
  private JComboBox<String> comboBoxBT;
  private JPanel panelButtons;
  private JButton buttonAdd;
  private JButton buttonEdit;
  private JButton buttonDelete;
  // JFormDesigner - End of variables declaration //GEN-END:variables
}
