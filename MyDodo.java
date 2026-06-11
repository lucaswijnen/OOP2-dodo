import greenfoot.*;

public class MyDodo extends Dodo
{
    /**
     * @author lucas
     */
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

    public void layTrailOfEggs(int n) {
        if (n <= 0) {
            showError("Voer een getal groter dan 0 in");
            return;
        }
        int aantalGelegd = 0;
        while (aantalGelegd < n) {
            layEgg();
            aantalGelegd = aantalGelegd + 1;
            if (aantalGelegd < n) {
                move();
            }
        }
    }

    public boolean validCoordinates(int x, int y) {
        if (x < 0 || x >= getWorld().getWidth()) {
            showError("Invalid coordinates");
            return false;
        }
        if (y < 0 || y >= getWorld().getHeight()) {
            showError("Invalid coordinates");
            return false;
        }
        return true;
    }

    public void goToLocation(int coordX, int coordY) {
        if (!validCoordinates(coordX, coordY)) {
            return;
        }
        while (getX() != coordX) {
            if (getX() < coordX) {
                setDirection(EAST);
            } else {
                setDirection(WEST);
            }
            move();
        }
        while (getY() != coordY) {
            if (getY() < coordY) {
                setDirection(SOUTH);
            } else {
                setDirection(NORTH);
            }
            move();
        }
    }

    public int countAllEggs() {
        int totaal = 0;
        int rij = 0;
        while (rij < getWorld().getHeight()) {
            goToLocation(0, rij);
            faceEast();
            totaal = totaal + countEggsInRow();
            rij = rij + 1;
        }
        goToLocation(0, 0);
        faceEast();
        System.out.println("Totaal aantal eieren: " + totaal);
        return totaal;
    }

    public void findRowWithMostEggs() {
        int besteRij = 0;
        int meeste = 0;
        int rij = 0;
        while (rij < getWorld().getHeight()) {
            goToLocation(0, rij);
            faceEast();
            int aantal = countEggsInRow();
            if (aantal > meeste) {
                meeste = aantal;
                besteRij = rij;
            }
            rij = rij + 1;
        }
        goToLocation(0, 0);
        faceEast();
        System.out.println("Rij met de meeste eieren: " + besteRij);
    }

    public double averageEggsPerRow() {
        int totaal = 0;
        int rij = 0;
        while (rij < getWorld().getHeight()) {
            goToLocation(0, rij);
            faceEast();
            totaal = totaal + countEggsInRow();
            rij = rij + 1;
        }
        goToLocation(0, 0);
        faceEast();
        double gemiddelde = (double) totaal / getWorld().getHeight();
        System.out.println("Gemiddeld aantal eieren per rij: " + gemiddelde);
        return gemiddelde;
    }
}