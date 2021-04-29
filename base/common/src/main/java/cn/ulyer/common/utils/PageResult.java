package cn.ulyer.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T>  implements Serializable {

    private long pages;

    private long current;

    private long pageSize;

    private List<T> records;

    private long total;


    public PageResult(Page<T> pager){
        this.current = pager.getCurrent();
        this.pageSize = pager.getSize();
        this.records = pager.getRecords();
        this.total = pager.getTotal();
        this.pages = pager.getPages();
    }
}
