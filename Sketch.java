import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	
	PImage imgBoard;
  PImage imgStick;
  PImage imgWhite;

  double[][] ballPos = new double[16][2];
  double stickX = 250, stickY = 100;
  double rotation = 0;
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
      //image (imgWhite, ballPos[0][0], ballPos[0][1]);
      ellipse ((float) ballPos[0][0], (float) ballPos[0][1], 24, 24);
      pushMatrix();
      translate ((float) (stickX + imgStick.width / 2), (float) (stickY + imgStick.height / 2));
      rotate ((float) (rotation));
      translate ((float) (-1.05 * imgStick.width), (float) (-0.5 * imgStick.height));
      if (hideStick == false) {
        image (imgStick, 0, 0);
        // if slope is undefined?
        slope = -yChange / xChange;
        System.out.println (yChange);
      }
      popMatrix();
      //ellipse ((float) (ballPos[0][0] + xChange), (float) (ballPos[0][1] + yChange), 20, 20);
      
      if (isHit == true) {
        int hitPower = 100; //this can be changed by user
        if (hitPower > 0) {
          hitPower --;
          if (xChange > 0) { // zero?
            ballPos[0][0] ++;
          }
          else {
            ballPos[0][0] --;
          }
          if (yChange < 0) { //zero?
            ballPos[0][1] -= Math.abs (slope);
          }
          else {
            ballPos[0][1] += Math.abs (slope);
          }
          ellipse ((float) ballPos[0][0], (float) ballPos[0][1], 24, 24);
        }
      }
  }

  public void mouseDragged () { //not simply dragged, level stages needed.
    rotation = atan2((float) (mouseY - ballPos[0][1]), (float) (mouseX - ballPos[0][0]));
    xChange = 1.05 * imgStick.width * Math.cos (rotation);
    yChange = 1.05 * imgStick.width * Math.sin (rotation);
  }

  public void mouseClicked () {
    isHit = true;
    //hideStick = true;
    
  }
 
}