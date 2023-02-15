package com.anand.withu.Models;

public class Users {

    String prfpic ,fnm , lnm , email , password;

    public Users(String pfpic, String fnm ,String lnm , String email , String password)
    {
        this.fnm = fnm;
        this.lnm = lnm;
        this.email = email;
        this.password = password;
    }

    public Users()
    {

    }

    //signup constructor....

    public  Users(String fnm , String lnm , String email , String password )
    {
        this.fnm = fnm;
        this.lnm = lnm;
        this.email = email;
        this.password = password;
    }

    public String getPrfpic() {
        return prfpic;
    }

    public void setPrfpic(String prfpic) {
        this.prfpic = prfpic;
    }

    public String getFnm() {
        return fnm;
    }

    public void setFnm(String fnm) {
        this.fnm = fnm;
    }

    public String getLnm() {
        return lnm;
    }

    public void setLnm(String lnm) {
        this.lnm = lnm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
