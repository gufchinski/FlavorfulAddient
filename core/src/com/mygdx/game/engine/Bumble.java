package com.mygdx.game.engine;

import com.mygdx.game.item.ItemList;
import com.mygdx.game.screen.LevelScreen;
import com.mygdx.game.screen.MenuScreen;

public class Bumble extends BaseGame {
		public void create()
		{
			addItemPool();
			setActiveScreen( new MenuScreen());
		}
		private  void addItemPool()
		{
			for(ItemList i:ItemList.values())
			itemPool.add(i);
		}
	}