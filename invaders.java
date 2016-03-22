
import javax.swing.*;
import java.awt.*;
    
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.awt.image.BufferedImage;

import javax.sound.sampled.*;
import java.io.File;
import java.lang.System;

//###############################################################
abstract class GameObject
{
    public static World ItsWorld;
    public static int ScreenWidth = 640;
    public static int ScreenHeight = 480;
    
    public static int CellWidthPixels = 10;
    public static int CellHeightPixels = 10;
    public static int GridWidth = ScreenWidth/CellWidthPixels;
    public static int GridHeight = ScreenHeight/CellHeightPixels;   
    
    public static long KeyPressDelay = 0;
    public static long KeyPressDelayMax = 100000;    
    
    public static final float pi = 3.141594f;
    public static final Random Rand = new Random();
    protected float x, y;
    protected float Angle;
    public int ActiveFlag = 1;
    
    public int DrawLayer = 0;
    public static final int DRAW_LAYER_MAX = 4;
    
    public static int vk_up = 0, vk_down = 0, vk_left = 0, vk_right = 0, vk_r = 0, vk_f = 0, vk_enter = 0;
    
    public final static int ID_NOTHING = 0, ID_INFORMATION_BAR = 1, ID_SNAKE_HEAD = 2, ID_SNAKE_SEGMENT = 3,
                    ID_PLAY_FIELD = 4;
    public final static int ID_SHIP = 10, ID_PROJECTILE = 11, ID_ALIEN_SHIP = 12;
                    
    public int Id;
    
