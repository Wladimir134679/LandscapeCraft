package lc.world.objectes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.nio.ByteBuffer;
import lc.item.block.Block;
import lc.item.block.TypeBlock;
import lc.world.Ground;
import lc.world.World;

/**
 * @author Death
 */
public class WorldObject{

    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, VERTICALLY = 4, HORIZONTAL = 5; //Направления
    
    
    protected World world = null; // Мир игровой
    protected Ground ground = null; // С чем проверяем столкновение
    protected final Rectangle rect; // Для getSize()
    protected boolean accessoryFlag1; // Вспомогательные флаги
    
//    public CollisionGround collisionGround = null; // Это нужно было при начале создании игры, но уже это лишниее...
//    public CollisionGroundExpose groundExpose = null; // ...убераем.
    public boolean onLand, freeSide;
    
    public int dirX, dirY;
    public float x, y, speedX, speedY;
    private float deltaX, deltaY;
    public float maxSpeedX, maxSpeedY;
    public float width, height;
    public float heightJump;
    public float beginHeightJump;
    public float acceleration; // Ускорение
    public double powAcceleration; // Степень сукорения
    public double strengthJump; // Сила прыжка
    public double strengthGravity; // Сила Гравитации
    public double maxSpeedGravity; // Максемальная скорость Гравитации
    public boolean jump;
    public boolean gravity;
    public boolean autoBrakeX, autoBrakeY;
    public boolean[] moveKey;
    public boolean[] moveDir;
    public Sprite sprite = null;
    public TypeObjectWorld type;
    
    public int group = 0; // Для игры, группы объектов
    
    private float beginFall = 0;
    
    public WorldObject(){
        this.rect = new Rectangle();
        this.type = TypeObjectWorld.STATIC; // В игре ещё не принимается STATIC, сейчас только игрок и предметы DYNAMIC
        this.moveKey = new boolean[4]; // Куда мы движемся, при зажатии клавиши
        this.moveDir = new boolean[4]; // Направление, куда "смотрит" объект, движется
        this.maxSpeedX = 8;
        this.maxSpeedY = 8;
        this.gravity = false;
        this.onLand = false;
        this.freeSide = true;
        this.acceleration = 0.25f;
        this.powAcceleration = 1.1;
        this.strengthJump = 6.5; // =/ при нажатии на SPACE, объекту даётся это ускорение вверх, и потом под силой гравитации он летит вниз
        this.strengthGravity = 0.25f;
        this.maxSpeedGravity = this.maxSpeedY * 2; //Скорость падения = максимальная скорость по Y умножается на два =D
        this.beginHeightJump = 0;
        this.heightJump = Block.SIZE * 4.5f; // Высота прыжка
        this.jump = false;
        this.autoBrakeX = false; // Затухание движения по X
        this.autoBrakeY = false; // Аналогично
        
        this.accessoryFlag1 = false; // Вспомогательный флаг
    }
    
    public void draw(SpriteBatch b){
        if(sprite == null) return;
        sprite.setPosition(x, y);
        sprite.setSize(width, height);
        sprite.draw(b);
    }
    
    public Rectangle getRect(){
        return rect.set(x, y, width, height);
    }
    
    public void initPhysics(World map){
        this.initPhysics(map, true); // Добавляем в мир объект
    }
    
    public void initPhysics(World map, boolean g){ // Этот отдельно от initPhysics(World) вызывается
        this.gravity = g;
        this.world = map;
        this.ground = map.ground;
    }
    
    public void move(){ // При обновлении мира, вызывается у всех объектов этот метод. Логичнее его назвать update
        deltaX = x;
        deltaY = y;
        if(moveKey[RIGHT]){//Если движение, то ускоряем(!) до max скорости
            speedX += this.voile();
            moveDir[RIGHT] = true;
            if(speedX > maxSpeedX) speedX = maxSpeedX;
        }else{
            if(moveDir[RIGHT]){// Останавливаем объект, если нет команды движения
                speedX -= this.brake();
                if(speedX < 0){
                    speedX = 0;
                    moveDir[RIGHT] = false;
                }
            }
        }
        
        if(moveKey[LEFT]){// Аналогично
            speedX -= this.voile();
            moveDir[LEFT] = true;
            if(speedX < -maxSpeedX) speedX = -maxSpeedX;
        }else{
            if(moveDir[LEFT]){
                speedX += this.brake();
                if(speedX > 0){
                    speedX = 0;
                    moveDir[LEFT] = false;
                }
            }
        }
        
        if(autoBrakeX){// Остановка объекта
            if(speedX < 0.0){ // Если движение в лево
                if(onLand) // На земле быстрее остановка
                    speedX += this.voile(20);
                else
                    speedX += this.voile(5); // В воздухе
                if(speedX > 0.0) speedX = 0;
            }else if(speedX > 0.0){ // Аналогично
                if(onLand)
                    speedX -= this.voile(20);
                else
                    speedX -= this.voile(5);
                if(speedX < 0.0) speedX = 0;
            }
        }
        
        this.gravity();
        
        this.moveY(speedY); // Смещаем на скорость
        this.moveX(speedX); // Так же
        
        if(onLand) this.moveY(-1); // Проверка на землю, если на земле =).
    }
    
