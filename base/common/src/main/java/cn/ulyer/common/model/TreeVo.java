package cn.ulyer.common.model;

import java.util.List;

public interface TreeVo<T,E> {

   T getParentId();

   T getId();

   List<E> getChildren();

   void setChildren(List<E> children);
}
