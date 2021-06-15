package cn.ulyer.common.model;

import java.io.Serializable;
import java.util.List;

public interface TreeVo {

   Serializable getParentId();

   Serializable getId();

   List getChildren();

   void setChildren(List children);
}
