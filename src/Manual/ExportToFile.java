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

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rik
 */
public class ExportToFile {
     Writer writer = null;
     DefaultTableModel dtm;
     int RowNum ;
     int ColNum ;
    public void exportToFile(String path, String FileName, JTable sheet) throws UnsupportedEncodingException, FileNotFoundException, IOException{
        dtm = (DefaultTableModel) sheet.getModel();
        RowNum = dtm.getRowCount();
        ColNum = dtm.getColumnCount();
        
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+"/"+FileName), "utf-8"));
            System.out.println(path+"/"+FileName);

            //write the header information
            StringBuffer Header = new StringBuffer();
            for (int j = 0; j < ColNum; j++) {
                Header.append(dtm.getColumnName(j));
                if (j!=ColNum) Header.append("\t");
            }
            writer.write(Header.toString() + "\r\n");

           //write row information
            for (int i = 0 ; i < RowNum ; i++){
                 StringBuffer data = new StringBuffer();
                for (int j = 0 ; j < ColNum ; j++){
                    data.append(dtm.getValueAt(i,j));
                    if (j!=ColNum) data.append("\t");
                }
                writer.write(data.toString() + "\r\n");
            }
        } finally {
              writer.close();
        }

    }
    
    
}
