/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//long 21
package puzz;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import puzz.GUIGame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Kero
 */
public class Controller {

    private puzz.GUIGame pz;
    private int size = 3;
    private JButton btn;
    private JButton[][] matrix;
    private int moveCount = 0;
    private Timer timer;
    public boolean check = false;

    public Controller(GUIGame pz) {
        this.pz = pz;
        addButton();
    }

    public void run() {

        this.countTime();
        this.addButton();

        check = true;
    }

    public void addButton() {
        moveCount = 0;
        pz.lbCountMove.setText(moveCount + "");
        String txt = pz.cbSize.getSelectedItem().toString();//vị trí người dùng chọn
        String[] k = txt.split("x");//cắt x ra lấy 1 số
        size = Integer.parseInt(k[0]);
        System.out.println(size);
        pz.pzLayout.removeAll();//remove all before new
        pz.pzLayout.setLayout(new GridLayout(size, size, 10, 10));//bao nhiêu hàng, cột và khaongr cách
        pz.pzLayout.setPreferredSize(new Dimension(size * 60, size * 60));
        matrix = new JButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton btn = new JButton(i * size + j + 1 + "");
                matrix[i][j] = btn;
                pz.pzLayout.add(btn);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (check) {
                            if (checkMove(btn)) {
                                moveButton(btn);
                                if (checkWin()) {
                                    check = false;
                                    JOptionPane.showMessageDialog(null, "You Win");
                                    timer.stop();

                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Press New Game to start");
                        }

                    }
                }
                );
            }
        }
        matrix[size - 1][size - 1].setText("");//set gt cuối = null
        mixButton();
        pz.pack();
    }

    public Point getEmpPos() {//return tọa độ btn rỗng
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
                    if (i > 0) {
                        String txt = matrix[i - 1][j].getText();
                        matrix[i][j].setText(txt);
                        matrix[i - 1][j].setText("");
                    }
                    break;
                case 1: //down
                    if (i < size - 1) {
                        String txt = matrix[i + 1][j].getText();
                        matrix[i][j].setText(txt);
                        matrix[i + 1][j].setText("");
                    }
                    break;
                case 2: //left
                    if (j > 0) {
                        String txt = matrix[i][j - 1].getText();
                        matrix[i][j].setText(txt);
                        matrix[i][j - 1].setText("");
                    }
                    break;
                case 3: //right
                    if (j < size - 1) {
                        String txt = matrix[i][j + 1].getText();
                        matrix[i][j].setText(txt);
                        matrix[i][j + 1].setText("");
                    }
                    break;
            }

        }
    }

    public boolean checkMove(JButton btn) {
        Point p = getEmpPos();
        Point p1 = null;
//        int i1 = 0;
//        int j1 = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (btn.getText().equals(matrix[i][j].getText())) {
                    p1 = new Point(i, j);
//                    i1 = i;
//                    j1 = j;
                    // break;
                }
            }

        }
        if (p.x == p1.x && (Math.abs(p.y - p1.y) == 1)) {
            return true;
        }
        if (p.y == p1.y && (Math.abs(p.x - p1.x) == 1)) {
            return true;
        }
        return false;

    }

    public void moveButton(JButton btn) {
        Point p = getEmpPos();
        String txt = btn.getText();//lấy text btn
        matrix[p.x][p.y].setText(btn.getText());//set gt rỗng với gt txt
        btn.setText("");
        moveCount++;
        pz.lbCountMove.setText(moveCount + "");
    }

    public boolean checkWin() { //giong ban dau trc khi trộn
        if (matrix[size - 1][size - 1].getText().equals("")) {//btn cuối phải = null
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (i == size - 1 && j == size - 1) {
                        return true;
                    }
                    if (!matrix[i][j].getText().equals(i * 3 + j + 1 + "")) {//1 btn ko theo quy luat
                        return false;
                    }
                }
            }
            return true;
        }
        return false;

    }

    public void newGame() {
        if (check) {
            timer.stop();
            int confirm = JOptionPane.showConfirmDialog(null, "Do you must"
                    + " be want to make new game?", "New Game", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                this.run();
                moveCount = 0;
                pz.lbCountMove.setText(moveCount + "");
            } else if (confirm == JOptionPane.NO_OPTION) {
                timer.start();
            }
        } else {
            this.run();
        }
    }

    public void countTime() {

        pz.lbElapse.setText("0");
        timer = new Timer(1000, new ActionListener() {
            int second = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                second++;
                pz.lbElapse.setText(String.valueOf(second));
            }
        });
        timer.start();

    }
}
