package org.bqf.fallback.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.bqf.fallback.dao.model.FallbackConfigModel;
import org.springframework.web.bind.annotation.RequestBody;

public interface FallbackConfigDao {
    
    @Insert({"insert into t_fallback_config (service_name, service_path, client_name, client_path, params, result_class, result_value, config_level)"
            + " values (#{serviceName},#{servicePath}, #{clientName}, #{clientPath}, #{params}, #{resultClass}, #{resultValue}, #{level})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insert(@RequestBody FallbackConfigModel po);

    
    @Update({"update t_fallback_config set service_name=#{serviceName}, service_path=#{servicePath}, client_name = #{clientName}, client_path= #{clientPath}"
            + ", params= #{params}, result_class= #{resultClass}, result_value= #{resultValue}, config_level= #{level} where id = #{id}"})
    Long update(@RequestBody FallbackConfigModel po);
    
    @Delete({"delete from t_fallback_config where id = #{id}"})
    Long deleteById(@RequestBody String id);
    
    @Delete({"delete from t_fallback_config where service_name=#{serviceName}"})
    Long deleteByServiceName(@RequestBody String serviceName);
    
    @Delete({"delete from t_fallback_config where service_name=#{serviceName} and service_path=#{servicePath}"})
    Long deleteByServicePath(@RequestBody String serviceName, @RequestBody String servicePath);

    @Results(id="fallbackConfigMap", value={
    @Result(property = "id", column = "id", id = true, jdbcType=JdbcType.VARCHAR)
    , @Result(property = "serviceName", column = "service_name", jdbcType=JdbcType.VARCHAR)
    , @Result(property = "servicePath", column = "service_path", jdbcType=JdbcType.VARCHAR)
    , @Result(property = "clientName", column = "client_name", jdbcType=JdbcType.VARCHAR)
    , @Result(property = "clientPath", column = "client_path", jdbcType=JdbcType.VARCHAR)
    , @Result(property = "params", column = "params", jdbcType=JdbcType.VARCHAR)
    , @Result(property = "resultClass", column = "result_class", jdbcType=JdbcType.VARCHAR)
    , @Result(property = "resultValue", column = "result_value", jdbcType=JdbcType.LONGVARCHAR)
    , @Result(property = "level", column = "config_level", jdbcType=JdbcType.VARCHAR)})
    @Select("select * from t_fallback_config where service_name = #{serviceName}")
    List<FallbackConfigModel> selectByServiceName(String serviceName);
    
    @Select({"select * from t_fallback_config"})
    @ResultMap(value="fallbackConfigMap")
    List<FallbackConfigModel> selectAll();
}
