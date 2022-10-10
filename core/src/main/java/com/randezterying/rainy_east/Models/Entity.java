package com.randezterying.rainy_east.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.randezterying.rainy_east.GameCore.Assets;

public class Entity extends Actor {

    private ShapeRenderer shapeRenderer;

    private Texture head, body, leftArm1, leftArm2,
            rightArm1, rightArm2, leftLeg1, leftLeg2, rightLeg1, rightLeg2;
    private float scalePercent;
    private float anim_timer = 0f;
    private float shake_timer = 0f;
    private final float moveDeadRadius = 4f;
    private float moveSpeed = 4f;
    protected boolean isStanding = true;

    private float movePointX;
    private float movePointY;

    private float hp;
    private float sp;

    private int maxHp;
    private int maxSp;
    private int tookWeight;
    private int maxWeight;
    private int damage;
    private int defence;
    private int healing;
    private int agility;

    private float attackCd = 0;
    private float evadeCd = 0;
    private float slowTime = 0;

    private boolean dead = false;

    public Entity(float x, float y, float scalePercent) {
        setPosition(x, y * Assets.h/Assets.w);
        this.scalePercent = scalePercent;

        shapeRenderer = new ShapeRenderer();

        head = new Texture(Gdx.files.internal("playerAssets/e.png"));
        body = new Texture(Gdx.files.internal("playerAssets/e.png"));
        leftArm1 = new Texture(Gdx.files.internal("playerAssets/e.png"));
        leftArm2 = new Texture(Gdx.files.internal("playerAssets/e.png"));
        rightArm1 = new Texture(Gdx.files.internal("playerAssets/e.png"));
        rightArm2 = new Texture(Gdx.files.internal("playerAssets/e.png"));
        leftLeg1 = new Texture(Gdx.files.internal("playerAssets/e.png"));
        leftLeg2 = new Texture(Gdx.files.internal("playerAssets/e.png"));
        rightLeg1 = new Texture(Gdx.files.internal("playerAssets/e.png"));
        rightLeg2 = new Texture(Gdx.files.internal("playerAssets/e.png"));

        setMaxHp(100);
        setHp(100);
        setMaxSp(100);
        setSp(100);
        setMaxWeight(10);
    }

