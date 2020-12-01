/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzz;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Random;
import puzz.GUIGame;
import javax.swing.JButton;

/**
 *
 * @author Kero
 */
public class Controller {

    private puzz.GUIGame pz;
    private int size = 3;
    private JButton btn;
    private JButton[][] matrix;

    public Controller(GUIGame pz) {
        this.pz = pz;
        addButton();
    }

    public void addButton() {
        size = pz.cbSize.getSelectedIndex() + 3;
        pz.pzLayout.setLayout(new GridLayout(size, size, 10, 10));
        pz.pzLayout.setPreferredSize(new Dimension(size * 60, size * 60));
        matrix = new JButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                btn = new JButton(i * 3 + j + 1 + "");
                matrix[i][j] = btn;
                pz.pzLayout.add(btn);
            }
        }
        matrix[size - 1][size - 1].setText("");
        mixButton();
        pz.pack();
    }

    public Point getEmpPos() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j].getText().equals("")) {
                    return new Point(i, j);
                }
            }

        }
        return null;
    }

    public void mixButton() {
        for (int k = 0; k < 1000; k++) {
            Point p = getEmpPos(); //lay dc toa do rong
            int i = p.x;
            int j = p.y;
            Random r = new Random();
            int choice = r.nextInt(4);
            switch (choice) {
                case 0: //up
                    if(i > 0){
                        String txt = matrix[i-1][j].getText();
                    matrix[i][j].setText(txt);
                    matrix[i-1][j].setText("");
                    }
                    break;
                case 1: //down
                    if (i < size - 1){
                        String txt = matrix[i + 1][j].getText();
                        matrix[i][j].setText(txt);
                        matrix[i + 1][j].setText("");
                    }
                    break;
                case 2: //left
                    if ( j > 0){
                        String txt = matrix[i][j - 1].getText();
                        matrix[i][j].setText(txt);
                        matrix[i][j - 1].setText("");
                    }
                    break;
                case 3: //right
                    if (j < size - 1){
                        String txt = matrix[i][j + 1].getText();
                        matrix[i][j].setText(txt);
                        matrix[i][j + 1].setText("");
                    }
                    break;
            }

        }
    }
    
    public boolean checkMove(JButton btn){
        Point p = getEmpPos();
        if(p.x == btn.getX() && (Math.abs(p.y-btn.getY()) == 1)){
            return true;
        }
        if(p.y == btn.getY() && (Math.abs(p.x-btn.getX()) == 1)){
            return true;
        }
        return false;
        
    }
}
