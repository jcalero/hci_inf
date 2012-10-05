package hci;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileBrowser {
  public static File open() {
    JFileChooser fileopen = new JFileChooser();
    FileFilter jpgFilter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
    fileopen.addChoosableFileFilter(jpgFilter);
    fileopen.setAcceptAllFileFilterUsed(false);
    fileopen.setFileFilter(jpgFilter);

    int ret = fileopen.showDialog(null, "Choose an image");

    if (ret == JFileChooser.APPROVE_OPTION) {
      File file = fileopen.getSelectedFile();
      System.out.println(file);
      return file;
    }
    return null;
  }
}