    public float getDeltaX(){
        return deltaX - x;
    }
    
    public float getDeltaY(){
        return deltaY - y;
    }
    
    private void gravity(){ // Графитация, только падение
        if(jump){
            if(speedY <= 0){
                jump = false;
                return;
            }
            if(this.y > this.beginHeightJump + heightJump){
                speedY -= (strengthGravity * 2);
            }else{
                speedY -= (strengthGravity / 4);
            }
            return;
        }else{
            if(speedY > 0){
                if(this.y > this.beginHeightJump + Block.SIZE * 2){
                    speedY -= (strengthGravity * 3);
                    return;
                }else{
                    speedY -= (strengthGravity / 4);
                }
            }
        }
        if(!this.gravity || onLand) return;
        if(beginFall == 0){
            if(speedY < 0)
                beginFall = y;
        }
        
        speedY -= strengthGravity;
        if(speedY > maxSpeedGravity) speedY = (int)-maxSpeedGravity;
    }
    
    public void jump(boolean flag){ // Вызывается при нажатии на пробел
        jump = flag;
        if(!onLand) return;// Если не земля то не даём прыгнуть
        if(jump){
            beginHeightJump = this.y;
            speedY = (float)strengthJump;
        }
    }
    
    public boolean moveX(float speed){
        if(speed == 0) return false;
        if(speed > 0) dirX = RIGHT;
        else dirX = LEFT;
        if(Math.abs(speed) > Block.SIZE)
            return this.superMoveX(Math.round(speed)); // Если скорость больше одного блока, то...
        this.x = this.x + Math.round(speed);
        return collisionGround(speed, HORIZONTAL);
    }
    public boolean moveY(float speed){
        if(speed == 0) return false;
        if(speed > 0) dirY = UP;
        else dirY = DOWN;
        if(Math.abs(speed) > Block.SIZE)
            return this.superMoveY(Math.round(speed));
        this.y = this.y + Math.round(speed);
        return collisionGround(speed, VERTICALLY);
    }
    
    private boolean superMoveX(int speed){ 
//...разбиваем скорость на расстояние в блоки и перемещаем по блокам, 
//чтобы при скорости в 3 блока и при размере в полублок, не проскочить несколько блоков,
//то есть не пройти сквозь стену
        int sizeCell = this.getSizeBlock();
        int sx = Math.abs(speed);
        int vx = sx / speed;
        while(sx > sizeCell){
            this.x = this.x + Block.SIZE * vx;
            sx -= sizeCell;
            accessoryFlag1 = collisionGround(speed, HORIZONTAL);
            if(speed == 0){
                sx = 0;
                break;
            }
            if(accessoryFlag1){
                return true;
            }
        }
        this.x = this.x + sx * vx;
        return collisionGround(speed, HORIZONTAL);
    }
    
    private boolean superMoveY(int speed){
        int sizeCell = this.getSizeBlock();
        int sy = Math.abs(speed);
        int vy = sy / speed;
        while(sy > sizeCell){
            this.y = this.y + sizeCell * vy;
            sy -= sizeCell;
            accessoryFlag1 = collisionGround(speed, VERTICALLY);
            if(speed == 0){
                sy = 0;
                break;
            }
            if(accessoryFlag1){
                return true;
            }
        }
        this.y = this.y + sy * vy;
        return collisionGround(speed, VERTICALLY);
    }
    
