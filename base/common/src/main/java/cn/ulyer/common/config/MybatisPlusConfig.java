package cn.ulyer.common.config;

import cn.ulyer.common.model.AbstractBaseModel;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;


@Configuration
@Slf4j
public class MybatisPlusConfig {


    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new Audit());
        //乐观锁
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //分页
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        //避免全表删除操作
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }


    /**
     * 日期填充
     */
    public static class Audit implements InnerInterceptor {

        @Override
        public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter)  {
            if(ms.getSqlCommandType()== SqlCommandType.UPDATE){
                audit(parameter,false,true);
            }else if(ms.getSqlCommandType()==SqlCommandType.INSERT){
                audit(parameter,true,true);
            }
        }


        private void audit(Object parameter, boolean createTime, boolean updateTime){
            if(parameter instanceof AbstractBaseModel){
                setValue((AbstractBaseModel) parameter,createTime,updateTime);
            }
            if(parameter instanceof MapperMethod.ParamMap){
                ((MapperMethod.ParamMap<?>) parameter).forEach((k,v)->{
                    if(v instanceof AbstractBaseModel){
                        setValue((AbstractBaseModel) v,createTime,updateTime);
                    }
                });
            }
        }

        private void setValue(AbstractBaseModel model,boolean create,boolean update){
            if(create){
                model.setCreateTime(new Date());
            }
            if(update){
                model.setUpdateTime(new Date());
            }
        }
    }

}
