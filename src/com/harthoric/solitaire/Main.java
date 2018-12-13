package com.harthoric.solitaire;

import com.harthoric.solitaire.board.Board;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int WIDTH = 500, HEIGHT = 550;

	public static final int BOARD[][] = { { -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2 },
			{ -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -2, -2, -1, -1, 1, 1, 1, -1, -1, -2, -2 },
			{ -2, -2, -1, -1, 1, 1, 1, -1, -1, -2, -2 }, { -2, -2, 1, 1, 1, 1, 1, 1, 1, -2, -2 },
			{ -2, -2, 1, 1, 1, 0, 1, 1, 1, -2, -2 }, { -2, -2, 1, 1, 1, 1, 1, 1, 1, -2, -2 },
			{ -2, -2, -1, -1, 1, 1, 1, -1, -1, -2, -2 }, { -2, -2, -1, -1, 1, 1, 1, -1, -1, -2, -2 },
			{ -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2 }, { -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2 } };

	private Board board;
	private boolean select = false;

	@Override
	public void start(Stage stage) throws Exception {
		Pane root = new Pane();
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		Label label = new Label("START");
		label.setLayoutX(450);
		Board board = new Board(BOARD, root, label);
		Button undo = new Button("Undo");
		undo.setOnMouseClicked(e -> {
			board.undo();
		});
		Button swap = new Button("Swap");
		swap.setOnMouseClicked(e -> {
			System.out.println(select);
			select = select ? false : true;
			board.setupBoard(select);
		});
		undo.setLayoutX(215);
		root.getChildren().addAll(undo, swap, label);
		board.setupBoard(false);
		stage.setTitle("Peg Solitaire");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}

}