    public boolean collisionGround(float speed, int dir){ // Столкновение со слоем "земли", грунта
        if(world == null || ground == null) return false;
        if(collisionBorder()) return true;
        // Перебераем циклами все ячейки, которые задевает объект
        for(int i = (int)(this.x / this.getSizeBlock()); i < Math.floor(this.x + this.width) / this.getSizeBlock(); i++){
            for(int j = (int)(this.y / this.getSizeBlock()); j < Math.floor(this.y + this.height) / this.getSizeBlock(); j++){
                boolean collision = ground.getBlock(i, j) != null; // true, если блок
                if(collision)
                    if(ground.getBlock(i, j).type == TypeBlock.PASSABLE) continue; // Пропускаем ячейку, так как она проходима на скозь
                //if(collision && groundExpose != null)
                //    collision = groundExpose.expose(ground.getBlock(i, j), i, j); // Интерфейс возвращает возможность столкновения
                if(collision){
                    int dirColl = -1;
                    // Если команда, проверять по горизонтали
                    if(dir == HORIZONTAL){
                        if(speed > 0){
                            this.x = i * this.getSizeBlock() - this.width;
                            dirColl = RIGHT;
                            moveDir[RIGHT] = false;
                        }else if(speed < 0){
                            this.x = i * this.getSizeBlock() + this.getSizeBlock();
                            dirColl = LEFT;
                            moveDir[LEFT] = false;
                        }
                        speedX = 0;
                    }else if(dir == VERTICALLY){
                        if(speed > 0){
                            this.y = j * this.getSizeBlock() - this.height;
                            dirColl = UP;
                            moveDir[UP] = false;
                        }else if(speed < 0){
                            if(beginFall != 0) this.fall(beginFall - this.y);
                            beginFall = 0;
                            this.onLand = true;
                            this.y = j * this.getSizeBlock() + this.getSizeBlock();
                            dirColl = DOWN;
                            moveDir[DOWN] = false;
                        }
                        speedY = 0;
                    }
                    //if(dirColl != -1 && collisionGround != null){
                    //    collisionGround.collision(i, j, dirColl); // Отправляем интерфейсу индекс ячейки и направление "вхождение" в неё
                    //}
                    return true;
                }
            }
        }
        if(dir == VERTICALLY){// Если проверяем по вертикали, то не на блоке мы
            this.onLand = false;
        }
        return false;
    }
    
    public boolean isCollisionBlock(int dx, int dy){//Сталкивается ли с ячекой объект
        int xID = (int)Math.floor((this.x + dx) / Block.SIZE);
        int yID = (int)Math.floor((this.y + dy) / Block.SIZE);
        if(ground.getBlock(xID, yID) != null){ // Если есть блок
            return ground.getBlock(xID, yID).type != TypeBlock.PASSABLE; // И если он не проходимый, то true
        }
        return false;
    }
    public void fall(float heightFall){} // Для наследников, отбработка высоты падения
    
    private boolean collisionBorder(){// Столкновения с границей мира
        if(this.x < 0){
            this.x = 0;
            return true;
        }
        if(this.y < 0){
            this.y = 0;
            this.onLand = true;
            return true;
        }
        if(this.x + this.width > world.getWidth()){
            this.x = world.getWidth() - this.width;
            return true;
        }
        if(this.y + this.height > world.getHeight()){
            this.y = world.getHeight() - this.height;
            return true;
        }
        return false;
    }
    
    public float getCenterXId(){
        return (x + width / 2) / Block.SIZE;
    }
    
    public float getCenterYId(){
        return (y + height / 2) / Block.SIZE;
    }
    
    public int getSizeBlock(){
        return Block.SIZE;
    }
    
    private float voile(){// Ускорение в бока, возарвщение числа, которое прибавляется каждый тик, при ускорении
        return voile(acceleration);
    }
    private float voile(float acc){// Общее скорение, acc - перемнная, которая возводится в степень powAcceleration
        return (float)Math.pow(acc, this.powAcceleration);
    }
    private float brake(){// Тормажение в бока
        return brake(acceleration);
    }
    private float brake(float acc){// Общее тормажение, смысли тот же что и в voile, но это число от скорости за тик отнимается и оно в два раза сильнее. От маленького значения powAcceleration будет создоваться чувство скольжения
        return (float)Math.pow(acc * 2, this.powAcceleration);
    }
    
    public byte[] getData(){ // Возвращение данных объекта мира в байтах
        byte[] data = ByteBuffer.allocate(100)
                .putInt((int)x)
                .putInt((int)y)
                .putInt((int)width)
                .putInt((int)height)
                .putFloat(heightJump)
                .putFloat(maxSpeedX)
                .putFloat(maxSpeedY)
                .array();
        return data;
    }
    
    public void setData(byte[] data){ // Разбираем данные в объект
        ByteBuffer buf = ByteBuffer.wrap(data);
        x = buf.getInt();
        y = buf.getInt();
        width = buf.getInt();
        height = buf.getInt();
        heightJump = buf.getFloat();
        maxSpeedX = buf.getFloat();
        maxSpeedY = buf.getFloat();
    }
}
