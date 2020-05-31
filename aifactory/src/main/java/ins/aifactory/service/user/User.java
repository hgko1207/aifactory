package ins.aifactory.service.user;

import java.util.Date;

import ins.core.entity.EntityUser;

public class User extends EntityUser {
    private String userEmail;
    private String userPwd;
    private String mbtlnum;
    private String sexdstn;
    private Date brthdy;
    private String useStplatAgreYn;
    private String indvdlinfoProcessPolcyAgreYn;
    private String agreeTerm;
    
    public User(){
    }
    
    public User(String userId){
        this.setUserId(userId);
    }

    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserPwd() {
        return userPwd;
    }
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    public String getMbtlnum() {
        return mbtlnum;
    }
    public void setMbtlnum(String mbtlnum) {
        this.mbtlnum = mbtlnum;
    }
    public String getSexdstn() {
        return sexdstn;
    }
    public void setSexdstn(String sexdstn) {
        this.sexdstn = sexdstn;
    }
    public Date getBrthdy() {
        return brthdy;
    }
    public void setBrthdy(Date brthdy) {
        this.brthdy = brthdy;
    }
    public String getUseStplatAgreYn() {
        return useStplatAgreYn;
    }
    public void setUseStplatAgreYn(String useStplatAgreYn) {
        this.useStplatAgreYn = useStplatAgreYn;
    }
    public String getIndvdlinfoProcessPolcyAgreYn() {
        return indvdlinfoProcessPolcyAgreYn;
    }
    public void setIndvdlinfoProcessPolcyAgreYn(String indvdlinfoProcessPolcyAgreYn) {
        this.indvdlinfoProcessPolcyAgreYn = indvdlinfoProcessPolcyAgreYn;
    }
    public String getAgreeTerm() {
        return agreeTerm;
    }
    public void setAgreeTerm(String agreeTerm) {
        this.agreeTerm = agreeTerm;
    }
}
