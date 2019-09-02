/*
 * Created by JFormDesigner on Wed Jan 03 22:34:19 CST 2018
 */

package homework7;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

class StrategiesUI extends JDialog {

  private Controller controller;

  StrategiesUI() {
    initComponents();

    panelSS.setVisible(false);
    panelCS.setVisible(false);

    controller = Controller.getInstance();

    // start loading items
    ArrayList<StrategyMode> strategyModes = controller.getStrategyCatalog().getStrategyList();
    DefaultTableModel model = new DefaultTableModel(new String[] { "No.", "Name", "Type", "Book Type", "Discount" },
        0) {
      // editing is not allowed
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    if (strategyModes.isEmpty()) {
      model.addRow(new Object[] { "Empty", "NaN", "NaN", "NaN", "NaN" });
    } else {
      for (StrategyMode s : strategyModes) {
        model
            .addRow(
                new Object[] { "Discount" + String.format("%03d", model.getRowCount() + 1), s.getName(),
                    s.getTypeName(), s.getBookType(),
                    s.getStrategyType() == StrategyMode.CompositeDiscount ? "Discount"
                        + String.format("%03d", s.getsType1()) + "+" + "Discount" + String.format("%03d", s.getsType2())
                        : String.valueOf(s.getStrategy().getDiscount()) });
      }
    }

    table.setModel(model);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.getColumnModel().getColumn(4).setWidth(200);
    table.setRowHeight(50);
    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public int getHorizontalAlignment() {
        return SwingConstants.CENTER;
      }
    });
    // end loading items

    // start loading combo boxes
    // public static final int PercentageDiscount = 0;
    // public static final int AbsoluteDiscount = 1;
    // public static final int CompositeDiscount = 2;
    // public static final int NoDiscount = 3;
    comboBoxSSType.addItem("Percentage Discount");
    comboBoxSSType.addItem("Absolute Discount");

    comboBoxSSType2.addItem("Percentage Discount");
    comboBoxSSType2.addItem("Absolute Discount");

    int rowCount = model.getRowCount();
    for (int i = 0; i < rowCount; ++i) {
      String item = "Discount" + String.format("%03d", i + 1);
      comboBoxCSST1.addItem(item);
      comboBoxCSST2.addItem(item);
      comboBoxCSST12.addItem(item);
      comboBoxCSST22.addItem(item);
    }

    // public static final int ComputerBook = 0;
    // public static final int TextBook = 1;
    // public static final int ComicBook = 2;
    // public static final int HealthBook = 3;
    // public static final int OtherBook = 4;
    comboBoxSSBT.addItem("Computer Books");
    comboBoxSSBT.addItem("Text Books");
    comboBoxSSBT.addItem("Comic Books");
    comboBoxSSBT.addItem("Health Books");
    comboBoxSSBT.addItem("Other Books");

    comboBoxCSBT.addItem("Computer Books");
    comboBoxCSBT.addItem("Text Books");
    comboBoxCSBT.addItem("Comic Books");
    comboBoxCSBT.addItem("Health Books");
    comboBoxCSBT.addItem("Other Books");

    comboBoxSSBT2.addItem("Computer Books");
    comboBoxSSBT2.addItem("Text Books");
    comboBoxSSBT2.addItem("Comic Books");
    comboBoxSSBT2.addItem("Health Books");
    comboBoxSSBT2.addItem("Other Books");

    comboBoxCSBT2.addItem("Computer Books");
    comboBoxCSBT2.addItem("Text Books");
    comboBoxCSBT2.addItem("Comic Books");
    comboBoxCSBT2.addItem("Health Books");
    comboBoxCSBT2.addItem("Other Books");

