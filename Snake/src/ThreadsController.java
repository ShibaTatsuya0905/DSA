import java.util.ArrayList;

//Controls all the game logic .. most important class in this project.
public class ThreadsController extends Thread {
	ArrayList<ArrayList<DataOfSquare>> Squares = new ArrayList<ArrayList<DataOfSquare>>();
	Tuple headSnakePos;
	int sizeSnake = 3;
	long speed = 150;
	public static int directionSnake;

	ArrayList<Tuple> positions = new ArrayList<Tuple>();
	Tuple foodPosition;

	//Constructor of ControlleurThread
	ThreadsController(Tuple positionDepart) {
		//Get all the threads
		Squares = Window.Grid;

		headSnakePos = new Tuple(positionDepart.x, positionDepart.y);
		directionSnake = 1;

		//!!! Pointer !!!!
		Tuple headPos = new Tuple(headSnakePos.getX(), headSnakePos.getY());
		positions.add(headPos);

		foodPosition = new Tuple(Window.height - 1, Window.width - 1);
		spawnFood(foodPosition);

	}

	//Important part :
	public void run() {
		while (true) {
			moveInterne(directionSnake);
			checkCollision();
			moveExterne();
			deleteTail();
			pauser();
		}
	}

	//delay between each move of the snake
	private void pauser() {
		try {
			sleep(speed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//Checking if the snake bites itself or is eating
	private void checkCollision() {
		Tuple posCritique = positions.get(positions.size() - 1);
		// Check collision with itself
		for (int i = 0; i <= positions.size() - 2; i++) {
			boolean biteItself = posCritique.getX() == positions.get(i).getX() && posCritique.getY() == positions.get(i).getY();
			if (biteItself) {
				stopTheGame();
			}
		}
		//Check collision with the borders of the screen.
		if (posCritique.getX() >= Window.width || posCritique.getX() < 0 || posCritique.getY() >= Window.height || posCritique.getY() < 0) {
			stopTheGame();
		}

		boolean eatingFood = posCritique.getX() == foodPosition.y && posCritique.getY() == foodPosition.x;
		if (eatingFood) {
			System.out.println("Yummy!");
			sizeSnake = sizeSnake + 1;
			foodPosition = getValAleaNotInSnake();

			spawnFood(foodPosition);
		}
	}

	//Stops The Game
	private void stopTheGame() {
		System.out.println("COLISION! \n");
		while (true) {
			pauser();
		}
	}

	//Put food in a position and displays it
	private void spawnFood(Tuple foodPositionIn) {
		Squares.get(foodPositionIn.x).get(foodPositionIn.y).lightMeUp(1);
	}

	//return a position not occupied by the snake
	private Tuple getValAleaNotInSnake() {
		Tuple p;
		int ranX = 0 + (int) (Math.random() * Window.width);
		int ranY = 0 + (int) (Math.random() * Window.height);
		p = new Tuple(ranX, ranY);
		for (int i = 0; i <= positions.size() - 1; i++) {
			if (p.getY() == positions.get(i).getX() && p.getX() == positions.get(i).getY()) {
				ranX = 0 + (int) (Math.random() * Window.width);
				ranY = 0 + (int) (Math.random() * Window.height);
				p = new Tuple(ranX, ranY);
				i = 0;
			}
		}
		return p;
	}

	//Moves the head of the snake and refreshes the positions in the arraylist
	//1:right 2:left 3:top 4:bottom 0:nothing
	private void moveInterne(int dir) {
		switch (dir) {
			case 4:
				headSnakePos.ChangeData(headSnakePos.x, (headSnakePos.y + 1));
				positions.add(new Tuple(headSnakePos.x, headSnakePos.y));
				break;
			case 3:
				headSnakePos.ChangeData(headSnakePos.x, (headSnakePos.y - 1));
				positions.add(new Tuple(headSnakePos.x, headSnakePos.y));
				break;
			case 2:
				headSnakePos.ChangeData((headSnakePos.x-1), headSnakePos.y);
				positions.add(new Tuple(headSnakePos.x, headSnakePos.y));
				break;
			case 1:
				headSnakePos.ChangeData((headSnakePos.x+1), headSnakePos.y);
				positions.add(new Tuple(headSnakePos.x, headSnakePos.y));
				break;
		}
	}

	//Refresh the squares that needs to be
	private void moveExterne() {
		for (Tuple t : positions) {
			int y = t.getX();
			int x = t.getY();
			Squares.get(x).get(y).lightMeUp(0);
		}
	}

	//Refreshes the tail of the snake, by removing the superfluous data in positions arraylist
	//and refreshing the display of the things that is removed
	private void deleteTail() {
		int cmpt = sizeSnake;
		for (int i = positions.size() - 1; i >= 0; i--) {
			if (cmpt == 0) {
				Tuple t = positions.get(i);
				Squares.get(t.y).get(t.x).lightMeUp(2);
			} else {
				cmpt--;
			}
		}
		cmpt = sizeSnake;
		for (int i = positions.size() - 1; i >= 0; i--) {
			if (cmpt == 0) {
				positions.remove(i);
			} else {
				cmpt--;
			}
		}
	}

}