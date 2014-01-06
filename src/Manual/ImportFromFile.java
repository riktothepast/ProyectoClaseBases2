/*
Copyright [2014] [Rik]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
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
