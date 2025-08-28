package Task1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Installation {
    public static void main(String[] args) {
        StringBuilder log = new StringBuilder();

        // Путь установки
        String basePath = "C://Games";

        // Список директорий
        String[] dirs = {
                basePath + "/src",
                basePath + "/res",
                basePath + "/savegames",
                basePath + "/temp",
                basePath + "/src/main",
                basePath + "/src/test",
                basePath + "/res/drawables",
                basePath + "/res/vectors",
                basePath + "/res/icons"
        };

        // Создание директорий
        for (String dir : dirs) {
            File directory = new File(dir);
            if (directory.mkdir()) {
                log.append("Каталог " + dir + " создан\n");
            } else {
                log.append("Не удалось создать каталог " + dir + "\n");
            }
        }

        // Создание файлов
        String[] files = {
                basePath + "/src/main/Main.java",
                basePath + "/src/main/Utils.java",
                basePath + "/temp/temp.txt"
        };

        for (String filePath : files) {
            File file = new File(filePath);
            try {
                if (file.createNewFile()) {
                    log.append("Файл " + filePath + " создан\n");
                } else {
                    log.append("Не удалось создать файл " + filePath + "\n");
                }
            } catch (IOException e) {
                log.append("Ошибка при создании файла " + filePath + ": " + e.getMessage() + "\n");
            }
        }

        // Запись лога в temp.txt
        try (FileWriter writer = new FileWriter(basePath + "/temp/temp.txt", false)) {
            writer.write(log.toString());
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл temp.txt: " + e.getMessage());
        }
    }
}