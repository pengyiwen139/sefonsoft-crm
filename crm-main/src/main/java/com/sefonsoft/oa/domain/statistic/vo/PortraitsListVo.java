package com.sefonsoft.oa.domain.statistic.vo;

import java.util.List;

/**
 * @author xielf
 */
public class PortraitsListVo {


  PortraitsTypeEnum type;

  List<PortraitsVo> portraitsList;


  public PortraitsTypeEnum getType() {
    return type;
  }

  public void setType(PortraitsTypeEnum type) {
    this.type = type;
  }

  public List<PortraitsVo> getPortraitsList() {
    return portraitsList;
  }

  public void setPortraitsList(List<PortraitsVo> portraitsList) {
    this.portraitsList = portraitsList;
  }
}