    public void Do() {};
    public void Draw( Graphics g ) {};
//---------------------------------------------------------------    
    protected void DrawOvalCenter( Graphics g, int x, int y, int width, int height )
    {
       g.drawOval( x - width/2, y - height/2, width, height );
    }
//---------------------------------------------------------------    
    protected void DrawFilledRect( Graphics g, int x, int y, int width, int height, Color c )
    {
       g.setColor(c);
       g.fillRect( x, y, width, height );
    }
//--------------------------------------------------------------- 
    public static int RandNum( int lower, int upper )
    {
      return Rand.nextInt(upper-lower+1) + lower;
    }   
//--------------------------------------------------------------- 
};        
//###############################################################
class SowBugScript
{
public final static int

DO_NOTHING = 0,
GOTO_LINE_CONST = 1,
COPY_VAR_VAR = 2,
COPY_VAR_CONST = 3,
ADD_VAR_VAR = 4,
ADD_VAR_CONST = 5,
MULT_VAR_VAR = 6,
MULT_VAR_CONST = 7,
COS_VAR = 8,
SIN_VAR = 9,
RANDOM_VAR_VAR_VAR = 10,
IF_VAR_EQUAL_CONST = 11,
IF_VAR_LESS_OR_EQUAL_THAN_CONST = 12,
IF_VAR_GREATER_OR_EQUAL_THAN_CONST = 13,
IF_VAR_EQUAL_VAR = 14,
IF_VAR_LESS_OR_EQUAL_THAN_VAR = 15,
IF_VAR_GREATER_OR_EQUAL_THAN_VAR = 16,
CALL_FUNCTION = 17,
END_FRAME = 18,
END_SCRIPT = 19;

public final static int

VAR_A = 0, VAR_B = 1, VAR_C = 2, VAR_D = 3, VAR_E = 4, VAR_F = 5,
VAR_G = 6, VAR_H = 7, VAR_I = 8, VAR_J = 9, VAR_K = 10, VAR_L = 11,
VAR_M = 12, VAR_N = 13, VAR_O = 14, VAR_P = 15, VAR_Q = 16, VAR_R = 17,
VAR_S = 18, VAR_T = 19, VAR_U = 20, VAR_V = 21, VAR_W = 22, VAR_X = 23, VAR_Y = 24, VAR_Z = 25;

public final static int MAX_VARIABLES = 26;

public float[] varlist = new float[MAX_VARIABLES];

public final static int MAX_INSTRUCTIONS = 100;

public float[] list = new float[MAX_INSTRUCTIONS * 4];

public int CurrentIndex = 0;

protected static final Random Rand = new Random();

//--------------------------------------------------------------------------
public static int RandNum( int lower, int upper )
{
   return Rand.nextInt(upper-lower+1) + lower;
}   
//--------------------------------------------------------------------------
public void set( int index, int instruction_id, float arg1, float arg2, float arg3 )
{
   list[index * 4] = (float)instruction_id;
   list[index * 4 + 1] = arg1;
   list[index * 4 + 2] = arg2;
   list[index * 4 + 3] = arg3;
}
//--------------------------------------------------------------------------
protected void ProcessScript()
{
   int i, k, breakflag = 0;
   int var_index_1, var_index_2, var_index_3; 
   float const_1, const_2, const_3;
   
   for( i = CurrentIndex; i < MAX_INSTRUCTIONS; )
   {
      k = (int)list[i * 4];
      
      switch(k)
      {
        case DO_NOTHING:
          i++;
          break;
        case GOTO_LINE_CONST:
          i = (int)list[i * 4 + 1]; 
          break;
        case COPY_VAR_VAR:
          var_index_1 = (int)list[i * 4 + 1];
          var_index_2 = (int)list[i * 4 + 2];
          varlist[var_index_1] = varlist[var_index_2];
          i++;
          break;
        case COPY_VAR_CONST:
          var_index_1 = (int)list[i * 4 + 1];
          const_1 = list[i * 4 + 2];
          varlist[var_index_1] = const_1;
          i++;        
          break;
        case ADD_VAR_VAR:
          var_index_1 = (int)list[i * 4 + 1];
          var_index_2 = (int)list[i * 4 + 2];
          varlist[var_index_1] += varlist[var_index_2];
          i++;        
          break;
        case ADD_VAR_CONST:
          var_index_1 = (int)list[i * 4 + 1];
          const_1 = list[i * 4 + 2];
          varlist[var_index_1] += const_1;
          i++;        
          break;
        case MULT_VAR_VAR:
          var_index_1 = (int)list[i * 4 + 1];
          var_index_2 = (int)list[i * 4 + 2];
          varlist[var_index_1] *= varlist[var_index_2];
          i++;        
          break;
        case MULT_VAR_CONST:
          var_index_1 = (int)list[i * 4 + 1];
          const_1 = list[i * 4 + 2];
          varlist[var_index_1] *= const_1;
          i++;        
          break;        
        case COS_VAR:
          var_index_1 = (int)list[i * 4 + 1];
          varlist[var_index_1] = (float)Math.cos( varlist[var_index_1] );
          i++;
          break;
        case SIN_VAR:
          var_index_1 = (int)list[i * 4 + 1];
          varlist[var_index_1] = (float)Math.sin( varlist[var_index_1] );
          i++;
          break;        
        case RANDOM_VAR_VAR_VAR:
          var_index_1 = (int)list[i * 4 + 1];
          var_index_2 = (int)list[i * 4 + 2];
          var_index_3 = (int)list[i * 4 + 3];
          varlist[var_index_1] = RandNum( (int)varlist[var_index_2], (int)varlist[var_index_3] );
          i++;
          break;
        case IF_VAR_EQUAL_CONST:
          var_index_1 = (int)list[i * 4 + 1];
          const_1 = list[i * 4 + 2];

          if( varlist[var_index_1] == const_1 )
              i++;
          else
              i = i + 2;
          break;
        case IF_VAR_LESS_OR_EQUAL_THAN_CONST:
          var_index_1 = (int)list[i * 4 + 1];
          const_1 = list[i * 4 + 2];

          if( varlist[var_index_1] <= const_1 )
              i++;
          else
              i = i + 2;
          break;        
        case IF_VAR_GREATER_OR_EQUAL_THAN_CONST:   
          var_index_1 = (int)list[i * 4 + 1];
          const_1 = list[i * 4 + 2];

          if( varlist[var_index_1] >= const_1 )
              i++;
          else
              i = i + 2;
          break;   
        case IF_VAR_EQUAL_VAR:
          var_index_1 = (int)list[i * 4 + 1];
          var_index_2 = (int)list[i * 4 + 2];

          if( varlist[var_index_1] == varlist[var_index_2] )
              i++;
          else
              i = i + 2;
          break;          
        case IF_VAR_LESS_OR_EQUAL_THAN_VAR:
          var_index_1 = (int)list[i * 4 + 1];
          var_index_2 = (int)list[i * 4 + 2];

          if( varlist[var_index_1] <= varlist[var_index_2] )
              i++;
          else
              i = i + 2;
          break;          
        case IF_VAR_GREATER_OR_EQUAL_THAN_VAR:
          var_index_1 = (int)list[i * 4 + 1];
          var_index_2 = (int)list[i * 4 + 2];

          if( varlist[var_index_1] >= varlist[var_index_2] )
              i++;
          else
              i = i + 2;
          break;          
        case CALL_FUNCTION:
          const_1 = list[i * 4 + 1];
          CallFunction( (int)const_1 );
          i++;
          break;
        case END_FRAME:          
          i++;
          breakflag = 1;
          break;
        case END_SCRIPT:   
          breakflag = 1; 
          break;
        default:
          i++;
          break;
      }
      
      if( breakflag >= 1 )
          break;
   }
   
   CurrentIndex = i;
 
}
//--------------------------------------------------------------------------
public void Init()
{
}
//--------------------------------------------------------------------------
public void CallFunction( int id )
{
}
//--------------------------------------------------------------------------
public void Do()
{
  ProcessScript();
}
//--------------------------------------------------------------------------
}
//###############################################################
class InvaderPath extends SowBugScript
{
public float[] xy = new float[2];
public float[] speed = new float[1];
public final static int DOWNWARD_COUNTER = InformationBar.ALIEN_SHIP_Y_SPEED;
public static int SpeedIncrease = 0;

//--------------------------------------------------------------------------
public InvaderPath()
{
  speed[0] = 2;
}
//--------------------------------------------------------------------------
public void Init()
{   
 set( 0, COPY_VAR_CONST, VAR_A, 200, 0 );
 set( 1, ADD_VAR_VAR, VAR_A, VAR_N, 0 );
 set( 2, ADD_VAR_VAR, VAR_X, VAR_P, 0 ); 
 set( 3, END_FRAME, 0, 0, 0 );
 set( 4, IF_VAR_GREATER_OR_EQUAL_THAN_CONST, VAR_A, 1, 0 );
 set( 5, GOTO_LINE_CONST, 1, 0, 0 );
 
 set( 6, COPY_VAR_CONST, VAR_A, DOWNWARD_COUNTER, 0 );
 set( 7, ADD_VAR_VAR, VAR_A, VAR_N, 0 );
 set( 8, ADD_VAR_VAR, VAR_Y, VAR_P, 0 ); 
 set( 9, END_FRAME, 0, 0, 0 );
 set( 10, IF_VAR_GREATER_OR_EQUAL_THAN_CONST, VAR_A, 1, 0 );
 set( 11, GOTO_LINE_CONST, 7, 0, 0 );
 
 set( 12, COPY_VAR_CONST, VAR_A, 400, 0 );
 set( 13, ADD_VAR_VAR, VAR_A, VAR_N, 0 );
 set( 14, ADD_VAR_VAR, VAR_X, VAR_N, 0 ); 
 set( 15, END_FRAME, 0, 0, 0 );
 set( 16, IF_VAR_GREATER_OR_EQUAL_THAN_CONST, VAR_A, 1, 0 );
 set( 17, GOTO_LINE_CONST, 13, 0, 0 ); 
 
 set( 18, COPY_VAR_CONST, VAR_A, DOWNWARD_COUNTER, 0 );
 set( 19, ADD_VAR_VAR, VAR_A, VAR_N, 0 );
 set( 20, ADD_VAR_VAR, VAR_Y, VAR_P, 0 ); 
 set( 21, END_FRAME, 0, 0, 0 );
 set( 22, IF_VAR_GREATER_OR_EQUAL_THAN_CONST, VAR_A, 1, 0 );
 set( 23, GOTO_LINE_CONST, 19, 0, 0 );
 
 set( 24, COPY_VAR_CONST, VAR_A, 400, 0 );
 set( 25, ADD_VAR_VAR, VAR_A, VAR_N, 0 );
 set( 26, ADD_VAR_VAR, VAR_X, VAR_P, 0 ); 
 set( 27, END_FRAME, 0, 0, 0 );
 set( 28, IF_VAR_GREATER_OR_EQUAL_THAN_CONST, VAR_A, 1, 0 );
 set( 29, GOTO_LINE_CONST, 25, 0, 0 ); 
 set( 30, GOTO_LINE_CONST, 6, 0, 0 );

}
//--------------------------------------------------------------------------
public void Do()
{
 varlist[VAR_X] = xy[0];
 varlist[VAR_Y] = xy[1];
 varlist[VAR_N] = -(speed[0] + SpeedIncrease);
 varlist[VAR_P] = speed[0] + SpeedIncrease;
 
 ProcessScript();
 
 xy[0] = varlist[VAR_X];
 xy[1] = varlist[VAR_Y];
}
//--------------------------------------------------------------------------
}
//###############################################################
class Ship extends GameObject
{
   protected Color ItsColor;
   protected int LeftXWall, RightXWall;   
   protected int StartX, StartY;
   protected int[] VertexListX, VertexListY;
   public final static int MAX_VERTICES = 3;
   protected int Radius;
   
//---------------------------------------------------------------  
public Ship()
{
   Id = ID_SHIP;
   LeftXWall = (int)(ScreenWidth * 0.05f);
   RightXWall = (int)(ScreenWidth - ScreenWidth * 0.05f);
   StartX = ScreenWidth/2;
   StartY = (int)(ScreenHeight - ScreenHeight * 0.1f);
   Radius = CellWidthPixels;
   
   ItsColor = new Color( 255, 0, 0 );
   
   VertexListX = new int[MAX_VERTICES];
   VertexListY = new int[MAX_VERTICES];
   
   x = StartX; y = StartY;
   
   UpdateVertices();
}
//---------------------------------------------------------------
public void UpdateVertices()
{
   VertexListX[0] = (int)x + 0; VertexListY[0] = (int)y - Radius;
   VertexListX[1] = (int)x - Radius; VertexListY[1] = (int)y + Radius;
   VertexListX[2] = (int)x + Radius; VertexListY[2] = (int)y + Radius;
}
//---------------------------------------------------------------
public void Do()
{
 if( System.nanoTime() - KeyPressDelay > KeyPressDelayMax )
 {
   if( vk_left == 1 )
   {
       if( x >= LeftXWall )
           x -= 3;   
       KeyPressDelay = System.nanoTime(); 
       UpdateVertices();
   } 
   else
   if( vk_right == 1 )   
   {  
       if( x <= RightXWall )
           x += 3;
       KeyPressDelay = System.nanoTime(); 
       UpdateVertices();
   }   
   
   if( vk_f == 1 )
   {   
      World.me.AddGameObject( new Projectile( (int)x, (int)y, 3, 0.0f, -10.0f , 40 ));
      KeyPressDelay = System.nanoTime();
   }   
 }
}
//---------------------------------------------------------------   
public void Draw( Graphics g )
{    
  g.setColor( ItsColor );
  g.drawLine( VertexListX[0], VertexListY[0], VertexListX[1], VertexListY[1] );
  g.drawLine( VertexListX[0], VertexListY[0], VertexListX[2], VertexListY[2] );
  g.drawLine( VertexListX[2], VertexListY[2], VertexListX[1], VertexListY[1] );
}
//---------------------------------------------------------------  
}
//###############################################################
class Projectile extends GameObject
{
   protected Color ItsColor;
   protected int Radius = 3;
   protected float SpeedX = 0.0f;
   protected float SpeedY = -4.0f;
   protected int LifeSpanTicks = 50;
   public static int TotalProjectileCount = 0;
   public static final int MAX_PROJECTILES = 10;
   
//---------------------------------------------------------------  
public Projectile( int nStartX, int nStartY, int nRadius, float fSpeedX, float fSpeedY, int nLifeSpanTicks )
{
   Id = ID_PROJECTILE;
   x = (float)nStartX; y = (float)nStartY;
   Radius = nRadius;
   SpeedX = fSpeedX; SpeedY = fSpeedY;
   LifeSpanTicks = nLifeSpanTicks;
   
   ItsColor = new Color( 255, 200, 200 );
   TotalProjectileCount += 1;
   
   if( TotalProjectileCount > MAX_PROJECTILES )
   {
     TotalProjectileCount = MAX_PROJECTILES;       
     ActiveFlag = 0;
   }
   
}
//---------------------------------------------------------------
public void Do()
{
   x += SpeedX; y += SpeedY;
   
   if( LifeSpanTicks <= 0 )
   {
       ActiveFlag = 0;
       
       if( TotalProjectileCount > 0 )
           TotalProjectileCount -= 1;          
       return;
   }
   
   LifeSpanTicks--;   
   CheckCollide();
}
//---------------------------------------------------------------
public void Draw( Graphics g )
{  
   g.setColor( ItsColor );
   g.drawOval( (int)x - Radius, (int)y - Radius,  Radius, Radius );
}
//---------------------------------------------------------------
protected void CheckCollide()
{
  int i;
  
  GameObject[] objlist = World.me.GetGameObjectList();   
  AlienShip as_obj; 
  
  for( i = 0; i < objlist.length; i++ )
    if( objlist[i] != null )
     if( objlist[i].ActiveFlag >= 1 && objlist[i].Id == ID_ALIEN_SHIP )
     {
       as_obj = (AlienShip)objlist[i];
       if( Math.sqrt((double)(as_obj.x - x)*(as_obj.x - x) + (as_obj.y - y)*(as_obj.y - y)) <= as_obj.Radius )
       {
         ActiveFlag = 0;
         as_obj.Remove();
         
         if( TotalProjectileCount > 0 )
           TotalProjectileCount -= 1;          
           
         InformationBar.me.Score += 10;         
         break;
       }       
     }
     
   if( AlienShip.TotalAlienShipCount <= 0 )
   {
      RemoveAll();
      InformationBar.me.PauseDelayCounter = 30;
      InformationBar.me.NewLevelFlag = 1;
   }
}
//---------------------------------------------------------------
public static void RemoveAll()
{
  int i, CollideFlag = 0;
  
  GameObject[] objlist = World.me.GetGameObjectList();   
  Projectile obj; 
  
  for( i = 0; i < objlist.length; i++ )
    if( objlist[i] != null )
     if( objlist[i].ActiveFlag >= 1 && objlist[i].Id == ID_PROJECTILE )
     {
       obj = (Projectile)objlist[i];
       obj.ActiveFlag = 0;                  
     }
  TotalProjectileCount = 0; 
}
//---------------------------------------------------------------
}
//###############################################################
class AlienShip extends GameObject
{
   protected Color ItsColor;
   protected int[] VertexListX, VertexListY;
   public final static int MAX_VERTICES = 3;
   protected int Radius;
   protected InvaderPath ItsPath;

