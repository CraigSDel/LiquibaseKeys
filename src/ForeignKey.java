import java.util.StringJoiner;

/**
 * @author Craig Stroberg <strobergcd@gmail.com>
 */
public class ForeignKey {
    private String constraintName;
    private String columnName;
    private String tableName;
    private String updateRule;
    private String deleteRule;
    private String referenceColumnName;
    private String referenceTableName;

    public ForeignKey(String constraintName, String columnName, String tableName, String updateRule, String deleteRule, String referenceColumnName, String referenceTableName) {
        this.constraintName = constraintName;
        this.columnName = columnName;
        this.tableName = tableName;
        this.updateRule = updateRule;
        this.deleteRule = deleteRule;
        this.referenceColumnName = referenceColumnName;
        this.referenceTableName = referenceTableName;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public ForeignKey setConstraintName(String constraintName) {
        this.constraintName = constraintName;
        return this;
    }

    public String getColumnName() {
        return columnName;
    }

    public ForeignKey setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public ForeignKey setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getUpdateRule() {
        return updateRule;
    }

    public ForeignKey setUpdateRule(String updateRule) {
        this.updateRule = updateRule;
        return this;
    }

    public String getDeleteRule() {
        return deleteRule;
    }

    public ForeignKey setDeleteRule(String deleteRule) {
        this.deleteRule = deleteRule;
        return this;
    }

    public String getReferenceColumnName() {
        return referenceColumnName;
    }

    public ForeignKey setReferenceColumnName(String referenceColumnName) {
        this.referenceColumnName = referenceColumnName;
        return this;
    }

    public String getReferenceTableName() {
        return referenceTableName;
    }

    public ForeignKey setReferenceTableName(String referenceTableName) {
        this.referenceTableName = referenceTableName;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ForeignKey.class.getSimpleName() + "[", "]")
                .add("constraintName='" + constraintName + "'")
                .add("columnName='" + columnName + "'")
                .add("tableName='" + tableName + "'")
                .add("updateRule='" + updateRule + "'")
                .add("deleteRule='" + deleteRule + "'")
                .add("referenceColumnName='" + referenceColumnName + "'")
                .add("referenceTableName='" + referenceTableName + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ForeignKey)) return false;

        ForeignKey that = (ForeignKey) o;

        if (constraintName != null ? !constraintName.equals(that.constraintName) : that.constraintName != null)
            return false;
        if (columnName != null ? !columnName.equals(that.columnName) : that.columnName != null) return false;
        if (tableName != null ? !tableName.equals(that.tableName) : that.tableName != null) return false;
        if (updateRule != null ? !updateRule.equals(that.updateRule) : that.updateRule != null) return false;
        if (deleteRule != null ? !deleteRule.equals(that.deleteRule) : that.deleteRule != null) return false;
        if (referenceColumnName != null ? !referenceColumnName.equals(that.referenceColumnName) : that.referenceColumnName != null)
            return false;
        return referenceTableName != null ? referenceTableName.equals(that.referenceTableName) : that.referenceTableName == null;
    }

    @Override
    public int hashCode() {
        int result = constraintName != null ? constraintName.hashCode() : 0;
        result = 31 * result + (columnName != null ? columnName.hashCode() : 0);
        result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
        result = 31 * result + (updateRule != null ? updateRule.hashCode() : 0);
        result = 31 * result + (deleteRule != null ? deleteRule.hashCode() : 0);
        result = 31 * result + (referenceColumnName != null ? referenceColumnName.hashCode() : 0);
        result = 31 * result + (referenceTableName != null ? referenceTableName.hashCode() : 0);
        return result;
    }
}
