package com.mygdx.game.effect;

import com.mygdx.game.engine.BaseActor;

/**
 * Абстарктный класс эффектов
 */
public abstract class Effect {
    public float timePlay = 6.1f, timeDelay = 1.5f;

    /**
     * Основное поведение эффекта
     *
     * @param baseActor
     */
    public abstract void play(BaseActor baseActor);

    /**
     * Оканчание эффекта
     */
    public abstract void effectFinish();
}
