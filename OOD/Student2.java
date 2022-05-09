package OOD;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;

enum ScoreComparator implements Comparator<Student2> {
    NAME {
        @Override
        public int compare(Student2 s1, Student2 s2) {
            return s1.name.compareTo(s2.name);
        }
    },

    ID {
        @Override
        public int compare(Student2 s1, Student2 s2) {
            return Integer.compare(s1.id, s2.id);
        }
    },

    MATH {
        @Override
        public int compare(Student2 s1, Student2 s2) {
            return Integer.compare(s1.math, s2.math);
        }
    },

    PHYSICS {
        @Override
        public int compare(Student2 s1, Student2 s2) {
            return Character.compare(s2.physics, s1.physics);
        }
    },

    ENGLISH {
        @Override
        public int compare(Student2 s1, Student2 s2) {
            return s1.English.compareTo(s2.English);
        }
    },

    CS {
        @Override
        public int compare(Student2 s1, Student2 s2) {
            return s2.CS.compareTo(s1.CS);
        }
    };

    ScoreComparator() {
        System.out.printf("%7s is constructed\n", this.name());
    }
}

enum LambdaComparator implements Comparator<Student2> {
    NAME    ((s1, s2) -> s1.name.compareTo(s2.name)),
    ID      ((s1, s2) -> Integer.compare(s1.id, s2.id)),
    MATH    ((s1, s2) -> Integer.compare(s1.math, s2.math)),
    PHYSICS ((s1, s2) -> Character.compare(s2.physics, s1.physics)),
    ENGLISH ((s1, s2) -> s1.English.compareTo(s2.English)),
    CS      ((s1, s2) -> s2.CS.compareTo(s1.CS)),
    ;

    private final BiFunction<Student2, Student2, Integer> func;

    LambdaComparator(BiFunction<Student2, Student2, Integer> func) {
        this.func = func;
        System.out.printf("%7s is constructed!\n", this.name());
        //System.out.printf("%7s is constructed!\n", this.toString());
    }

    @Override
    public int compare(Student2 s1, Student2 s2) {return func.apply(s1, s2);}
}

public class Student2 implements Comparable<Student2> {
    String name;
    int id;
    int math;
    char physics;
    Grade0 English;
    Grade1 CS;

    static String[] Names = new String[]{"Sunny", "Pete", "Jason", "Lucy", "Kate", "Anna", "Nate", "John", "Sun"};
    static int[] IDs = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
    static int[] Math = new int[] {90, 80, 75, 60, 83, 95, 77, 93, 88};
    static Grade0[] EN = new Grade0[] {Grade0.Pass, Grade0.Pass, Grade0.Fail, Grade0.Fail, Grade0.Pass, Grade0.Fail, Grade0.Fail, Grade0.Pass, Grade0.Fail};
    static char[] Physics = new char[] {'A', 'B', 'C', 'D', 'A', 'B', 'E', 'A', 'C'};
    static Grade1[] cs = new Grade1[] {Grade1.A, Grade1.B, Grade1.F, Grade1.D, Grade1.E, Grade1.D, Grade1.A, Grade1.C, Grade1.B};


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student2 student = (Student2) o;
        return id == student.id && math == student.math && physics == student.physics && name.equals(student.name) && English == student.English && CS == student.CS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, math, physics, English, CS);
    }

    Student2(String name, int id, int math, char physics, Grade0 English, Grade1 CS) {
        this.name = name;
        this.id = id;
        this.math = math;
        this.physics = physics;
        this.English = English;
        this.CS = CS;
    }

    public String toString() {
        return String.format("{%5s, ID: %05d, Math: %d, English: %s, Physics: %s, CS: %s}", name, id, math, English, physics, CS);
    }

    public static List<Student2> createStudentsList() {
        List<Student2> list = new ArrayList<>();
        for (int i = 0; i < Names.length; i++)
            list.add(new Student2(Names[i], IDs[i], Math[i], Physics[i], EN[i], cs[i]));
        return list;
    }

    public static Student2[] createStudentsArray() {
        Student2[] students = new Student2[Names.length];
        for (int i = 0; i < Names.length; i++)
            students[i] = new Student2(Names[i], IDs[i], Math[i], Physics[i], EN[i], cs[i]);
        return students;
    }

    public static void main(String[] args) {
        List<Student2> students = createStudentsList();
        rankByField("NAME", students, true);
        // OOD.LambdaComparator.MATH (and everything else we created of course) is an object of both enum and comparator
        // you can call both ordinal method (from enum) and compare method (from comparator) using the object we created
        System.out.println("Ordinal of MATH in OOD.LambdaComparator : " + LambdaComparator.MATH.ordinal()); // 2
        System.out.println("Priority(student 0 vs 1): " + LambdaComparator.CS.compare(students.get(0), students.get(1))); // 1
        // static members in OOD.LambdaComparator class
        System.out.println();
        System.out.println("All comparators created in OOD.LambdaComparator: ");
        System.out.println(Arrays.toString(LambdaComparator.values()));
        System.out.println();

        for (Comparator<Student2> c : LambdaComparator.values())
            if (c instanceof LambdaComparator)
                System.out.printf("%7s is an instance of OOD.LambdaComparator\n", c);

        System.out.println();

        for (LambdaComparator c : LambdaComparator.values())
            if (c instanceof Comparator)
                System.out.printf("%7s is an instance of Comparator\n", c);

        System.out.println();

        for (Comparator<Student2> c : LambdaComparator.values())
            if (c instanceof Enum)
                System.out.printf("%7s is an instance of Enum\n", c);

    }

    public static void rankByField(String rankBy, List<Student2> list, boolean desc) {
        System.out.println("Before using OOD.LambdaComparator class for the first time.");
        //Comparator<OOD.Student> comparator = OOD.ScoreComparator.valueOf(rankBy);
        Comparator<Student2> comparator = LambdaComparator.valueOf(rankBy); // OOD.LambdaComparator.rankBy
        System.out.println("After  using OOD.LambdaComparator class for the first time.");
        System.out.println();
        if (desc)
            //comparator = Collections.reverseOrder(comparator);
            comparator = comparator.reversed();

        PriorityQueue<Student2> pq = new PriorityQueue<>(comparator);

        for (Student2 student : list)
            pq.offer(student);

        while (pq.size() > 0)
            System.out.println(pq.poll());

        System.out.println();
    }

    @Override
    public int compareTo(@NotNull Student2 o) {
        return this.name.compareTo(o.name);
    }
}
