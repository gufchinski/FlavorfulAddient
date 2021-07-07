package com.mygdx.game.map;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.enemy.Enemy;
import com.mygdx.game.engine.BaseActor;
import com.mygdx.game.item.Item;

import java.util.ArrayList;

/**
 * Хранит координты комнат на экране, тип комнаты, массивы со стенами, врагами, дверьми, предметами для комнат со столом, меняет текстуры миникомнат и проверяет нахождение актёра
 */

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

    /**
     * Первый раз создаёт миникомнату на миникарте, если игрок в этой комнате - то миникомната прорисовывается как открытая, иначе как закрытая
     * @param y Координата комнаты по оси Y в двумерном массиве Map
     * @param x Координата комнаты по оси X в двумерном массиве Map
     * @param miniRoomSize Размер миникомнаты на миникарте в пикселях
     * @param miniRoomDistance Дистанция между миникомнатами на миникарте в пикселях
     * @param width Дистанция, которую надо отступить от нулевой координаты по оси X для прорисовки миникарты
     * @param height Дистанция, которую надо отступить от нулевой координаты по оси Y для прорисовки миникарты
     * @param ui Слой интерфейса, закреплённый на месте, для отрисовки миникарты
     * @param mapSize Размер стороны двумерного массива Map (размер массива Map это mapSize * mapSize)
     * @param miniRoomType Закрытый или открытый тип комнаты
     */

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

    /**
     * Меняет текстуру данной миникамнаты на открытую
     * @param y Координата комнаты по оси Y в двумерном массиве Map
     * @param x Координата комнаты по оси X в двумерном массиве Map
     * @param miniRoomSize Размер миникомнаты на миникарте в пикселях
     */

    public void setOpenedRoom(int y, int x, int miniRoomSize) {
        miniRoom.loadTexture("map/openedRoom.png");
        miniRoom.setSize(miniRoomSize, miniRoomSize);
    }

    /**
     * Меняет текстуру миникомнаты на миникомнату, в которой находится персонаж
     * @param y Координата комнаты по оси Y в двумерном массиве Map
     * @param x Координата комнаты по оси X в двумерном массиве Map
     * @param miniRoomSize Размер миникомнаты на миникарте в пикселях
     */

    public void setInRoom(int y, int x, int miniRoomSize) {
        miniRoom.loadTexture("map/inMiniRoom.png");
        miniRoom.setSize(miniRoomSize, miniRoomSize);
    }

    /**
     * Проверяет, если актёр находится в комнате, используется если нельзя передать BaseActor как параметр
     * @param y Координата комнаты по оси Y в двумерном массиве Map
     * @param x Координата комнаты по оси X в двумерном массиве Map
     * @param width Ширина комнаты
     * @param height Высота комнаты
     * @return Возвращает true, если актёр находится в комнате, иначе false
     */

    public boolean roomCheck(float y, float x, float width, float height) {
        if (x >= x0 && x + width <= x1 && y >= y0 && y + height <= y1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Проверяет, если актёр находится в комнате
     * @param eps Координаты актёра в пикселях
     * @return Возвращает true, если актёр находится в комнате, иначе false
     */

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

    /**
     * Меняет текстуру двери на открытую или закрытую
     * @param isClose Если дверь закрыта, то значение параметра true, иначе false
     */

    public void setDoorTexture(boolean isClose) {
        if (isClose) {
            for (Door i : doorList) {
                switch (i.doorType) {
                    case TOP:
                        i.loadTexture("map/doorCloseUp.png");
                        break;
                    case LEFT:
                        i.loadTexture("map/doorCloseLeft.png");
                        break;
                    case RIGHT:
                        i.loadTexture("map/doorCloseRight.png");
                        break;
                    case BOTTOM:
                        i.loadTexture("map/doorCloseDown.png");
                        break;
                    case SUPPORT:
                        i.setVisible(false);
                        break;
                }
            }

        } else {
            for (Door i : doorList) {
                switch (i.doorType) {
                    case TOP:
                        i.loadTexture("map/doorUp.png");
                        break;
                    case LEFT:
                    case RIGHT:
                        i.loadTexture("map/doorLR.png");
                        break;
                    case BOTTOM:
                        i.loadTexture("map/doorDown.png");
                        break;
                    case SUPPORT:
                        i.setVisible(true);
                        break;
                }
            }

        }

    }
}