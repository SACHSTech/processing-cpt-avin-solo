import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	
	PImage imgBoard;
  PImage imgStick;
  PImage imgWhite;

  double[][] ballPos = new double[16][2]; //row zero corresponds to X, one corresponds to Y
  float[][] ballSpeed = new float[16][2]; //row zero corresponds to X, one corresponds to Y
  double stickX = 250, stickY = 100;
  double rotation = 0;
  int hitPower = 500;
  boolean hideStick = false, isHit = false;
  double xChange = 0, yChange = 0;
  double slope = 1;

  public void settings() {
    size(750, 422);
  }

  public void setup() {

    imgBoard = loadImage ("board.png");
    imgStick = loadImage ("stick.png");
    imgWhite = loadImage ("white.png");

    ballPos[0][0] = width / 2;
    ballPos[0][1] = height / 2;
    //upd stickX and stickY

    stickX = ballPos[0][0] + (-0.5 * imgStick.width);
    stickY = ballPos[0][1] + (-0.5 * imgStick.height);
   
  }

  public void draw() {
	    image (imgBoard, 0, 0);
      ellipse ((float) ballPos[0][0], (float) ballPos[0][1], 24, 24);
      pushMatrix();
      translate ((float) (stickX + imgStick.width / 2), (float) (stickY + imgStick.height / 2));
      rotate ((float) (rotation));
      translate ((float) (-1.05 * imgStick.width), (float) (-0.5 * imgStick.height));
      if (hideStick == false) {
        image (imgStick, 0, 0);
        // if slope is undefined?
        slope = -yChange / xChange;
      }
      popMatrix();
      //ellipse ((float) (ballPos[0][0] + xChange), (float) (ballPos[0][1] + yChange), 20, 20);
      
      if (isHit == true) {
        if (hitPower > 0) {
          hitPower --;
          if (xChange > 0) { // zero?
            ballPos[0][0] += ballSpeed[0][0];
          }
          else {
            ballPos[0][0] -= ballSpeed[0][0];
          }
          if (yChange > 0) { //zero?
            ballPos[0][1] += ballSpeed[0][1];
          }
          else {
            ballPos[0][1] -= ballSpeed[0][1];
          }
          
          if (ballPos[0][0] + 12 > width - 50 || ballPos[0][0] - 12 < 50) {
            ballSpeed[0][0] *= -1;
          }
          if (ballPos[0][1] + 12 > height - 50 || ballPos[0][1] - 12 < 50) {
            ballSpeed[0][1] *= -1;
          }
        }
      }
  }

  public void mouseDragged () { //not simply dragged, level stages needed.
    rotation = atan2((float) (mouseY - ballPos[0][1]), (float) (mouseX - ballPos[0][0]));
    xChange = 1.05 * imgStick.width * Math.cos (rotation);
    yChange = 1.05 * imgStick.width * Math.sin (rotation);
  }

  public void mouseClicked () { //set a stage cause clicking during ruins directions
    ballSpeed[0][0] = 2;
    ballSpeed[0][1] = (float) (2 * Math.abs (slope));
    isHit = true;
    hideStick = true;
    
  }
 
}