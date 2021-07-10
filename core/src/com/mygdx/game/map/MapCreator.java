package com.mygdx.game.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Person;
import com.mygdx.game.enemy.Donut;
import com.mygdx.game.enemy.Pizza;
import com.mygdx.game.enemy.Slime;
import com.mygdx.game.enemy.Egg;
import com.mygdx.game.enemy.Wizard;
import com.mygdx.game.engine.BaseScreen;
import com.mygdx.game.engine.RepeatActor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Создаёт случайно сгенерированную карту, прорисовывает комнаты, стены, двери и проходы, миникарту
 */

public class MapCreator {
    Random random;

    int screenHeight = 1080;
    int screenWidth = 1920;
    int height, width;

    float[] vertices = new float[4];

    public int mapSize, roomCount, roomsLeft;
    public int roomCoordinates = 3456, roomWidth = 2048, roomHeight = 2048;
    public int miniRoomDistance = 70, miniRoomSize = 45;
    public int tdBarrierSize = 256, lrBarrierSize = 64;
    public int passSize = 256;
    public float xCoordinate1 = (roomWidth - passSize) / 2, xCoordinate2 = xCoordinate1 + passSize;
    public float yCoordinate1 = (roomHeight - passSize) / 2, yCoordinate2 = yCoordinate1 + passSize;
    public int offsetPolygonTop = 100, offsetPolygonBot = 90;

    public Stage stage;
    public Stage ui, back, front;

    public com.mygdx.game.engine.BaseActor room;
    public com.mygdx.game.engine.BaseActor pass;
    public com.mygdx.game.engine.BaseActor miniRoom;
    public com.mygdx.game.engine.BaseActor miniPass;
    public com.mygdx.game.engine.BaseActor loadingScreen;

    public Person person;

    public com.mygdx.game.map.Barrier wall;
    public Door door;

    private TiledMap tiledMap;

