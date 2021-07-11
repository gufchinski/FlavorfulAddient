package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mygdx.game.ActionButton;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.HealthBar;
import com.mygdx.game.effect.Poison;
import com.mygdx.game.enemy.Enemy;
import com.mygdx.game.engine.BaseActor;
import com.mygdx.game.engine.BaseGame;
import com.mygdx.game.engine.DialogBox;
import com.mygdx.game.engine.JoyStick;
import com.mygdx.game.engine.Name;
import com.mygdx.game.item.Item;
import com.mygdx.game.map.MapCreator;
import com.mygdx.game.Person;
import com.mygdx.game.map.MiniRoomType;
import com.mygdx.game.map.Portal;
import com.mygdx.game.map.Room;
import com.mygdx.game.engine.BaseScreen;
import com.mygdx.game.map.RoomType;
import com.mygdx.game.weapon.Weap;

import java.util.Iterator;

import static com.mygdx.game.engine.BaseGame.setActiveScreen;

/**
 * Экран уровня
 */
public class LevelScreen extends BaseScreen {

    private Person person;
    JoyStick joystick, weaponJoystick;
    Vector2 joy, wepJoy;
    MapCreator map;
    Room room;
    float screenWidth, screenHeight, time = 0, dialogtime = 0;
    int x, y;
    String xS, yS;
    Label xCoordinate, yCoordinate;
    ProgressBar healthBar;
    Weap weapon;
    Image itemIcon;
    ActionButton actBtn;
    int typeAct = 0;
    public static Item itemAct, itemActive;
    BaseActor deadTextureScreen, deadTexturePlay, deadTextureExit;
    public Effect bulletEffect;
    Music music;
    DialogBox dialogBox;
    Portal portal = null;
    public boolean isReload;
    public float timeReload, timeReloadSup;


