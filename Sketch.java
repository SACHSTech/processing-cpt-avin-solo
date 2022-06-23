import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	
	PImage imgBoard;
  PImage imgStick;
  PImage imgBall1;
  PImage imgBall2;
  PImage imgBall3;
  PImage imgBall4;
  PImage imgBall5;
  PImage imgBall6;
  PImage imgBall7;
  PImage imgBall8;
  PImage imgBall9;
  PImage imgBall10;
  PImage imgBall11;
  PImage imgBall12;
  PImage imgBall13;
  PImage imgBall14;
  PImage imgBall15;
  PImage imgRightArrow;
  PImage imgLeftArrow;


  double[][] ballPos = new double[16][2]; //row zero corresponds to X, one corresponds to Y
  float[][] ballSpeed = new float[16][2]; //row zero corresponds to X, one corresponds to Y
  float[][] velocity = new float[16][2]; //row zero corresponds to X, one corresponds to Y
  boolean[] mustHide = new boolean[16];
  double stickX = 250, stickY = 100;
  double rotation = 0;
  double xChange = 0, yChange = 0;
  double slope = 1;
  int ballsMoving = 0;
  int intLeftPerson = -1;
  boolean mouseIsDragged = false, needsToChoosePower = false, boolBallsAssigned = false;
  int power = 0;
  int intPlayer = -1, intSolids = 7, intStripes = 7, intSolidsShot = 0, intStripesShot = 0, intLastBall = -1;
  boolean choosingBlackPocket = true;
  int intPocketChosen1 = -1, intPocketChosen2 = -1;
  boolean boolP1Lost = false, boolP1Won = false, boolGameCrash = false;

  public void settings() {
    size(750, 472);
  }

  public void setup() {

    imgBoard = loadImage ("board.png");
    imgStick = loadImage ("stick.png");
    imgBall1 = loadImage("ball 1.png");
    imgBall2 = loadImage("ball 2.png");
    imgBall3 = loadImage("ball 3.png");
    imgBall4 = loadImage("ball 4.png");
    imgBall5 = loadImage("ball 5.png");
    imgBall6 = loadImage("ball 6.png");
    imgBall7 = loadImage("ball 7.png");
    imgBall8 = loadImage("ball 8.png");
    imgBall9 = loadImage("ball 9.png");
    imgBall10 = loadImage("ball 10.png");
    imgBall11 = loadImage("ball 11.png");
    imgBall12 = loadImage("ball 12.png");
    imgBall13 = loadImage("ball 13.png");
    imgBall14 = loadImage("ball 14.png");
    imgBall15 = loadImage("ball 15.png");
    imgRightArrow = loadImage("arrow to right.png");
    imgLeftArrow = loadImage("arrow to left.png");

    imgBall1.resize (20, 20);
    imgBall2.resize (20, 20);
    imgBall3.resize (20, 20);
    imgBall4.resize (20, 20);
    imgBall5.resize (20, 20);
    imgBall6.resize (20, 20);
    imgBall7.resize (20, 20);
    imgBall8.resize (20, 20);
    imgBall9.resize (20, 20);
    imgBall10.resize (20, 20);
    imgBall11.resize (20, 20);
    imgBall12.resize (20, 20);
    imgBall13.resize (20, 20);
    imgBall14.resize (20, 20);
    imgBall15.resize (20, 20);

    ballPos[0][0] = 211;
    ballPos[0][1] = 211;
    ballPos[1][0] = 613;
    ballPos[1][1] = 255;
    ballPos[2][0] = 594;
    ballPos[2][1] = 222;
    ballPos[3][0] = 556;
    ballPos[3][1] = 222;
    ballPos[4][0] = 575;
    ballPos[4][1] = 189;
    ballPos[5][0] = 594;
    ballPos[5][1] = 200;
    ballPos[6][0] = 575;
    ballPos[6][1] = 233;
    ballPos[7][0] = 594;
    ballPos[7][1] = 178;
    ballPos[8][0] = 575;
    ballPos[8][1] = 211;
    ballPos[9][0] = 556;
    ballPos[9][1] = 200;
    ballPos[10][0] = 537;
    ballPos[10][1] = 211;
    ballPos[11][0] = 613;
    ballPos[11][1] = 167;
    ballPos[12][0] = 613;
    ballPos[12][1] = 189;
    ballPos[13][0] = 613;
    ballPos[13][1] = 211;
    ballPos[14][0] = 613;
    ballPos[14][1] = 233;
    ballPos[15][0] = 594;
    ballPos[15][1] = 244;

    for (int i = 1; i < 16; i ++) {
      velocity[i][0] = 0;
      velocity[i][1] = 0;
      mustHide[i] = false;
    }
   
  }

  public void draw() {
    
    System.out.println ("player: " + intPlayer + " left person " + intLeftPerson );
    background(125, 255, 205);
    if (boolGameCrash == false) {
      if (boolP1Lost == false && boolP1Won == false) {
        stickX = ballPos[0][0] + (-0.5 * imgStick.width);
        stickY = ballPos[0][1] + (-0.5 * imgStick.height);
        int intPrevBallsMoving = ballsMoving;
        ballsMoving = 0;
        for (int i = 0; i < 16; i ++) {
          for (int j = 0; j < 2; j ++) {
            if (velocity[i][j] != 0) {
              ballsMoving ++;
              break;
            }
          }
        }
        if (ballsMoving == 0) {
          intPocketChosen1 = -1;
          intPocketChosen2 = -1;
        }
        if (intPrevBallsMoving != 0 && ballsMoving == 0) {
          if (intPlayer == -1) {
            if (intLeftPerson == 1) {
              if (intSolidsShot > 0) {
                intPlayer *= -1;
              }
            }
            else if (intLeftPerson == 9) {
              if (intStripesShot > 0) {
                intPlayer *= -1;
              } 
            }
          }
          else {
            if (intLeftPerson == 9) {
              if (intSolidsShot > 0) {
                intPlayer *= -1;
              }
            }
            else if (intLeftPerson == 1) {
              if (intStripesShot > 0) {
                intPlayer *= -1;
              } 
            }
          }
        }

        image (imgBoard, 0, 0);

        if (intLeftPerson == 1) {
          image (imgBall7, 90, 437);
          image (imgBall15, width - 110, 437);
        }
        else if (intLeftPerson == 9) {
          image (imgBall7, 90, 437);
          image (imgBall15, width - 110, 437);
        }

        fill (255);
        ellipse ((float) ballPos[0][0], (float) ballPos[0][1], 20, 20); //add conditions of when moving
        if (intPlayer == -1 && ballsMoving == 0) {
          image (imgRightArrow, width - 80, 430);
        }
        else if (ballsMoving == 0) {
          image (imgLeftArrow, 20, 430);
        }

        if (mustHide[1] == false) {
          image (imgBall1, (float) (ballPos[1][0] - 10), (float) (ballPos[1][1] - 10));
        }
        if (mustHide[2] == false) {
        image (imgBall2, (float) (ballPos[2][0] - 10), (float) (ballPos[2][1] - 10));
        }
        if (mustHide[3] == false) {
        image (imgBall3, (float) (ballPos[3][0] - 10), (float) (ballPos[3][1] - 10));
        }
        if (mustHide[4] == false) {
        image (imgBall4, (float) (ballPos[4][0] - 10), (float) (ballPos[4][1] - 10));
        }
        if (mustHide[5] == false) {
        image (imgBall5, (float) (ballPos[5][0] - 10), (float) (ballPos[5][1] - 10));
        }
        if (mustHide[6] == false) {
        image (imgBall6, (float) (ballPos[6][0] - 10), (float) (ballPos[6][1] - 10));
        }
        if (mustHide[7] == false) {
        image (imgBall7, (float) (ballPos[7][0] - 10), (float) (ballPos[7][1] - 10));
        }
        if (mustHide[8] == false) {
        image (imgBall8, (float) (ballPos[8][0] - 10), (float) (ballPos[8][1] - 10));
        }
        if (mustHide[9] == false) {
        image (imgBall9, (float) (ballPos[9][0] - 10), (float) (ballPos[9][1] - 10));
        }
        if (mustHide[10] == false) {
        image (imgBall10, (float) (ballPos[10][0] - 10), (float) (ballPos[10][1] - 10));
        }
        if (mustHide[11] == false) {
        image (imgBall11, (float) (ballPos[11][0] - 10), (float) (ballPos[11][1] - 10));
        }
        if (mustHide[12] == false) {
        image (imgBall12, (float) (ballPos[12][0] - 10), (float) (ballPos[12][1] - 10));
        }
        if (mustHide[13] == false) {
          image (imgBall13, (float) (ballPos[13][0] - 10), (float) (ballPos[13][1] - 10));
        }
        if (mustHide[14] == false) {
          image (imgBall14, (float) (ballPos[14][0] - 10), (float) (ballPos[14][1] - 10));
        }    
        if (mustHide[15] == false) {
          image (imgBall15, (float) (ballPos[15][0] - 10), (float) (ballPos[15][1] - 10));
        }

        pushMatrix();
        translate ((float) (stickX + imgStick.width / 2), (float) (stickY + imgStick.height / 2));
        rotate ((float) (rotation));
        translate ((float) (-1.05 * imgStick.width), (float) (-0.5 * imgStick.height));
        if (intPlayer == 1) {
          if (intLeftPerson == 1 && intSolids == 0) {
            choosingBlackPocket = true;
          }
          else if (intLeftPerson == 9 && intStripes == 0) {
            choosingBlackPocket = true;
          }
        }
        else {
          if (intLeftPerson == 1 && intStripes == 0) {
            choosingBlackPocket = true;
          }
          else if (intLeftPerson == 9 && intSolids == 0) {
            choosingBlackPocket = true;
          }
        }
        if (ballsMoving == 0 && choosingBlackPocket == false) {
          image (imgStick, 0, 0);
          // if slope is undefined?
          slope = -yChange / xChange;
        }
        popMatrix();

        
        if (choosingBlackPocket == true) {
          fill (0);
          textSize(12);
          text ("CLICK ON A POCKET FOR YOUR LAST SHOT", 249, 444);
        }

        if (needsToChoosePower) {
          fill (0);
          textSize(12);
          text ("ENTER A NUMBER BETWEEN 1 AND 9 TO INDICATE THE POWER OF YOUR HIT", 149, 444);
        }
        
        for (int i = 0; i < 16; i ++) {     
          velocity[i][0] *= 0.98;
          velocity[i][1] *= 0.98;
          for (int j = 0; j < 2; j ++) {
            if (Math.abs (velocity[i][j]) < 2) {
              velocity[i][j] = 0;
            }
          }
          ballSpeed[i][0] = (float) (velocity[i][0] / 100);
          ballSpeed[i][1] = (float) (velocity[i][1] / 100);
          ballPos[i][0] += ballSpeed[i][0];
          ballPos[i][1] += ballSpeed[i][1];
          
          for (int j = i + 1; j < 16; j ++) {
            if (collisionCheck (i, j) == true) {
              collided(i, j);
            }
          }

          if (ballPos[i][0] + 10 > width - 52 && ballPos[i][1] - 10 < 52) {
            if (mustHide[i] == false) {
              mustHide[i] = true;
              intLastBall = i;
              if (i == 8) {
                blackBallGoneIn(1);
              }
              
              if (i <= 7 && i != 0) {
                intSolids --;
                intSolidsShot ++;
              }
              else if (i > 8) {
                intStripes --;
                intStripesShot ++;
              }
              if (mustHide[8] == false && intSolids + intStripes == 13) {
                boolBallsAssigned = true;
                if (intPlayer == 1) {
                  if (intLastBall < 8) {
                    intLeftPerson = 9;
                  }
                  else if (intLastBall > 8) {
                    intLeftPerson = 1;
                  }
                }
                else {
                  if (intLastBall < 8) {
                    intLeftPerson = 1;
                  }
                  else if (intLastBall > 8) {
                    intLeftPerson = 9;
                  }
                }
              }
            }
          }
          else if (ballPos[i][0] + 10 > width - 52 && ballPos[i][1] + 10 > 422 - 52) {
            if (mustHide[i] == false) {
              mustHide[i] = true;
              intLastBall = i;
              
              if (i == 8) {
                blackBallGoneIn(2);
              }
              if (i <= 7 && i != 0) {
                intSolids --;
                intSolidsShot ++;
              }
              else if (i > 8) {
                intStripes --;
                intStripesShot ++;
              }
              if (mustHide[8] == false && intSolids + intStripes == 13) {
                boolBallsAssigned = true;
                if (intPlayer == 1) {
                  if (intLastBall < 8) {
                    intLeftPerson = 9;
                  }
                  else if (intLastBall > 8) {
                    intLeftPerson = 1;
                  }
                }
                else {
                  if (intLastBall < 8) {
                    intLeftPerson = 1;
                  }
                  else if (intLastBall > 8) {
                    intLeftPerson = 9;
                  }
                }
              }
            }
          }
          else if (ballPos[i][0] - 10 < 52 && ballPos[i][1] - 10 < 52) {
            if (mustHide[i] == false) {
              mustHide[i] = true;
              intLastBall = i;
              if (i == 8) {
                blackBallGoneIn(3);
              }
              if (i <= 7 && i != 0) {
                intSolids --;
                intSolidsShot ++;
              }
              else if (i > 8) {
                intStripes --;
                intStripesShot ++;
              }
              if (mustHide[8] == false && intSolids + intStripes == 13) {
                boolBallsAssigned = true;
                if (intPlayer == 1) {
                  if (intLastBall < 8) {
                    intLeftPerson = 9;
                  }
                  else if (intLastBall > 8) {
                    intLeftPerson = 1;
                  }
                }
                else {
                  if (intLastBall < 8) {
                    intLeftPerson = 1;
                  }
                  else if (intLastBall > 8) {
                    intLeftPerson = 9;
                  }
                }
              }
            }
          }
          else if (ballPos[i][0] - 10 < 52 && ballPos[i][1] + 10 > 422 - 52) {
            if (mustHide[i] == false) {
              mustHide[i] = true;
              intLastBall = i;
              if (i == 8) {
                blackBallGoneIn(4);
              }
              if (i <= 7 && i != 0) {
                intSolids --;
                intSolidsShot ++;
              }
              else if (i > 8) {
                intStripes --;
                intStripesShot ++;
              }
              if (mustHide[8] == false && intSolids + intStripes == 13) {
                boolBallsAssigned = true;
                if (intPlayer == 1) {
                  if (intLastBall < 8) {
                    intLeftPerson = 9;
                  }
                  else if (intLastBall > 8) {
                    intLeftPerson = 1;
                  }
                }
                else {
                  if (intLastBall < 8) {
                    intLeftPerson = 1;
                  }
                  else if (intLastBall > 8) {
                    intLeftPerson = 9;
                  }
                }
              }
            }
          }
          else if (ballPos[i][0] >= 355 && ballPos[i][0] <= 395 && ballPos[i][1] - 10 < 50) {
            if (mustHide[i] == false) {
              mustHide[i] = true;
              intLastBall = i;
              if (i == 8) {
                blackBallGoneIn(5);
              }
              if (i <= 7 && i != 0) {
                intSolids --;
                intSolidsShot ++;
              }
              else if (i > 8) {
                intStripes --;
                intStripesShot ++;
              }
              if (mustHide[8] == false && intSolids + intStripes == 13) {
                boolBallsAssigned = true;
                if (intPlayer == 1) {
                  if (intLastBall < 8) {
                    intLeftPerson = 9;
                  }
                  else if (intLastBall > 8) {
                    intLeftPerson = 1;
                  }
                }
                else {
                  if (intLastBall < 8) {
                    intLeftPerson = 1;
                  }
                  else if (intLastBall > 8) {
                    intLeftPerson = 9;
                  }
                }
              }
            }
          } 
          else if (ballPos[i][0] >= 355 && ballPos[i][0] <= 395 && ballPos[i][1] + 10 > 422 - 50) {
            if (mustHide[i] == false) {
              mustHide[i] = true;
              intLastBall = i;
              if (i == 8) {
                blackBallGoneIn(6);
              }
              if (i <= 7 && i != 0) {
                intSolids --;
                intSolidsShot ++;
              }
              else if (i > 8) {
                intStripes --;
                intStripesShot ++;
              }
              if (mustHide[8] == false && intSolids + intStripes == 13) {
                boolBallsAssigned = true;
                if (intPlayer == 1) {
                  if (intLastBall < 8) {
                    intLeftPerson = 9;
                  }
                  else if (intLastBall > 8) {
                    intLeftPerson = 1;
                  }
                }
                else {
                  if (intLastBall < 8) {
                    intLeftPerson = 1;
                  }
                  else if (intLastBall > 8) {
                    intLeftPerson = 9;
                  }
                }
              }
            }
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
      }
      else if (boolP1Lost == true) {
        background(255);
        textSize(14);
        text ("Player 2; CONGRATS!", 300, 250);
      }
      else if (boolP1Won == true) {
        background(255);
        textSize(14);
        text ("Player 1; CONGRATS!", 300, 250);
      }
      else {
        boolGameCrash = true;
      }
    }

    // white ball doesnt go through mustHide process
  }

  public void mouseDragged () {
    mouseIsDragged = true;
    rotation = atan2((float) (mouseY - ballPos[0][1]), (float) (mouseX - ballPos[0][0]));
    xChange = 1.05 * imgStick.width * Math.cos (rotation);
    yChange = 1.05 * imgStick.width * Math.sin (rotation);
    slope = -yChange / xChange;
    mouseIsDragged = false;
  }

  public void mouseClicked () { //set a stage cause clicking during ruins directions
    // click without dragging doesnt work (screen goes blank)

    if (mouseIsDragged == true) {
      return;
    }
    if (choosingBlackPocket == false) {
      needsToChoosePower = true;
    }
    if (intPlayer == 1) {
      if (mouseX > 19 && mouseX < 59 && mouseY > 17 && mouseY < 57) {
        intPocketChosen1 = 1;
        choosingBlackPocket = false;
      }
      else if (mouseX > 359 && mouseX < 393 && mouseY > 10 && mouseY < 50) {
        intPocketChosen1 = 2;
        choosingBlackPocket = false;
      }
      else if (mouseX > 690 && mouseX < 730 && mouseY > 17 && mouseY < 57) {
        intPocketChosen1 = 3;
        choosingBlackPocket = false;
      }
      else if (mouseX > 19 && mouseX < 59 && mouseY > 364 && mouseY < 404) {
        intPocketChosen1 = 4;
        choosingBlackPocket = false;
      }
      else if (mouseX > 359 && mouseX < 393 && mouseY > 374 && mouseY < 414) {
        intPocketChosen1 = 5;
        choosingBlackPocket = false;
      }
      else if (mouseX > 690 && mouseX < 730 && mouseY > 364 && mouseY < 404) {
        intPocketChosen1 = 6;
        choosingBlackPocket = false;
      }
    }
    else {
      if (mouseX > 19 && mouseX < 59 && mouseY > 17 && mouseY < 57) {
        intPocketChosen2 = 1;
        choosingBlackPocket = false;
      }
      else if (mouseX > 359 && mouseX < 393 && mouseY > 10 && mouseY < 50) {
        intPocketChosen2 = 2;
        choosingBlackPocket = false;
      }
      else if (mouseX > 690 && mouseX < 730 && mouseY > 17 && mouseY < 57) {
        intPocketChosen2 = 3;
        choosingBlackPocket = false;
      }
      else if (mouseX > 19 && mouseX < 59 && mouseY > 364 && mouseY < 404) {
        intPocketChosen2 = 4;
        choosingBlackPocket = false;
      }
      else if (mouseX > 359 && mouseX < 393 && mouseY > 374 && mouseY < 414) {
        intPocketChosen2 = 5;
        choosingBlackPocket = false;
      }
      else if (mouseX > 690 && mouseX < 730 && mouseY > 364 && mouseY < 404) {
        intPocketChosen2 = 6;
        choosingBlackPocket = false;
      }
    }
  }

  public void keyPressed () {
    if (needsToChoosePower == false) {
      return;
    }
    power = 48 - (int) key;

    if (Math.cos (rotation) < 0) {
      velocity[0][0] = (float) (power * (-250) * Math.cos(rotation));
    }
    else {
      velocity[0][0] = (float) (power * (-250) * Math.cos(rotation));
    }
    if (Math.sin (rotation) > 0) {
      velocity[0][1] = Math.abs (velocity[0][0] * (float) slope);
    }
    else {
      velocity[0][1] = -1 * Math.abs (velocity[0][0] * (float) slope);
    }

    needsToChoosePower = false;
    
    intPlayer *= -1;
    intSolidsShot = 0;
    intStripesShot = 0;
  }
  private void blackBallGoneIn (int pock) {
    if (intPlayer == -1 && intPocketChosen1 == pock) {
      boolP1Won = true;
    }
    else if (intPlayer == -1 && intPocketChosen1 != -1) {
      boolP1Lost = true;
    }
    else if (intPlayer == -1 && intPocketChosen1 == -1) {
      boolGameCrash = true;
    }
    else if (intPlayer == 1 && intPocketChosen2 == pock) {
      boolP1Lost = true;
    }
    else if (intPlayer == 1 && intPocketChosen2 != -1) {
      boolP1Won = true;
    }
    else if (intPlayer == 1 && intPocketChosen1 == -1) {
      boolGameCrash = true;
    }
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