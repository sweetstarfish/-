package code6.entity;

public class User {
    Integer id;
    String passwd;
    String uname;
    Integer ismanger;

    public User(Integer id, String passwd, String uname, Integer ismanger) {
        this.id = id;
        this.passwd = passwd;
        this.uname = uname;
        this.ismanger = ismanger;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    public Integer getIsmanger() {
        return ismanger;
    }

    public void setIsmanger(Integer ismanger) {
        this.ismanger = ismanger;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", passwd='" + passwd + '\'' +
                ", uname='" + uname + '\'' +
                ", ismanger=" + ismanger +
                '}';
    }
}