    public Entity() {
        super();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!dead) {
            float xLen = getMovePointX() - getX();
            float yLen = getMovePointY() - getY();
            moveEntity(xLen, yLen);
            animStand(batch, xLen, yLen);
            animRunRight(batch, xLen);
            animRunLeft(batch, xLen);

//            shake_timer += 0.3f;
//            if (shake_timer > 100) shake_timer = 0;

            batch.end();
            float hpPercent = (getHp() * 100) / getMaxHp();
            float spPercent = (getSp() * 100) / getMaxSp();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(getX()-30*scalePercent, getY()+65*scalePercent, 110*scalePercent, 11*scalePercent);
            shapeRenderer.rect(getX()-30*scalePercent, getY()+77*scalePercent, 110*scalePercent, 11*scalePercent);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(getX()-30*scalePercent, getY()+65*scalePercent, ((110*scalePercent)/100)* hpPercent, 11*scalePercent);
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(getX()-30*scalePercent, getY()+77*scalePercent, ((110*scalePercent)/100)* spPercent, 11*scalePercent);
            shapeRenderer.end();
            batch.begin();

            // This part doesn't allow HP and SP to go out of bounds
            if (hp > maxHp) hp = maxHp;
            if (hp <= 0) {
                hp = 0;
                dead = true;
            }
            if (sp > maxSp) sp = maxSp;
            if (sp <= 0) sp = 0;

            //Auto regen hp and sp
            if (hp < maxHp) hp += 0.1f;
            if (sp < maxSp) sp += 0.1f;

            System.out.println(sp + " " + getOverweight() * 0.05f);

            if (!isStanding) sp -= getOverweight() * 0.05f;

            // Evade CoolDown
            if (getEvadeCd() > 0) {
                setEvadeCd(getEvadeCd() - Gdx.graphics.getDeltaTime() * 1.5f);
                if (getEvadeCd() < 0) setEvadeCd(0);
            }

            // Attack CoolDown
            if (getAttackCd() > 0) {
                setAttackCd(getAttackCd() - Gdx.graphics.getDeltaTime() * 1.5f);
                if (getAttackCd() < 0) setAttackCd(0);
            }
        } else {
            hp = 0;
//            animStand(batch);
        }
    }

    public void moveEntity(float xLen, float yLen) {
        float dX = 1, dY = 1;

        if (Math.abs(xLen) >= Math.abs(yLen)) {
            dY = yLen / xLen;
            if (xLen < 0) {
                dX *= -1;
                dY *= -1;
            }
        } else {
            dX = xLen / yLen;
            if (yLen < 0) {
                dX *= -1;
                dY *= -1;
            }
        }

        if (xLen < moveDeadRadius && xLen > -moveDeadRadius) setMovePointX(getX());
        else setX(getX() + moveSpeed * dX);
        if (yLen < moveDeadRadius && yLen > -moveDeadRadius) setMovePointY(getY());
        else setY(getY() + moveSpeed * dY);
    }

    private void animStand(Batch batch, float xLen, float yLen) {
        if (xLen < moveDeadRadius && xLen > -moveDeadRadius && yLen < moveDeadRadius && getY() > -moveDeadRadius) {
            isStanding = true;
            batch.draw(head, getX(), getY(),50*scalePercent, 50*scalePercent);
            batch.draw(body, getX()-(5*scalePercent), getY()-(100*scalePercent),60*scalePercent, 100*scalePercent);

            batch.draw(leftArm1, getX()-(30*scalePercent), getY()-(60*scalePercent), 25*scalePercent, 60*scalePercent);
            batch.draw(leftArm2, getX()-(30*scalePercent), getY()-(120*scalePercent), 25*scalePercent, 60*scalePercent);
            batch.draw(rightArm1, getX()+(55*scalePercent), getY()-(60*scalePercent), 25*scalePercent, 60*scalePercent);
            batch.draw(rightArm2, getX()+(55*scalePercent), getY()-(120*scalePercent), 25*scalePercent, 60*scalePercent);

            batch.draw(leftLeg1, getX()-(5*scalePercent), getY()-(160*scalePercent), 25*scalePercent, 60*scalePercent);
            batch.draw(leftLeg2, getX()-(5*scalePercent), getY()-(230*scalePercent), 25*scalePercent, 70*scalePercent);
            batch.draw(rightLeg1, getX()+(30*scalePercent), getY()-(160*scalePercent), 25*scalePercent, 60*scalePercent);
            batch.draw(rightLeg2, getX()+(30*scalePercent), getY()-(230*scalePercent), 25*scalePercent, 70*scalePercent);
        }
    }

    private void animRunRight(Batch batch, float xLen) {
        if (getX() < getMovePointX() || (xLen < moveDeadRadius && xLen > -moveDeadRadius && getY() > getMovePointY())) {
            anim_timer += 0.2f;
            isStanding = false;
//            setY((float) (getY() + (Math.cos(shake_timer) * -1.3)));
            batch.draw(head, getX()+34*scalePercent, getY()-7, 25*scalePercent, 0, 50*scalePercent, 50*scalePercent, 1, 1, -20, 0, 0, 256, 256, false, false);

            batch.draw(rightArm1, getX()+85*scalePercent, getY()-78*scalePercent, 0, 60*scalePercent, 25*scalePercent, 60*scalePercent, 1, 1, -getCos(anim_timer, 3), 0,0, 256, 256, false, false);
            batch.draw(rightArm2, (getX()+75*scalePercent) + getCos(anim_timer, -3), getY()-125*scalePercent, 13*scalePercent, 60*scalePercent, 25*scalePercent, 60*scalePercent, 1, 1, 50 + getCos(anim_timer, -5), 0,0, 256, 256, false, false);

            batch.draw(body, getX()-5*scalePercent, getY()-100*scalePercent, 30*scalePercent, 0, 60*scalePercent, 100*scalePercent, 1, 1, -20, 0,0, 256, 256, false, false);

            batch.draw(leftArm1, getX()+6*scalePercent, getY()-58*scalePercent, 25*scalePercent, 60*scalePercent, 25*scalePercent, 60*scalePercent, 1, 1, getCos(anim_timer, 3), 0,0, 256, 256, false, false);
            batch.draw(leftArm2, (getX()+1*scalePercent) + getCos(anim_timer, 3), getY()-110*scalePercent, 13*scalePercent, 60*scalePercent, 25*scalePercent, 60*scalePercent, 1, 1, 50 + getCos(anim_timer, 5), 0,0, 256, 256, false, false);

            batch.draw(rightLeg1, getX()+30*scalePercent, getY()-163*scalePercent, 13*scalePercent, 60*scalePercent, 25*scalePercent, 60*scalePercent, 1, 1, getCos(anim_timer, 3), 0,0, 256, 256, false, false);
            batch.draw(rightLeg2, (getX()+30*scalePercent) + getCos(anim_timer, 3), getY()-225*scalePercent, 13*scalePercent, 70*scalePercent, 25*scalePercent, 70*scalePercent, 1, 1, -50 + getCos(anim_timer, 6), 0,0, 256, 256, false, false);
            batch.draw(leftLeg1, getX()-4*scalePercent, getY()-153*scalePercent, 13*scalePercent, 60*scalePercent, 25*scalePercent, 60*scalePercent, 1, 1, -getCos(anim_timer, 3), 0,0, 256, 256, false, false);
            batch.draw(leftLeg2, (getX()-5*scalePercent) - getCos(anim_timer, 3), getY()-215*scalePercent, 13*scalePercent, 70*scalePercent, 25*scalePercent, 70*scalePercent, 1, 1, -50 + getCos(anim_timer, -6), 0,0, 256, 256, false, false);
        }
    }

    private void animRunLeft(Batch batch, float xLen) {
        if (getX() > getMovePointX() || (xLen < moveDeadRadius && xLen > -moveDeadRadius && getY() < getMovePointY())) {
            anim_timer += 0.2f;
            isStanding = false;
//            setY((float) (getY() + (Math.cos(shake_timer) * -1.3)));
            batch.draw(head, getX()-34*scalePercent, getY()-7*scalePercent, 25*scalePercent, 0, 50*scalePercent, 50*scalePercent, 1, 1, 20, 0, 0, 256, 256, false, false);

            batch.draw(leftArm1, getX()-60*scalePercent, getY()-78*scalePercent, 25*scalePercent, 60*scalePercent, 25*scalePercent, 60*scalePercent, 1, 1, getCos(anim_timer, 3), 0,0, 256, 256, false, false);
            batch.draw(leftArm2, (getX()-60*scalePercent) + getCos(anim_timer, 3), getY()-125*scalePercent, 13*scalePercent, 60*scalePercent, 25*scalePercent, 60*scalePercent, 1, 1, -50 + getCos(anim_timer, 5), 0,0, 256, 256, false, false);

            batch.draw(body, getX()-5*scalePercent, getY()-100*scalePercent, 30*scalePercent, 0, 60*scalePercent, 100*scalePercent, 1, 1, 20, 0,0, 256, 256, false, false);

            batch.draw(rightArm1, getX()+20*scalePercent, getY()-58*scalePercent, 0, 60*scalePercent, 25*scalePercent, 60*scalePercent, 1, 1, -getCos(anim_timer, 3), 0,0, 256, 256, false, false);
            batch.draw(rightArm2, (getX()+20*scalePercent) + getCos(anim_timer, -3), getY()-110*scalePercent, 13*scalePercent, 60*scalePercent, 25*scalePercent, 60*scalePercent, 1, 1, -50 + getCos(anim_timer, -5), 0,0, 256, 256, false, false);

            batch.draw(leftLeg1, getX()-4*scalePercent, getY()-163*scalePercent, 13*scalePercent, 60*scalePercent, 25*scalePercent, 60*scalePercent, 1, 1, -getCos(anim_timer, 3), 0,0, 256, 256, false, false);
            batch.draw(leftLeg2, (getX()-4*scalePercent) - getCos(anim_timer, 3), getY()-225*scalePercent, 13*scalePercent, 70*scalePercent, 25*scalePercent, 70*scalePercent, 1, 1, 50 + getCos(anim_timer, -6), 0,0, 256, 256, false, false);
            batch.draw(rightLeg1, getX()+30*scalePercent, getY()-153*scalePercent, 13*scalePercent, 60*scalePercent, 25*scalePercent, 60*scalePercent, 1, 1, getCos(anim_timer, 3), 0,0, 256, 256, false, false);
            batch.draw(rightLeg2, (getX()+30*scalePercent) + getCos(anim_timer, 3), getY()-215*scalePercent, 13*scalePercent, 70*scalePercent, 25*scalePercent, 70*scalePercent, 1, 1, 50 + getCos(anim_timer, 6), 0,0, 256, 256, false, false);
        }
    }

    private int getCos(float num, float speed) {
        return (int) (Math.cos(num) * -10 * (speed * scalePercent));
    }

    public void evade() {
        if (evadeCd == 0 && sp > 10) {
            setX(getX() + 50 * (movePointX > 0 ? 1 : -1));
            setY(getY() + 50 * (movePointY > 0 ? 1 : -1));
            evadeCd = 1;
            sp -= 10;

            System.out.println((getX() + 50 * (movePointX > 0 ? 1 : -1)) + " " + (getY() + 50 * (movePointY > 0 ? 1 : -1)));
        }
    }

