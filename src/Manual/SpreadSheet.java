/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Manual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTable; 
import javax.swing.table.DefaultTableModel; 
import javax.swing.JScrollPane; 

/**
 *
 * @author Rik
 */
public class SpreadSheet extends JFrame{
    
    DefaultTableModel dtm;
    JTable sheet;
    JButton addRow, deleteRow;
    JMenuBar menuBar;
    JMenu menu, menu2, file;
    JMenuItem menuItem, menuItem2, menuItem3, menuItem4, fileItem, fileItem2;
    
    //new blank spreadsheet
    public void newSpreadSheet(){
        init_sheet();
        //fill with blank row and columns
        for(int column = 0; column < 5; column++){ 
            dtm.addColumn("Col " + column); 
        }
        Object[] data =  new Object[99];
        for(int row = 0; row < 10; row++) { 
            for(int column = 0; column < 5; column++) { 
               data[column] = column+" , "+row; 
            } 
            dtm.addRow(data); 
        }
        setTitle("SpreadSheet Data Creator");
        pack(); 
        setVisible(true); 
    }
    
    //load from file?
    public void loadSpreadSheet(String[] headers, String [][] data){
        
    }
    
    void init_sheet(){
        
        dtm = new DefaultTableModel();
        sheet = new JTable(dtm);
        sheet.setPreferredScrollableViewportSize(new Dimension(600, 400));
        
        menuBar = new JMenuBar();
        //file operations?
        file = new JMenu("File");
        menuBar.add(file);
        fileItem = new JMenuItem("Save as...");
        file.add(fileItem);
        //row operation menu
        menu = new JMenu("Row Menu");
        menuBar.add(menu);
        menuItem = new JMenuItem("Add row");
        menu.add(menuItem);
        menuItem2 = new JMenuItem("Delete row(s)");
        menu.add(menuItem2);
        //column operation menu
        menu2 = new JMenu("Column Menu");
        menuBar.add(menu2);
        menuItem3 = new JMenuItem("Add column");
        menu2.add(menuItem3);
        menuItem4 = new JMenuItem("Delete coulmn(s)");
        menu2.add(menuItem4);
        
        setJMenuBar(menuBar);
       
        
        JScrollPane scrollPane = new JScrollPane(sheet);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
    
    void delete_selected_rows(){
        int[] selectedRows = sheet.getSelectedRows();
        if (selectedRows.length > 0) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                dtm.removeRow(selectedRows[i]);
            }
        }
    }
    
    void add_row(){
        
    }
}