    public void initialize() {
        itemActive = null;
        isReload = false;
        //добавления джостиков
        weaponJoystick = new JoyStick(10, "ui/joy_background.png", "ui/joy_Knob.png", 50, 50, 200, 200);
        joystick = new JoyStick(10, "ui/joy_background.png", "ui/joy_Knob.png", 50, 50, 200, 200);
        wepJoy = new Vector2(1, 0);
        joy = new Vector2(0, 0);
        uiStage.addActor(joystick.getTouchpad());
        uiStage.addActor(weaponJoystick.getTouchpad());

        Table joysticTable = new Table();
        joysticTable.setFillParent(true);
        Table deadTable = new Table();
        deadTable.setFillParent(true);
        bulletEffect = null;



        //генерация карты
        map = new MapCreator(mainStage, uiStage, backgrondStage, frontStage, "mapgen2.tmx",backBackgrondStage);
        map.mapGenerator();
        y = map.mapSize / 2;
        x = map.mapSize / 2;
        map.miniRoomsAdder(y, x, MiniRoomType.OPENED);
        map.Map.get(y).get(x).setInRoom(map.miniRoomSize);

        weapon = new Weap(0, 0, mainStage, weaponJoystick, wepJoy);
        person = new Person((map.mapSize / 2) * map.roomCoordinates + 100, (map.mapSize / 2) * map.roomCoordinates + 100, mainStage);

        BaseActor.setPerson(person);
        BaseActor.weap = weapon;
        BaseActor.setRoom(map.Map.get(y).get(x));
        BaseActor.setScreen(this);

        person.hp = 100;
        map.setPerson(person);

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        xCoordinate = new Label("", lb);
        uiStage.addActor(xCoordinate);
        yCoordinate = new Label("", lb);
        uiStage.addActor(yCoordinate);
        xCoordinate.setPosition(10, 50);
        yCoordinate.setPosition(50, 50);
        xS = Integer.toString(x);
        yS = Integer.toString(y);
        //xCoordinate.setText(map.getRoomCount());
        // yCoordinate.setText(yS);

        healthBar = new HealthBar(500, 35);
        healthBar.setWidth(1000);
        healthBar.setValue(person.maxHp);


        uiStage.addActor(healthBar);

        itemIcon = new Image(new Texture("ui/pystota.png"));
        uiStage.addActor(itemIcon);

        BaseActor healthIcon = new BaseActor(0, 0, uiStage);
        healthIcon.loadTexture("ui/heart.png");

        timeReload = 25;
        timeReloadSup = 0;

        uiTable.pad(20);

        uiTable.add(healthIcon).padRight(20);
        uiTable.add(healthBar).width(500);
        uiTable.row().padTop(20);
        uiTable.add(itemIcon).left().colspan(2);
        uiTable.left().top();

        dialogBox = new DialogBox(0, 0, uiStage);
        dialogBox.setBackgroundColor(Color.TAN);
        dialogBox.setFontColor(Color.WHITE);
        dialogBox.setDialogSize(600, 300);
        dialogBox.setFontScale(0.80f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);
        dialogBox.setText("");
        dialogBox.setSecondText("");

        // uiTable.row();


        uiStage.addActor(joysticTable);
        joysticTable.add(dialogBox).colspan(2);
        joysticTable.row();


        actBtn = new ActionButton("ui/emptyButton.png", (Gdx.graphics.getWidth() - (Gdx.graphics.getHeight() / 3f)), 50 + Gdx.graphics.getHeight() / 2.5f + 50, Gdx.graphics.getHeight() / 5f, Gdx.graphics.getHeight() / 5f);
        actBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (actBtn.isUse) {
                    actUse();
                    actBtn.isUse = false;
                    actBtn.setDrawable(actBtn.textnotUse);
                    dialogBox.setVisible(true);
                } else if (itemActive != null && itemActive.type != 3) {
                    itemActive.use();
                    itemIcon.setDrawable(new SpriteDrawable(new Sprite(new Texture("ui/pystota.png"))));
                    itemActive = null;

                } else if (itemActive != null && itemActive.type == 3 && !isReload) {
                    itemActive.use();
                    isReload = true;
                }
                return true;
            }
        });

        uiStage.addActor(actBtn);


        joysticTable.left().bottom();
        joysticTable.add(actBtn).colspan(2).size(200).right().padRight(70).padBottom(30);
        joysticTable.row();
        joysticTable.add(joystick.getTouchpad()).size(400).expandX().left();
        joysticTable.add(weaponJoystick.getTouchpad()).size(400).expandX().right();
        joysticTable.padBottom(50).padRight(50).padLeft(50);

        deadTextureScreen = new BaseActor(100, 0, epsStage);
        deadTextureScreen.loadTexture("ui/deadScreen.png");
        deadTextureScreen.setSize(1920, 1080);
        deadTextureScreen.moveBy(-50,0);
        deadTextureScreen.setVisible(false);

        deadStage.addActor(deadTable);

        deadTexturePlay = new BaseActor(0, 0, deadStage);
        deadTexturePlay.loadTexture("ui/playAgain.png");
        deadTexturePlay.setScale(3);
        deadTexturePlay.setVisible(false);
        deadTexturePlay.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                disp();
                setActiveScreen(new LevelScreen());
                im.removeProcessor(deadStage);
                im.addProcessor(uiStage);
                im.addProcessor(mainStage);
                return true;
            }
        });

        deadTextureExit = new BaseActor(0, 0, deadStage);
        deadTextureExit.loadTexture("ui/exitDeadButton.png");
        deadTextureExit.setScale(3);
        deadTextureExit.setVisible(false);
        deadTextureExit.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                disp();
                setActiveScreen(new MenuScreen());
                im.removeProcessor(deadStage);
                im.addProcessor(uiStage);
                im.addProcessor(mainStage);
                return true;
            }
        });
        deadTable.add(deadTexturePlay).expandX();
        deadTable.add(deadTextureExit).expandX();
        deadTable.bottom().padBottom(100);

        if (isSave)
            loadSave();
    }

    public void update(float dt) {

        typeAct = 0;
        time += dt;
        person.timeImmortal += dt;


        //перемещение персонажи
        boolean norn = false;

        if (joystick.isTouch()) {

            person.setAnimationPaused(false);
            weapon.setAnimationPaused(false);

            joy.x = joystick.getX();
            joy.y = joystick.getY();

            norn = false;

            if (joy.x < 0) {
                person.isRight = false;
                weapon.isRight = false;
            } else if (joy.x > 0) {
                person.isRight = true;
                weapon.isRight = true;
            }

            person.moveBy(joy.x * dt * person.getSpeed(), joy.y * dt * person.getSpeed());

            if (!map.Map.get(y).get(x).roomCheck(person)) {
                if (y != 0 && map.Map.get(y - 1).get(x).roomCheck(person)) {
                    map.Map.get(y).get(x).setOpenedRoom(y, x, map.miniRoomSize);
                    y--;

                    if (!map.Map.get(y).get(x).getIsDrawedCleared()) {
                        map.miniRoomsAdder(y, x, MiniRoomType.OPENED);
                    }

                    map.Map.get(y).get(x).setInRoom(map.miniRoomSize);

                } else if (x != 0 && map.Map.get(y).get(x - 1).roomCheck(person)) {
                    map.Map.get(y).get(x).setOpenedRoom(y, x, map.miniRoomSize);
                    x--;

                    if (!map.Map.get(y).get(x).getIsDrawedCleared()) {
                        map.miniRoomsAdder(y, x, MiniRoomType.OPENED);
                    }

                    map.Map.get(y).get(x).setInRoom(map.miniRoomSize);

                } else if (y != (map.mapSize - 1) && map.Map.get(y + 1).get(x).roomCheck(person)) {
                    map.Map.get(y).get(x).setOpenedRoom(y, x, map.miniRoomSize);
                    y++;

                    if (!map.Map.get(y).get(x).getIsDrawedCleared()) {
                        map.miniRoomsAdder(y, x, MiniRoomType.OPENED);
                    }

                    map.Map.get(y).get(x).setInRoom(map.miniRoomSize);

                } else if (x != (map.mapSize - 1) && map.Map.get(y).get(x + 1).roomCheck(person)) {
                    map.Map.get(y).get(x).setOpenedRoom(y, x, map.miniRoomSize);
                    x++;

                    if (!map.Map.get(y).get(x).getIsDrawedCleared()) {
                        map.miniRoomsAdder(y, x, MiniRoomType.OPENED);
                    }

                    map.Map.get(y).get(x).setInRoom(map.miniRoomSize);
                }

                xS = Integer.toString(x);
                yS = Integer.toString(y);

            }

        } else {
            person.setAnimationPaused(true);
            weapon.setAnimationPaused(true);
        }

        room = map.Map.get(y).get(x);
        person.setRoom(room);

        person.alignCamera();
        camUpdate();

        if (weaponJoystick.isTouch() && (weaponJoystick.getX() != 0 || weaponJoystick.getY() != 0)) {
            norn = true;
            wepJoy.x = weaponJoystick.getX();
            wepJoy.y = weaponJoystick.getY();
            if (wepJoy.x < 0) {
                person.isRight = false;
                weapon.isRight = false;
            } else if (wepJoy.x > 0) {
                person.isRight = true;
                weapon.isRight = true;
            }
        }


        if (room.getRoomType() == RoomType.ENEMY || room.getRoomType() == RoomType.BOSS) {
            if (room.isFight) {
                Iterator<Enemy> enemyIterator = room.getEnemyList().iterator();
                met:
                while (enemyIterator.hasNext()) {
                    BaseActor enemyActor = enemyIterator.next();
                    Iterator<Enemy> enemyIterator1 = room.getEnemyList().iterator();
                    while (enemyIterator1.hasNext()) {
                        BaseActor enemyActor1 = enemyIterator1.next();
                        if (enemyActor != enemyActor1 && enemyActor.name != Name.BOSS)
                            enemyActor.preventOverlap(enemyActor1);
                    }

                    for (BaseActor bulletActor : BaseActor.getList(mainStage, "weapon.Bullet")) {
                        if (enemyActor.overlaps(bulletActor)) {
                            if (bulletEffect != null)
                                enemyActor.setEffect(bulletEffect);
                            if (!enemyActor.isImmortal)
                                enemyActor.hp -= bulletActor.dmg;
                            bulletActor.remove();

                        }
                    }
                    if (enemyActor.hp <= 0) {
                        enemyActor.death();
                        break met;
                    }
                    if (!person.isImmortal && person.overlaps(enemyActor) && enemyActor.name != Name.BOOM) {
                        person.hp -= enemyActor.dmg;
                        person.isImmortal = true;
                        person.timeImmortal = 0;
                    }
                    if (joystick.isTouch() && enemyActor.name != Name.BOSS)
                        enemyActor.preventOverlap(person);

                    person.preventOverlap(enemyActor);

                    for (BaseActor wallActor : room.getWalls()) {
                        if (enemyActor.name == Name.BOSS && enemyActor.overlaps(wallActor)) {
                            enemyActor.setMotionAngle(enemyActor.getMotionAngle() - 180);
                            if (enemyActor.isRight)
                                enemyActor.isRight = false;
                            else
                                enemyActor.isRight = true;
                        }
                        enemyActor.preventOverlap(wallActor);

                    }
                    for (BaseActor shieldActor : BaseActor.getList(mainStage, "item.ActiveShield")) {
                        if (enemyActor.name != Name.BOSS)
                            enemyActor.preventOverlap(shieldActor);
                    }


                }

                for (BaseActor bulletActor : BaseActor.getList(backgrondStage, "weapon.EnemyBullet")) {
                    if (person.overlaps(bulletActor)) {

                        if (!person.isImmortal) {
                            person.hp -= bulletActor.dmg;
                            person.isImmortal = true;
                            person.timeImmortal = 0;


                        }
                        bulletActor.remove();
                    }
                    for (BaseActor wallActor : room.getWalls()) {
                        if (bulletActor.overlaps(wallActor))
                            bulletActor.remove();
                    }
                    for (BaseActor shieldActor : BaseActor.getList(backgrondStage, "item.ActiveShield")) {
                        bulletActor.preventOverlap(shieldActor);
                    }
                }
                if (person.timeImmortal >= 1)
                    person.isImmortal = false;

                for (BaseActor doorActor : room.getDoorList()) {
                    person.preventOverlap(doorActor);

                    for (BaseActor enemyActor : room.getEnemyList()) {
                        enemyActor.preventOverlap(doorActor);
                    }

                    for (BaseActor bulletActor : BaseActor.getList(mainStage, "weapon.Bullet")) {
                        if (bulletActor.overlaps(doorActor))
                            bulletActor.remove();

                    }
                    for (BaseActor bulletActor : BaseActor.getList(backgrondStage, "weapon.EnemyBullet")) {
                        if (bulletActor.overlaps(doorActor))
                            bulletActor.remove();

                    }

                }
                for (BaseActor jamActor : BaseActor.getList(backBackgrondStage, "enemy.DonutJam")) {
                    if (person.legs.overlaps(jamActor)) {

                        if (!person.isImmortal) {
                            person.hp -= jamActor.dmg;
                            person.isImmortal = true;
                            person.timeImmortal = 0;


                        }

                    }

                }

                if (room.getEnemyList().isEmpty()) {
                    room.isFight = false;
                    map.roomsLeft--;
                    if (map.roomsLeft == 0 && BaseScreen.level < 2) {
                        room.setRoomType(RoomType.EXIT);
                        room.setInRoom(map.miniRoomSize);
                        portal = new Portal(room.x0 + map.roomWidth / 2, room.y0 + map.roomHeight / 2, backgrondStage);
                        portal.moveBy(-portal.getWidth() / 2, -portal.getHeight() / 2);
                    }
                    room.setDoorTexture(true);
                }

            }
        }

        for (BaseActor wallActor : room.getWalls()) {


            person.preventOverlap(wallActor);
            for (BaseActor bulletActor : BaseActor.getList(mainStage, "weapon.Bullet")) {
                if (wallActor.overlaps(bulletActor)) {
                    bulletActor.remove();
                }
            }
        }
        weapon.centerAtActor(person);

        if (norn) {
            if (weapon.isRight) {

                weapon.setOrigin(0, weapon.getHeight() / 2 - 8);
                weapon.setRotation(wepJoy.angleDeg());
                weapon.moveBy(person.getWidth() - 10, 30);
            } else {

                weapon.setOrigin(weapon.getWidth(), weapon.getHeight() / 2 - 8);
                weapon.setRotation(wepJoy.angleDeg() + 180);
                weapon.moveBy(-person.getWidth() + 10, 30);
            }
        }//разворот оружия
        else {
            if (person.isRight && wepJoy.x > 0) {
                weapon.setOrigin(0, weapon.getHeight() / 2 - 8);
                weapon.setRotation(wepJoy.angleDeg());
                weapon.moveBy(person.getWidth() - 10, 30);
            } else if (!person.isRight && wepJoy.x < 0) {
                weapon.setOrigin(weapon.getWidth(), weapon.getHeight() / 2 - 8);
                weapon.setRotation(wepJoy.angleDeg() + 180);
                weapon.moveBy(-person.getWidth() + 10, 30);
            } else if (person.isRight && wepJoy.x < 0) {
                weapon.setOrigin(0, weapon.getHeight() / 2 - 8);
                weapon.setRotation(180 - wepJoy.angleDeg());
                weapon.moveBy(person.getWidth() - 10, 30);
            } else {
                weapon.setOrigin(weapon.getWidth(), weapon.getHeight() / 2 - 8);
                weapon.setRotation(-wepJoy.angleDeg());
                weapon.moveBy(-person.getWidth() + 10, 30);
            }


        }
        if (weaponJoystick.isTouch())
            weapon.shoot();
        healthBar.setValue(person.hp);
        healthBar.setRange(healthBar.getMinValue(), person.maxHp);
        if(itemActive!=null&&itemActive.type==3) {
            if(isReload)
            actBtn.setDrawable(actBtn.textActNotReady);
            else
                actBtn.setDrawable(actBtn.textActReady);
        }
        else if(itemActive!=null&&itemActive.type==0)
            actBtn.setDrawable(actBtn.textActReady);
        else
            actBtn.setDrawable(actBtn.textnotUse);
        if (portal != null) {
            boolean boolportal = person.isWithinDistance(3, portal);
            actBtn.isUse = boolportal;
            if (boolportal) {
                typeAct = 2;
                actBtn.setDrawable(actBtn.textExit);
            } else {
                actBtn.setDrawable(actBtn.textnotUse);
            }
        }
        for (Item item : room.getItemList()) {
            boolean boolitem = person.isWithinDistance(3, room.chest);

            actBtn.isUse = boolitem;
            if (boolitem) {
                typeAct = item.type;
                itemAct = item;
                actBtn.setDrawable(actBtn.textPickUp);
            } else {
                actBtn.setDrawable(actBtn.textnotUse);
            }
        }

        if (person.hp <= 0) {
            deadTextureScreen.setVisible(true);
            deadTextureExit.setVisible(true);
            deadTexturePlay.setVisible(true);
            gameOver = true;
            im.addProcessor(deadStage);
            im.removeProcessor(uiStage);
            im.removeProcessor(mainStage);
            isSave = false;
        }
        if (dialogBox.isVisible()) {
            dialogtime += dt;
            if (dialogtime >= 3) {
                dialogtime = 0;
                dialogBox.setVisible(false);
            }
        }
        if (isReload) {
            timeReloadSup += dt;
        }
        if (timeReloadSup >= timeReload && isReload) {
            isReload = false;
            timeReloadSup = 0;
        }
        for (BaseActor item : BaseActor.getList(backBackgrondStage, "item.PizzaIteam")) {
                if(person.overlaps(item))
                {
                    setActiveScreen(new FinalScreen());
                }
        }

    }

    /**
     * использование предмета
     */
    public void actUse() {

        switch (typeAct) {
            case 0:
            case 3:
                dialogBox.setText(itemAct.nameItem);
                dialogBox.setSecondText(itemAct.descriptionItem);
                itemIcon.setDrawable(new SpriteDrawable(new Sprite(new Texture(itemAct.textureName))));
                itemActive = itemAct;
                itemActive.setVisible(false);
                itemAct.remove();
                room.itemList.remove(itemAct);
                break;
            case 1:
                dialogBox.setText(itemAct.nameItem);
                dialogBox.setSecondText(itemAct.descriptionItem);
                itemAct.use();
                itemAct.remove();
                room.itemList.remove(itemAct);
                break;
            case 2:
                BaseScreen.complexity += 0.1f;
                BaseScreen.level++;
                save();
                disp();
                setActiveScreen(new LevelScreen());
                break;
        }
    }

    public void loadSave() {
        person.hp = personHp;
        weapon.dmg = damg;
        person.setMaxSpeed(personSpeed);
        person.setSpeed(personSpeed);
        bulletEffect = bulletEff;
        itemActive = items;
        person.maxHp = maxHP;
        if (itemActive != null)
            itemIcon.setDrawable(draw);
        weapon.rate = rat;
    }

    public void save() {
        isSave = true;
        setSetting(person.hp, weapon.dmg, person.getSpeed(), bulletEffect, itemActive, itemIcon.getDrawable(), person.maxHp, weapon.rate);
    }

    /**
     * обновления камер на всех слоях
     */
    private void camUpdate() {
        Camera eps = backgrondStage.getCamera();
        eps.position.set(mainStage.getCamera().position.x, mainStage.getCamera().position.y, 0);
        eps.update();
        eps = frontStage.getCamera();
        eps.position.set(mainStage.getCamera().position.x, mainStage.getCamera().position.y, 0);
        eps.update();
        eps = backBackgrondStage.getCamera();
        eps.position.set(mainStage.getCamera().position.x, mainStage.getCamera().position.y, 0);
        eps.update();
    }
     void disp()
    {

        mainStage.clear();
        mainStage.dispose();

        backgrondStage.clear();
        backgrondStage.dispose();

        uiStage.clear();
        uiStage.dispose();

        backBackgrondStage.clear();
        backBackgrondStage.dispose();
        epsStage.clear();
        epsStage.dispose();
        frontStage.clear();
        frontStage.dispose();
        deadStage.clear();
        deadStage.dispose();
        loadStage.clear();
        loadStage.dispose();
        this.dispose();
    }

}
