import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Char {

	public String name;
	public float x;
	public float y;
	public float mapY;
	public boolean isMovingLeft;
	public boolean isMovingRight;
	public boolean isOnGround;
	public SpriteSheet ss;
	public SpriteSheet ss2;
	public Animation walkingRightAnimation;
	public Animation walkingLeftAnimation;
	
	public Rectangle playerBoundingRect;
	public Rectangle playerHitBox;
	public Block[] map;
	public Inventory playerInv;
	
	private float velY;
	
	private int screenHeight;
	private int delta;
	private int timer;
	private int blockBreakTimer;
	private int genX,genY,blockSize;
	
	public Char(String name, terrainGen terGen, float x, float y, int screenHeight) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.screenHeight = screenHeight;
		map = terGen.rects;
		genX = terGen.xGen;
		genY = terGen.yGen;
		blockSize = terGen.grid;
		
		try {
			ss = new SpriteSheet("src/Assets/HumanWalkCaucasian.png", 32, 64);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		try {
			ss2 = new SpriteSheet("src/Assets/HumanWalkCaucasianFlip.png", 32, 64);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		walkingRightAnimation = new Animation(ss, 100);
		walkingLeftAnimation = new Animation(ss2, 100);
		
		playerBoundingRect = new Rectangle(x,y, 26, 56);
		playerHitBox = new Rectangle(x,y,1,1);
		playerInv = new Inventory();
		mapY = -336 / 2;
	}
	
	public void falling() {
		if(!isBlocked(x, y + velY + 1f)){
			timer += delta;
			
			if(velY < 7){
				if(timer > 1 * 50){
					velY = velY += 1f;
					timer = 0;
				}
			}
			mapY += velY;
			isOnGround = false;
		}else{
			velY = 0;	
			isOnGround = true;
		}
	}

	public void movement(Input input, int delta, Block[] map) {
		this.map = map;
		int MOUSE = Input.MOUSE_LEFT_BUTTON;
		
		int KEYD = Input.KEY_D;
		int KEYRIGHT = Input.KEY_RIGHT;
		
		int KEYA = Input.KEY_A;
		int KEYLEFT = Input.KEY_LEFT;
		
		int KEYSPACE = Input.KEY_SPACE;
		int KEYW = Input.KEY_W;
		int KEYUP = Input.KEY_UP;
		
		int KEYDOWN = Input.KEY_DOWN;
		
		int jumpHeight = -8;
	
		this.delta = delta;
		
		//MOVEMENT
		if((input.isKeyDown(KEYSPACE) || input.isKeyDown(KEYW)) && !isBlocked(x, y + jumpHeight) && isOnGround == true){
			velY = jumpHeight;
		}
		
		
		if((input.isKeyDown(KEYSPACE) || input.isKeyDown(KEYW)) && (input.isKeyDown(KEYD)) && !isBlocked(x + 5, y + jumpHeight) && isOnGround == true){
			velY = jumpHeight;
			x+= 5;
		}else if (input.isKeyDown(KEYD)) {
			if(!isBlocked(x + 5, y)){
				x += 5;

				isMovingRight = true;
				isMovingLeft = false;
			}else if(!isBlocked(x + 4, y)){
				x += 4;

				isMovingRight = true;
				isMovingLeft = false;
			}else if(!isBlocked(x + 3, y)){
				x += 3;

				isMovingRight = true;
				isMovingLeft = false;
			}else if(!isBlocked(x + 2, y)){
				x += 2;

				isMovingRight = true;
				isMovingLeft = false;
			}else if(!isBlocked(x + 1, y)){
				x += 1;

				isMovingRight = true;
				isMovingLeft = false;
			}
			
		}else if((input.isKeyDown(KEYSPACE) || input.isKeyDown(KEYW)) && (input.isKeyDown(KEYA)) && !isBlocked(x - 5, y + jumpHeight) && isOnGround == true){
			velY = jumpHeight;
			x-= 5;
		}else if (input.isKeyDown(KEYA)) {
			if(!isBlocked(x - 5, y)){
				x -= 5;

				isMovingRight = false;
				isMovingLeft = true;
			}else if(!isBlocked(x - 4, y)){
				x -= 4;

				isMovingRight = false;
				isMovingLeft = true;
			}else if(!isBlocked(x - 3, y)){
				x -= 3;

				isMovingRight = false;
				isMovingLeft = true;
			}else if(!isBlocked(x - 2, y)){
				x -= 2;

				isMovingRight = false;
				isMovingLeft = true;
			}else if(!isBlocked(x - 1, y)){
				x -= 1;

				isMovingRight = false;
				isMovingLeft = true;
			}
		}else{
			isMovingRight = false;
			isMovingLeft = false;
		}
		
		//SANDBOX
		
		if(input.isMouseButtonDown(MOUSE)){
			destroy(input.getMouseX(), input.getMouseY());
		}
		
		/*if(input.isKeyDown(KEYUP)){
			destroy(x,y - 32);
		}
		if(input.isKeyDown(KEYDOWN)){
			destroy(x,y + 64);
		}
		if(input.isKeyDown(KEYLEFT)){
			destroy(x - 32,y + 32);
		}
		if(input.isKeyDown(KEYRIGHT)){
			destroy(x + 32,y + 32);
		}*/
		
		falling(); 
	}
	
	
	
	private void destroy(int x, int y) {
        /*playerHitBox.setLocation(x + 8,y + mapY); 
        int startY =  (Math.round(mapY / blockSize))* rowCount + 1;          
		int endY = (Math.round((mapY + screenHeight) / blockSize) - 1)* rowCount;
		if(startY < 0) startY = 0;
		if(endY > map.length) endY = map.length;
        
		for(int i = startY; i < endY; i++){
			if(playerHitBox.intersects(map[i]) && map[i].type != 0){
				playerInv.addItem(map[i].type);	
				map[i].type = 0;
			}
		}*/
		int mouseX = x;
		int mouseY = y;
		
		int rowCount = genX / blockSize;
		int startY =  (Math.round(mapY / blockSize))* rowCount + 1;          
		int endY = (Math.round((mapY + screenHeight) / blockSize) - 1)* rowCount;
		
		if(startY < 0) startY = 0;
		if(endY > map.length) endY = map.length;
		
		playerHitBox.setLocation(mouseX, mouseY + mapY);
		for(int i = startY; i < endY; i++){
			if(playerHitBox.intersects(map[i]) && map[i].type != 0){
				blockBreakTimer += delta;
				if(blockBreakTimer > map[i].breakTime){
					playerInv.addItem(map[i].type);
					map[i].type = 0;
					blockBreakTimer = 0;
				}
			}
		}
    }
	
	private boolean isBlocked(float x, float y) {
        boolean blocked = false;
        
        float tweakedX = x + 4;
        float tweakedY = y + 8 + mapY;
        int rowCount = genX / blockSize;
        int startY =  (Math.round(mapY / blockSize))* rowCount + 1;          
		int endY = (Math.round((mapY + screenHeight) / blockSize) - 1)* rowCount;

		if(startY < 0) startY = 0;
		if(endY > map.length) endY = map.length;
        
        playerBoundingRect.setLocation(tweakedX,tweakedY);
		for(int i = startY; i < endY; i++){
				if(playerBoundingRect.intersects(map[i])){
					if(map[i].type != 0){
						blocked = true;
					}
				}
		}
		return blocked;
    }
}