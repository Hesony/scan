package com.ticket.scan.dao.mapper;

import com.ticket.scan.dao.AuthorityTokenInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface AuthorityTokenInfoMapper {

	@Insert("insert into authority_token_info " +
			"(authority_id, access_token,expires, refresh_token, creat_time, update_time) " +
			"values(" +
			"#{authorityId,jdbcType=VARCHAR}," +
			"#{accessToken,jdbcType=VARCHAR}," +
			"#{expires,jdbcType=VARCHAR}," +
			"#{refreshToken,jdbcType=VARCHAR}," +
			"SUBSTR(date_format(now(3),'%Y-%m-%d %H:%i:%s:%f'),1,19))"
	)
	int insertByObject(AuthorityTokenInfo authorityTokenInfo);

	@Update("update authority_token_info set " +
			"access_token = #{accessToken,jdbcType=VARCHAR}," +
			"expires = #{expires,jdbcType=VARCHAR}," +
			"refresh_token = #{refreshToken,jdbcType=VARCHAR}, " +
			"update_time = SUBSTR(date_format(now(3),'%Y-%m-%d %H:%i:%s:%f'),1,19) " +
			"where authority_id=#{authorityId,jdbcType=VARCHAR}"
	)
	void updateById(AuthorityTokenInfo authorityTokenInfo);

	@Select("select count(1) " +
			"from authority_token_info " +
			"where authority_id=#{authorityId,jdbcType=VARCHAR}"
	)
	int selectById(String authorityId);

	@Select("select authority_id, access_token,expires, refresh_token " +
			"from authority_token_info " +
			"where authority_id=#{authorityId,jdbcType=VARCHAR}")
	@Results({
			@Result(column = "authority_id", property = "authorityId", jdbcType = JdbcType.VARCHAR),
			@Result(column = "access_token", property = "accessToken", jdbcType = JdbcType.VARCHAR),
			@Result(column = "expires", property = "expires", jdbcType = JdbcType.VARCHAR),
			@Result(column = "refresh_token", property = "refreshToken", jdbcType = JdbcType.VARCHAR),

	})
	AuthorityTokenInfo selectAcceccToken(String authorityId);


	@Select("select authority_id, access_token, expires, refresh_token " +
			"from authority_token_info ")
	@Results({
			@Result(column = "authority_id", property = "authorityId", jdbcType = JdbcType.VARCHAR),
			@Result(column = "access_token", property = "accessToken", jdbcType = JdbcType.VARCHAR),
			@Result(column = "expires", property = "expires", jdbcType = JdbcType.VARCHAR),
			@Result(column = "refresh_token", property = "refreshToken", jdbcType = JdbcType.VARCHAR),

	})
	List<AuthorityTokenInfo> selectAllAcceccToken();
}
