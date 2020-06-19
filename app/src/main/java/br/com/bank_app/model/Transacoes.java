package br.com.bank_app.model;

public class Transacoes {

    private String title;
    private String desc;
    private String date;
    private String value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transacoes{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", date='" + date + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
