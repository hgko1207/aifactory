package ins.core.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class EntityInfo {

    protected LoginInfo loginInfo;
    protected EntityUser register;
    protected Date registDttm;
    protected EntityUser updusr;
    protected Date updtDttm;
    
    public LoginInfo getLoginInfo() {
        if( this.loginInfo == null ){
            this.loginInfo = new LoginInfo();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if( auth != null){
                Object principal = auth.getPrincipal();
                if( principal != null && principal instanceof LoginInfo ){
                     this.loginInfo.setId(((LoginInfo)principal).getId());
                     this.loginInfo.setEmail(((LoginInfo)principal).getEmail());
                     this.loginInfo.setName(((LoginInfo)principal).getName());
                }
            }
        }
        
        return loginInfo;
    }
    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
    public EntityUser getRegister() {
        return register;
    }
    public void setRegister(EntityUser register) {
        this.register = register;
    }
    public Date getRegistDttm() {
        return registDttm;
    }
    public String getRegistDttmStr() {
        String formatDate = "";
        if( registDttm != null ){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatDate = sdf.format(registDttm);
        }
        return formatDate;
    }
    public void setRegistDttm(Date registDttm) {
        this.registDttm = registDttm;
    }
    public EntityUser getUpdusr() {
        return updusr;
    }
    public void setUpdusr(EntityUser updusr) {
        this.updusr = updusr;
    }
    public Date getUpdtDttm() {
        return updtDttm;
    }
    public String getUpdtDttmStr() {
        String formatDate = "";
        if( updtDttm != null ){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatDate = sdf.format(updtDttm);
        }
        return formatDate;
    }
    public void setUpdtDttm(Date updtDttm) {
        this.updtDttm = updtDttm;
    }
    
}