import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Craig Stroberg <strobergcd@gmail.com>
 */
public class UniqueKeyMain {

    public static void main(String[] args) {
        List<UniqueKey> uniqueKeys = readUniqueKeyFromCSV("uniquekey.csv");
        List<String> dropUniqueKeyConstraints = createDropUnique(uniqueKeys);
        List<String> createAddUniqueKeyConstraints = createAddUnique(uniqueKeys);
        for (int i = 0; i < dropUniqueKeyConstraints.size(); i++) {
            System.out.println(dropUniqueKeyConstraints.get(i) + "\n" +
                    createAddUniqueKeyConstraints.get(i) + "\n");
        }
    }

    private static List<String> createAddUnique(List<UniqueKey> uniqueKeys) {
        List<String> addUniqueKeyList = new ArrayList<>();
        uniqueKeys.forEach(uniqueKey -> addUniqueKeyList.add("<addUniqueConstraint columnNames=\"" + uniqueKey.getColumnName() + "\"\n" +
                "tableName=\"" + uniqueKey.getTableName() + "\"\n" +
                "constraintName=\"uc_" + condenceName(uniqueKey.getTableName()) + "_" + condenceName(uniqueKey.getColumnName()) + "\"/>"));
        return addUniqueKeyList;
    }

    private static String condenceName(String tableName) {
        final String[] splitTableName = tableName.split("_");
        String condensedTableName = "";
        for (int i = 0; i < splitTableName.length; i++) {
            if (1 <= i) {
                splitTableName[i] = splitTableName[i].substring(0, 1).toUpperCase() + splitTableName[i].substring(1);
            }
            condensedTableName = condensedTableName + splitTableName[i];
        }
        return condensedTableName;
    }

    private static List<UniqueKey> readUniqueKeyFromCSV(String fileName) {
        Map<String, UniqueKey> uniqueKeys = new HashMap<>();
        Path pathToFile = Paths.get(fileName);
        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            // read the first line from the text file
            String line = br.readLine();
            // loop until all lines are read
            while (line != null) {
                // 6 use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");
                UniqueKey uniqueKey = createUniqueKey(attributes);
                // adding foreign key into ArrayList
                if (uniqueKeys.containsKey(uniqueKey.getConstraintName())) {
                    UniqueKey existing = uniqueKeys.get(uniqueKey.getConstraintName());
                    existing.setColumnName(existing.getColumnName() + "," + uniqueKey.getColumnName());
                } else {
                    uniqueKeys.put(uniqueKey.getConstraintName(), uniqueKey);
                }
                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return new ArrayList<>(uniqueKeys.values());
    }

    private static List<String> createDropUnique(List<UniqueKey> uniqueKeys) {
        List<String> dropUniqueKeyList = new ArrayList<>();
        uniqueKeys.forEach(uniqueKey -> dropUniqueKeyList.add("<dropUniqueConstraint uniqueColumns=\"" + uniqueKey.getColumnName() + "\"\n" +
                "tableName=\"" + uniqueKey.getTableName() + "\"\n" +
                "constraintName=\"" + uniqueKey.getConstraintName() + "\"/>"));
        return dropUniqueKeyList;
    }

    private static UniqueKey createUniqueKey(String[] metadata) {
        // create and return unique key of this metadata
        String constraintName = metadata[0];
        String columnName = metadata[1];
        String tableName = metadata[2];
        return new UniqueKey(constraintName, columnName, tableName);
    }
}
