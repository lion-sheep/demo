package test.demo.query;

public enum BulkOptions {
    INDEX("index"),UPDATE("update"),DELETE("delete");
    private String option;

    BulkOptions(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
