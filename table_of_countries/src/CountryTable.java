import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public
    class CountryTable {
    JTable jt;
    String x;
    Object[] columnNames;
    List<Object> columnNamesHelp=new ArrayList<>();
    Object[][] data;
    List<Object> dataHelp=new ArrayList<>();
    TableColorCellRenderer renderer=new TableColorCellRenderer();
    public CountryTable(String x) {
        this.x=x;
    }
    public void readFromFile() throws IOException {
        File file=new File(x);
        FileReader fr=new FileReader(file);
        StringBuilder sb=new StringBuilder();
        int x;
        int line=0;
        while((x=fr.read())!=-1) {
            if(x!=9 && x!=10){
                    sb.append((char)x);
            } else if(line==0){
                columnNamesHelp.add(sb.toString());
                sb.delete(0,sb.length());
                if(x==10) line++;
            } else if(line>0){
                if ((dataHelp.size() + 2) % (columnNamesHelp.size()) == 0) {
                    Integer ludnosc = Integer.parseInt(sb.toString());
                    dataHelp.add(ludnosc);
                } else if ((dataHelp.size() + 1) % columnNamesHelp.size() == 0) {
                    File file2 = new File("data/flags/" + sb + ".png");
                    dataHelp.add(new ImageIcon(file2.toString()));
                } else
                    dataHelp.add(sb.toString());
                    sb.delete(0, sb.length());
            }

        }
    }
    public void fromVectorToArray(){
        columnNames=new String[columnNamesHelp.size()];
        for (int i = 0; i < columnNames.length; i++) {
            columnNames[i]=columnNamesHelp.get(i);
        }
        int licznik=0;
        data=new Object[dataHelp.size()/columnNames.length][columnNames.length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j]=dataHelp.get(licznik);
                licznik++;
            }
        }
    }
    public JTable create() throws IOException {
        readFromFile();
        fromVectorToArray();
        jt=new JTable(data,columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int column) {
                switch(column){
                    case 2: return Integer.class;
                    case 3: return ImageIcon.class;
                    default: return String.class;
                }
            }
        };
        jt.setDefaultRenderer(Integer.class,renderer);
        return jt;
    }
}


