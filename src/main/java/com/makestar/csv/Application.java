package com.makestar.csv;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Application {
  public static void main(String[] args) throws IOException, InterruptedException {
    try {
      if (args != null && args.length != 0) {
        //JOptionPane.showMessageDialog(null, "args : " + String.join(" ", args));
      } else {
        JOptionPane.showMessageDialog(null, "please drag csv file to this program");
        return;
      }

      File from = new File(args[0]);
      File to = null;
      char bomMarker = '\ufeff';

      try (FileReader fr = new FileReader(from);
           BufferedReader br = new BufferedReader(fr)) {
        br.mark(4);
        if (br.read() != bomMarker) {
          br.reset();
          to = new File(from.getAbsoluteFile() + ".temp");
          try (FileWriter fw = new FileWriter(to);
               BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(bomMarker);
            while (true) {
              int read = br.read();
              if (read == -1) break;
              bw.write(read);
            }
          }
        }
      }
      if (to != null) {
        from.delete();
        to.renameTo(from);
        JOptionPane.showMessageDialog(null, "BOM marker added");
      } else {
        JOptionPane.showMessageDialog(null, "Nothing to do");
      }
      Desktop.getDesktop().open(from);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.getMessage());
      e.printStackTrace();
    }

  }
}
