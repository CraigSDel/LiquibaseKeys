/**
 * @author Craig Stroberg <strobergcd@gmail.com>
 */
public class UniqueKey {

    private String constraintName;
    private String columnName;
    private String tableName;

    public UniqueKey(String constraintName, String columnName, String tableName) {
        this.constraintName = constraintName;
        this.columnName = columnName;
        this.tableName = tableName;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public UniqueKey setConstraintName(String constraintName) {
        this.constraintName = constraintName;
        return this;
    }

    public String getColumnName() {
        return columnName;
    }

    public UniqueKey setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public UniqueKey setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UniqueKey)) return false;

        UniqueKey uniqueKey = (UniqueKey) o;

        if (constraintName != null ? !constraintName.equals(uniqueKey.constraintName) : uniqueKey.constraintName != null)
            return false;
        if (columnName != null ? !columnName.equals(uniqueKey.columnName) : uniqueKey.columnName != null) return false;
        return tableName != null ? tableName.equals(uniqueKey.tableName) : uniqueKey.tableName == null;
    }

    @Override
    public int hashCode() {
        int result = constraintName != null ? constraintName.hashCode() : 0;
        result = 31 * result + (columnName != null ? columnName.hashCode() : 0);
        result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
        return result;
    }
}
