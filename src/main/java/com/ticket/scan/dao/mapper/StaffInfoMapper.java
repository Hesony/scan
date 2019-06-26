package com.ticket.scan.dao.mapper;


import com.ticket.scan.dao.StaffInfo;
import com.ticket.scan.models.model.StaffModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface StaffInfoMapper {

	@Insert("insert into staff_info " +
			"(authority_id,staff,openid,nickname,sex,province,city,country,headimgurl)" +
			"values(" +
			"#{authorityId,jdbcType=VARCHAR}," +
			"#{staff,jdbcType=VARCHAR}," +
			"#{openid,jdbcType=VARCHAR}," +
			"#{nickname,jdbcType=VARCHAR}," +
			"#{sex,jdbcType=VARCHAR}," +
			"#{province,jdbcType=VARCHAR}," +
			"#{city,jdbcType=VARCHAR}," +
			"#{country,jdbcType=VARCHAR}," +
			"#{headimgurl,jdbcType=VARCHAR}" +
			")")
	int insertByObject(StaffInfo staffInfo);


	@Select("select authority_id,staff,openid,nickname,sex,province,city,country,headimgurl from " +
			"staff_info " +
			"where openid=#{openid,jdbcType=VARCHAR}")
	@Results({
			@Result(column = "authority_id", property = "authorityId", jdbcType = JdbcType.VARCHAR),
			@Result(column = "staff", property = "staff", jdbcType = JdbcType.VARCHAR),
			@Result(column = "openid", property = "openid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nickname", property = "nickname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "sex", property = "sex", jdbcType = JdbcType.VARCHAR),
			@Result(column = "province", property = "province", jdbcType = JdbcType.VARCHAR),
			@Result(column = "city", property = "city", jdbcType = JdbcType.VARCHAR),
			@Result(column = "country", property = "country", jdbcType = JdbcType.VARCHAR),
			@Result(column = "headimgurl", property = "headimgurl", jdbcType = JdbcType.VARCHAR),
	}
	)
	StaffInfo selectByOpenid(String openid);


	@Update("update staff_info set " +
			"staff = #{staff,jdbcType=VARCHAR} " +
			"where openid = #{openid, jdbcType=VARCHAR}"
	)
	int updateByObject(StaffInfo staffInfo);



	@Select("select b.shop_name,a.authority_id, a.staff, a.openid, a.nickname, a.sex, a.province, a.city, a.country ,a.headimgurl " +
			"from authority_info a " +
			"LEFT JOIN id b on a.authority_id = b. " +
			"where a.staff is not null")
	@Results({
			@Result(column = "shop_name", property = "shopName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "shop_id", property = "shopId", jdbcType = JdbcType.VARCHAR),
			@Result(column = "staff", property = "staff", jdbcType = JdbcType.VARCHAR),
			@Result(column = "openid", property = "openid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nickname", property = "nickname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "sex", property = "sex", jdbcType = JdbcType.VARCHAR),
			@Result(column = "province", property = "province", jdbcType = JdbcType.VARCHAR),
			@Result(column = "city", property = "city", jdbcType = JdbcType.VARCHAR),
			@Result(column = "country", property = "country", jdbcType = JdbcType.VARCHAR),
	}
	)
	List<StaffModel> selectAllStaff();

	@Delete("delete from staff_info " +
			"where openid = #{openid, jdbcType=VARCHAR}"
	)
	int deleteByOpenid(String openid);

}
