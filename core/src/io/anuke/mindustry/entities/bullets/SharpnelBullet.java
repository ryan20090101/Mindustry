package io.anuke.mindustry.entities.bullets;

import io.anuke.mindustry.entities.Bullet;
import io.anuke.mindustry.entities.BulletType;
import io.anuke.ucore.graphics.Draw;

public class SharpnelBullet extends BulletType {

    protected int sharpnels = 8;

    public SharpnelBullet(float speed, int damage,String name){
        super(speed,damage,name);
    }

    public void draw(Bullet b){
        Draw.rect(b.name, b.x, b.y, b.angle());
        Draw.reset();
    }

    public void hit(Bullet b, float hitx, float hity) {
        for(int i = 0; i < sharpnels; i ++){
            Bullet bullet = new Bullet(blueShard, b.owner, b.x, b.y, 360/sharpnels*i);
            bullet.add();
        }
    }
}