    setModal(true);
    pack();
    setLocationRelativeTo(getOwner());
    setVisible(true);
  }

  private void tableMouseClicked(MouseEvent e) {
    int row = table.getSelectedRow();
    ArrayList<StrategyMode> strategyModes = controller.getStrategyCatalog().getStrategyList();

    StrategyMode strategy = strategyModes.get(row);
    int stype = strategy.getStrategyType();
    if (stype == StrategyMode.CompositeDiscount) {
      textFieldCSName.setText(strategy.getName());
      comboBoxCSBT.setSelectedIndex(strategy.getBookType());
      comboBoxCSST1.setSelectedIndex(strategy.getsType1() - 1);
      comboBoxCSST2.setSelectedIndex(strategy.getsType2() - 1);

      panelSS.setVisible(false);
      panelCS.setVisible(true);
    } else {
      textFieldSSName.setText(strategy.getName());
      comboBoxSSBT.setSelectedIndex(strategy.getBookType());
      comboBoxSSType.setSelectedIndex(strategy.getStrategyType());
      spinnerSSDiscount.setValue(strategy.getStrategy().getDiscount());

      panelSS.setVisible(true);
      panelCS.setVisible(false);
    }

    pack();
    setLocationRelativeTo(getOwner());
  }

  private void buttonAddSSActionPerformed(ActionEvent e) {
    textFieldSSName2.setText(null);
    comboBoxSSType2.setSelectedIndex(0);
    comboBoxSSBT2.setSelectedIndex(0);
    spinnerSSDiscount2.setValue(0);

    dialogAddSS.pack();
    dialogAddSS.setLocationRelativeTo(this);
    dialogAddSS.setModal(true);
    dialogAddSS.setVisible(true);
  }

  private void buttonAddSSConfirmActionPerformed(ActionEvent e) {
    String name = textFieldSSName2.getText();
    int stype = comboBoxSSType2.getSelectedIndex();
    int btype = comboBoxSSBT2.getSelectedIndex();
    double discount = Double.parseDouble(spinnerSSDiscount2.getValue().toString());

    controller.addSimpleStrategy(name, btype, stype, discount);

    ArrayList<StrategyMode> strategyModes = controller.getStrategyCatalog().getStrategyList();
    StrategyMode lastMode = strategyModes.get(strategyModes.size() - 1);

    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.addRow(new Object[] { "Discount" + String.format("%03d", model.getRowCount()), lastMode.getName(),
        lastMode.getTypeName(), lastMode.getBookType(), String.valueOf(lastMode.getStrategy().getDiscount()) });

    refreshComboBoxes();

    textFieldSSName2.setText(null);
    comboBoxSSType2.setSelectedIndex(0);
    comboBoxSSBT2.setSelectedIndex(0);
    spinnerSSDiscount2.setValue(0);
    dialogAddSS.setVisible(false);

    JOptionPane.showMessageDialog(null, "Successfully added simple strategy " + name + "!");
  }

  private void buttonAddCSActionPerformed(ActionEvent e) {
    textFieldCSName2.setText(null);
    comboBoxCSST12.setSelectedIndex(0);
    comboBoxCSST22.setSelectedIndex(0);
    comboBoxCSBT2.setSelectedIndex(0);

    dialogAddCS.pack();
    dialogAddCS.setLocationRelativeTo(this);
    dialogAddCS.setModal(true);
    dialogAddCS.setVisible(true);
  }

  private void buttonAddCSConfirmActionPerformed(ActionEvent e) {
    String name = textFieldCSName2.getText();
    int stype1 = comboBoxCSST12.getSelectedIndex();
    int stype2 = comboBoxCSST22.getSelectedIndex();
    int btype = comboBoxCSBT2.getSelectedIndex();

    controller.addCompositeStrategy(name, btype, stype1, stype2);

    ArrayList<StrategyMode> strategyModes = controller.getStrategyCatalog().getStrategyList();
    StrategyMode lastMode = strategyModes.get(strategyModes.size() - 1);

    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.addRow(new Object[] { "Discount" + String.format("%03d", model.getRowCount()), lastMode.getName(),
        lastMode.getTypeName(), lastMode.getBookType(), "Discount" + String.format("%03d", lastMode.getsType1()) + "+"
            + "Discount" + String.format("%03d", lastMode.getsType2()) });

    refreshComboBoxes();

    textFieldCSName2.setText(null);
    comboBoxCSST12.setSelectedIndex(0);
    comboBoxCSST22.setSelectedIndex(0);
    comboBoxCSBT2.setSelectedIndex(0);
    dialogAddSS.setVisible(false);

    JOptionPane.showMessageDialog(null, "Successfully added composite strategy " + name + "!");
  }

  private void buttonSSUpdateActionPerformed(ActionEvent e) {
    int row = table.getSelectedRow();
    if (row < 0 || row > table.getRowCount()) {
      JOptionPane.showMessageDialog(null, "Please select a line first!");
      return;
    }

    String name = textFieldSSName.getText();
    int bType = comboBoxSSBT.getSelectedIndex();
    int sType = comboBoxSSType.getSelectedIndex();
    double discount;

    try {
      discount = Double.parseDouble(spinnerSSDiscount.getValue().toString());

      controller.updateSimpleStrategy(row, name, bType, sType, discount);

      ArrayList<StrategyMode> strategyModes = controller.getStrategyCatalog().getStrategyList();
      StrategyMode lastMode = strategyModes.get(row);

      DefaultTableModel model = (DefaultTableModel) table.getModel();
      model.removeRow(row);
      model.insertRow(row,
          new Object[] { "Discount" + String.format("%03d", row), name, lastMode.getTypeName(), bType, discount });

      panelSS.setVisible(false);
      this.pack();
      this.setLocationRelativeTo(getOwner());

      JOptionPane.showMessageDialog(null, "Successfully updated " + name + "!");
    } catch (NumberFormatException nfe) {
      nfe.printStackTrace();
      JOptionPane.showMessageDialog(null, "Discount illegal!", "Warning", JOptionPane.WARNING_MESSAGE);
    }
  }

  private boolean deleteStrategy() {
    int row = table.getSelectedRow();
    if (0 <= row && row <= 3) {
      JOptionPane.showMessageDialog(null, "Default strategies cannot be deleted!");
      return false;
    }
    if (row < 0 || row > table.getRowCount()) {
      JOptionPane.showMessageDialog(null, "Please select a line first!");
      return false;
    }

    controller.deleteStrategy(row);

    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.removeRow(row);

    refreshComboBoxes();

    return true;
  }

  private void buttonSSDeleteActionPerformed(ActionEvent e) {
    if (deleteStrategy()) {
      panelSS.setVisible(false);
      this.pack();
      this.setLocationRelativeTo(getOwner());

      JOptionPane.showMessageDialog(null, "Successfully deleted!");
    }
  }

  private void buttonCSUpdateActionPerformed(ActionEvent e) {
    int row = table.getSelectedRow();
    if (row < 0 || row > table.getRowCount()) {
      JOptionPane.showMessageDialog(null, "Please select a line first!");
      return;
    }

    String name = textFieldCSName.getText();
    int bType = comboBoxCSBT.getSelectedIndex();
    int sType1 = comboBoxCSST1.getSelectedIndex() + 1;
    int sType2 = comboBoxCSST2.getSelectedIndex() + 1;

    controller.updateCompositeStrategy(row, name, bType, sType1, sType2);

    ArrayList<StrategyMode> strategyModes = controller.getStrategyCatalog().getStrategyList();
    StrategyMode lastMode = strategyModes.get(row);

    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.removeRow(row);
    model.insertRow(row, new Object[] { "Discount" + String.format("%03d", row), name, lastMode.getTypeName(), bType,
        "Discount" + String.format("%03d", sType1) + "+" + "Discount" + String.format("%03d", sType2) });

    panelCS.setVisible(false);
    this.pack();
    this.setLocationRelativeTo(getOwner());

    JOptionPane.showMessageDialog(null, "Successfully updated " + name + "!");
  }

  private void buttonCSDeleteActionPerformed(ActionEvent e) {
    if (deleteStrategy()) {
      panelCS.setVisible(false);
      this.pack();
      this.setLocationRelativeTo(getOwner());

      JOptionPane.showMessageDialog(null, "Successfully deleted!");
    }
  }

  private void refreshComboBoxes() {
    int rowCount = table.getModel().getRowCount();
    comboBoxCSST1.removeAllItems();
    comboBoxCSST2.removeAllItems();
    comboBoxCSST12.removeAllItems();
    comboBoxCSST22.removeAllItems();
    for (int i = 0; i < rowCount; ++i) {
      String item = table.getValueAt(i, 0).toString();
      comboBoxCSST1.addItem(item);
      comboBoxCSST2.addItem(item);
      comboBoxCSST12.addItem(item);
      comboBoxCSST22.addItem(item);
    }
  }

  // @Override
  // public Dimension getPreferredSize() {
  // return new Dimension(700, 700);
  // }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY
    // //GEN-BEGIN:initComponents
    JScrollPane scrollPane = new JScrollPane();
    table = new JTable();
    JPanel panelAdd = new JPanel();
    JButton buttonAddSS = new JButton();
    JButton buttonAddCS = new JButton();
    panelSS = new JPanel();
    JLabel labelSS = new JLabel();
    JLabel labelSSName = new JLabel();
    textFieldSSName = new JTextField();
    JLabel labelSSType = new JLabel();
    comboBoxSSType = new JComboBox<>();
    JButton buttonSSUpdate = new JButton();
    JLabel labelSSBT = new JLabel();
    comboBoxSSBT = new JComboBox<>();
    JButton buttonSSDelete = new JButton();
    JLabel labelSSDiscount = new JLabel();
    spinnerSSDiscount = new JSpinner();
    panelCS = new JPanel();
    JLabel labelCS = new JLabel();
    JLabel labelCSName = new JLabel();
    textFieldCSName = new JTextField();
    JLabel labelCSST1 = new JLabel();
    comboBoxCSST1 = new JComboBox<>();
    JButton buttonCSUpdate = new JButton();
    JLabel labelCSST2 = new JLabel();
    comboBoxCSST2 = new JComboBox<>();
    JButton buttonCSDelete = new JButton();
    JLabel labelCSBT = new JLabel();
    comboBoxCSBT = new JComboBox<>();
    dialogAddSS = new JDialog();
    JLabel labelSSName2 = new JLabel();
    textFieldSSName2 = new JTextField();
    JLabel labelSSType2 = new JLabel();
    comboBoxSSType2 = new JComboBox<>();
    JLabel labelSSBT2 = new JLabel();
    comboBoxSSBT2 = new JComboBox<>();
    JLabel labelSSDiscount2 = new JLabel();
    spinnerSSDiscount2 = new JSpinner();
    JButton buttonAddSSConfirm = new JButton();
    dialogAddCS = new JDialog();
    JLabel labelCSST12 = new JLabel();
    comboBoxCSST12 = new JComboBox<>();
    JLabel labelCSBT2 = new JLabel();
    comboBoxCSST22 = new JComboBox<>();
    comboBoxCSBT2 = new JComboBox<>();
    textFieldCSName2 = new JTextField();
    JLabel labelCSST22 = new JLabel();
    JLabel labelCSName2 = new JLabel();
    JButton buttonAddCSConfirm = new JButton();

    // ======== this ========
    setModal(true);
    setTitle("Strategy Management");
    setBackground(new Color(204, 255, 204));
    setMinimumSize(new Dimension(800, 34));
    Container contentPane = getContentPane();
    contentPane.setLayout(new GridLayoutManager(4, 1, new Insets(10, 0, 10, 0), -1, -1));

    // ======== scrollPane ========
    {
      scrollPane.setMinimumSize(new Dimension(400, 27));

      // ---- table ----
      table.setMaximumSize(new Dimension(2147483647, 32));
      table.setMinimumSize(new Dimension(400, 32));
      table.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          tableMouseClicked(e);
        }
      });
      scrollPane.setViewportView(table);
    }
    contentPane.add(scrollPane,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

    // ======== panelAdd ========
    {
      panelAdd.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 0, 0));

      // ---- buttonAddSS ----
      buttonAddSS.setText("Add Simple Strategy");
      buttonAddSS.addActionListener(e -> buttonAddSSActionPerformed(e));
      panelAdd.add(buttonAddSS,
          new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 1),
              null));

      // ---- buttonAddCS ----
      buttonAddCS.setText("Add Composite Strategy");
      buttonAddCS.addActionListener(e -> buttonAddCSActionPerformed(e));
      panelAdd.add(buttonAddCS,
          new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 1),
              null));
    }
    contentPane.add(panelAdd,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

    // ======== panelSS ========
    {
      panelSS.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));

      // ---- labelSS ----
      labelSS.setText("Simple Strategies");
      labelSS.setBorder(null);
      panelSS.add(labelSS,
          new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- labelSSName ----
      labelSSName.setText("Name");
      panelSS.add(labelSSName,
          new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      panelSS.add(textFieldSSName,
          new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(200, 1), null,
              null));

      // ---- labelSSType ----
      labelSSType.setText("Type");
      panelSS.add(labelSSType,
          new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      panelSS.add(comboBoxSSType,
          new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- buttonSSUpdate ----
      buttonSSUpdate.setText("Update");
      buttonSSUpdate.addActionListener(e -> buttonSSUpdateActionPerformed(e));
      panelSS.add(buttonSSUpdate,
          new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- labelSSBT ----
      labelSSBT.setText("Book type");
      panelSS.add(labelSSBT,
          new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      panelSS.add(comboBoxSSBT,
          new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- buttonSSDelete ----
      buttonSSDelete.setText("Delete");
      buttonSSDelete.setBackground(new Color(255, 0, 51));
      buttonSSDelete.setForeground(Color.white);
      buttonSSDelete.addActionListener(e -> buttonSSDeleteActionPerformed(e));
      panelSS.add(buttonSSDelete,
          new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- labelSSDiscount ----
      labelSSDiscount.setText("Discount");
      panelSS.add(labelSSDiscount,
          new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      panelSS.add(spinnerSSDiscount,
          new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
    }
    contentPane.add(panelSS,
        new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

    // ======== panelCS ========
    {
      panelCS.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));

      // ---- labelCS ----
      labelCS.setText("Composite Strategies");
      labelCS.setBackground(new Color(204, 255, 204));
      labelCS.setBorder(null);
      panelCS.add(labelCS,
          new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- labelCSName ----
      labelCSName.setText("Name");
      panelCS.add(labelCSName,
          new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      panelCS.add(textFieldCSName,
          new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(200, 1), null,
              null));

      // ---- labelCSST1 ----
      labelCSST1.setText("Strategy type 1");
      panelCS.add(labelCSST1,
          new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      panelCS.add(comboBoxCSST1,
          new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- buttonCSUpdate ----
      buttonCSUpdate.setText("Update");
      buttonCSUpdate.addActionListener(e -> buttonCSUpdateActionPerformed(e));
      panelCS.add(buttonCSUpdate,
          new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- labelCSST2 ----
      labelCSST2.setText("Strategy type 2");
      panelCS.add(labelCSST2,
          new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      panelCS.add(comboBoxCSST2,
          new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- buttonCSDelete ----
      buttonCSDelete.setText("Delete");
      buttonCSDelete.setForeground(new Color(238, 238, 238));
      buttonCSDelete.setBackground(Color.red);
      buttonCSDelete.addActionListener(e -> buttonCSDeleteActionPerformed(e));
      panelCS.add(buttonCSDelete,
          new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- labelCSBT ----
      labelCSBT.setText("Book type");
      panelCS.add(labelCSBT,
          new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      panelCS.add(comboBoxCSBT,
          new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
    }
    contentPane.add(panelCS,
        new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
    pack();
    setLocationRelativeTo(getOwner());

    // ======== dialogAddSS ========
    {
      dialogAddSS.setTitle("Adding Simple Strategy");
      dialogAddSS.setName("dialogAddSS");
      Container dialogAddSSContentPane = dialogAddSS.getContentPane();
      dialogAddSSContentPane.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), 0, 0));

      // ---- labelSSName2 ----
      labelSSName2.setText("Name");
      dialogAddSSContentPane.add(labelSSName2,
          new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      dialogAddSSContentPane.add(textFieldSSName2,
          new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- labelSSType2 ----
      labelSSType2.setText("Type");
      dialogAddSSContentPane.add(labelSSType2,
          new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      dialogAddSSContentPane.add(comboBoxSSType2,
          new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- labelSSBT2 ----
      labelSSBT2.setText("Book type");
      dialogAddSSContentPane.add(labelSSBT2,
          new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      dialogAddSSContentPane.add(comboBoxSSBT2,
          new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- labelSSDiscount2 ----
      labelSSDiscount2.setText("Discount");
      dialogAddSSContentPane.add(labelSSDiscount2,
          new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      dialogAddSSContentPane.add(spinnerSSDiscount2,
          new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- buttonAddSSConfirm ----
      buttonAddSSConfirm.setText("Confirm");
      buttonAddSSConfirm.addActionListener(e -> buttonAddSSConfirmActionPerformed(e));
      dialogAddSSContentPane.add(buttonAddSSConfirm,
          new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      dialogAddSS.pack();
      dialogAddSS.setLocationRelativeTo(dialogAddSS.getOwner());
    }

    // ======== dialogAddCS ========
    {
      dialogAddCS.setTitle("Adding Composite Strategy");
      Container dialogAddCSContentPane = dialogAddCS.getContentPane();
      dialogAddCSContentPane.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));

      // ---- labelCSST12 ----
      labelCSST12.setText("Strategy type 1");
      dialogAddCSContentPane.add(labelCSST12,
          new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      dialogAddCSContentPane.add(comboBoxCSST12,
          new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- labelCSBT2 ----
      labelCSBT2.setText("Book type");
      dialogAddCSContentPane.add(labelCSBT2,
          new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      dialogAddCSContentPane.add(comboBoxCSST22,
          new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      dialogAddCSContentPane.add(comboBoxCSBT2,
          new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      dialogAddCSContentPane.add(textFieldCSName2,
          new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(200, 1), null,
              null));

      // ---- labelCSST22 ----
      labelCSST22.setText("Strategy type 2");
      dialogAddCSContentPane.add(labelCSST22,
          new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- labelCSName2 ----
      labelCSName2.setText("Name");
      dialogAddCSContentPane.add(labelCSName2,
          new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

      // ---- buttonAddCSConfirm ----
      buttonAddCSConfirm.setText("Confirm");
      buttonAddCSConfirm.addActionListener(e -> buttonAddCSConfirmActionPerformed(e));
      dialogAddCSContentPane.add(buttonAddCSConfirm,
          new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
      dialogAddCS.pack();
      dialogAddCS.setLocationRelativeTo(dialogAddCS.getOwner());
    }
    // JFormDesigner - End of component initialization //GEN-END:initComponents
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY //GEN-BEGIN:variables
  private JTable table;
  private JPanel panelSS;
  private JTextField textFieldSSName;
  private JComboBox<String> comboBoxSSType;
  private JComboBox<String> comboBoxSSBT;
  private JSpinner spinnerSSDiscount;
  private JPanel panelCS;
  private JTextField textFieldCSName;
  private JComboBox<String> comboBoxCSST1;
  private JComboBox<String> comboBoxCSST2;
  private JComboBox<String> comboBoxCSBT;
  private JDialog dialogAddSS;
  private JTextField textFieldSSName2;
  private JComboBox<String> comboBoxSSType2;
  private JComboBox<String> comboBoxSSBT2;
  private JSpinner spinnerSSDiscount2;
  private JDialog dialogAddCS;
  private JComboBox<String> comboBoxCSST12;
  private JComboBox<String> comboBoxCSST22;
  private JComboBox<String> comboBoxCSBT2;
  private JTextField textFieldCSName2;
  // JFormDesigner - End of variables declaration //GEN-END:variables
}
