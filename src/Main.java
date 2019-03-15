import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Craig Stroberg <strobergcd@gmail.com>
 */
public class Main {

    public static void main(String[] args) {
        List<ForeignKey> foreignKeyList = readForeignKeyFromCSV("foriegnkey.csv");
        List<String> dropForeignKeyConstraints = createDrop(foreignKeyList);
        List<String> createAddForeignKeyConstraints = createAddForeignKey(foreignKeyList);
        for (int i = 0; i < dropForeignKeyConstraints.size(); i++) {
            System.out.println(dropForeignKeyConstraints.get(i) + "\n" +
                    createAddForeignKeyConstraints.get(i) + "\n");
        }
    }

    private static List<String> createAddForeignKey(List<ForeignKey> foreignKeyList) {
        List<String> dropForeignKeyList = new ArrayList<>();
        foreignKeyList.forEach(foreignKey -> dropForeignKeyList.add(
                "<addForeignKeyConstraint baseColumnNames=\"" + foreignKey.getColumnName() + "\"\n"
                        + "baseTableName=\"" + foreignKey.getTableName() + "\"\n"
                        + "constraintName=\"fk_" + condenceTableName(foreignKey.getTableName()) + "_references_" + condenceTableName(foreignKey.getReferenceTableName()) + "\"\n"
                        + "onDelete=\"" + foreignKey.getDeleteRule() + "\"\n"
                        + "onUpdate=\"" + foreignKey.getUpdateRule() + "\"\n"
                        + "referencedColumnNames=\"" + foreignKey.getReferenceColumnName() + "\"\n"
                        + "referencedTableName=\"" + foreignKey.getReferenceTableName() + "\"/>"));
        return dropForeignKeyList;
    }

    private static String condenceTableName(String tableName) {
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

    private static List<ForeignKey> readForeignKeyFromCSV(String fileName) {
        List<ForeignKey> foreignKeys = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);
        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            // read the first line from the text file
            String line = br.readLine();
            // loop until all lines are read
            while (line != null) {
                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");
                ForeignKey foreignKey = createForeignKey(attributes);
                // adding foreign key into ArrayList
                foreignKeys.add(foreignKey);
                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return foreignKeys;
    }

    private static List<String> createDrop(List<ForeignKey> foreignKeyList) {
        List<String> dropForeignKeyList = new ArrayList<>();
        foreignKeyList.forEach(foreignKey -> dropForeignKeyList.add(
                "<dropForeignKeyConstraint baseTableName=\"" + foreignKey.getTableName()
                        + "\"\n constraintName=\"" + foreignKey.getConstraintName() + "\"/>"));
        return dropForeignKeyList;
    }

    private static ForeignKey createForeignKey(String[] metadata) {
        // create and return foreign key of this metadata
        String constraintName = metadata[0];
        String columnName = metadata[1];
        String tableName = metadata[2];
        String updateRule = metadata[3];
        String deleteRule = metadata[4];
        String referenceColumnName = metadata[5];
        String referenceTableName = metadata[6];
        return new ForeignKey(constraintName, columnName, tableName, updateRule, deleteRule, referenceColumnName, referenceTableName);
    }
}
