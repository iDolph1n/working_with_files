package Task2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Saving {
    public static void main(String[] args) {
        // Создаём сохранения
        GameProgress save1 = new GameProgress(100, 2, 5, 123.5);
        GameProgress save2 = new GameProgress(80, 5, 10, 250.4);
        GameProgress save3 = new GameProgress(50, 7, 15, 500.0);

        String saveDir = "C:/Games/savegames/";

        File dir = new File(saveDir);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                System.out.println("Не удалось создать папку: " + saveDir);
                return;
            }
        }

        // Сохраняем в файлы
        saveGame(saveDir + "save1.dat", save1);
        saveGame(saveDir + "save2.dat", save2);
        saveGame(saveDir + "save3.dat", save3);

        // Формируем список файлов для архивации
        List<String> saveFiles = new ArrayList<>();
        saveFiles.add(saveDir + "save1.dat");
        saveFiles.add(saveDir + "save2.dat");
        saveFiles.add(saveDir + "save3.dat");

        // Архивируем
        zipFiles(saveDir + "saves.zip", saveFiles);

        // Удаляем оригиналы (оставляем только архив)
        for (String file : saveFiles) {
            File f = new File(file);
            if (f.delete()) {
                System.out.println("Файл " + file + " удалён");
            } else {
                System.out.println("Не удалось удалить файл " + file);
            }
        }
    }

    // Метод сохранения
    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            System.out.println("Сохранение успешно: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод архивации
    public static void zipFiles(String zipFilePath, List<String> filePaths) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (String path : filePaths) {
                try (FileInputStream fis = new FileInputStream(path)) {
                    ZipEntry entry = new ZipEntry(new File(path).getName());
                    zout.putNextEntry(entry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zout.write(buffer, 0, length);
                    }

                    zout.closeEntry();
                    System.out.println("Файл добавлен в архив: " + path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Архив создан: " + zipFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}