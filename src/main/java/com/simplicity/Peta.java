package com.simplicity;

import java.util.*;

import javax.swing.*;

public class Peta<T> {
    // Kalo col maksudnya sumbu X (karena col arahnya horizontal)
    // Kalo row maksudnya sumbu Y (karena row arahnya vertikal)
    private ArrayList<ArrayList<T>> matriks;

    public Peta(int column, int row) {
        matriks = new ArrayList<>(row);
        for (int i = 0; i < row; i++) {
            ArrayList<T> rowList = new ArrayList<>(column);
            for (int j = 0; j < column; j++) {
                rowList.add(null);
            }
            matriks.add(rowList);
        }
    }

    // colIndex = sumbu X, rowIndex = sumbu Y
    public void setElement(int colIndex, int rowIndex, T element) {
        if (rowIndex < 0 || rowIndex >= matriks.size() || colIndex < 0 || colIndex >= matriks.get(0).size()) {
            throw new IndexOutOfBoundsException("Invalid matrix index");
        }
        matriks.get(rowIndex).set(colIndex, element);
    }

    public T getElement(int colIndex, int rowIndex) {
        if (rowIndex < 0 || rowIndex >= matriks.size() || colIndex < 0 || colIndex >= matriks.get(0).size()) {
            throw new IndexOutOfBoundsException("Invalid matrix index");
        }
        return matriks.get(rowIndex).get(colIndex);
    }

    public void addBottom() {
        ArrayList<T> newRow = new ArrayList<T>(getColumn());
        for (int i = 0; i < getColumn(); i++) {
            newRow.add(null);
        }
        matriks.add(newRow);
    }

    public void addTop() {
        ArrayList<T> newRow = new ArrayList<T>(getColumn());
        for (int i = 0; i < getColumn(); i++) {
            newRow.add(null);
        }
        matriks.add(0, newRow);
    }

    public void addRight() {
        for (ArrayList<T> row : matriks) {
            row.add(null);
        }
    }

    public void addLeft() {
        for (ArrayList<T> row : matriks) {
            row.add(0, null);
        }
    }

    public void removeRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= matriks.size()) {
            throw new IndexOutOfBoundsException("Invalid matrix index");
        }
        matriks.remove(rowIndex);
    }

    public void removeColumn(int colIndex) {
        if (colIndex < 0 || colIndex >= matriks.get(0).size()) {
            throw new IndexOutOfBoundsException("Invalid matrix index");
        }
        for (ArrayList<T> row : matriks) {
            row.remove(colIndex);
        }
    }

    public int getRow() {
        return matriks.size();
    }

    public int getColumn() {
        return matriks.isEmpty() ? 0 : matriks.get(0).size();
    }

    public void displayList() {
        HashSet<T> listElement = new HashSet<T>();
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getColumn(); j++) {
                T el = getElement(i, j);
                if (el != null) {
                    listElement.add(el);
                }
            }
        }

        StringBuilder message = new StringBuilder("");
        int idx = 1;
        for (T el : listElement) {
            message.append(String.format("%d. %s\n", idx, el.toString()));
            idx++;
        }
        JOptionPane.showMessageDialog(null, message, "List Object", JOptionPane.INFORMATION_MESSAGE);
    }

    public Point getElementCoordinate(T element) {
        for (int rowIndex = 0; rowIndex < getRow(); rowIndex++) {
            for (int colIndex = 0; colIndex < getColumn(); colIndex++) {
                T currentElement = getElement(colIndex, rowIndex);
                if (currentElement != null && currentElement.equals(element)) {
                    return new Point(colIndex, rowIndex);
                }
            }
        }

        return null;
    }

    public Point getClosestElementCoordinate(Point initialPoint, T element) {
        int closestRowIndex = -1;
        int closestColIndex = -1;
        double closestDistance = Double.MAX_VALUE;

        for (int rowIndex = 0; rowIndex < getRow(); rowIndex++) {
            for (int colIndex = 0; colIndex < getColumn(); colIndex++) {
                T currentElement = getElement(colIndex, rowIndex);
                if (currentElement != null && currentElement.equals(element)) {
                    float distance = initialPoint.distance(new Point(colIndex, rowIndex));
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestRowIndex = rowIndex;
                        closestColIndex = colIndex;
                    }
                }
            }
        }

        if (closestRowIndex != -1 && closestColIndex != -1) {
            return new Point(closestColIndex, closestRowIndex);
        } else {
            return null;
        }
    }

    public T selectElement() {
        return selectElement(null);
    }

    public T selectElement(T currentElement) {
        TreeSet<String> setOfElement = new TreeSet<String>();

        for (int i = 0; i < getColumn(); i++) {
            for (int j = 0; j < getRow(); j++) {
                T el = getElement(i, j);
                if (currentElement == null) {
                    if (el != null) {
                        setOfElement.add(el.toString());
                    }
                } else {
                    if (el != null && !el.equals(currentElement)) {
                        setOfElement.add(el.toString());
                    }
                }
            }
        }
        ArrayList<String> listElement = new ArrayList<>(setOfElement);

        String[] roomOptions = listElement.toArray(new String[0]);

        JList<String> list = new JList<>(roomOptions);
        String header = "Select";
        JOptionPane.showMessageDialog(null, new JScrollPane(list), header, JOptionPane.PLAIN_MESSAGE);
        String selectedOption = list.getSelectedValue();

        T element = null;
        for (int i = 0; i < getColumn(); i++) {
            for (int j = 0; j < getRow(); j++) {
                T tempEl = getElement(i, j);
                if (tempEl != null && tempEl.toString().equals(selectedOption)) {
                    element = tempEl;
                }
            }
        }
        return element;
    }
}
