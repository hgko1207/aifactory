package ins.aifactory.service.lapRank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ins.aifactory.service.lap.Lap;
import ins.core.entity.EntityInfo;

public class LapRank extends EntityInfo {
    @JsonIgnore
    private Lap lap;
    private Integer rank;
    private Integer money;
    
    public Lap getLap() {
        return lap;
    }
    public void setLap(Lap lap) {
        this.lap = lap;
    }
    public Integer getRank() {
        return rank;
    }
    public void setRank(Integer rank) {
        this.rank = rank;
    }
    public Integer getMoney() {
        return money;
    }
    public void setMoney(Integer money) {
        this.money = money;
    }
}
