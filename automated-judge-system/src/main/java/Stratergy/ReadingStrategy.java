package Stratergy;

import com.voidlings.FileHandling.Folder;

public interface ReadingStrategy {
    Boolean checkFormat(String path);
  //  Boolean containsFileType(String filePath);
    Folder extractFiles(String filePath, String destinationPath);
  //  Folder getFolder();
}
