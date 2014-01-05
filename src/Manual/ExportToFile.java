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
                if (j!=ColNum) Header.append("\\t");
            }
            writer.write(Header.toString() + "\r\n");

           //write row information
            for (int i = 0 ; i < RowNum ; i++){
                 StringBuffer data = new StringBuffer();
                for (int j = 0 ; j < ColNum ; j++){
                    data.append(dtm.getValueAt(i,j));
                    if (j!=ColNum) data.append("\\t");
                }
                writer.write(data.toString() + "\r\n");
            }
        } finally {
              writer.close();
        }

    }
    
    
}
