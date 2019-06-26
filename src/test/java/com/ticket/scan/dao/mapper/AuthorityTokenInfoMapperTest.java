package com.ticket.scan.dao.mapper;

import com.ticket.scan.dao.AuthorityTokenInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorityTokenInfoMapperTest {
	@Autowired
	private AuthorityTokenInfoMapper mapper;
	@Test
	public void insertByObject() {
		AuthorityTokenInfo authorityTokenInfo = new AuthorityTokenInfo();
		authorityTokenInfo.setAccessToken("aaaaa");
		authorityTokenInfo.setAuthorityId("42867351");
		authorityTokenInfo.setExpires("8888888");
		authorityTokenInfo.setRefreshToken("dfdfdfdfdf");

		mapper.updateById(authorityTokenInfo);
	}

	@Test
	public void selectById() {
		int a = mapper.selectById("42867351");
		Assert.assertEquals(a,1);
	}
}