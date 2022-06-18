import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	
	PImage imgBoard;
  PImage imgStick;

  double[][] ballPos = new double[16][2]; //row zero corresponds to X, one corresponds to Y
  float[][] ballSpeed = new float[16][2]; //row zero corresponds to X, one corresponds to Y
  float[][] velocity = new float[16][2]; //row zero corresponds to X, one corresponds to Y
  boolean [] mustHide = new boolean[16];
  double velTemp = 0;
  double stickX = 250, stickY = 100;
  double rotation = 0;
  double xChange = 0, yChange = 0;
  double slope = 1;
  int ballsMoving = 0;
  boolean mouseIsDragged = false, needsToChoosePower = false;
  int power = 0;

  public void settings() {
    size(750, 472);
  }

  public void setup() {

    imgBoard = loadImage ("board.png");
    imgStick = loadImage ("stick.png");

    ballPos[0][0] = 211;
    ballPos[0][1] = 211;
    ballPos[1][0] = 537;
    ballPos[1][1] = 211;
    ballPos[2][0] = 556;
    ballPos[2][1] = 200;
    ballPos[3][0] = 556;
    ballPos[3][1] = 222;
    ballPos[4][0] = 575;
    ballPos[4][1] = 189;
    ballPos[5][0] = 575;
    ballPos[5][1] = 211;
    ballPos[6][0] = 575;
    ballPos[6][1] = 233;
    ballPos[7][0] = 594;
    ballPos[7][1] = 178;
    ballPos[8][0] = 594;
    ballPos[8][1] = 200;
    ballPos[9][0] = 594;
    ballPos[9][1] = 222;
    ballPos[10][0] = 594;
    ballPos[10][1] = 244;
    ballPos[11][0] = 613;
    ballPos[11][1] = 167;
    ballPos[12][0] = 613;
    ballPos[12][1] = 189;
    ballPos[13][0] = 613;
    ballPos[13][1] = 211;
    ballPos[14][0] = 613;
    ballPos[14][1] = 233;
    ballPos[15][0] = 613;
    ballPos[15][1] = 255;

    for (int i = 1; i < 16; i ++) {
      velocity[i][0] = 0;
      velocity[i][1] = 0;
      mustHide[i] = false;
    }
   
  }

  public void draw() {

    background(125, 255, 205);

    stickX = ballPos[0][0] + (-0.5 * imgStick.width);
    stickY = ballPos[0][1] + (-0.5 * imgStick.height);
    ballsMoving = 0;
    for (int i = 0; i < 16; i ++) {
      for (int j = 0; j < 2; j ++) {
        if (velocity[i][j] != 0) {
          ballsMoving ++;
          break;
        }
      }
    }
	  image (imgBoard, 0, 0);
    fill (255);
    ellipse ((float) ballPos[0][0], (float) ballPos[0][1], 20, 20); //add conditions of when moving
    for (int i = 1; i < 16; i ++) {
      if (collisionCheck (0, i) == true) {
        collided(0, i);
      }
    }
    fill (3, 252, 94);
    for (int i = 1; i < 16; i ++) {
      if (mustHide[i] == false) {
        ellipse ((float) ballPos[i][0], (float) ballPos[i][1], 20, 20);
        for (int j = i + 1; j < 16; j ++) {
          if (collisionCheck (i, j) == true) {
            collided(i, j);
          }
        }
      }
    }
      
    
    pushMatrix();
    translate ((float) (stickX + imgStick.width / 2), (float) (stickY + imgStick.height / 2));
    rotate ((float) (rotation));
    translate ((float) (-1.05 * imgStick.width), (float) (-0.5 * imgStick.height));
    if (ballsMoving == 0) {
      image (imgStick, 0, 0);
      // if slope is undefined?
      slope = -yChange / xChange;
    }
    popMatrix();

    if (needsToChoosePower) {
      fill (0);
      text ("ENTER A NUMBER BETWEEN 1 AND 9 TO INDICATE THE POWER OF YOUR HIT", 10, 444);
    }
    for (int i = 0; i < 16; i ++) {
      velocity[i][0] *= 0.97;
      velocity[i][1] *= 0.97;
      for (int j = 0; j < 2; j ++) {
        if (Math.abs (velocity[i][j]) < 2) {
          velocity[i][j] = 0;
        }
      }
      ballSpeed[i][0] = (float) (velocity[i][0] / 100); //add acceleration?
      ballSpeed[i][1] = (float) (velocity[i][1] / 100);
      ballPos[i][0] += ballSpeed[i][0];
      ballPos[i][1] += ballSpeed[i][1];

      if (ballPos[i][0] + 10 > width - 55 && ballPos[i][1] - 10 < 55) {
        mustHide[i] = true;
      }
      else if (ballPos[i][0] + 10 > width - 55 && ballPos[i][1] + 10 > 422 - 55) {
        mustHide[i] = true;
      }
      else if (ballPos[i][0] - 10 < 55 && ballPos[i][1] - 10 < 55) {
        mustHide[i] = true;
      }
      else if (ballPos[i][0] - 10 < 55 && ballPos[i][1] + 10 > 422 - 55) {
        mustHide[i] = true;
      }
      else if (ballPos[i][0] >= 355 && ballPos[i][0] <= 395 && ballPos[i][1] - 10 < 50) {
        mustHide[i] = true;
      } 
      else if (ballPos[i][0] >= 355 && ballPos[i][0] <= 395 && ballPos[i][1] + 10 > 422 - 50) {
        mustHide[i] = true;
      }
      else {
        if (ballPos[i][0] + 10 > width - 50 || ballPos[i][0] - 10 < 45) {
          velocity[i][0] *= -1;
        }
        if (ballPos[i][1] + 10 > height - 100 || ballPos[i][1] - 10 < 45) {
          velocity[i][1] *= -1;
        }
      }
    }

    // white ball doesnt go through mustHide process
  }

  public void mouseDragged () { //not simply dragged, level stages needed.
    mouseIsDragged = true;
    rotation = atan2((float) (mouseY - ballPos[0][1]), (float) (mouseX - ballPos[0][0]));
    xChange = 1.05 * imgStick.width * Math.cos (rotation);
    yChange = 1.05 * imgStick.width * Math.sin (rotation);
    slope = -yChange / xChange;
    mouseIsDragged = false;
  }

  public void mouseClicked () { //set a stage cause clicking during ruins directions
    // click without dragging doesnt work

    if (mouseIsDragged == true) {
      return;
    }
    needsToChoosePower = true;
    
  }

  public void keyPressed () {
    if (needsToChoosePower == false) {
      return;
    }
    power = 48 - (int) key;


    if (Math.cos (rotation) < 0) {
      velocity[0][0] = (float) (power * (-350) * Math.cos(rotation));
    }
    else {
      velocity[0][0] = (float) (power * (-350) * Math.cos(rotation));
    }
    if (Math.sin (rotation) > 0) {
      velocity[0][1] = Math.abs (velocity[0][0] * (float) slope);
    }
    else {
      velocity[0][1] = -1 * Math.abs (velocity[0][0] * (float) slope);
    }

    
    needsToChoosePower = false;
  }

  private boolean collisionCheck (int a, int b) {
    if (dist ((float) ballPos[a][0], (float) ballPos[a][1], (float) ballPos[b][0], (float) ballPos[b][1]) < 21) {
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