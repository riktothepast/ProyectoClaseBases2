/*
 * The MIT License
 *
 * Copyright 2014 Rik.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package Manual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable; 
import javax.swing.table.DefaultTableModel; 
import javax.swing.JScrollPane; 
import javax.swing.table.TableColumn;

/**
 *
 * @author Rik
 */
public class SpreadSheet extends JFrame implements ActionListener{
    DefaultTableModel dtm;
    JTable sheet;
    JButton addRow, deleteRow;
    JMenuBar menuBar;
    JMenu menu, menu2, file;
    JMenuItem menuItem, menuItem2, menuItem3, menuItem4,menuItem5, fileItem, fileItem2;
    JScrollPane scrollPane;
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
    public void loadSpreadSheet(ImportFromFile IPF){
        init_sheet();
        sheet.setModel(IPF);
                
         for(int column = 0; column < sheet.getColumnCount(); column++){ 
            dtm.addColumn(sheet.getColumnName(column)); 
          
            System.out.println(dtm.getColumnName(column));
        }
        Object[] data =  new Object[99];
        
        for(int row = 0; row < sheet.getRowCount(); row++) { 
            for(int column = 0; column < sheet.getColumnCount(); column++) { 
               data[column] = sheet.getValueAt(row, column);
               System.out.println(data[column]);
            } 
            dtm.addRow(data); 
        }

        
        sheet = new JTable(dtm);
        getContentPane().remove(scrollPane);
        scrollPane = new JScrollPane(sheet);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setTitle("SpreadSheet Data Creator");
        pack(); 
        setVisible(true);
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
        menuItem5 = new JMenuItem("Remove column");
        menu2.add(menuItem5);
        menuItem4 = new JMenuItem("Change Column name");
        menu2.add(menuItem4);
        setJMenuBar(menuBar);
        // listeners
        menuItem.addActionListener(this);
        menuItem2.addActionListener(this);
        menuItem3.addActionListener(this);
        menuItem5.addActionListener(this);
        menuItem4.addActionListener(this);
        fileItem.addActionListener(this);
        scrollPane = new JScrollPane(sheet);
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
        int index = 0;
        if( sheet.getSelectedRow()>0)
            index = sheet.getSelectedRow();
        else
            index = 0;
        dtm.insertRow(index,new Vector());  
    }
    
    void add_column(){
         String response = JOptionPane.showInputDialog(null,
            "Column ID?",
                "enter id",
             JOptionPane.QUESTION_MESSAGE);
        dtm.addColumn(response);
    }
    
    void remove_column(){
        TableColumn tcol = sheet.getColumnModel().getColumn(sheet.getSelectedColumn());
        sheet.removeColumn(tcol);
    }

    void change_column_name(){
        String response = JOptionPane.showInputDialog(null,
            "new column ID?",
                "enter id",
             JOptionPane.QUESTION_MESSAGE);
      changeName(sheet, sheet.getSelectedColumn(), response);
    }
    
    void changeName(JTable table, int col_index, String col_name){
        table.getColumnModel().getColumn(col_index).setHeaderValue(col_name);
    }
    
    void saveFile(String p, String n){
        try {
               new ExportToFile().exportToFile(p,n,sheet);
           } catch (FileNotFoundException ex) {
               Logger.getLogger(SpreadSheet.class.getName()).log(Level.SEVERE, null, ex);
           } catch (IOException ex) {
               Logger.getLogger(SpreadSheet.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource().equals(menuItem)){
           add_row();
       } 
       if(e.getSource().equals(menuItem2)){
           delete_selected_rows();
       } 
       if(e.getSource().equals(menuItem3)){
           add_column();
       } 
       if(e.getSource().equals(menuItem4)){
           change_column_name();
       } 
       if(e.getSource().equals(menuItem5)){
           remove_column();
       } 
       
       if(e.getSource().equals(fileItem)){
          String path;
          JFileChooser chooser = new JFileChooser();
          chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          int returnVal = chooser.showOpenDialog(this);
          if(returnVal == JFileChooser.APPROVE_OPTION) {
              path = chooser.getSelectedFile().getAbsolutePath();
              String response = JOptionPane.showInputDialog(null,
            "Enter file name",
                "filename.txt",
             JOptionPane.QUESTION_MESSAGE);
             saveFile(path, response);
          }
           
       }
    }
    
}
