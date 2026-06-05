import greenfoot.*;

public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;

    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
    }

    public void act() {
    }

    public void move() {
        if ( canMove() ) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }

    public boolean canMove() {
        if (borderAhead()) {
            return false;
        }
        if (fenceAhead()) {
            return false;
        }
        return true;
    }

    public void hatchEgg() {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }

    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }

    public void jump( int distance ) {
        int nrStepsTaken = 0;
        while ( nrStepsTaken < distance ) {
            move();
            nrStepsTaken = nrStepsTaken + 1;
            System.out.println("moved " + nrStepsTaken);
        }
    }

    public void walkToWorldEdgePrintingCoordinates() {
        while ( !borderAhead() ) {
            System.out.println("x=" + getX() + " y=" + getY());
            move();
        }
    }

    public boolean canLayEgg() {
        if (onEgg()) {
            return false;
        }
        layEgg();
        return true;
    }

    public void turn180() {
        turnRight();
        turnRight();
    }

    public void climbOverFence() {
        turnLeft();
        move();
        turnRight();
        move();
        turnRight();
        move();
        turnLeft();
    }

    public void stepOneCellBackwards() {
        turn180();
        move();
        turn180();
    }

    public boolean grainAhead() {
        move();
        if (onGrain()) {
            stepOneCellBackwards();
            return true;
        }
        stepOneCellBackwards();
        return false;
    }

    public void gotoEgg() {
        while (!onEgg()) {
            move();
        }
    }

    public void walkToWorldEdge() {
        while (!borderAhead()) {
            move();
        }
    }

    public void goBackToStartOfRowAndFaceBack() {
        turn180();
        walkToWorldEdge();
        turn180();
    }

    public void walkToWorldEdgeClimbingOverFences() {
        while (!borderAhead()) {
            if (fenceAhead()) {
                climbOverFence();
            } else {
                move();
            }
        }
    }

    public void pickUpGrainsAndPrintCoordinates() {
        while (!borderAhead()) {
            if (onGrain()) {
                System.out.println("x=" + getX() + " y=" + getY());
                pickUpGrain();
            }
            move();
        }
        if (onGrain()) {
            System.out.println("x=" + getX() + " y=" + getY());
            pickUpGrain();
        }
    }

    public void layEggOnEmptyNests() {
        while (!borderAhead()) {
            if (onNest() && !onEgg()) {
                layEgg();
            }
            move();
        }
        if (onNest() && !onEgg()) {
            layEgg();
        }
    }

    public void walkToNestClimbingOverFences() {
        while (!onNest()) {
            if (fenceAhead()) {
                climbOverFence();
            } else {
                move();
            }
        }
        layEgg();
    }

    public void walkAroundFencedArea() {
        while (!onEgg()) {
            if (fenceAhead()) {
                turnRight();
            } else {
                move();
            }
        }
    }

    public void eggTrailToNest() {
        while (!onNest()) {
            if (onEgg()) {
                hatchEgg();
            }
            if (eggAhead()) {
                move();
            } else {
                turnRight();
            }
        }
    }

    public void solveSimpleMaze() {
        while (!onNest()) {
            if (!fenceAhead()) {
                move();
            } else {
                turnRight();
            }
        }
    }

    public void solveHardMaze() {
        while (!onNest()) {
            if (!fenceAhead()) {
                move();
            } else {
                turnRight();
            }
        }
        showCompliment("GOed gedaan, je hebt het nest gevonden!!");
    }

    public void faceEast() {
        while (getDirection() != EAST) {
            turnRight();
        }
    }

    public int countEggsInRow() {
        int aantalEieren = 0;
        while (!borderAhead()) {
            if (onEgg()) {
                aantalEieren = aantalEieren + 1;
            }
            move();
        }
        if (onEgg()) {
            aantalEieren = aantalEieren + 1;
        }
        goBackToStartOfRowAndFaceBack();
        showCompliment("je aantal eieren in deze rij: " + aantalEieren);
        return aantalEieren;
    }
    

}