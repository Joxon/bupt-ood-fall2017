package homework7;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

class ShoppingCarUI extends JDialog {

  // public static void main(String[] args) {
  // new ShoppingCarUI();
  // }

  ShoppingCarUI() {
    initComponents();

    Controller controller = Controller.getInstance();

    // start loading shopping car items
    ArrayList<SaleLineItem> itemArrayList = controller.getSale().getItemList();
    DefaultTableModel model = new DefaultTableModel(
        new String[] { "Title", "Original Price", "Amount", "Subtotal(discounted)" }, 0) {
      // editing is not allowed
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    if (itemArrayList.isEmpty()) {
      model.addRow(new Object[] { "Empty", "NaN", "NaN", "NaN" });
    } else {
      for (SaleLineItem item : itemArrayList) {
        model.addRow(new Object[] { item.getProduct().getTitle(), item.getProduct().getPrice(), item.getAmount(),
            String.format("%.2f", item.getStrategy().getSubTotal(item)) });
      }
    }

    tableShoppingCar.setModel(model);
    tableShoppingCar.setRowHeight(50);
    tableShoppingCar.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public int getHorizontalAlignment() {
        return SwingConstants.CENTER;
      }
    });
    // end loading shopping car items

    double total = controller.getSale().getTotal();
    labelTotal.setText("Total = " + String.format("%.2f", total));

    setModal(true);
    setVisible(true);
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY
    // //GEN-BEGIN:initComponents
    scrollPaneForTable = new JScrollPane();
    tableShoppingCar = new JTable();
    labelTotal = new JLabel();

    // ======== this ========
    setMinimumSize(new Dimension(18, 200));
    setResizable(false);
    Container contentPane = getContentPane();
    contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));

    // ======== scrollPaneForTable ========
    {

      // ---- tableShoppingCar ----
      tableShoppingCar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      tableShoppingCar.setRowSorter(null);
      tableShoppingCar.setRowSelectionAllowed(false);
      scrollPaneForTable.setViewportView(tableShoppingCar);
    }
    contentPane.add(scrollPaneForTable,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));

    // ---- labelTotal ----
    labelTotal.setText("Total  = ");
    labelTotal.setMinimumSize(new Dimension(60, 50));
    contentPane.add(labelTotal,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
    pack();
    setLocationRelativeTo(getOwner());
    // JFormDesigner - End of component initialization //GEN-END:initComponents
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY //GEN-BEGIN:variables
  private JScrollPane scrollPaneForTable;
  private JTable tableShoppingCar;
  private JLabel labelTotal;
  // JFormDesigner - End of variables declaration //GEN-END:variables
}
