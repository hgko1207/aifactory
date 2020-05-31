package ins.aifactory.service.team;

import ins.core.entity.EntityInfo;

public class Team extends EntityInfo{

    private Long teamSn;
    private String teamNm;
    
    public Long getTeamSn() {
        return teamSn;
    }
    public void setTeamSn(Long teamSn) {
        this.teamSn = teamSn;
    }
    public String getTeamNm() {
        return teamNm;
    }
    public void setTeamNm(String teamNm) {
        this.teamNm = teamNm;
    }
}