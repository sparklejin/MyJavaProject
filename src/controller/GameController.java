package controller;

import model.Direction;
import model.MapMatrix;
import view.game.Box;
import view.game.GamePanel;
import view.game.GridComponent;
import view.game.Hero;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapMatrix model;

    public GameController(GamePanel view, MapMatrix model) {
        this.view = view;
        this.model = model;
        view.setController(this);
    }

    public void restartGame() {
        System.out.println("Do restart game here");
        this.model.resetMapMatrix();
        this.view.restartGame();
    }

    public boolean doMove(int row, int col, Direction direction) {
        GridComponent currentGrid = view.getGridComponent(row, col);
        //target row can column.
        int tRow = row + direction.getRow();
        int tCol = col + direction.getCol();
        GridComponent targetGrid = view.getGridComponent(tRow, tCol);
        int[][] map = model.getMatrix();
        if (map[tRow][tCol] == 0 || map[tRow][tCol] == 2) {
            //update hero in MapMatrix
            model.getMatrix()[row][col] -= 20;
            model.getMatrix()[tRow][tCol] += 20;
            //Update hero in GamePanel
            Hero h = currentGrid.removeHeroFromGrid();
            targetGrid.setHeroInGrid(h);
            //Update the row and column attribute in hero
            h.setRow(tRow);
            h.setCol(tCol);
            return true;
        }else if (map[tRow][tCol]/10*10 == 10){
            int boxTRow = tRow +direction.getRow();
            int boxTCol = tCol +direction.getCol();
            GridComponent boxtargetGrid = view.getGridComponent(boxTRow,boxTCol);
            if (map[boxTRow][boxTCol] / 10 == 0 && map[boxTRow][boxTCol] % 10 != 1){
                model.getMatrix()[row][col] -= 20;
                model.getMatrix()[tRow][tCol] += 20;
                model.getMatrix()[tRow][tCol] -= 10;
                model.getMatrix()[boxTRow][boxTCol] += 10;
                Box b = targetGrid.removeBoxFromGrid();
                boxtargetGrid.setBoxInGrid(b);
                Hero h = currentGrid.removeHeroFromGrid();
                targetGrid.setHeroInGrid(h);
                h.setRow(tRow);
                h.setCol(tCol);

                return true;
            }
        }
        return false;
    }

    //todo: add other methods such as loadGame, saveGame...

}
