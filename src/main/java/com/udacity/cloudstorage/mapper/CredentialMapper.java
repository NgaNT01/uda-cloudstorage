package com.udacity.cloudstorage.mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;
import com.udacity.cloudstorage.domain.Credential;

@Mapper
public interface CredentialMapper {

    @Delete("DELETE FROM CREDENTIALS WHERE id = #{id} AND userid = #{userId}")
    void removeCredential(Credential credential);

    @Select("SELECT id, url, password, username, userid FROM CREDENTIALS WHERE userid = #{userId} ORDER BY id DESC")
    List<Credential> findAllByUserId(String userId);

    @Select("SELECT id, key, url, password, username, userid FROM CREDENTIALS WHERE id = #{id} AND userid = #{userId}")
    Credential getCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, password=#{password} WHERE id =#{id}")
    void updateCredential(Credential credential);

    @Insert("INSERT INTO CREDENTIALS (id, url, key, username, password, userid) VALUES(#{id}, #{url}, #{key}, #{username}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addCredential(Credential credential);

}
