package com.ticket.scan.dao.mapper;

import com.ticket.scan.dao.AuthorityInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface AuthorityInfoMapper {

	@Insert("insert into authority_info " +
			"(id,name,logo,intro,create_time) " +
			"values(" +
			"#{id,jdbcType=VARCHAR}," +
			"#{name,jdbcType=VARCHAR}," +
			"#{logo,jdbcType=VARCHAR}," +
			"#{intro,jdbcType=VARCHAR}," +
			"SUBSTR(date_format(now(3),'%Y-%m-%d %H:%i:%s:%f'),1,19))"
	)
	int insertByObject(AuthorityInfo authorityInfo);


	@Update("update authority_info set " +
			"name = #{name,jdbcType=VARCHAR}," +
			"logo = #{logo,jdbcType=VARCHAR}," +
			"intro = #{intro,jdbcType=VARCHAR}, " +
			"update_time = SUBSTR(date_format(now(3),'%Y-%m-%d %H:%i:%s:%f'),1,19) " +
			"where id=#{id,jdbcType=VARCHAR}"
	)
	void updateAuthorityInfo(String id);

	@Select("select count(1) " +
			"from authority_info " +
			"where id=#{id,jdbcType=VARCHAR}"
	)
	int selectById(String id);

	@Select("select id,name,logo,intro " +
			"from authority_info " +
			"where id=#{id,jdbcType=VARCHAR}"
	)
	@Results({
			@Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
			@Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "logo", property = "logo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "intro", property = "intro", jdbcType = JdbcType.VARCHAR),

	})
	AuthorityInfo selectAuthorityInfoById(String id);
}
