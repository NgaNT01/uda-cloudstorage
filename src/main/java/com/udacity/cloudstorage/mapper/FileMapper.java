package com.udacity.cloudstorage.mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;
import com.udacity.cloudstorage.domain.File;

@Mapper
public interface FileMapper {

    @Delete("DELETE FROM FILES WHERE id = #{id} AND userid = #{userId}")
    void removeFile(File file);

    @Select("SELECT id, name, content_type, size, data, userid FROM FILES WHERE id = #{id} AND userid = #{userId}")
    @Results({
            @Result(property = "contentType", column = "content_type")
    })
    File getFile(File file);

    @Select("SELECT id, name FROM FILES WHERE userid = #{userId} AND name = #{name}")
    File findByName(File file);

    @Select("SELECT id, name FROM FILES WHERE userid = #{userId} ORDER BY id DESC")
    List<File> getAllFilesByUserId(String userId);

    @Insert("INSERT INTO FILES (id, name, content_type, size, data, userid) " +
            "VALUES(#{id}, #{name}, #{contentType}, #{size}, #{data}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addFile(File file);

}
