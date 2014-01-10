/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalysisData {
    
    private id3 _id3 = new id3();
    public String HTMLTree;
    private String NameFile;
    public String LogicalCodeDecision;
    
    public boolean LoadFile(String pFile, int pAtributeClass) {
        boolean _successfull = true;
        String htmlTree;
        NameFile = pFile;
        try {
            _id3.readData(pFile);
            _id3.atributoClase = pAtributeClass;
            _id3.createDecisionTree();
            getLogicalCodeDecision(_id3.root, "");
            LogicalCodeDecision = LogicalCodeDecision.substring(4);
            htmlTree = printHTMLTree(_id3.root, "", "                ");
            writeHTMLTree(htmlTree);
        } catch (Exception ex) { _successfull = false; }
        return _successfull;
    }
    
    private String printHTMLTree(TreeNode node, String domain, String tab) {
        String result = "";
        System.out.print(tab + "<li>\n");
        result += tab + "<li>\n";

        System.out.print(tab + "   <div>");
        result += tab + "   <div>";

        if (domain != null && !domain.isEmpty()) {
            System.out.print("<p class=\"etiqueta\">" + domain + "</p>");
            result += "<p class=\"etiqueta\">" + domain + "</p>";
        }

        int outputattr = _id3.atributoClase;
        if (node.children == null) {
            int[] values = _id3.getAllValues(node.data, outputattr);
            String valores = "";
            for (int i = 0; i < values.length; i++) {
                valores = valores + _id3.domains[outputattr].elementAt(values[i]);
                if (i != values.length - 1) {
                    valores = valores + ", ";
                }
            }
            System.out.print("<span class=\"leaf-node\">" + valores + "</span></div>\n");
            result += "<span class=\"leaf-node\">" + valores + "</span></div>\n";
        } else {
            System.out.print("<span class=\"square-node\">" + _id3.attributeNames[node.decompositionAttribute] + "</span></div>\n");
            result += "<span class=\"square-node\">" + _id3.attributeNames[node.decompositionAttribute] + "<br>[" + String.valueOf(node.entropy).substring(0,5) + "]</span></div>\n";

            System.out.print(tab + "   <ul>\n");
            result += tab + "   <ul>\n";

            int numvalues = node.children.length;
            for (int i = 0; i < numvalues; i++) {
                result += printHTMLTree(node.children[i], _id3.domains[node.decompositionAttribute].elementAt(i).toString(), tab + "      ");
            }
            System.out.print(tab + "   </ul>\n");
            result += tab + "   </ul>\n";
        }
        System.out.print(tab + "</li>\n");
        result += tab + "</li>\n";
        return result;
    }

    private void writeHTMLTree(String htmlTree) {
        String htmlTemplate = "";
        URL url = getClass().getResource("TreeTemplate.html");
        File file = new File("Result.html");

        try (BufferedReader br = new BufferedReader(new FileReader(url.getPath().replace("%20", " ")))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                htmlTemplate += sCurrentLine;
                //System.out.println(sCurrentLine);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        String html = htmlTemplate.replace("[CONTENIDO_ARBOL]", htmlTree);
        html = html.replace("[TITULO]", NameFile);
        //html = html.replace("[DESCRIPCION]", "Descripcion");
        try {
            //File file = new File("test.html");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(html);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        HTMLTree = html;
    }
    
    private void getLogicalCodeDecision(TreeNode node, String tab) {
        int outputattr = _id3.atributoClase;

        if (node.children == null) {
            int[] values = _id3.getAllValues(node.data, outputattr);
            if (values.length == 1) {
                LogicalCodeDecision += tab + "\t" + _id3.attributeNames[outputattr] + " = \"" + _id3.domains[outputattr].elementAt(values[0]) + "\";\n" ;
                return;
            }
            LogicalCodeDecision += tab + "\t" + _id3.attributeNames[outputattr] + " = {\n";
            for (int i = 0; i < values.length; i++) {
                LogicalCodeDecision += "\"" + _id3.domains[outputattr].elementAt(values[i]) + "\" \n";
                if (i != values.length - 1) {
                    LogicalCodeDecision += " , \n";
                }
            }
            LogicalCodeDecision += " };\n";
            return;
        }

        int numvalues = node.children.length;
        for (int i = 0; i < numvalues; i++) {
            LogicalCodeDecision += tab + "if( " + _id3.attributeNames[node.decompositionAttribute] + " == \""
                    + _id3.domains[node.decompositionAttribute].elementAt(i) + "\") {\n";
            getLogicalCodeDecision(node.children[i], tab + "\t");
            if (i != numvalues - 1) {
                LogicalCodeDecision += tab + "} else \n";
            } else {
                LogicalCodeDecision += tab + "}\n";
            }
        }
        return;
    }
}
