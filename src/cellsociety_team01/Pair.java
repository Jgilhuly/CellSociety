package cellsociety_team01;

public class Pair implements Comparable<Pair> {

	private int myX;
	private int myY;

	public Pair(int x, int y){
		this.myX = x;
		this.myY = y;
	}

	public int getX() {
		return myX;
	}
	public int getY() {
		return myY;
	}
	public void setX(int x) {
		this.myX = x;
	}
	public void setY(int y) {
		this.myY = y;
	}

	@Override
	public int compareTo(Pair p) {
		if (p.getX() == myX && p.getY() == myY)
			return 0;
		else
			return 1;
	}

	@Override
	public int hashCode() {
		return 1000*myX + myY;
	}
}