import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	
	PImage imgBoard;
  PImage imgStick;

  double[][] ballPos = new double[16][2]; //row zero corresponds to X, one corresponds to Y
  float[][] ballSpeed = new float[16][2]; //row zero corresponds to X, one corresponds to Y
  float[][] velocity = new float[16][2];
  double velTemp = 0;
  double stickX = 250, stickY = 100;
  double rotation = 0;
  int defaultPower = 200;
  boolean hideStick = false, isHit = false;
  double xChange = 0, yChange = 0;
  double slope = 1;

  public void settings() {
    size(750, 422);
  }

  public void setup() {

    imgBoard = loadImage ("board.png");
    imgStick = loadImage ("stick.png");

    ballPos[0][0] = width / 2;
    ballPos[0][1] = height / 2;
    ballPos[1][0] = 500;
    ballPos[1][1] = 300;
    ballPos[2][0] = 100;
    ballPos[2][1] = 100;
    
    //upd stickX and stickY after hits

    stickX = ballPos[0][0] + (-0.5 * imgStick.width);
    stickY = ballPos[0][1] + (-0.5 * imgStick.height);
    velocity[0][0] = 300;
    velTemp = velocity[0][0];

    for (int i = 1; i < 16; i ++) {
      velocity[i][0] = 0;
      velocity[i][1] = 0;
    }
   
  }

  public void draw() {
	    image (imgBoard, 0, 0);
      fill (255);
      ellipse ((float) ballPos[0][0], (float) ballPos[0][1], 24, 24); //add conditions of when moving
      for (int i = 1; i < 3; i ++) {
        if (collisionCheck (0, i) == true) {
          collided(0, i);
        }
      }
      fill (0);
      ellipse ((float) ballPos[1][0], (float) ballPos[1][1], 24, 24);
      fill (3, 252, 94);
      ellipse ((float) ballPos[2][0], (float) ballPos[2][1], 24, 24);
      pushMatrix();
      translate ((float) (stickX + imgStick.width / 2), (float) (stickY + imgStick.height / 2));
      rotate ((float) (rotation));
      translate ((float) (-1.05 * imgStick.width), (float) (-0.5 * imgStick.height));
      if (hideStick == false) {
        image (imgStick, 0, 0);
        // if slope is undefined?
        slope = -yChange / xChange;
        velocity[0][1] = (float) slope * (-velocity[0][0]);
      }
      popMatrix();
      
      if (isHit == true) {
        if (velTemp > 0) { //ballSpeed?
          velTemp --;

          /*if (ballPos[0][0] + 12 > width - 50 || ballPos[0][0] - 12 < 50) {
            ballSpeed[0][0] *= -1;
          }
          if (ballPos[0][1] + 12 > height - 50 || ballPos[0][1] - 12 < 50) {
            ballSpeed[0][1] *= -1;
          }
          */

          for (int i = 0; i < 3; i ++) {
            ballSpeed[i][0] = (float) (velocity[i][0] / 100 * 0.5);
            ballSpeed[i][1] = (float) (velocity[i][1] / 100 * 0.5);
            ballPos[i][0] += ballSpeed[i][0];
            ballPos[i][1] += ballSpeed[i][1];
          }
        }
        

      }
  }

  public void mouseDragged () { //not simply dragged, level stages needed.
    rotation = atan2((float) (mouseY - ballPos[0][1]), (float) (mouseX - ballPos[0][0]));
    xChange = 1.05 * imgStick.width * Math.cos (rotation);
    yChange = 1.05 * imgStick.width * Math.sin (rotation);
    slope = -yChange / xChange;
    if (Math.cos (rotation) < 0) {
      velocity[0][0] = -200;
    }
    else {
      velocity[0][0] = 200;
    }
    if (Math.sin (rotation) < 0) {
      velocity[0][1] = Math.abs (velocity[0][0] * (float) slope);
    }
    else {
      velocity[0][1] = -1 * Math.abs (velocity[0][0] * (float) slope);
    }
  }

  public void mouseClicked () { //set a stage cause clicking during ruins directions
    isHit = true;
    hideStick = true;
    
  }

  private boolean collisionCheck (int a, int b) {
    if (dist ((float) ballPos[a][0], (float) ballPos[a][1], (float) ballPos[b][0], (float) ballPos[b][1]) < 24) {
      return true;
    }
    return false;
  }

  private void collided (int a, int b) {
    float normalX = (float) (ballPos[b][0] - ballPos[a][0]);
    float normalY = (float) (ballPos[b][1] - ballPos[a][1]);

    float magnitude = (float) Math.sqrt (normalX * normalX + normalY * normalY);
    float unitNormX = normalX / magnitude;
    float unitNormY = normalY / magnitude;
    float unitTangentX = -unitNormY;
    float unitTangentY = unitNormX;

    float aVelDotNorm = (float) (unitNormX * velocity[a][0] + unitNormY * velocity[a][1]);
    float aVelDotTan = (float) (unitTangentX * velocity[a][0] + unitTangentY * velocity[a][1]);
    float bVelDotNorm = (float) (unitNormX * velocity[b][0] + unitNormY * velocity[b][1]);
    float bVelDotTan = (float) (unitTangentX * velocity[b][0] + unitTangentY * velocity[b][1]);
    float aVelDotNormTag = bVelDotNorm;
    float bVelDotNormTag = aVelDotNorm;
    float aVelDotNormTagX = unitNormX * aVelDotNormTag;
    float aVelDotNormTagY = unitNormY * aVelDotNormTag;
    float aVelDotTangentTagX = unitTangentX * aVelDotTan;
    float aVelDotTangentTagY = unitTangentY * aVelDotTan;
    float bVelDotNormTagX = unitNormX * bVelDotNormTag;
    float bVelDotNormTagY = unitNormY * bVelDotNormTag;
    float bVelDotTangentTagX = unitTangentX * bVelDotTan;
    float bVelDotTangentTagY = unitTangentY * bVelDotTan;
    velocity[a][0] = aVelDotNormTagX + aVelDotTangentTagX;
    velocity[a][1] = aVelDotNormTagY + aVelDotTangentTagY;
    velocity[b][0] = bVelDotNormTagX + bVelDotTangentTagX;
    velocity[b][1] = bVelDotNormTagY + bVelDotTangentTagY;



  }
 
}