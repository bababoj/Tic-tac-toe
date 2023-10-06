import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cell extends JButton {
    private static CurrentState nextFigure; // Начинаем с крестиков

    private CurrentState currentState;
    private static CurrentState[][] gameBoard;


    public Cell(){
        nextFigure = CurrentState.CROSS;
        currentState = CurrentState.EMPTY;
        gameBoard = new CurrentState[3][3];
       setBackground(Color.BLACK);
       for(int i = 0; i < 3; i++){
           for(int j = 0; j < 3; j++){
               gameBoard[i][j] = CurrentState.EMPTY;
           }
       }
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentState == CurrentState.EMPTY) {
                    currentState = nextFigure;
                    gameBoard[getXPosition()][getYPosition()] = currentState;
                    nextFigure = (nextFigure == CurrentState.CROSS) ? CurrentState.CIRCLE : CurrentState.CROSS; // Чередование фигур
                    repaint();
                    checkForWin();
                }
            }
        });

    }

    private void checkForWin() {
        if (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin()) {
            JOptionPane.showMessageDialog(this, currentState + " wins!");
            resetGame();

            // Добавьте здесь логику завершения игры, если нужно.
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            resetGame();

            // Добавьте здесь логику завершения игры, если нужно.
        }
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameBoard[row][col] = CurrentState.EMPTY;
            }
        }
        nextFigure = CurrentState.CROSS;
        repaint();

        if (gameResetListener != null) {
            gameResetListener.onGameReset();
        }
    }

    private boolean checkRowsForWin() {
        for (int row = 0; row < 3; row++) {
            if (gameBoard[row][0] == currentState && gameBoard[row][1] == currentState && gameBoard[row][2] == currentState) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumnsForWin() {
        for (int col = 0; col < 3; col++) {
            if (gameBoard[0][col] == currentState && gameBoard[1][col] == currentState && gameBoard[2][col] == currentState) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalsForWin() {
        if ((gameBoard[0][0] == currentState && gameBoard[1][1] == currentState && gameBoard[2][2] == currentState) ||
                (gameBoard[0][2] == currentState && gameBoard[1][1] == currentState && gameBoard[2][0] == currentState)) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (gameBoard[row][col] == CurrentState.EMPTY) {
                    return false; // Найдена пустая клетка, игра не окончена.
                }
            }
        }
        return true; // Все клетки заполнены, игра завершена как ничья.
    }

    private int getXPosition() {
        return (getY() / getHeight());
    }

    private int getYPosition() {
        return (getX() / getWidth());
    }



    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);

        g2d.setStroke(new BasicStroke(3));
        int size = Math.min(getWidth(), getHeight()) - 60;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        if (currentState == CurrentState.CROSS) {
            // Рисуем крестик
            g2d.drawLine(x, y, x + size, y + size);
            g2d.drawLine(x, y + size, x + size, y);
        } else if (currentState == CurrentState.CIRCLE) {
            // Рисуем нолик
            g2d.drawOval(x, y, size, size);
        }

    }
    public interface GameResetListener {
        void onGameReset();
    }

    private static GameResetListener gameResetListener;

    public static void registerGameResetListener(GameResetListener listener) {
        gameResetListener = listener;
    }
    public void resetCell() {
        currentState = CurrentState.EMPTY;
        repaint();
    }


}
