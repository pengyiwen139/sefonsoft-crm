package com.sefonsoft.oa.entity.trans;

import java.util.Date;

/**
 * Trans实体类
 *
 * @author Aron
 * @version 1.0.0
 */
public class Trans {
    private Long id;

    private Long orgId;

    private String transCode;

    private String assetCode;

    private String transName;

    private String transStatus;

    private Date instalTime;

    private String pubSpeFlag;

    private Byte dataStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode == null ? null : transCode.trim();
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode == null ? null : assetCode.trim();
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName == null ? null : transName.trim();
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus == null ? null : transStatus.trim();
    }

    public Date getInstalTime() {
        return instalTime;
    }

    public void setInstalTime(Date instalTime) {
        this.instalTime = instalTime;
    }

    public String getPubSpeFlag() {
        return pubSpeFlag;
    }

    public void setPubSpeFlag(String pubSpeFlag) {
        this.pubSpeFlag = pubSpeFlag == null ? null : pubSpeFlag.trim();
    }

    public Byte getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Byte dataStatus) {
        this.dataStatus = dataStatus;
    }
}