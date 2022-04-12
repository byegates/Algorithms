/*
        Given an array of 2D coordinates of points (all the coordinates are integers),
        find the largest number of points that can be crossed by a single line in 2D space.

        Assumptions
        The given array is not null, and it has at least 2 points
        Examples

<0, 0>, <1, 1>, <2, 3>, <3, 3>, the maximum number of points on a line is 3(<0, 0>, <1, 1>, <3, 3> are on the same line)
*/

import java.awt.Point;
import java.util.*;
import java.util.List;

public class MostPointsOnALine {
    //Use below wrapper to submit to leetCode 149. Max Points on a Line (https://leetcode.com/problems/max-points-on-a-line/)
    public int maxPoints(int[][] points) {
        Point[] points2 = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            points2[i] = new Point(points[i][0], points[i][1]);
        }

        return most(points2);
    }

    // method 1 start, simple and can handle duplicates
    public int most(Point[] points) { // TC: O(n^2), SC: O(n)
        int max = 0;

        for (Point point1 : points) {
            int same = 0, sameX = 0, slopeMax = 0;
            Map<Double, Integer> map = new HashMap<>();
            for (Point point2 : points) {
                if (point1.x == point2.x && point1.y == point2.y) same++;
                else if (point1.x == point2.x) sameX++;
                else {
                    double slope = (point2.y - point1.y + .0) / (point2.x - point1.x);
                    int count = map.getOrDefault(slope, 0) + 1;
                    map.put(slope, count);
                    slopeMax = Math.max(slopeMax, count);
                }
            }
            max = Math.max(max, Math.max(sameX, slopeMax) + same);
        }

        return max;
    }
    // method 1 ends here

    // Method 2 using slope(a) and intercept(b) pair to denote a line: y = ax + b;
    static class Line {
        double slope;
        double intercept;
        Line(double slope, double intercept) {
            this.slope = slope;
            this.intercept = intercept;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Line line = (Line) o;
            return Double.compare(line.slope, slope) == 0 && Double.compare(line.intercept, intercept) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(slope, intercept);
        }
    }

    public int most2(Point[] points) { // TC: O(n^2), SC: O(n)
        int max = 0;

        Map<Line, Set<Point>> m1 = new HashMap<>();
        Map<Integer, Set<Point>> m2 = new HashMap<>();
        for (Point point1 : points) {
            for (Point point2 : points) {
                if (point1.x == point2.x) {
                    Set<Point> set = m2.getOrDefault(point1.x, new HashSet<>());
                    set.add(point1);
                    set.add(point2);
                    m2.put(point1.x, set);
                    max = Math.max(max, set.size());
                } else {
                    double slope = (point2.y - point1.y + .0) / (point2.x - point1.x);
                    double intercept = point1.y - slope * point1.x;
                    Line p = new Line(slope, intercept);
                    Set<Point> set = m1.getOrDefault(p, new HashSet<>());
                    set.add(point1);
                    set.add(point2);
                    m1.put(p, set);
                    max = Math.max(max, set.size());
                }
            }
        }

        return max;
    }
    // method 2 ends here

    // method 3 just for fun, only modify to method 2 is using List for storing pair, as List has equals & hashCode implemented
    public int most3(Point[] points) { // TC: O(n^2), SC: O(n)
        int max = 0;

        Map<List<Double>, Set<Point>> m1 = new HashMap<>();
        Map<Integer, Set<Point>> m2 = new HashMap<>();
        for (Point point1 : points) {
            for (Point point2 : points) {
                if (point1.x == point2.x) {
                    Set<Point> set = m2.getOrDefault(point1.x, new HashSet<>());
                    set.add(point1);
                    set.add(point2);
                    m2.put(point1.x, set);
                    max = Math.max(max, set.size());
                } else {
                    double slope = (point2.y - point1.y + .0) / (point2.x - point1.x);
                    double intercept = point1.y - slope * point1.x;
                    List<Double> pair = Arrays.asList(slope, intercept);
                    Set<Point> set = m1.getOrDefault(pair, new HashSet<>());
                    set.add(point1);
                    set.add(point2);
                    m1.put(pair, set);
                    max = Math.max(max, set.size());
                }
            }
        }

        return max;
    }
    // method 3 ends here


    public static void main(String[] args) {
        MostPointsOnALine mpa = new MostPointsOnALine();
        Point[] points = {new Point(0,0), new Point(1,1), new Point(2,3), new Point(3,3), };
        System.out.println(mpa.most(points)); // 3
        Point[] points2 = {new Point(1,1), new Point(5,1), new Point(2,2), new Point(3,3), new Point(4,4), new Point(6,2), new Point(7,3), };
        System.out.println(mpa.most(points2)); // 4

        Point[] points3 = {new Point(1,1), new Point(2,2), new Point(3,3), };
        System.out.println(mpa.most2(points3)); // 3

        System.out.println(mpa.most3(points2)); // 4

        // to demo List have equals and hashCode implemented
        List<Integer> l1 = Arrays.asList(1, 2, 3);
        List<Integer> l2 = Arrays.asList(1, 2, 3);
        System.out.printf("l1 == l2                             : %s\n", l1 == l2); // false
        System.out.printf("l1.equals(l2)                        : %s\n", l1.equals(l2)); // true
        System.out.printf("l1.hashCode() == l2.hashCode()       : %s\n", l1.hashCode() == l2.hashCode()); // true

        // test our hashCode and equals implementation for Pair
        Line line1 = new Line(1.9087654321, 2.1023456789);
        Line line2 = new Line(1.9087654321, 2.1023456789);
        System.out.printf("pair1 == pair2                       : %s\n", line1 == line2); // false
        System.out.printf("pair1.equals(pair2)                  : %s\n", line1.equals(line2)); // true
        System.out.printf("pair1.hashCode() == pair2.hashCode() : %s\n", line1.hashCode() == line2.hashCode()); // true
    }
}
