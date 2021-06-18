package com.mygdx.game.map;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.enemy.Enemy;
import com.mygdx.game.engine.BaseActor;
import com.mygdx.game.item.Item;

import java.util.ArrayList;

public class Room {

    public float x0, y0, x1, y1;
    int roomType = 0;

    public boolean isDrawedCleared = false, isDrawedUncleared = false, isFight = false;

    Stage stage;

    public ArrayList<Enemy> enemyList;
    public ArrayList<Door> doorList;
    public ArrayList<com.mygdx.game.engine.BaseActor> walls;
    public ArrayList<Item> itemList;
    public Chest chest;
    public com.mygdx.game.engine.BaseActor miniRoom;

    public Room(Stage stage) {
        this.stage = stage;
        enemyList = new ArrayList<>();
        walls = new ArrayList<>();
        doorList = new ArrayList<>();
        itemList = new ArrayList<>();
    }


    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public int getRoomType() {
        return roomType;
    }


    public void setCoordinates(int y, int x, int roomCoordinates, int roomWidth, int roomHeight) {
        y0 = y * roomCoordinates;
        x0 = x * roomCoordinates;
        x1 = x0 + roomWidth;
        y1 = y0 + roomHeight;
    }

    public boolean getIsDrawedCleared() {
        return isDrawedCleared;
    }

    public void setIsDrawedCleared() {
        isDrawedCleared = true;
    }

    public boolean getIsDrawedUncleared() {
        return isDrawedUncleared;
    }

    public void setIsDrawedUncleared() {
        isDrawedUncleared = true;
    }

    public void firstRoomCreate(int y, int x, int miniRoomSize, int miniRoomDistance, int width, int height, Stage ui, int mapSize, int miniRoomType) {
        miniRoom = new com.mygdx.game.engine.BaseActor(x * miniRoomDistance + width, (mapSize - y - 1) * miniRoomDistance + height, ui);
        if (miniRoomType == 1) {
            miniRoom.loadTexture("map/closedRoom.png");
            miniRoom.setSize(miniRoomSize, miniRoomSize);
            setIsDrawedUncleared();
        }
        else {
            miniRoom.loadTexture("map/openedRoom.png");
            miniRoom.setSize(miniRoomSize, miniRoomSize);
            setIsDrawedCleared();
        }
    }

    public void setOpenedRoom(int y, int x, int miniRoomSize) {
        miniRoom.loadTexture("map/openedRoom.png");
        miniRoom.setSize(miniRoomSize, miniRoomSize);
    }

    public void setInRoom(int y, int x, int miniRoomSize) {
        miniRoom.loadTexture("map/inMiniRoom.png");
        miniRoom.setSize(miniRoomSize, miniRoomSize);
    }

    public boolean roomCheck(float y, float x, float width, float height) {
        if (x >= x0 && x + width <= x1 && y >= y0 && y + height <= y1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean roomCheck(BaseActor eps) {
        if (eps.getX() >= x0 - 5 && eps.getX() + eps.getWidth() <= x1 + 5 && eps.getY() >= y0 - 5 && eps.getY() + eps.getHeight() <= y1 + 5) {
            return true;
        } else {
            return false;
        }
    }


    public void setWalls(BaseActor wall) {
        walls.add(wall);
    }

    public void setDoor(Door door) {
        doorList.add(door);
    }

    public ArrayList<Enemy> getEnemyList() {
        return enemyList;
    }

    public ArrayList<BaseActor> getWalls() {
        return walls;
    }

    public ArrayList<Door> getDoorList() {
        return doorList;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void setDoorTexture(boolean isClose) {
        if (isClose) {
            for (Door i : doorList) {
                if (i.doorType == 1) {
                    i.loadTexture("map/doorCloseUp.png");
                } else if (i.doorType == 2) {
                    i.loadTexture("map/doorCloseDown.png");
                } else if (i.doorType == 3) {
                    i.loadTexture("map/doorCloseLeft.png");
                } else if (i.doorType == 4) {
                    i.loadTexture("map/doorCloseRight.png");
                } else if (i.doorType == 5) {
                    i.setVisible(false);
                }
            }
        } else {
            for (Door i : doorList) {
                if (i.doorType == 1) {
                    i.loadTexture("map/doorUp.png");
                } else if (i.doorType == 2) {
                    i.loadTexture("map/doorDown.png");
                } else if (i.doorType == 3 || i.doorType == 4) {
                    i.loadTexture("map/doorLR.png");
                } else if (i.doorType == 5)
                    i.setVisible(true);
            }
        }
        //i.setPreventSize();
    }
}