/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Manual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
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
    //new blank spreadsheet
    public void newSpreadSheet(){
        dtm = new DefaultTableModel();
        sheet = new JTable(dtm);
        sheet.setPreferredScrollableViewportSize(new Dimension(600, 400));
        
        JScrollPane scrollPane = new JScrollPane(sheet);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        //fill with blank row and columns
        for(int column = 0; column < 5; column++){ 
            dtm.addColumn("Col " + column); 
        }
         Object[] data =  new Object[10];
        for(int row = 0; row < 10; row++) { 
            for(int column = 0; column < 5; column++) { 
               data[column] = column+" , "+row; 
            } 
            dtm.addRow(data); 
        }
        pack(); 
        setVisible(true); 
    }
    
    //load from file?
    public void loadSpreadSheet(String [][] data){
        
    }
    
    
}
