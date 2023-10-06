import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JFrame {

    ArrayList<Cell> board; // create a board as an array of buttons
    JPanel mainPanel;
    public Window(){

        super("Tic-tac-toe");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 3));
        board = new ArrayList<>();
        //create 9 buttons and add them to the "board" array with the loop
        for(int i = 0; i < 9; i++){
            Cell cell = new Cell();
            board.add(cell);
        }
        //insert this board onto the panel
        for( Cell cell : board){
            mainPanel.add(cell);
        }
        //put panel on the frame
        add(mainPanel, BorderLayout.CENTER);

        //create a listener, thanks to which the game will be updated when you win
        Cell.registerGameResetListener(new Cell.GameResetListener() {
            @Override
            public void onGameReset() {
                for (Cell cell : board) {
                    cell.resetCell();
                }
            }
        });

        setVisible(true);
    }


}