//    public void attack(Entity entity) {
//        if (attackCd == 0) {
//            if (Math.abs(this.getX() - entity.getX()) < 80 && Math.abs(this.getY() - entity.getY()) < 80) {
//                entity.setHp(entity.getHp() - (this.getDamage() - entity.getDefence()));
//            }
//            attackCd = 1;
//        }
//    }

    public void shot(Entity entity) {
        if (attackCd == 0) {
//            float distX = entity.getX() - getX();
//            float distY = entity.getY() - getY();
//
//            Bullet bullet = new Bullet();
//            bullet.setX(this.getX());
//            bullet.setY(this.getY());
//
//            attackCd = 1;
        }
    }

    public void applyHeal() {
        hp += healing;
        healing = 0;
        if (hp > maxHp) hp = maxHp;
    }

    public float getHp() {return hp;}

    public void setHp(int hp) {
        if (hp < 0) hp = 0;
        if (hp > getMaxHp()) hp = getMaxHp();
        this.hp = hp;
    }

    public void setMaxHp(int maxHp) {this.maxHp = maxHp;}
    public int getMaxHp() {return maxHp;}
    public float getSp() {return sp;}

    public void setSp(int sp) {
        if (sp < 0) sp = 0;
        if (sp > getMaxSp()) sp = getMaxSp();
        this.sp = sp;
    }
    public int getMaxSp() {return maxSp;}
    public void setMaxSp(int maxSp) {this.maxSp = maxSp;}
    public int getTookWeight() {return tookWeight;}
    public void setTookWeight(int tookWeight) {this.tookWeight = tookWeight;}
    public int getMaxWeight() {return maxWeight;}
    public float getOverweight() {return Math.max(getTookWeight() - getMaxWeight(), 0);}
    public void setMaxWeight(int maxWeight) {this.maxWeight = maxWeight;}
    public int getDamage() {return damage;}
    public void setDamage(int damage) {this.damage = damage;}
    public int getDefence() {return defence;}
    public void setDefence(int defence) {this.defence = defence;}
    public int getHealing() {return healing;}
    public void setHealing(int healing) {this.healing = healing;}
    public int getAgility() {return agility;}
    public void setAgility(int agility) {this.agility = agility;}
    public float getAttackCd() {return attackCd;}
    public void setAttackCd(float attackCd) {this.attackCd = attackCd;}
    public float getEvadeCd() {return evadeCd;}
    public void setEvadeCd(float evadeCd) {this.evadeCd = evadeCd;}
    public float getSlowTime() {return slowTime;}
    public void setSlowTime(float slowTime) {this.slowTime = slowTime;}
    public boolean isDead() { return dead; }
    public void setDead(boolean dead) { this.dead = dead; }

    public float getMovePointX() {return movePointX;}
    public void setMovePointX(float movePointX) {this.movePointX = movePointX;}
    public float getMovePointY() {return movePointY;}
    public void setMovePointY(float movePointY) {this.movePointY = movePointY;}
}