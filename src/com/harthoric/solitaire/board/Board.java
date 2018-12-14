package com.harthoric.solitaire.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Board {

	private int board[][];
	private Pane root;
	private Label label;

	private ArrayList<Integer[]> validHole;
	private int lastPeg[];
	private Stack<int[][]> moves;

	Map<Integer, String> demo = new HashMap<Integer, String>();

	public Board(int board[][], Pane root, Label label) {
		this.board = board;
		this.root = root;
		this.label = label;

		validHole = new ArrayList<>();
		moves = new Stack<>();
	}

	public void setupBoard(boolean select) {
		root.getChildren().removeIf(n -> n instanceof Circle);

		int row = 0;
		for (int i[] : board) {
			int column = 0;
			for (int j : i) {
				Circle circle = new Circle();
				circle.setRadius(j == -2 ? 15 : 25);
				circle.setLayoutX(column * 50);
				circle.setLayoutY(row * 50 + 50);
				circle.setFill(j == -1 ? Color.TRANSPARENT
						: j == 0 ? Color.GREY : j == 1 ? Color.RED : j == -2 ? Color.TRANSPARENT : Color.INDIANRED);
				int encRow = row;
				int encColumn = column;
				circle.setOnMouseClicked(event -> {
					if (!select) {
						if (j == 1) {
							if (!checkBoard())
								label.setText("FINISHED!");
							lastPeg = new int[] { encRow, encColumn };
							checkValid(encColumn, encRow);
						} else if (j == 2)
							movePeg(encRow, encColumn);
					} else
						selectTile(encRow, encColumn);
				});
				root.getChildren().add(circle);
				column++;
			}
			row++;
		}

	}

	public void checkValid(int pegX, int pegY) {
		resetHighlight();

		if (board[pegY + 1][pegX] == 1 && board[pegY + 2][pegX] == 0 || board[pegY + 2][pegX] == 2) {
			validHole.add(new Integer[] { pegY + 2, pegX });
			board[pegY + 2][pegX] = 2;
		}
		if (board[pegY - 1][pegX] == 1 && board[pegY - 2][pegX] == 0 || board[pegY - 2][pegX] == 2) {
			validHole.add(new Integer[] { pegY - 2, pegX });
			board[pegY - 2][pegX] = 2;
		}
		if (board[pegY][pegX + 1] == 1 && board[pegY][pegX + 2] == 0 || board[pegY][pegX + 2] == 2) {
			validHole.add(new Integer[] { pegY, pegX + 2 });
			board[pegY][pegX + 2] = 2;
		}
		if (board[pegY][pegX - 1] == 1 && board[pegY][pegX - 2] == 0 || board[pegY][pegX - 2] == 2) {
			validHole.add(new Integer[] { pegY, pegX - 2 });
			board[pegY][pegX - 2] = 2;
		}

		setupBoard(false);
	}

	public void resetHighlight() {
		for (Integer i[] : validHole) {
			board[i[0]][i[1]] = 0;
		}
		validHole.clear();
	}

	public void movePeg(int spaceX, int spaceY) {
		resetHighlight();
		label.setText(Integer.toString(moves.size() + 1));
		if (lastPeg[0] == spaceX) {
			board[spaceX][spaceY] = 1;
			board[spaceX][spaceY > lastPeg[1] ? spaceY - 1 : spaceY + 1] = 0;
			board[lastPeg[0]][lastPeg[1]] = 0;
			moves.push(new int[][] { { spaceX, spaceY }, { lastPeg[0], lastPeg[1] },
					{ spaceX, spaceY > lastPeg[1] ? spaceY - 1 : spaceY + 1 } });
		} else if (lastPeg[1] == spaceY) {
			board[spaceX][spaceY] = 1;
			board[spaceX > lastPeg[0] ? spaceX - 1 : spaceX + 1][spaceY] = 0;
			board[lastPeg[0]][lastPeg[1]] = 0;
			moves.push(new int[][] { { spaceX, spaceY }, { lastPeg[0], lastPeg[1] },
					{ spaceX > lastPeg[0] ? spaceX - 1 : spaceX + 1, spaceY } });
		}

		validHole.clear();
		setupBoard(false);
	}

	public void undo() {
		resetHighlight();
		label.setText(Integer.toString(moves.size() - 1));
		if (moves.size() != 0) {
			int move[][] = moves.pop();
			if (move.length == 1)
				board[move[0][0]][move[0][1]] = (board[move[0][0]][move[0][1]] == -1) ? 1 : board[move[0][0]][move[0][1]] - 1;
			else {
			board[move[0][0]][move[0][1]] = 0;
			board[move[1][0]][move[1][1]] = 1;
			board[move[2][0]][move[2][1]] = 1;
			}
		}

		setupBoard(false);
	}

	public boolean checkBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if ((board[i][j] == 0 && board[i][j + 1] == 1 && board[i][j + 2] == 1)
						|| (board[i][j] == 1 && board[i][j + 1] == 1 && board[i][j + 2] == 0))
					return true;
				if ((board[i][j] == 0 && board[i + 1][j] == 1 && board[i + 2][j] == 1)
						|| (board[i][j] == 1 && board[i + 1][j] == 1 && board[i + 2][j] == 0))
					return true;
			}

		}

		return false;
	}

	public void selectTile(int x, int y) {
		moves.push(new int[][] {{x, y}});
		board[x][y] = board[x][y] == 1 ? -1 : board[x][y] + 1;
		setupBoard(true);
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int board[][]) {
		this.board = board;
	}

}
