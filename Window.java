import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JFrame {

    ArrayList<Cell> board;
    public Window(){

        super("Tic-tac-toe");

        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridLayout(3, 3));

        board = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            Cell cell = new Cell();
            board.add(cell);
        }
        for( Cell cell : board){
            mainpanel.add(cell);
        }
        add(mainpanel, BorderLayout.CENTER);

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