   public final static int X_SEPARATE = CellWidthPixels * 3;
   public final static int Y_SEPARATE = CellHeightPixels * 3;
   
   public static int TotalAlienShipCount = 0;
   
//---------------------------------------------------------------
public AlienShip( int nX, int nY, int Speed )
{
   Id = ID_ALIEN_SHIP;
   Radius = CellWidthPixels;
   
   ItsColor = new Color( 0, 255, 0 );
   
   VertexListX = new int[MAX_VERTICES];
   VertexListY = new int[MAX_VERTICES];
   x = nX; y = nY;
   UpdateVertices();
   
   ItsPath = new InvaderPath();
   ItsPath.Init();   
   ItsPath.xy[0] = x; ItsPath.xy[1] = y;
   ItsPath.speed[0] = Speed;
   
   TotalAlienShipCount += 1;
}
//---------------------------------------------------------------
public void Do()
{    
   ItsPath.xy[0] = x;
   ItsPath.xy[1] = y;
   ItsPath.Do();
   x = ItsPath.xy[0];
   y = ItsPath.xy[1];
   UpdateVertices();
   
   if( (int)y > ScreenHeight - CellHeightPixels * 4 )
   {
       InformationBar.me.PauseDelayCounter = 30;
           
       InformationBar.me.AliensReachedBoundaryFlag = 1;
       InformationBar.me.AlienShipsRemaining = TotalAlienShipCount;
       InformationBar.me.AlienShipSpeed = (int)ItsPath.speed[0];
   }
}
//---------------------------------------------------------------
public void Draw( Graphics g )
{
  g.setColor( ItsColor );
  g.drawLine( VertexListX[0], VertexListY[0], VertexListX[1], VertexListY[1] );
  g.drawLine( VertexListX[0], VertexListY[0], VertexListX[2], VertexListY[2] );
  g.drawLine( VertexListX[2], VertexListY[2], VertexListX[1], VertexListY[1] );
}
//---------------------------------------------------------------
public void UpdateVertices()
{
   VertexListX[0] = (int)x + 0; VertexListY[0] = (int)y + Radius;
   VertexListX[1] = (int)x - Radius; VertexListY[1] = (int)y - Radius;
   VertexListX[2] = (int)x + Radius; VertexListY[2] = (int)y - Radius;
}
//---------------------------------------------------------------
public static void CreateFleet( int Speed, int TotalCount )
{
  int xx, yy, x_start, y_start, currentcount = 0;
  
  x_start = X_SEPARATE * 6;
  y_start = Y_SEPARATE * 1;
  
  for( yy = 0; yy < 10; yy++ )
  {
   for( xx = 0; xx < 10; xx++ )
   {
      World.me.AddGameObject( new AlienShip( x_start + xx * X_SEPARATE, y_start + yy * Y_SEPARATE, Speed ) ); 
      currentcount++;
      
      if( currentcount >= TotalCount )
          break;
   }       
      if( currentcount >= TotalCount )
          break;   
  }
}
//---------------------------------------------------------------
public void Remove()
{
  ActiveFlag = 0;
  
  if( TotalAlienShipCount > 0 )
      TotalAlienShipCount--;
      
  if( TotalAlienShipCount <= 90 )
      ItsPath.SpeedIncrease = 1;
  if( TotalAlienShipCount <= 80 )
      ItsPath.SpeedIncrease = 2;
  if( TotalAlienShipCount <= 70 )
      ItsPath.SpeedIncrease = 3;      
}
//---------------------------------------------------------------
public static void RemoveAll()
{
  int i;
  
  GameObject[] objlist = World.me.GetGameObjectList();   
  AlienShip obj; 
  
  for( i = 0; i < objlist.length; i++ )
    if( objlist[i] != null )
     if( objlist[i].ActiveFlag >= 1 && objlist[i].Id == ID_ALIEN_SHIP )
     {
       obj = (AlienShip)objlist[i];
       obj.ActiveFlag = 0;                  
     }
  TotalAlienShipCount = 0; 
}
//---------------------------------------------------------------
}
//###############################################################
class InformationBar extends GameObject
{

public final static int ALIEN_SHIP_X_SPEED = 5;
public final static int ALIEN_SHIP_Y_SPEED = 10;

public int Score = 0;
public int Lives = 3;
public int Level = 1;
public int HighScore = 0;
public int GameOverFlag = 0, PauseDelayCounter = 0, AliensReachedBoundaryFlag = 0, NewLevelFlag = 0;
public int AlienShipsRemaining = 0, AlienShipSpeed = ALIEN_SHIP_X_SPEED;

public final static InformationBar me = new InformationBar();
public Color ItsColor;

//---------------------------------------------------------------
protected InformationBar()
{
   Init();
   x = 5; y = 20;
   ItsColor = new Color( 200, 200, 200 );
}
//---------------------------------------------------------------
public void Init()
{
  Score = 0; Lives = 3; Level = 1; GameOverFlag = 0;
}
//---------------------------------------------------------------
public void Draw( Graphics g ) 
{
  g.setColor( ItsColor );
  g.drawString("LIVES x " + Lives + "     SCORE " + Score + "     LEVEL " + Level + "    HIGHSCORE " + HighScore, (int)x, (int)y );
  
  if( Lives <= 0 )
      g.drawString( "GAME OVER", GameObject.ScreenWidth/2 - 50, GameObject.ScreenHeight/2 );
} 
//---------------------------------------------------------------
public void Do()
{
       
  if( AliensReachedBoundaryFlag == 1 && PauseDelayCounter == 0 )
  {
    AliensReachedBoundaryFlag = 0;
    AlienShip.RemoveAll();
    AlienShip.CreateFleet( ALIEN_SHIP_X_SPEED, AlienShipsRemaining );

    if( InformationBar.me.Lives > 0 )
        InformationBar.me.Lives--;    
    if( Lives <= 0 )
        GameOverFlag = 1;
  }
  
  if( NewLevelFlag >= 1 )
  {
     NewLevelFlag = 0;
     Level++;
     AlienShip.CreateFleet( InformationBar.ALIEN_SHIP_X_SPEED, 100); 
  }
  
  if( GameOverFlag == 1 )
   if( vk_enter == 1 )
   {
     Init();
     AlienShip.RemoveAll();
     Projectile.RemoveAll();
     World.me.RemoveObjectsId( ID_SHIP );
     World.me.RemoveObjectsId( ID_ALIEN_SHIP );     
     World.me.RemoveObjectsId( ID_PROJECTILE );
     World.me.AddGameObject( new Ship() );
     AlienShip.CreateFleet( ALIEN_SHIP_X_SPEED, 100);  
   }
}
//---------------------------------------------------------------
}
//###############################################################
class World
{
  public final static int MAX_GAME_OBJECT_COUNT = 500;
  protected static GameObject[] GameObjectList;
  
