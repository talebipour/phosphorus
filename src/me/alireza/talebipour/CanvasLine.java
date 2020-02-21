package me.alireza.talebipour;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Solution for NWERC 2019.
 */
public class CanvasLine {

    private final Scanner scanner = new Scanner(System.in);

    private int[] existingPegs;
    private List<Integer> result;
    private Canvas first;


    private void readInputs() {
        System.out.println("Please provide inputs:");
        int canvasCount = Integer.parseInt(scanner.nextLine());
        first = new Canvas(scanner.nextLine(), null);
        Canvas previous = first;
        for (int i = 1; i < canvasCount; i++) {
            previous = new Canvas(scanner.nextLine(), previous);
        }
        int pegCount = Integer.parseInt(scanner.nextLine());
        existingPegs = pegCount == 0 ? new int[0] :
                Arrays.stream(scanner.nextLine().split("\\s")).mapToInt(Integer::parseInt).toArray();
        assert pegCount == existingPegs.length;
    }


    private List<Integer> solve() {
        result = new ArrayList<>();
        Canvas current = first;
        for (int pegCursor = 0; current != null; current = current.next) {
            for (int j = pegCursor; j < existingPegs.length; j++) {
                int peg = existingPegs[j];
                if (peg < current.right) {
                    pegCursor = j;
                }
                if (current.containsPoint(peg)) {
                    current.pegs.add(peg);
                }
                if (peg >= current.right) {
                    break;
                }
            }
            if (current.previous != null) {
                current.previous.stabilize();
            }
            if (current.next == null) {
                current.stabilize();
            }
        }
        return result;
    }




    private class Canvas {
        final Canvas previous;
        Canvas next;
        final int left;
        final int right;

        List<Integer> pegs = new ArrayList<>();


        Canvas(String line, Canvas previous) {
            this.previous = previous;
            if (previous != null) {
                previous.next = this;
            }
            String[] split = line.split("\\s");
            this.left = Integer.parseInt(split[0]);
            this.right = Integer.parseInt(split[1]);
        }

        void stabilize() {
            int neededPegs = 2 - pegs.size();
            for (int i = 0; i < neededPegs; i++) {
                addPeg();
            }
            if (pegs.size() != 2) {
                throw new IllegalStateException("impossible");
            }
        }

        void addPeg() {
            int peg = -1;
            if (!this.pegs.contains(right) && (next == null || next.pegs.size() < 2 || right != next.left)) {
                peg = right;
            } else if (!this.pegs.contains(left) && (previous == null || previous.right != left
                    || previous.pegs.size() < 2)) {
                peg = left;
            } else  {
                peg = findEmptyPegPoint();
            }
            this.pegs.add(peg);
            if (next != null && next.containsPoint(peg)) {
                next.pegs.add(peg);
            }
            result.add(peg);
        }

        private int findEmptyPegPoint() {
            for (int i = left + 1; i < right; i++) {
                if (!this.pegs.contains(i)) {
                    return i;
                }
            }
            throw new IllegalStateException("impossible");
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
        try {
            List<Integer> result = canvasLine.solve();
            System.out.println(result.size());
            System.out.println(result.stream().map(Object::toString).collect(Collectors.joining(" ")));
        } catch (IllegalStateException ex) {
            System.out.println("impossible");
        }

    }



}
