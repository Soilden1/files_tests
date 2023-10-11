package dimacm14.guru.qa.files;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ArchiveTest {

    @Test
    public void readFilesFromArchiveTest() throws Exception {
        try (ZipFile zf = new ZipFile(new File("src/test/resources/archive.zip"));
             ZipInputStream zis = new ZipInputStream(Objects.requireNonNull(ArchiveTest.class.getResourceAsStream("/archive.zip")))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String fileExtension = getFileExtension(entry.getName());
                try (InputStream is = zf.getInputStream(entry)) {
                    switch (Objects.requireNonNull(fileExtension)) {
                        case ".pdf" -> readPdf(is);
                        case ".csv" -> readCSV(is);
                        case ".xlsx" -> readXLSX(is);
                    }
                }
            }
        }
    }

    private static String getFileExtension(String fileName) {
        int index = fileName.indexOf('.');
        return index == -1 ? null : fileName.substring(index);
    }

    private static void readPdf(InputStream is) throws Exception {
        PDF pdf = new PDF(is);

        Assertions.assertEquals("JUnit 5 User Guide", pdf.title);
        Assertions.assertEquals("Asciidoctor PDF 1.5.0.alpha.16, based on Prawn 2.2.2", pdf.creator);
    }

    private static void readCSV(InputStream is) throws Exception {
        CSVReader reader = new CSVReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        List<String[]> content = reader.readAll();

        Assertions.assertEquals(3, content.size());

        String[] firstLine = content.get(0);
        String[] secondLine = content.get(1);
        String[] thirdLine = content.get(2);

        Assertions.assertArrayEquals(new String[]{"Овощ", "Фиолетовый"}, firstLine);
        Assertions.assertArrayEquals(new String[]{"Фрукт", "Зеленый"}, secondLine);
        Assertions.assertArrayEquals(new String[]{"Ягода", "Красный"}, thirdLine);
    }

    private static void readXLSX(InputStream is) throws Exception {
        XLS xlsx = new XLS(is);

        Assertions.assertEquals("Техническая поддержка",
                xlsx.excel.getSheetAt(0)
                        .getRow(5)
                        .getCell(2)
                        .getStringCellValue());
    }
}
