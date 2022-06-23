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


  double[][] dblBallPos = new double[16][2]; //row zero corresponds to X, one corresponds to Y
  float[][] fltBallSpeed = new float[16][2]; //row zero corresponds to X, one corresponds to Y
  float[][] fltVelocity = new float[16][2]; //row zero corresponds to X, one corresponds to Y
  boolean[] blMustHide = new boolean[16];
  double dblStickX = 250, dblStickY = 100;
  double dblRotation = 0;
  double dblXChange = 0, dblYChange = 0;
  double dblSlope = 1;
  int intBallsMoving = 0;
  int intLeftPerson = -1;
  boolean blMouseIsDragged = false, blNeedsToChoosePower = false, blBallsAssigned = false;
  int intPower = 0;
  int intPlayer = -1, intSolids = 7, intStripes = 7, intSolidsShot = 0, intStripesShot = 0, intLastBall = -1;
  boolean blChoosingBlackPocket = false;
  int intPocketChosen1 = -1, intPocketChosen2 = -1;
  boolean blP1Lost = false, blP1Won = false, blGameCrash = false;

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

    dblBallPos[0][0] = 211;
    dblBallPos[0][1] = 211;
    dblBallPos[1][0] = 613;
    dblBallPos[1][1] = 255;
    dblBallPos[2][0] = 594;
    dblBallPos[2][1] = 222;
    dblBallPos[3][0] = 556;
    dblBallPos[3][1] = 222;
    dblBallPos[4][0] = 575;
    dblBallPos[4][1] = 189;
    dblBallPos[5][0] = 594;
    dblBallPos[5][1] = 200;
    dblBallPos[6][0] = 575;
    dblBallPos[6][1] = 233;
    dblBallPos[7][0] = 594;
    dblBallPos[7][1] = 178;
    dblBallPos[8][0] = 575;
    dblBallPos[8][1] = 211;
    dblBallPos[9][0] = 556;
    dblBallPos[9][1] = 200;
    dblBallPos[10][0] = 537;
    dblBallPos[10][1] = 211;
    dblBallPos[11][0] = 613;
    dblBallPos[11][1] = 167;
    dblBallPos[12][0] = 613;
    dblBallPos[12][1] = 189;
    dblBallPos[13][0] = 613;
    dblBallPos[13][1] = 211;
    dblBallPos[14][0] = 613;
    dblBallPos[14][1] = 233;
    dblBallPos[15][0] = 594;
    dblBallPos[15][1] = 244;

    for (int i = 1; i < 16; i ++) {
      fltVelocity[i][0] = 0;
      fltVelocity[i][1] = 0;
      blMustHide[i] = false;
    }
   
  }

  public void draw() {
    
    background(125, 255, 205);
    if (blGameCrash == false) {
      if (blP1Lost == false && blP1Won == false) {
        dblStickX = dblBallPos[0][0] + (-0.5 * imgStick.width);
        dblStickY = dblBallPos[0][1] + (-0.5 * imgStick.height);
        int intPrevBallsMoving = intBallsMoving;
        intBallsMoving = 0;
        for (int i = 0; i < 16; i ++) {
          for (int j = 0; j < 2; j ++) {
            if (fltVelocity[i][j] != 0) {
              intBallsMoving ++;
              break;
            }
          }
        }
        if (intBallsMoving == 0) {
          intPocketChosen1 = -1;
          intPocketChosen2 = -1;
        }
        if (intPrevBallsMoving != 0 && intBallsMoving == 0) {
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
        ellipse ((float)dblBallPos[0][0], (float)dblBallPos[0][1], 20, 20); //add conditions of when moving
        if (intPlayer == -1 && intBallsMoving == 0) {
          image (imgRightArrow, width - 80, 430);
        }
        else if (intBallsMoving == 0) {
          image (imgLeftArrow, 20, 430);
        }

        if (blMustHide[1] == false) {
          image (imgBall1, (float) (dblBallPos[1][0] - 10), (float) (dblBallPos[1][1] - 10));
        }
        if (blMustHide[2] == false) {
        image (imgBall2, (float) (dblBallPos[2][0] - 10), (float) (dblBallPos[2][1] - 10));
        }
        if (blMustHide[3] == false) {
        image (imgBall3, (float) (dblBallPos[3][0] - 10), (float) (dblBallPos[3][1] - 10));
        }
        if (blMustHide[4] == false) {
        image (imgBall4, (float) (dblBallPos[4][0] - 10), (float) (dblBallPos[4][1] - 10));
        }
        if (blMustHide[5] == false) {
        image (imgBall5, (float) (dblBallPos[5][0] - 10), (float) (dblBallPos[5][1] - 10));
        }
        if (blMustHide[6] == false) {
        image (imgBall6, (float) (dblBallPos[6][0] - 10), (float) (dblBallPos[6][1] - 10));
        }
        if (blMustHide[7] == false) {
        image (imgBall7, (float) (dblBallPos[7][0] - 10), (float) (dblBallPos[7][1] - 10));
        }
        if (blMustHide[8] == false) {
        image (imgBall8, (float) (dblBallPos[8][0] - 10), (float) (dblBallPos[8][1] - 10));
        }
        if (blMustHide[9] == false) {
        image (imgBall9, (float) (dblBallPos[9][0] - 10), (float) (dblBallPos[9][1] - 10));
        }
        if (blMustHide[10] == false) {
        image (imgBall10, (float) (dblBallPos[10][0] - 10), (float) (dblBallPos[10][1] - 10));
        }
        if (blMustHide[11] == false) {
        image (imgBall11, (float) (dblBallPos[11][0] - 10), (float) (dblBallPos[11][1] - 10));
        }
        if (blMustHide[12] == false) {
        image (imgBall12, (float) (dblBallPos[12][0] - 10), (float) (dblBallPos[12][1] - 10));
        }
        if (blMustHide[13] == false) {
          image (imgBall13, (float) (dblBallPos[13][0] - 10), (float) (dblBallPos[13][1] - 10));
        }
        if (blMustHide[14] == false) {
          image (imgBall14, (float) (dblBallPos[14][0] - 10), (float) (dblBallPos[14][1] - 10));
        }    
        if (blMustHide[15] == false) {
          image (imgBall15, (float) (dblBallPos[15][0] - 10), (float) (dblBallPos[15][1] - 10));
        }

        pushMatrix();
        translate ((float) (dblStickX + imgStick.width / 2), (float) (dblStickY + imgStick.height / 2));
        rotate ((float) (dblRotation));
        translate ((float) (-1.05 * imgStick.width), (float) (-0.5 * imgStick.height));
        if (intPlayer == 1) {
          if (intLeftPerson == 1 && intSolids == 0) {
            blChoosingBlackPocket = true;
          }
          else if (intLeftPerson == 9 && intStripes == 0) {
            blChoosingBlackPocket = true;
          }
        }
        else {
          if (intLeftPerson == 1 && intStripes == 0) {
            blChoosingBlackPocket = true;
          }
          else if (intLeftPerson == 9 && intSolids == 0) {
            blChoosingBlackPocket = true;
          }
        }
        if (intBallsMoving == 0 && blChoosingBlackPocket == false) {
          image (imgStick, 0, 0);
          // if slope is undefined?
          dblSlope = -dblYChange / dblXChange;
        }
        popMatrix();

        
        if (blChoosingBlackPocket == true) {
          fill (0);
          textSize(12);
          String strPockShot = "CLICK ON A POCKET FOR YOUR LAST SHOT";
          text (strPockShot, 249, 444);
        }

        if (blNeedsToChoosePower) {
          fill (0);
          textSize(12);
          String strPowerDetect = "ENTER A NUMBER BETWEEN 1 AND 9 TO INDICATE THE POWER OF YOUR HIT";
          text (strPowerDetect, 149, 444);
        }
        
        for (int i = 0; i < 16; i ++) {     
         fltVelocity[i][0] *= 0.98;
         fltVelocity[i][1] *= 0.98;
          for (int j = 0; j < 2; j ++) {
            if (Math.abs (fltVelocity[i][j]) < 2) {
             fltVelocity[i][j] = 0;
            }
          }
          fltBallSpeed[i][0] = (float) (fltVelocity[i][0] / 100);
          fltBallSpeed[i][1] = (float) (fltVelocity[i][1] / 100);
         dblBallPos[i][0] += fltBallSpeed[i][0];
         dblBallPos[i][1] += fltBallSpeed[i][1];
          
          for (int j = i + 1; j < 16; j ++) {
            if (collisionCheck (i, j) == true) {
              collided(i, j);
            }
          }

          if (dblBallPos[i][0] + 10 > width - 55 && dblBallPos[i][1] - 10 < 55) {
            if (blMustHide[i] == false) {
              blMustHide[i] = true;
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
              if (blMustHide[8] == false && intSolids + intStripes == 13) {
                blBallsAssigned = true;
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
          else if (dblBallPos[i][0] + 10 > width - 55 &&dblBallPos[i][1] + 10 > 422 - 55) {
            if (blMustHide[i] == false) {
              blMustHide[i] = true;
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
              if (blMustHide[8] == false && intSolids + intStripes == 13) {
                blBallsAssigned = true;
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
          else if (dblBallPos[i][0] - 10 < 55 &&dblBallPos[i][1] - 10 < 55) {
            if (blMustHide[i] == false) {
              blMustHide[i] = true;
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
              if (blMustHide[8] == false && intSolids + intStripes == 13) {
                blBallsAssigned = true;
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
          else if (dblBallPos[i][0] - 10 < 55 &&dblBallPos[i][1] + 10 > 422 - 55) {
            if (blMustHide[i] == false) {
              blMustHide[i] = true;
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
              if (blMustHide[8] == false && intSolids + intStripes == 13) {
                blBallsAssigned = true;
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
          else if (dblBallPos[i][0] >= 355 &&dblBallPos[i][0] <= 395 &&dblBallPos[i][1] - 10 < 50) {
            if (blMustHide[i] == false) {
              blMustHide[i] = true;
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
              if (blMustHide[8] == false && intSolids + intStripes == 13) {
                blBallsAssigned = true;
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
          else if (dblBallPos[i][0] >= 355 &&dblBallPos[i][0] <= 395 &&dblBallPos[i][1] + 10 > 422 - 50) {
            if (blMustHide[i] == false) {
              blMustHide[i] = true;
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
              if (blMustHide[8] == false && intSolids + intStripes == 13) {
                blBallsAssigned = true;
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
            if (dblBallPos[i][0] + 10 > width - 50 ||dblBallPos[i][0] - 10 < 45) {
             fltVelocity[i][0] *= -1;
            }
            if (dblBallPos[i][1] + 10 > height - 100 ||dblBallPos[i][1] - 10 < 45) {
             fltVelocity[i][1] *= -1;
            }
          }
        }
      }
      else if (blP1Lost == true) {
        background(255);
        textSize(14);
        text ("Player 2; CONGRATS!", 300, 250);
      }
      else if (blP1Won == true) {
        background(255);
        textSize(14);
        text ("Player 1; CONGRATS!", 300, 250);
      }
      else {
        blGameCrash = true;
      }
    }

    // white ball doesnt go through mustHide process
  }

  public void mouseDragged () {
    blMouseIsDragged = true;
    dblRotation = atan2((float) (mouseY -dblBallPos[0][1]), (float) (mouseX -dblBallPos[0][0]));
    dblXChange = 1.05 * imgStick.width * Math.cos (dblRotation);
    dblYChange = 1.05 * imgStick.width * Math.sin (dblRotation);
    dblSlope = -dblYChange / dblXChange;
    blMouseIsDragged = false;
  }

  public void mouseClicked () { //set a stage cause clicking during ruins directions
    // click without dragging doesnt work (screen goes blank)

    if (blMouseIsDragged == true) {
      return;
    }
    if (blChoosingBlackPocket == false) {
      blNeedsToChoosePower = true;
    }
      if (mouseX > 19 && mouseX < 59 && mouseY > 17 && mouseY < 57) {
        intPocketChosen1 = 1;
        intPocketChosen2 = 1;
        blChoosingBlackPocket = false;
      }
      else if (mouseX > 359 && mouseX < 393 && mouseY > 10 && mouseY < 50) {
        intPocketChosen1 = 2;
        intPocketChosen2 = 2;
        blChoosingBlackPocket = false;
      }
      else if (mouseX > 690 && mouseX < 730 && mouseY > 17 && mouseY < 57) {
        intPocketChosen1 = 3;
        intPocketChosen2 = 3;
        blChoosingBlackPocket = false;
      }
      else if (mouseX > 19 && mouseX < 59 && mouseY > 364 && mouseY < 404) {
        intPocketChosen1 = 4;
        intPocketChosen2 = 4;
        blChoosingBlackPocket = false;
      }
      else if (mouseX > 359 && mouseX < 393 && mouseY > 374 && mouseY < 414) {
        intPocketChosen1 = 5;
        intPocketChosen2 = 5;
        blChoosingBlackPocket = false;
      }
      else if (mouseX > 690 && mouseX < 730 && mouseY > 364 && mouseY < 404) {
        intPocketChosen1 = 6;
        intPocketChosen2 = 6;
        blChoosingBlackPocket = false;
      }
  
    else {
      if (mouseX > 19 && mouseX < 59 && mouseY > 17 && mouseY < 57) {
        intPocketChosen2 = 1;
        blChoosingBlackPocket = false;
      }
      else if (mouseX > 359 && mouseX < 393 && mouseY > 10 && mouseY < 50) {
        intPocketChosen2 = 2;
        blChoosingBlackPocket = false;
      }
      else if (mouseX > 690 && mouseX < 730 && mouseY > 17 && mouseY < 57) {
        intPocketChosen2 = 3;
        blChoosingBlackPocket = false;
      }
      else if (mouseX > 19 && mouseX < 59 && mouseY > 364 && mouseY < 404) {
        intPocketChosen2 = 4;
        blChoosingBlackPocket = false;
      }
      else if (mouseX > 359 && mouseX < 393 && mouseY > 374 && mouseY < 414) {
        intPocketChosen2 = 5;
        blChoosingBlackPocket = false;
      }
      else if (mouseX > 690 && mouseX < 730 && mouseY > 364 && mouseY < 404) {
        intPocketChosen2 = 6;
        blChoosingBlackPocket = false;
      }
    }
  }

  public void keyPressed () {
    if (blNeedsToChoosePower == false) {
      return;
    }
    intPower = 48 - (int) key;

    if (Math.cos (dblRotation) < 0) {
     fltVelocity[0][0] = (float) (intPower * (-250) * Math.cos(dblRotation));
    }
    else {
     fltVelocity[0][0] = (float) (intPower * (-250) * Math.cos(dblRotation));
    }
    if (Math.sin (dblRotation) > 0) {
     fltVelocity[0][1] = Math.abs (fltVelocity[0][0] * (float) dblSlope);
    }
    else {
     fltVelocity[0][1] = -1 * Math.abs (fltVelocity[0][0] * (float) dblSlope);
    }

    blNeedsToChoosePower = false;
    
    intPlayer *= -1;
    intSolidsShot = 0;
    intStripesShot = 0;
  }
  private void blackBallGoneIn (int pock) {
    if (intPlayer == -1 && intPocketChosen1 == pock) {
      blP1Won = true;
    }
    else if (intPlayer == -1 && intPocketChosen1 != -1) {
      blP1Lost = true;
    }
    else if (intPlayer == -1 && intPocketChosen1 == -1) {
      blGameCrash = true;
    }
    else if (intPlayer == 1 && intPocketChosen2 == pock) {
      blP1Lost = true;
    }
    else if (intPlayer == 1 && intPocketChosen2 != -1) {
      blP1Won = true;
    }
    else if (intPlayer == 1 && intPocketChosen1 == -1) {
      blGameCrash = true;
    }
  }

  

  private boolean collisionCheck (int a, int b) {
    if (dist ((float)dblBallPos[a][0], (float)dblBallPos[a][1], (float)dblBallPos[b][0], (float)dblBallPos[b][1]) < 21) {
      return true;
    }
    return false;
  }

  private void collided (int a, int b) {
    float normalX = (float) (dblBallPos[b][0] -dblBallPos[a][0]);
    float normalY = (float) (dblBallPos[b][1] -dblBallPos[a][1]);

    float magnitude = (float) Math.sqrt (normalX * normalX + normalY * normalY);
    float unitNormX = normalX / magnitude;
    float unitNormY = normalY / magnitude;
    float unitTangentX = -unitNormY;
    float unitTangentY = unitNormX;

    float aVelDotNorm = (float) (unitNormX *fltVelocity[a][0] + unitNormY *fltVelocity[a][1]);
    float aVelDotTan = (float) (unitTangentX *fltVelocity[a][0] + unitTangentY *fltVelocity[a][1]);
    float bVelDotNorm = (float) (unitNormX *fltVelocity[b][0] + unitNormY *fltVelocity[b][1]);
    float bVelDotTan = (float) (unitTangentX *fltVelocity[b][0] + unitTangentY *fltVelocity[b][1]);
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
    fltVelocity[a][0] = aVelDotNormTagX + aVelDotTangentTagX;
    fltVelocity[a][1] = aVelDotNormTagY + aVelDotTangentTagY;
    fltVelocity[b][0] = bVelDotNormTagX + bVelDotTangentTagX;
    fltVelocity[b][1] = bVelDotNormTagY + bVelDotTangentTagY;

  }

 
}