    public MapCreator(Stage stage, Stage ui, Stage backStage, Stage frontStage, String Tilemapfilename) {
        random = new Random();
        this.stage = stage;
        this.ui = ui;
        back = backStage;
        front = frontStage;
        tiledMap = new TmxMapLoader().load(Tilemapfilename);

    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getRoomsLeft() {
        return roomsLeft;
    }


    public ArrayList<Coordinates> potentialRoom = new ArrayList<>();
    public List<ArrayList<com.mygdx.game.map.Room>> Map = new ArrayList();
    //    public List<ArrayList<com.mygdx.game.map.Room>> MapbOSS = new ArrayList();

    /**
     * Генерирует двумерный массив в расположением и типом комнат
     */

    public void roomsGenerator() {
        mapSize = 5;

        width = screenWidth - mapSize * miniRoomDistance;
        height = screenHeight - mapSize * miniRoomDistance;

        for (int i = 0; i < mapSize; i++) {
            ArrayList<com.mygdx.game.map.Room> mp = new ArrayList<>();
            for (int j = 0; j < mapSize; j++) {
                com.mygdx.game.map.Room rm = new Room(stage);
                mp.add(rm);
            }
            Map.add(mp);
        }


        Map.get(mapSize / 2).get(mapSize / 2).setRoomType(RoomType.START);
        Map.get(mapSize / 2).get(mapSize / 2).setCoordinates(mapSize / 2, mapSize / 2, roomCoordinates, roomWidth, roomHeight);


        potentialRoom.add(new Coordinates(mapSize / 2 - 1, mapSize / 2, 0));
        Map.get(mapSize / 2 - 1).get(mapSize / 2).poten = false;
        potentialRoom.add(new Coordinates(mapSize / 2 + 1, mapSize / 2, 0));
        Map.get(mapSize / 2 + 1).get(mapSize / 2).poten = false;
        potentialRoom.add(new Coordinates(mapSize / 2, mapSize / 2 - 1, 0));
        Map.get(mapSize / 2).get(mapSize / 2 - 1).poten = false;
        potentialRoom.add(new Coordinates(mapSize / 2, mapSize / 2 + 1, 0));
        Map.get(mapSize / 2).get(mapSize / 2 + 1).poten = false;

        int addNumber, y, x;

        //Генерация обычных комнат
        for (int i = 1; i <= roomCount; i++) {

            addNumber = random.nextInt(potentialRoom.size());
            y = potentialRoom.get(addNumber).y;
            x = potentialRoom.get(addNumber).x;
            Map.get(y).get(x).setRoomType(RoomType.ENEMY);
            Map.get(y).get(x).setCoordinates(mapSize - y - 1, x, roomCoordinates, roomWidth, roomHeight);
            if (y != 0 && Map.get(y - 1).get(x).getRoomType() == RoomType.ABSENT) {
                if (Map.get(y - 1).get(x).poten) {
                    potentialRoom.add(new Coordinates(y - 1, x, potentialRoom.get(addNumber).count + 1));
                    Map.get(y - 1).get(x).poten = false;
                } else {

                    setPotentialRoomCount(y - 1, x, potentialRoom.get(addNumber).count + 1);
                }
            }
            if (y != (mapSize - 1) && Map.get(y + 1).get(x).getRoomType() == RoomType.ABSENT) {
                if (Map.get(y + 1).get(x).poten) {
                    potentialRoom.add(new Coordinates(y + 1, x, potentialRoom.get(addNumber).count + 1));
                    Map.get(y + 1).get(x).poten = false;
                } else {
                    setPotentialRoomCount(y + 1, x, potentialRoom.get(addNumber).count + 1);
                }
            }
            if (x != 0 && Map.get(y).get(x - 1).getRoomType() == RoomType.ABSENT) {
                if (Map.get(y).get(x - 1).poten) {
                    potentialRoom.add(new Coordinates(y, x - 1, potentialRoom.get(addNumber).count + 1));
                    Map.get(y).get(x - 1).poten = false;
                } else {
                    setPotentialRoomCount(y, x - 1, potentialRoom.get(addNumber).count + 1);
                }
            }
            if (x != (mapSize - 1) && Map.get(y).get(x + 1).getRoomType() == RoomType.ABSENT) {
                if (Map.get(y).get(x + 1).poten) {
                    potentialRoom.add(new Coordinates(y, x + 1, potentialRoom.get(addNumber).count + 1));
                    Map.get(y).get(x + 1).poten = false;
                } else {
                    setPotentialRoomCount(y, x + 1, potentialRoom.get(addNumber).count + 1);
                }
            }
            potentialRoom.remove(addNumber);
        }
        //Генерация комнаты с боссом
        Collections.sort(potentialRoom, Coordinates.countComparator);
        y = potentialRoom.get(0).y;
        x = potentialRoom.get(0).x;
        Map.get(y).get(x).setRoomType(RoomType.BOSS);
        Map.get(y).get(x).setCoordinates(mapSize - y - 1, x, roomCoordinates, roomWidth, roomHeight);
        potentialRoom.remove(0);
        //Генерация комнаты с сундуком
        addNumber = random.nextInt(potentialRoom.size());
        y = potentialRoom.get(addNumber).y;
        x = potentialRoom.get(addNumber).x;
        Map.get(y).get(x).setRoomType(RoomType.CHEST);
        Map.get(y).get(x).setCoordinates(mapSize - y - 1, x, roomCoordinates, roomWidth, roomHeight);
        potentialRoom.remove(addNumber);

        //Генерация комнаты с боссом
       /* addNumber = random.nextInt(potentialRoom.size());
        y = potentialRoom.get(addNumber).y;
        x = potentialRoom.get(addNumber).x;
        Map.get(y).get(x).setRoomType(4);
        Map.get(y).get(x).setCoordinates(mapSize - y - 1, x, roomCoordinates, roomWidth, roomHeight);*/

    }

    /**
     * Выбирает случайное количество комнат из диапазона, прорисовывает пол и проходы к другим комнатам
     */

    public void mapGenerator() {
        //roomCount = random.nextInt(4) + 5;
        roomCount = 1;
        roomsLeft = roomCount;
        roomsGenerator();

        for (int y = 0; y < mapSize; y++) {
            for (int x = 0; x < mapSize; x++) {
                if (Map.get(y).get(x).getRoomType() != RoomType.ABSENT) {

                    room = new RepeatActor(x * roomCoordinates, (mapSize - y - 1) * roomCoordinates, BaseScreen.backBackgrondStage, roomWidth, roomHeight, "map/floor.png");

                    /// создание проходов
                    if (y != 0 && Map.get(y - 1).get(x).getRoomType() != RoomType.ABSENT) {  ///проход для комнаты сверху
                        pass = new RepeatActor(x * roomCoordinates + xCoordinate1, (mapSize - y - 1) * roomCoordinates + roomHeight, back, passSize, roomCoordinates - roomHeight - tdBarrierSize, "map/floor.png");  ///создание прохода

                        wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + xCoordinate1 - lrBarrierSize, (mapSize - y - 1) * roomCoordinates + roomHeight + tdBarrierSize, front, lrBarrierSize, roomCoordinates - roomHeight - tdBarrierSize - lrBarrierSize, "map/wallSide.png"); ///создание левой стены прохода
                        vertices = new float[]{0, 0, wall.getWidth(), 0, wall.getWidth(), wall.getHeight() - (offsetPolygonBot - lrBarrierSize), 0, wall.getHeight() - (offsetPolygonBot - lrBarrierSize)};
                        wall.setBoundaryRectangle(vertices);
                        Map.get(y).get(x).setWalls(wall);
                        Map.get(y - 1).get(x).setWalls(wall);

                        wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + xCoordinate2, (mapSize - y - 1) * roomCoordinates + roomHeight + tdBarrierSize, front, lrBarrierSize, roomCoordinates - roomHeight - tdBarrierSize - lrBarrierSize, "map/wallSide.png");  ///создание правой стены прохода
                        vertices = new float[]{0, 0, wall.getWidth(), 0, wall.getWidth(), wall.getHeight() - (offsetPolygonBot - lrBarrierSize), 0, wall.getHeight() - (offsetPolygonBot - lrBarrierSize)};
                        wall.setBoundaryRectangle(vertices);
                        Map.get(y).get(x).setWalls(wall);
                        Map.get(y - 1).get(x).setWalls(wall);
                    }

                    if (x != (mapSize - 1) && Map.get(y).get(x + 1).getRoomType() != RoomType.ABSENT) {  ///проход для комнаты справа
                        pass = new RepeatActor(x * roomCoordinates + roomWidth, (mapSize - y - 1) * roomCoordinates + yCoordinate1, back, roomCoordinates - roomWidth - lrBarrierSize, passSize, "map/floor.png");  ///создание прохода

                        wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + roomWidth + lrBarrierSize, (mapSize - y - 1) * roomCoordinates + yCoordinate1 - tdBarrierSize, front, roomCoordinates - roomWidth - lrBarrierSize * 2, tdBarrierSize, "map/wallDown.png");  ///создание нижней стены прохода
                        vertices = new float[]{0, 0, wall.getWidth(), 0, wall.getWidth(), wall.getHeight() - offsetPolygonBot, 0, wall.getHeight() - offsetPolygonBot};
                        wall.setBoundaryRectangle(vertices);
                        Map.get(y).get(x).setWalls(wall);
                        Map.get(y).get(x + 1).setWalls(wall);
                        wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + roomWidth + lrBarrierSize, (mapSize - y - 1) * roomCoordinates + yCoordinate2, back, roomCoordinates - roomWidth - lrBarrierSize * 2, tdBarrierSize, "map/wallUp.png");  ///создание верхней стены прохода
                        vertices = new float[]{0, offsetPolygonTop, wall.getWidth(), offsetPolygonTop, wall.getWidth(), wall.getHeight(), 0, wall.getHeight()};
                        wall.setBoundaryRectangle(vertices);
                        Map.get(y).get(x).setWalls(wall);
                        Map.get(y).get(x + 1).setWalls(wall);
                    }

                    wallsAdder(y, x);
                }
            }
        }
    }

    /**
     * Прорисовывает стены и двери
     *
     * @param y Координата комнаты по оси Y в двумерном массиве Map
     * @param x Координата комнаты по оси X в двумерном массиве Map
     */

    public void wallsAdder(int y, int x) {

        /// стены сверху/снизу
        if (y != 0 && Map.get(y - 1).get(x).getRoomType() != RoomType.ABSENT) {  /// если есть комната сверху

            //левая половина верхней стены
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates, (mapSize - y - 1) * roomCoordinates + roomHeight, back, xCoordinate1, tdBarrierSize, "map/wallUp.png");
            vertices = new float[]{0, offsetPolygonTop, wall.getWidth(), offsetPolygonTop, wall.getWidth(), wall.getHeight(), 0, wall.getHeight()};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);

            Map.get(y - 1).get(x).setWalls(wall); //добавление этой части стены для верхней комнаты

            //левый угол верхнего прохода
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + xCoordinate1 - lrBarrierSize, (mapSize - y - 1) * roomCoordinates + roomHeight + tdBarrierSize - lrBarrierSize, stage, lrBarrierSize, lrBarrierSize, "map/passCLU.png");
            Map.get(y).get(x).setWalls(wall);

            Map.get(y - 1).get(x).setWalls(wall); //добавление этого угла для верхней комнаты

            //правая половина верхней стены
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + xCoordinate2, (mapSize - y - 1) * roomCoordinates + roomHeight, back, xCoordinate1, tdBarrierSize, "map/wallUp.png");
            vertices = new float[]{0, offsetPolygonTop, wall.getWidth(), offsetPolygonTop, wall.getWidth(), wall.getHeight(), 0, wall.getHeight()};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);

            Map.get(y - 1).get(x).setWalls(wall); //добавление этой части стены для верхней комнаты

            //правый угол верхнего прохода
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + xCoordinate2, (mapSize - y - 1) * roomCoordinates + roomHeight + tdBarrierSize - lrBarrierSize, stage, lrBarrierSize, lrBarrierSize, "map/passCRU.png");
            Map.get(y).get(x).setWalls(wall);

            Map.get(y - 1).get(x).setWalls(wall); //добавление этого угла для верхней комнаты

            //дверь верхнего прохода
            door = new Door(x * roomCoordinates + xCoordinate1, (mapSize - y - 1) * roomCoordinates + roomHeight, back, passSize, tdBarrierSize, Doortype.TOP);
            vertices = new float[]{0, offsetPolygonTop, door.getWidth(), offsetPolygonTop, door.getWidth(), door.getHeight(), 0, door.getHeight()};
            door.setBoundaryRectangle(vertices);
            door.loadTexture("map/doorCloseUp.png");
            Map.get(y).get(x).setDoor(door);
        } else { // если комнаты сверху нет
            //сплошная верхняя стена
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates, (mapSize - y - 1) * roomCoordinates + roomHeight, back, roomWidth, tdBarrierSize, "map/wallUp.png");
            vertices = new float[]{0, offsetPolygonTop, wall.getWidth(), offsetPolygonTop, wall.getWidth(), wall.getHeight(), 0, wall.getHeight()};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);
        }
        //левый верхний угол
        wall = new com.mygdx.game.map.Barrier(x * roomCoordinates - lrBarrierSize, (mapSize - y - 1) * roomCoordinates + roomHeight + tdBarrierSize - lrBarrierSize, stage, lrBarrierSize, lrBarrierSize, "map/cornLeftUp.png");
        vertices = new float[]{0 - lrBarrierSize, 0, wall.getWidth(), 0, wall.getWidth(), wall.getHeight() + lrBarrierSize, 0 - lrBarrierSize, wall.getHeight() + lrBarrierSize};
        wall.setBoundaryRectangle(vertices);
        Map.get(y).get(x).setWalls(wall);

        //правый верхний угол
        wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + roomWidth, (mapSize - y - 1) * roomCoordinates + roomHeight + tdBarrierSize - lrBarrierSize, stage, lrBarrierSize, lrBarrierSize, "map/cornRightUp.png");
        vertices = new float[]{0, 0, wall.getWidth() + lrBarrierSize, 0, wall.getWidth() + lrBarrierSize, wall.getHeight() + lrBarrierSize, 0, wall.getHeight() + lrBarrierSize};
        wall.setBoundaryRectangle(vertices);
        Map.get(y).get(x).setWalls(wall);


        if (y != (mapSize - 1) && Map.get(y + 1).get(x).getRoomType() != RoomType.ABSENT) {  /// если есть комната снизу
            //левая половина нижней стены
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates, (mapSize - y - 1) * roomCoordinates - tdBarrierSize, front, xCoordinate1, tdBarrierSize, "map/wallDown.png");
            vertices = new float[]{0, 0, wall.getWidth(), 0, wall.getWidth(), wall.getHeight() - offsetPolygonBot, 0, wall.getHeight() - offsetPolygonBot};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);

            Map.get(y + 1).get(x).setWalls(wall); //добавление этой части стены для нижней комнаты

            //левый угол нижнего прохода
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + xCoordinate1 - lrBarrierSize, (mapSize - y - 1) * roomCoordinates - lrBarrierSize, front, lrBarrierSize, lrBarrierSize, "map/passCLD.png");
            vertices = new float[]{0, 0 - offsetPolygonBot, wall.getWidth(), 0 - offsetPolygonBot, wall.getWidth(), wall.getHeight() - offsetPolygonBot, 0, wall.getHeight() - offsetPolygonBot};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);

            Map.get(y + 1).get(x).setWalls(wall); //добавление этого угла для нижней комнаты

            //правая половины нижней стены
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + xCoordinate2, (mapSize - y - 1) * roomCoordinates - tdBarrierSize, front, xCoordinate1, tdBarrierSize, "map/wallDown.png");
            vertices = new float[]{0, 0, wall.getWidth(), 0, wall.getWidth(), wall.getHeight() - offsetPolygonBot, 0, wall.getHeight() - offsetPolygonBot};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);

            Map.get(y + 1).get(x).setWalls(wall); //добавление этой части стены для нижней комнаты

            //правый угол нижнего прохода
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + xCoordinate2, (mapSize - y - 1) * roomCoordinates - lrBarrierSize, front, lrBarrierSize, lrBarrierSize, "map/passCRD.png");
            vertices = new float[]{0, 0 - offsetPolygonBot, wall.getWidth(), 0 - offsetPolygonBot, wall.getWidth(), wall.getHeight() - offsetPolygonBot, 0, wall.getHeight() - offsetPolygonBot};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);

            Map.get(y + 1).get(x).setWalls(wall); //добавление этого угла для нижней комнаты

            //дверь нижнего прохода
            door = new Door(x * roomCoordinates + xCoordinate1, (mapSize - y - 1) * roomCoordinates - tdBarrierSize, stage, passSize, tdBarrierSize, Doortype.BOTTOM);
            vertices = new float[]{0, 0, door.getWidth(), 0, door.getWidth(), door.getHeight() - offsetPolygonBot, 0, door.getHeight() - offsetPolygonBot};
            door.setBoundaryRectangle(vertices);
            door.loadTexture("map/doorCloseDown.png");
            Map.get(y).get(x).setDoor(door);
            door = new Door(x * roomCoordinates + xCoordinate1, (mapSize - y - 1) * roomCoordinates - tdBarrierSize, front, passSize, lrBarrierSize, Doortype.SUPPORT);
            door.setVisible(false);
            door.loadTexture("map/doorDown.png");
            Map.get(y).get(x).setDoor(door);
        } else {   /// если нет комнаты снизу
            //сплошная нижняя стена
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates, (mapSize - y - 1) * roomCoordinates - tdBarrierSize, front, roomWidth, tdBarrierSize, "map/wallDown.png");
            vertices = new float[]{0, 0, wall.getWidth(), 0, wall.getWidth(), wall.getHeight() - offsetPolygonBot, 0, wall.getHeight() - offsetPolygonBot};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);
        }
        //левый нижний угол
        wall = new com.mygdx.game.map.Barrier(x * roomCoordinates - lrBarrierSize, (mapSize - y - 1) * roomCoordinates - tdBarrierSize, front, lrBarrierSize, tdBarrierSize, "map/cornLeftDown.png");
        vertices = new float[]{0 - lrBarrierSize, 0, wall.getWidth(), 0, wall.getWidth(), wall.getHeight(), 0 - lrBarrierSize, wall.getHeight()};
        wall.setBoundaryRectangle(vertices);
        Map.get(y).get(x).setWalls(wall);

        //правый нижний угол
        wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + roomWidth, (mapSize - y - 1) * roomCoordinates - tdBarrierSize, front, lrBarrierSize, tdBarrierSize, "map/cornRightDown.png");
        vertices = new float[]{0, 0, wall.getWidth() + lrBarrierSize, 0, wall.getWidth() + lrBarrierSize, wall.getHeight(), 0, wall.getHeight()};
        wall.setBoundaryRectangle(vertices);
        Map.get(y).get(x).setWalls(wall);


        /// стены сбоку
        if (x != 0 && Map.get(y).get(x - 1).getRoomType() != RoomType.ABSENT) {  /// если есть комната слева
            //нижняя половина левой стены
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates - lrBarrierSize, (mapSize - y - 1) * roomCoordinates, front, lrBarrierSize, yCoordinate1 - lrBarrierSize, "map/wallSide.png");
            vertices = new float[]{0 - lrBarrierSize, 0, wall.getWidth(), 0, wall.getWidth(), wall.getHeight() - offsetPolygonBot, 0 - lrBarrierSize, wall.getHeight() - (offsetPolygonBot - lrBarrierSize)};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);

            if (y + 1 != mapSize && Map.get(y + 1).get(x).roomType != RoomType.ABSENT) {
                Map.get(y + 1).get(x).setWalls(wall); //добавление этой части стены для нижней комнаты
            }

            //нижний угол левого прохода
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates - lrBarrierSize, (mapSize - y - 1) * roomCoordinates + yCoordinate1 - lrBarrierSize, front, lrBarrierSize, lrBarrierSize, "map/passCLD.png");
            vertices = new float[]{0, 0 - offsetPolygonBot, wall.getWidth(), 0 - offsetPolygonBot, wall.getWidth(), wall.getHeight() - offsetPolygonBot, 0, wall.getHeight() - offsetPolygonBot};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);

            Map.get(y).get(x - 1).setWalls(wall); //добавление этого угла для левой комнаты

            //верхняя половина левой стены
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates - lrBarrierSize, (mapSize - y - 1) * roomCoordinates + yCoordinate2 + tdBarrierSize, front, lrBarrierSize, yCoordinate1 - lrBarrierSize, "map/wallSide.png");
            vertices = new float[]{0 - lrBarrierSize, 0, wall.getWidth(), 0, wall.getWidth(), wall.getHeight(), 0 - lrBarrierSize, wall.getHeight()};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);

            if (y != 0 && Map.get(y - 1).get(x).roomType != RoomType.ABSENT) {
                Map.get(y - 1).get(x).setWalls(wall); //добавление этой части стены для верхней комнаты
            }

            //верхний угол левого прохода
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates - lrBarrierSize, (mapSize - y - 1) * roomCoordinates + yCoordinate2, stage, lrBarrierSize, tdBarrierSize, "map/passSCLU.png");
            vertices = new float[]{0 - lrBarrierSize, offsetPolygonTop, wall.getWidth(), offsetPolygonTop, wall.getWidth(), wall.getHeight(), 0 - lrBarrierSize, wall.getHeight()};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);

            Map.get(y).get(x - 1).setWalls(wall); //добавление этого угла для левой комнаты

            //дверь левого прохода
            door = new Door(x * roomCoordinates - lrBarrierSize, (mapSize - y - 1) * roomCoordinates + yCoordinate1, stage, lrBarrierSize, passSize, Doortype.LEFT);
            vertices = new float[]{0 - lrBarrierSize, 0, door.getWidth(), 0, door.getWidth(), door.getHeight() + tdBarrierSize, 0 - lrBarrierSize, door.getHeight() + tdBarrierSize};
            door.setBoundaryRectangle(vertices);
            door.loadTexture("map/doorCloseLeft.png");
            Map.get(y).get(x).setDoor(door);
            door = new Door(x * roomCoordinates - lrBarrierSize, (mapSize - y - 1) * roomCoordinates + yCoordinate1, front, lrBarrierSize, passSize, Doortype.SUPPORT);
            door.setVisible(false);
            door.loadTexture("map/doorLR.png");
            Map.get(y).get(x).setDoor(door);
        } else {  /// если комнаты слева нет
            //сплошная левая стена
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates - lrBarrierSize, (mapSize - y - 1) * roomCoordinates, front, lrBarrierSize, roomHeight + tdBarrierSize - lrBarrierSize, "map/wallSide.png");
            vertices = new float[]{0 - lrBarrierSize, 0, wall.getWidth(), 0, wall.getWidth(), wall.getHeight(), 0 - lrBarrierSize, wall.getHeight()};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);
            if (y != 0 && Map.get(y - 1).get(x).roomType != RoomType.ABSENT) {
                Map.get(y - 1).get(x).setWalls(wall); //добавление этой стены для верхней комнаты
            }
            if (y != (mapSize - 1) && Map.get(y + 1).get(x).roomType != RoomType.ABSENT) {
                Map.get(y + 1).get(x).setWalls(wall); //добавление этой стены для ниней комнаты
            }
        }


        if (x != (mapSize - 1) && Map.get(y).get(x + 1).getRoomType() != RoomType.ABSENT) {  /// если есть комната справа
            //нижняя половина правой стены
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + roomWidth, (mapSize - y - 1) * roomCoordinates, front, lrBarrierSize, yCoordinate1, "map/wallSide.png");
            vertices = new float[]{0, 0, wall.getWidth() + lrBarrierSize, 0, wall.getWidth() + lrBarrierSize, wall.getHeight() - offsetPolygonBot, 0, wall.getHeight() - offsetPolygonBot};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);
            if ((y + 1) != mapSize && Map.get(y + 1).get(x).roomType != RoomType.ABSENT) {
                Map.get(y + 1).get(x).setWalls(wall); //добавление этой части стены для нижней комнаты
            }

            //нижний угол правого прохода
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + roomWidth, (mapSize - y - 1) * roomCoordinates + yCoordinate1 - lrBarrierSize, front, lrBarrierSize, lrBarrierSize, "map/passCRD.png");
            vertices = new float[]{0, 0 - offsetPolygonBot, wall.getWidth(), 0 - offsetPolygonBot, wall.getWidth(), wall.getHeight() - offsetPolygonBot, 0, wall.getHeight() - offsetPolygonBot};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);

            Map.get(y).get(x + 1).setWalls(wall); //добавление этого угла для правой комнаты

            //верхняя половина правой стены
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + roomWidth, (mapSize - y - 1) * roomCoordinates + yCoordinate2 + tdBarrierSize, front, lrBarrierSize, yCoordinate1 - lrBarrierSize, "map/wallSide.png");
            vertices = new float[]{0, 0, wall.getWidth() + lrBarrierSize, 0, wall.getWidth() + lrBarrierSize, wall.getHeight(), 0, wall.getHeight()};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);
            if ((y - 1) != -1 && Map.get(y - 1).get(x).roomType != RoomType.ABSENT) {
                Map.get(y - 1).get(x).setWalls(wall); //добавление этой части стены для верхней комнаты
            }

            //верхний угол правого прохода
            wall = new com.mygdx.game.map.Barrier(x * roomCoordinates + roomWidth, (mapSize - y - 1) * roomCoordinates + yCoordinate2, stage, lrBarrierSize, tdBarrierSize, "map/passSCRU.png");
            vertices = new float[]{0, offsetPolygonTop, wall.getWidth() + lrBarrierSize, offsetPolygonTop, wall.getWidth() + lrBarrierSize, wall.getHeight(), 0, wall.getHeight()};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);

            Map.get(y).get(x + 1).setWalls(wall); //добавление этого угла для правой комнаты

            //дверь правого прохода
            door = new Door(x * roomCoordinates + roomWidth, (mapSize - y - 1) * roomCoordinates + yCoordinate1, stage, lrBarrierSize, passSize, Doortype.RIGHT);
            vertices = new float[]{0, 0, door.getWidth() + lrBarrierSize, 0, door.getWidth() + lrBarrierSize, door.getHeight() + tdBarrierSize, 0, door.getHeight() + tdBarrierSize};
            door.setBoundaryRectangle(vertices);
            door.loadTexture("map/doorCloseRight.png");
            Map.get(y).get(x).setDoor(door);
            door = new Door(x * roomCoordinates + roomWidth, (mapSize - y - 1) * roomCoordinates + yCoordinate1, front, lrBarrierSize, passSize, Doortype.SUPPORT);
            door.setVisible(false);
            door.loadTexture("map/doorLR.png");
            Map.get(y).get(x).setDoor(door);
        } else {  /// если комнаты справа нет
            //сплошная правая стена
            wall = new Barrier(x * roomCoordinates + roomWidth, (mapSize - y - 1) * roomCoordinates, front, lrBarrierSize, roomHeight + tdBarrierSize - lrBarrierSize, "map/wallSide.png");
            vertices = new float[]{0, 0, wall.getWidth() + lrBarrierSize, 0, wall.getWidth() + lrBarrierSize, wall.getHeight(), 0, wall.getHeight()};
            wall.setBoundaryRectangle(vertices);
            Map.get(y).get(x).setWalls(wall);
            if (y != 0 && Map.get(y - 1).get(x).roomType != RoomType.ABSENT) {
                Map.get(y - 1).get(x).setWalls(wall); //добавление этой стены для верхней комнаты
            }
            if (y != (mapSize - 1) && Map.get(y + 1).get(x).roomType != RoomType.ABSENT) {
                Map.get(y + 1).get(x).setWalls(wall); //добавление этой стены для нижней комнаты
            }
        }

    }

    /**
     * Прорисовывает миникарту на основе двумерного массива Map
     *
     * @param y            Координата комнаты по оси Y в двумерном массиве Map
     * @param x            Координата комнаты по оси X в двумерном массиве Map
     * @param miniRoomType Закрытый или открытый тип комнаты
     */

    public void miniRoomsAdder(int y, int x, int miniRoomType) {
        if (miniRoomType == 1) {
            Map.get(y).get(x).firstRoomCreate(y, x, miniRoomSize, miniRoomDistance, width, height, ui, mapSize, miniRoomType);
        }
        if (miniRoomType == 2) {
            Map.get(y).get(x).firstRoomCreate(y, x, miniRoomSize, miniRoomDistance, width, height, ui, mapSize, miniRoomType);

            firstAppear(Map.get(y).get(x));

            if (y != 0 && Map.get(y - 1).get(x).getRoomType() != RoomType.ABSENT && !Map.get(y - 1).get(x).getIsDrawedCleared()) {  /// верхний проход
                miniPass = new com.mygdx.game.engine.BaseActor(x * miniRoomDistance + miniRoomSize / 3 + width, (mapSize - y - 1) * miniRoomDistance + miniRoomSize + height, ui);
                miniPass.loadTexture("map/openedRoom.png");
                miniPass.setSize(miniRoomSize / 3, miniRoomDistance - miniRoomSize);
                miniRoomsAdder(y - 1, x, 1);
            }
            if (y != (mapSize - 1) && Map.get(y + 1).get(x).getRoomType() != RoomType.ABSENT && !Map.get(y + 1).get(x).getIsDrawedCleared()) {  /// нижний проход
                miniPass = new com.mygdx.game.engine.BaseActor(x * miniRoomDistance + miniRoomSize / 3 + width, (mapSize - y - 1) * miniRoomDistance + miniRoomSize - miniRoomDistance + height, ui);
                miniPass.loadTexture("map/openedRoom.png");
                miniPass.setSize(miniRoomSize / 3, miniRoomDistance - miniRoomSize);
                miniRoomsAdder(y + 1, x, 1);
            }
            if (x != 0 && Map.get(y).get(x - 1).getRoomType() != RoomType.ABSENT && !Map.get(y).get(x - 1).getIsDrawedCleared()) {  ///левый проход
                miniPass = new com.mygdx.game.engine.BaseActor(x * miniRoomDistance + miniRoomSize - miniRoomDistance + width, (mapSize - y - 1) * miniRoomDistance + miniRoomSize / 3 + height, ui);
                miniPass.loadTexture("map/openedRoom.png");
                miniPass.setSize(miniRoomDistance - miniRoomSize, miniRoomSize / 3);
                miniRoomsAdder(y, x - 1, 1);
            }
            if (x != (mapSize - 1) && Map.get(y).get(x + 1).getRoomType() != RoomType.ABSENT && !Map.get(y).get(x + 1).getIsDrawedCleared()) {  ///правый проход
                miniPass = new com.mygdx.game.engine.BaseActor(x * miniRoomDistance + miniRoomSize + width, (mapSize - y - 1) * miniRoomDistance + miniRoomSize / 3 + height, ui);
                miniPass.loadTexture("map/openedRoom.png");
                miniPass.setSize(miniRoomDistance - miniRoomSize, miniRoomSize / 3);
                miniRoomsAdder(y, x + 1, 1);
            }
        }
    }

    /**
     * срабатывает при первом заходе в комнату
     *
     * @param room комната в которую ззаходит
     */

    public void firstAppear(Room room) {


        if (room.roomType == RoomType.BOSS) {
            room.isFight = true;
            room.setDoorTexture(false);
            String pat = "pattern-";
            //pat += String.valueOf(random.nextInt(9) + 1);
            pat += String.valueOf(1);
            MapLayer layer = tiledMap.getLayers().get(pat);
            for (MapObject obj : layer.getObjects()) {

                MapProperties props = obj.getProperties();

                if (props.containsKey("name") && props.get("name").equals("enemy")) {
//                    if (props.get("enemy").equals("slime")) {
//                        Donut eps = new Donut(room.x0 + (float) props.get("x"), room.y0 + (float) props.get("y"), back);
//                        room.enemyList.add(eps);
//                    }
//                    if (props.get("enemy").equals("wizard")) {
//                        Donut eps = new Donut(room.x0 + (float) props.get("x"), room.y0 + (float) props.get("y"), back);
//                        room.enemyList.add(eps);
//                    }
//                    if (props.get("enemy").equals("egg")) {
//                        Donut eps = new Donut(room.x0 + (float) props.get("x"), room.y0 + (float) props.get("y"), back);
//                        room.enemyList.add(eps);
//                    }
                    if (props.get("enemy").equals("pizza")) {
                        Pizza eps = new Pizza(room.x0 + (float) props.get("x"), room.y0 + (float) props.get("y"), back);
                        room.enemyList.add(eps);
                    }

                } else if (props.containsKey("name") && props.get("name").equals("solid")) {
                    if (props.get("texture").equals("box")) {
                        Solid eps = new Solid(room.x0 + (float) props.get("x"), room.y0 + (float) props.get("y"), stage, "box.png");
                        room.walls.add(eps);
                    }

                }

            }

        }
        if (room.roomType == RoomType.CHEST) {
            MapLayer layer = tiledMap.getLayers().get("chest");
            for (MapObject obj : layer.getObjects()) {

                MapProperties props = obj.getProperties();
                if (props.get("name").equals("chest")) {
                    Chest eps = new Chest(room.x0 + (float) props.get("x"), room.y0 + (float) props.get("y"), back, room);
                    room.chest = eps;
                }
            }
        }
    }

    private void setPotentialRoomCount(int y, int x, int count) {
        for (Coordinates co : potentialRoom) {
            if (co.y == y && co.x == x) {
                if (co.count > count)
                    co.count = count;
                break;
            }
        }
    }

    /**
     * Хранит координаты комнат в двумерном массиве Map
     */

    public static class Coordinates {
        int y, x, count;

        Coordinates(int y, int x, int count) {
            this.y = y;
            this.x = x;
            this.count = count;
        }

        public static Comparator<Coordinates> countComparator = new Comparator<Coordinates>() {
            @Override
            public int compare(Coordinates o1, Coordinates o2) {
                return o2.count - o1.count;
            }
        };
    }
}
