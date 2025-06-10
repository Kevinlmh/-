package com.course.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author lixuy
 * Created on 2019-04-10
 */
public class PointObject implements Serializable{

    private static final long serialVersionUID = 123456789L;

    private Integer id;
    //成长积分数
    private Integer growScore;
    //可兑换积分数
    private Integer exchangeScore;
    //总积分数
    private Integer scoreTotal;
    //是否已填写过个人资料
    private boolean filledInformation = false;
    private List<String> records = new ArrayList<>(); // 简单积分明细（如需详细可用PointRecord类）
    private Map<String, Date> lastActionDate = new HashMap<>(); // 记录各行为最后一次积分时间
    private Map<String, Integer> dailyActionCount = new HashMap<>(); // 记录每日行为次数
    private Date exchangeScoreStartDate; // 可兑换积分起始时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrowScore() {
        return growScore;
    }

    public void setGrowScore(Integer growScore) {
        this.growScore = growScore;
    }

    public Integer getExchangeScore() {
        return exchangeScore;
    }

    public void setExchangeScore(Integer exchangeScore) {
        this.exchangeScore = exchangeScore;
    }

    public Integer getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(Integer scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public void setFilledInformation(boolean filledInformation) {
        this.filledInformation = filledInformation;
    }

    public boolean isFilledInformation() {
        return filledInformation;
    }

    public List<String> getRecords() {
        return records;
    }
    public void setRecords(List<String> records) {
        this.records = records;
    }

    public Map<String, Date> getLastActionDate() {
        return lastActionDate;
    }
    public void setLastActionDate(Map<String, Date> lastActionDate) {
        this.lastActionDate = lastActionDate;
    }

    public Map<String, Integer> getDailyActionCount() {
        return dailyActionCount;
    }
    public void setDailyActionCount(Map<String, Integer> dailyActionCount) {
        this.dailyActionCount = dailyActionCount;
    }

    public Date getExchangeScoreStartDate() {
        return exchangeScoreStartDate;
    }
    public void setExchangeScoreStartDate(Date exchangeScoreStartDate) {
        this.exchangeScoreStartDate = exchangeScoreStartDate;
    }
}
