package cn.gyyx.testLcg.BLL;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.gyyx.testLcg.model.User;

public class UserBLLTest {
	UserBLL userBLL = new UserBLL();
	@Test
	public void insertTest() {
		User u = new User();
		u.name="testName1";
		u.pwd="123456";
		assertTrue(userBLL.insert(u) == true);
	}
}
