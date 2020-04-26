package com.sefonsoft.oa.domain.trans;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Aron
 * @version 1.0.0
 */
public class TransDto {
    private Long id;
    @NotNull(message = "orgId不能为空")
    private Long orgId;
    @NotNull(message = "transCode不能为空")
    private String transCode;
    private String assetCode;
    private String transName;
    private String transStatus;
    private Date instalTime;
    private String pubSpeFlag;
    private Byte dataStatus;

    public TransDto() {
    	//标记为@notNull的属性创建时赋予默认值.
    	this.orgId = 0L;
    	this.transCode = "";
    }

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
        this.transCode = transCode;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
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
        this.pubSpeFlag = pubSpeFlag;
    }

    public Byte getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Byte dataStatus) {
        this.dataStatus = dataStatus;
    }
}
