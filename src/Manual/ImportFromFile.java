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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Rik
 */
public class ImportFromFile extends AbstractTableModel  {
     Vector dataFile = new Vector();
     Vector ColName = new Vector();
     String fileForData;
    
  public void importFromFile(String f){
        fileForData = f;
        analData();
    }
    
    public void analData() {
        String aLine ;

        try {
            FileInputStream fin =  new FileInputStream(fileForData);
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            // extract column names
            StringTokenizer st1 =
                    new StringTokenizer(br.readLine(), "\\t");
            while(st1.hasMoreTokens())
                ColName.addElement(st1.nextToken());
            // extract dataFile
            while ((aLine = br.readLine()) != null) {
                StringTokenizer st2 =
                        new StringTokenizer(aLine, "\\t");
                while(st2.hasMoreTokens())
                    dataFile.addElement(st2.nextToken());
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

     @Override
    public int getRowCount() {
        return dataFile.size() / getColumnCount();
    }
    

     @Override
    public int getColumnCount(){
        return ColName.size();
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        String colName = "";
        
        if (columnIndex <= getColumnCount())
            colName = (String)ColName.elementAt(columnIndex);
        
        return colName;
    }
    
    @Override
    public Class getColumnClass(int columnIndex){
        return String.class;
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return (String)dataFile.elementAt( (rowIndex * getColumnCount()) + columnIndex);
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        return;
    }
    
    public Vector getData(){
        return dataFile;
     }
    
    public Vector getHeaders(){
        return ColName;
    }
}