  public static final World me = new World();
  
//---------------------------------------------------------------  
  protected World()
  {
    int i;
    GameObjectList = new GameObject[MAX_GAME_OBJECT_COUNT];
    for( i = 0; i < MAX_GAME_OBJECT_COUNT; i++ )
         GameObjectList[i] = null;
    GameObject.ItsWorld = this;
     
  }
//---------------------------------------------------------------  
  public static void AddGameObject( GameObject obj )
  {
    int i;
    for( i = 0; i < MAX_GAME_OBJECT_COUNT; i++ )
      if( GameObjectList[i] == null )
      {
          GameObjectList[i] = obj;
          break;
      }
  }
//---------------------------------------------------------------  
  public static void AddGameObjectArray( GameObject[] objlist )
  {
    int i, length = objlist.length;
    
    for( i = 0; i < length; i++ )
         AddGameObject( objlist[i] );
  }
//---------------------------------------------------------------  
  public GameObject[] GetGameObjectList()
  {
     return GameObjectList;
  }
//---------------------------------------------------------------  
  public static void Do()
  {

    int i;
    for( i = 0; i < MAX_GAME_OBJECT_COUNT; i++ )
      if( GameObjectList[i] != null )
      {
        if( GameObjectList[i].ActiveFlag == 0 )        
           GameObjectList[i] = null;
        else
        {
           if( InformationBar.me.GameOverFlag == 0 && InformationBar.me.PauseDelayCounter <= 0 )
             GameObjectList[i].Do();           
        }
      }
    
   if( InformationBar.me.PauseDelayCounter > 0 )
       InformationBar.me.PauseDelayCounter--;   
  
    InformationBar.me.Do();            
  }
//---------------------------------------------------------------  
  public static void Draw( Graphics g )
  {
    int i, k;
    
   for( k = 0; k <= GameObject.DRAW_LAYER_MAX; k++ )   
    for( i = 0; i < MAX_GAME_OBJECT_COUNT; i++ )
      if( GameObjectList[i] != null )
        if( GameObjectList[i].DrawLayer == k )    
          GameObjectList[i].Draw(g);
          
    InformationBar.me.Draw(g);
  }
//--------------------------------------------------------------- 
public void RemoveObjectsId( int Id )
{
    int i;
    for( i = 0; i < MAX_GAME_OBJECT_COUNT; i++ )
      if( GameObjectList[i] != null )
       if( GameObjectList[i].Id == Id )
         GameObjectList[i] = null;
} 
//--------------------------------------------------------------- 
}
//###############################################################
class SoundBox
{

public static final SoundBox me = new SoundBox();
public static final int MAX_SOUND_COUNT = 4;

protected Clip[] ClipList;

//--------------------------------------------------------------- 
protected SoundBox()
{
  ClipList = new Clip[MAX_SOUND_COUNT];
  LoadClip( 0, "projectile.wav");
  LoadClip( 1, "breakup.wav");
  LoadClip( 2, "death.wav" );   
  LoadClip( 3, "warp.wav" );   
}
//--------------------------------------------------------------- 
public void LoadClip( int index, String filename )
{
try
{
   ClipList[index] = AudioSystem.getClip();
   File SoundFile =  new File(filename);
   
   if( !SoundFile.isFile())
   {
      ClipList[index] = null;
      return;
   }         
   AudioInputStream ais_obj = AudioSystem.getAudioInputStream(SoundFile);
   ClipList[index].open(ais_obj);
}
catch(Exception e)
{
}

}
//--------------------------------------------------------------- 
public void Play( int index )
{
   if( ClipList[index] != null )
       ClipList[index].loop(1);
}
//--------------------------------------------------------------- 
}
//###############################################################
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
//###############################################################  
public class invaders extends JApplet     
implements KeyListener, MouseListener, Runnable
{     
      int MouseX, MouseY, MouseState;
      int KeyCode;
      int ScreenWidth, ScreenHeight;
      int FontWidth, FontHeight;
      Thread MainThread;

      Random Rand = new Random();     

      BufferedImage GraphicsBuffer = null;       
      Graphics GraphicsObj;
      
//---------------------------------------------------------------    
      public void init() 
      {       
       int i;              
             
       addKeyListener( this );
       addMouseListener( this );

       ScreenWidth = getSize().width;
       ScreenHeight = getSize().height;
       setBackground(Color.black);
       setForeground(Color.green);
       
       GameObject.ScreenWidth = ScreenWidth;
       GameObject.ScreenHeight = ScreenHeight;
       
       //World.me.AddGameObject( new SnakeHead() );
       //World.me.AddGameObject( new PlayField() );
       //PlayField.me.Init( InformationBar.me.Level );
       
       GraphicsBuffer = new BufferedImage(ScreenWidth, ScreenHeight, BufferedImage.TYPE_INT_RGB);
       GraphicsObj = GraphicsBuffer.createGraphics();
       
       FontWidth = FontHeight = 10;    
       setFocusable(true);
       MouseState = 0;
       KeyCode = 0;       
       
       World.me.AddGameObject( new Ship() );
       AlienShip.CreateFleet( InformationBar.ALIEN_SHIP_X_SPEED, 100);     
       World.me.AddGameObject( InformationBar.me );
      }
//---------------------------------------------------------------  
  public void start()
  {
    MainThread = new Thread(this);
    MainThread.start();
  }
//---------------------------------------------------------------  
  public void run()
  {
    long FramesPerSecond = 30;         
    long TicksPerSecond = 1000 / FramesPerSecond;
    long StartTime;
    long SleepTime;
    long ActualSleepTime;
  
    while (MainThread != null ) 
    {    
      StartTime = System.currentTimeMillis();
      repaint();
      
      SleepTime = TicksPerSecond-(System.currentTimeMillis() - StartTime);
 
      if (SleepTime > 0)
          ActualSleepTime = SleepTime;
      else
          ActualSleepTime = 10;    
      try 
      {
        MainThread.sleep(ActualSleepTime);
      } 
      catch (InterruptedException e) 
      {
      }          
    }      
  }
//---------------------------------------------------------------  
  public void stop()
  {
     MainThread = null;
  }
//--------------------------------------------------------------- 
  public void update(Graphics g)
  {    
  }  
//---------------------------------------------------------------  
      public void paint(Graphics g)
      {       
         
         Font f = new Font("monospace", Font.PLAIN, 16);   
         
         GraphicsObj.setFont(f);          
         GraphicsObj.setColor(Color.black);
         GraphicsObj.fillRect(0,0,ScreenWidth,ScreenHeight);          
         GraphicsObj.setColor(new Color(100,100,255));         

         World.me.Do();
         World.me.Draw(GraphicsObj); 

         g.drawImage(GraphicsBuffer, 0, 0, this);
        
      }      
//---------------------------------------------------------------  
   public void keyPressed( KeyEvent e ) 
     { 
      char c = e.getKeyChar();
      int k = e.getKeyCode();
      KeyCode = k;

          if( KeyEvent.VK_UP == k )
              GameObject.vk_up = 1;
          else
          if( KeyEvent.VK_DOWN == k  )
              GameObject.vk_down = 1;
          else
          if( KeyEvent.VK_LEFT == k )
              GameObject.vk_left = 1;
          else 
          if( KeyEvent.VK_RIGHT == k )
              GameObject.vk_right = 1;             
          else 
          if( KeyEvent.VK_F == k )
              GameObject.vk_f = 1; 
          else 
          if( KeyEvent.VK_R == k )
              GameObject.vk_r = 1;   
          else 
          if( KeyEvent.VK_ENTER == k )
              GameObject.vk_enter = 1;               
       
          e.consume();  
     }
//---------------------------------------------------------------       
   public void keyReleased( KeyEvent e ) 
     {
      char c = e.getKeyChar();
      int k = e.getKeyCode();
      KeyCode = k;

          if( KeyEvent.VK_UP == k )
              GameObject.vk_up = 0;
          else
          if( KeyEvent.VK_DOWN == k  )
              GameObject.vk_down = 0;
          else
          if( KeyEvent.VK_LEFT == k )
              GameObject.vk_left = 0;
          else 
          if( KeyEvent.VK_RIGHT == k )
              GameObject.vk_right = 0;             
          else 
          if( KeyEvent.VK_F == k )
              GameObject.vk_f = 0; 
          else 
          if( KeyEvent.VK_R == k )
              GameObject.vk_r = 0; 
          else 
          if( KeyEvent.VK_ENTER == k )
              GameObject.vk_enter = 0;                
       
          e.consume();       
     }
//---------------------------------------------------------------       
   public void keyTyped( KeyEvent e ) 
   {
   }
//---------------------------------------------------------------  
   public void mouseEntered( MouseEvent e ) 
   { 
   }
//---------------------------------------------------------------     
   public void mouseExited( MouseEvent e ) 
   { 
   }
//--------------------------------------------------------------- 
   public void mouseMoved( MouseEvent e )
   {
   }   
//---------------------------------------------------------------
  public void mouseDragged(MouseEvent e) 
  {
  }     
//---------------------------------------------------------------  
   public void mousePressed( MouseEvent e ) 
   {
      MouseX = e.getX();
      MouseY = e.getY();
      MouseState = 1;
      e.consume(); 
   }
//---------------------------------------------------------------     
   public void mouseReleased( MouseEvent e ) 
   { 
      MouseX = e.getX();
      MouseY = e.getY();
      MouseState = 0;      
      e.consume();   
   }
//---------------------------------------------------------------     
   public void mouseClicked( MouseEvent e ) 
   {
   }       
//------------------------------------------------------   
} ///:~