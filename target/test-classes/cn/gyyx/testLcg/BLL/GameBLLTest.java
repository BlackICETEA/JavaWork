package cn.gyyx.testLcg.BLL;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.gyyx.testLcg.BLL.GameBLL;
import cn.gyyx.testLcg.model.Game;


public class GameBLLTest {

	GameBLL gameBLL = new GameBLL();

	@Test
	public void insertTest() {
		Game g=new Game();
		g.game="问道外传1";
		assertTrue(gameBLL.insert(g)==true);
	}
}
