package cn.ulyer.common.utils;

import lombok.Data;




//requestBody formData

/**
 * @author: soft
 * <description>列表  分页 参数封装</description>
 * <update></update>
 * @date: 2020/3/19
 */

@Data
public class PageParam<T>{

	private Integer pageNum;

	private Integer pageSize;

	private T params;

	private String[] ascOrders;

	private String[] descOrders;


	public PageParam(){}

	public PageParam(Integer pageNum, Integer pageSize) {
		if(pageNum ==null){
			this.pageNum=1;
		}else {
			this.pageNum = pageNum;
		}
		if(pageSize==null){
			this.pageSize = 20;
		}else{
			this.pageSize = pageSize;
		}
	}

}
