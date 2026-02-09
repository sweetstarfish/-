package code6.entity;

public class Book {
    String name;
    Integer bid;
    Integer allnum;
    Integer borrownum;
    String type;
    public Book() {
    }
    public Book(String name, Integer bid, Integer allnum, Integer borrownum, String type) {
        //有参构造方法，它允许你在创建 Book 对象时指定属性值
        //this 关键字用于将传入的参数赋值给当前对象的实例变量
        this.name = name;
        this.bid = bid;
        this.allnum = allnum;
        this.borrownum = borrownum;
        this.type = type;
    }
    //Getter方法：用于获取对象的属性值
    //Setter方法：用于设置对象的属性值
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Integer getAllnum() {
        return allnum;
    }

    public void setAllnum(Integer allnum) {
        this.allnum = allnum;
    }

    public Integer getBorrownum() {
        return borrownum;
    }

    public void setBorrownum(Integer borrownum) {
        this.borrownum = borrownum;
    }

    @Override
    public String toString() {
        //返回一个格式化的字符串
        return "Book{" +
                "bname='" + name + '\'' +
                ", bid=" + bid +
                ", allnum=" + allnum +
                ", borrownum=" + borrownum +
                '}';
    }


}
