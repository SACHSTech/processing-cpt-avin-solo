import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	
	PImage imgBoard;
  PImage imgStick;
  PImage imgWhite;

  int[][] ballPos = new int[16][2];
  double stickX = 250, stickY = 100;
  double rotation = 0;
  boolean tempShit = false;

  public void settings() {
    size(750, 422);
  }

  public void setup() {

    imgBoard = loadImage ("board.png");
    imgStick = loadImage ("stick.png");
    imgWhite = loadImage ("white.png");

    ballPos[0][0] = width / 2;
    ballPos[0][1] = height / 2;

    stickX = ballPos[0][0] + (-0.5 * imgStick.width);
    stickY = ballPos[0][1] + (-0.5 * imgStick.height);
   
  }

  public void draw() {
	    image (imgBoard, 0, 0);
      //image (imgWhite, ballPos[0][0], ballPos[0][1]);
      //image (imgStick, stickX, stickY);
      ellipse (ballPos[0][0], ballPos[0][1], 24, 24);
      pushMatrix();
      translate ((float) (stickX + imgStick.width / 2), (float) (stickY + imgStick.height / 2));
      rotate ((float) rotation);
      translate ((float) (-1.05 * imgStick.width), (float) (-0.5 * imgStick.height));
      image (imgStick, 0, 0);
      popMatrix();
      //rotation = rotation + 0.05;
  }

  public void mouseDragged () {
    rotation = atan2((float) (mouseY - stickY), (float) (mouseX - stickX));
  }
 
}