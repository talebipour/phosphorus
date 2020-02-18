package me.alireza.talebipour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Solution for NWERC 2019.
 */
public class CanvasLine {

    private final Scanner scanner = new Scanner(System.in);

    private Canvas[] canvases;
    private int[] existingPegs;
    private List<Integer> result;


    private void readInputs() {
        System.out.println("Please provide inputs:");
        canvases = new Canvas[Integer.parseInt(scanner.nextLine())];
        for (int i = 0; i < canvases.length; i++) {
            canvases[i] = new Canvas(scanner.nextLine());
        }
        int pegCount = Integer.parseInt(scanner.nextLine());
        existingPegs = Arrays.stream(scanner.nextLine().split("\\s")).mapToInt(Integer::parseInt).toArray();
        assert pegCount == existingPegs.length;
    }


    private boolean solve() {
        result = new ArrayList<>();
        for (int i = 0, pegCursor = 0; i < canvases.length; i++) {
            Canvas canvas = canvases[i];
            for (int j = pegCursor; j < existingPegs.length; j++) {
                int peg = existingPegs[j];
                if (peg < canvas.right) {
                    pegCursor = j;
                }
                if (canvas.containsPoint(peg)) {
                    canvas.pegs.add(peg);
                }
                if (peg >= canvas.right) {
                    break;
                }
            }
            switch (canvas.pegs.size()) {
                case 0:
                    addPeg(canvas.left, canvas, null);
                    addPeg(canvas.right, canvas, i < canvases.length - 1 ? canvases[i + 1] : null);
                    break;
                case 1:
                    if (canvas.pegs.get(0) == canvas.right) {
                        addPeg(canvas.left, canvas, null);
                    } else {
                        addPeg(canvas.right, canvas, i < canvases.length - 1 ? canvases[i + 1] : null);
                    }
                    break;
                case 2:
                    // It's stable!!
                    break;
                default:
                    //"More than 2 pegs on one canvas!"
                    return false;
            }
        }
        return true;
    }

    private void addPeg(int peg, Canvas canvas, Canvas nextCanvas) {
        canvas.pegs.add(peg);
        if (nextCanvas != null && nextCanvas.containsPoint(peg)) {
            nextCanvas.pegs.add(peg);
        }
        result.add(peg);
    }

    private static class Canvas {
        final int left;
        final int right;

        List<Integer> pegs = new ArrayList<>();


        Canvas(String line) {
            String[] split = line.split("\\s");
            this.left = Integer.parseInt(split[0]);
            this.right = Integer.parseInt(split[1]);
        }

        /**
         * @return -1 if at the left of canvas, 0 on canvas, 1 right of canvas
         */
        boolean containsPoint(int x) {
            return x >= left && x <= right;
        }

        @Override
        public String toString() {
            return "(" + left + "," + right + ") pegs: " + pegs;
        }
    }

    public static void main(String[] args) {
        CanvasLine canvasLine = new CanvasLine();
        canvasLine.readInputs();
        if (canvasLine.solve()) {
            System.out.println(canvasLine.result.size());
            System.out.println(canvasLine.result.stream().map(Object::toString).collect(Collectors.joining(" ")));
        } else {
            System.out.println("impossible");
        }

    }



}
