import org.newdawn.slick.Color;



 
public class terrainGen {
	public Block[] rects;
	public boolean finishedGen = false;
	
	//BLOCKS
	public final int AIR = 0;
	public final int DIRT = 1;
	public final int GRASS = 2;

	public final int TNT = 3;
	public final int JOBBY = 4;
	float[] x;
	float[] y;
	public void setup(){	
		x = new float[256];
		y = new float[256];
		
		rects = new Block[256];  
		
		
		
		Thread t2 = new Thread(){
			public void run(){
				int grid = 64;
				
				int i = 0;
				for(int x = 0; x < 1080; x += grid){
					for(int y = 0; y < 1080; y += grid){
						if(y <= 128 + 64){
							rects[i] = new Block(x,y,64,64,AIR);
							i++;
							if(i > rects.length) break;
						}else{	
							int randBlock = (int) Math.round(Math.random() * JOBBY);
							rects[i] = new Block(x,y,64,64,randBlock);
							i++;
							if(i > rects.length) break;
						}
					}
				}
				
				finishedGen = true;
			}
			
		};t2.start();
		
	}
	
	public Color BlockColor(int type){
		Color c = null;
		 
		
		if(type == DIRT) c = RRGGBB.gray;
		if(type == GRASS)c = RRGGBB.green;
		if(type == AIR)c = RRGGBB.transparent;
		if(type == TNT)c = RRGGBB.red;
		if(type == JOBBY)c = new RRGGBB(89,48,1);
		
		return c;
	}
}
