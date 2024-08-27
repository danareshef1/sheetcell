package files;

import sheet.SheetImpl;

import java.io.*;

public class SaveLoadManager {

    public static void saveSystemState(SheetImpl sheet, String filePath) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(filePath + ".ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(sheet);
        }
    }

    public static SheetImpl loadSystemState(String filePath) throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(filePath + ".ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (SheetImpl) in.readObject();
        }
    }
}
