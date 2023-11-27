
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
public
class TableColorCellRenderer
        implements TableCellRenderer {
    private static final TableCellRenderer renderer=new DefaultTableCellRenderer();
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component=renderer.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
        if(column==2){
            Object result=table.getModel().getValueAt(row,2);
            double number=Double.parseDouble(result.toString());
            Color color=null;
            if(number>20000){
                color=Color.RED;
            }
            component.setForeground(color);
        } else {
            component.setForeground(Color.BLACK);
        }
        return component;
    }
}

