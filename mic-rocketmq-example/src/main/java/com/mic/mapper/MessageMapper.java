package com.mic.mapper;

import com.mic.entity.PublishMessageEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author tianp
 **/
public interface MessageMapper {
    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @Insert({"INSERT INTO t_publish_message(" +
            "topic,pub_key,tag,body,pub_group,stage) values(" +
            "#{topic},#{pubKey},#{tag},#{body},#{pubGroup},#{stage}" +
            ")"})
    @Options(useGeneratedKeys = true)
    int insert(PublishMessageEntity entity);

    /**
     * 根据id 修改发布消息状态
     *
     * @param id
     * @param stage
     * @return
     */
    @Update({"UPDATE t_publish_message set stage = #{stage} where id = #{id}"})
    int update(@Param("id") Integer id, @Param("stage") Integer stage);
}