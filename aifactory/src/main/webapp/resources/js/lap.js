/*******************************************************************************************************
 * Lap List 관리 
*******************************************************************************************************/
// Lap 관리 객체
var lapObjs = (function() {
    let lapList = new Array();
    
    return {
        getLapList : function(){
            return lapList;
        },
        getLap : function(idx){
            return lapList[idx];
        },
        size : function(){
            return lapList.length;
        },
        addLap : function (lapSn, lapEndCndCode, score, endDate, chkTime) {
            lapList.push({
                'lapSn':lapSn,
                'lapEndCndCode':lapEndCndCode,
                'goalScre':score,
                'endDttm':endDate,
                'chkPnttmCycle':chkTime,
                'ranks':[]
            });
        },
        removeLap : function(idx){
            lapList.splice(idx,1);
        },
        editLap : function(idx, lapEndCndCode, score, endDate, chkTime){
            lapList[idx].lapEndCndCode = lapEndCndCode;
            lapList[idx].goalScre = score;
            lapList[idx].endDttm = endDate;
            lapList[idx].chkPnttmCycle = chkTime;
        },
        addRank : function(lapIdx, point){
            if( lapList[lapIdx].ranks == null || lapList[lapIdx].ranks.length == 0){
                lapList[lapIdx].ranks = [];
            }  
            lapList[lapIdx].ranks.push({
                'point':point
            });
        },
        removeRank : function(lapIdx, rankIdx){
            if( lapList[lapIdx].ranks == null || lapList[lapIdx].ranks.length == 0){
                lapList[lapIdx].ranks = [];
            }  
            lapList[lapIdx].ranks.splice(rankIdx, 1);
        },
        toParam : function(){
            var param = "";
            var moneyPaymentMthd = "";
            for(var i=0; i<lapList.length; i++ ){
                if( param.length > 0 ) param += "&";
                param += 'laps['+i+'].lapSn='+lapList[i].lapSn;
                param += '&laps['+i+'].goalScre='+lapList[i].goalScre;
                param += '&laps['+i+'].endDttm='+lapList[i].endDttm;
                param += '&laps['+i+'].chkPnttmCycle='+lapList[i].chkPnttmCycle;
                param += '&laps['+i+'].lapEndCndCode.cmmnCode='+lapList[i].lapEndCndCode;
                for(var j=0; j<lapList[i].ranks.length; j++ ){
                    param += '&laps['+i+'].lapRank['+j+'].rank='+(j+1);
                    param += '&laps['+i+'].lapRank['+j+'].money='+lapList[i].ranks[j].point;
                }
            }
            return param;
        },
        print : function(){
            for( var i=0; i<lapList.length; i++)
                console.log(lapList[i]);
        }
        
    }
}());