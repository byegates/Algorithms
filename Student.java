import java.util.*;
import java.util.function.BiFunction;

enum ScoreComparator implements Comparator<Student> {
    NAME {
        @Override
        public int compare(Student s1, Student s2) {
            return s1.name.compareTo(s2.name);
        }
    },

    ID {
        @Override
        public int compare(Student s1, Student s2) {
            return Integer.compare(s1.id, s2.id);
        }
    },

    MATH {
        @Override
        public int compare(Student s1, Student s2) {
            return Integer.compare(s1.math, s2.math);
        }
    },

    PHYSICS {
        @Override
        public int compare(Student s1, Student s2) {
            return Character.compare(s2.physics, s1.physics);
        }
    },

    ENGLISH {
        @Override
        public int compare(Student s1, Student s2) {
            return s1.English.compareTo(s2.English);
        }
    },

    CS {
        @Override
        public int compare(Student s1, Student s2) {
            return s2.CS.compareTo(s1.CS);
        }
    };

    ScoreComparator() {
        System.out.printf("%7s is constructed\n", this.name());
    }
}

enum LambdaComparator implements Comparator<Student> {
    NAME    ((s1, s2) -> s1.name.compareTo(s2.name)),
    ID      ((s1, s2) -> Integer.compare(s1.id, s2.id)),
    MATH    ((s1, s2) -> Integer.compare(s1.math, s2.math)),
    PHYSICS ((s1, s2) -> Character.compare(s2.physics, s1.physics)),
    ENGLISH ((s1, s2) -> s1.English.compareTo(s2.English)),
    CS      ((s1, s2) -> s2.CS.compareTo(s1.CS)),
    ;

    private final BiFunction<Student, Student, Integer> func;

    LambdaComparator(BiFunction<Student, Student, Integer> func) {
        this.func = func;
        System.out.printf("%7s is constructed!\n", this.name());
        //System.out.printf("%7s is constructed!\n", this.toString());
    }

    @Override
    public int compare(Student s1, Student s2) {return func.apply(s1, s2);}
}

enum Grade0 {Fail, Pass}
enum Grade1 {A, B, C, D, E, F}

public class Student {
    String name;
    int id;
    int math;
    char physics;
    Grade0 English;
    Grade1 CS;

    Student(String name, int id, int math, char physics, Grade0 English, Grade1 CS) {
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

    public static List<Student> createSampleStudents() {
        List<Student> list = new ArrayList<>();
        String[] Names = new String[]{"Sunny", "Pete", "Jason", "Lucy", "Kate", "Anna", "Nate", "John", "Sun"};
        int[] IDs = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] Math = new int[] {90, 80, 75, 60, 83, 95, 77, 93, 88};
        Grade0[] English = new Grade0[] {Grade0.Pass, Grade0.Pass, Grade0.Fail, Grade0.Fail, Grade0.Pass, Grade0.Fail, Grade0.Fail, Grade0.Pass, Grade0.Fail};
        char[] Physics = new char[] {'A', 'B', 'C', 'D', 'A', 'B', 'E', 'A', 'C'};
        Grade1[] CS = new Grade1[] {Grade1.A, Grade1.B, Grade1.F, Grade1.D, Grade1.E, Grade1.D, Grade1.A, Grade1.C, Grade1.B};
        for (int i = 0; i < Names.length; i++)
            list.add(new Student(Names[i], IDs[i], Math[i], Physics[i], English[i], CS[i]));
        return list;
    }

    public static void main(String[] args) {
        List<Student> students = createSampleStudents();
        rankByField("NAME", students, true);
        // LambdaComparator.MATH (and everything else we created of course) is an object of both enum and comparator
        // you can call both ordinal method (from enum) and compare method (from comparator) using the object we created
        System.out.println("Ordinal of MATH in LambdaComparator : " + LambdaComparator.MATH.ordinal()); // 2
        System.out.println("Priority(student 0 vs 1): " + LambdaComparator.CS.compare(students.get(0), students.get(1))); // 1
        // static members in LambdaComparator class
        System.out.println();
        System.out.println("All comparators created in LambdaComparator: ");
        System.out.println(Arrays.toString(LambdaComparator.values()));
        System.out.println();

        for (Comparator<Student> c : LambdaComparator.values())
            if (c instanceof LambdaComparator)
                System.out.printf("%7s is an instance of LambdaComparator\n", c);

        System.out.println();

        for (LambdaComparator c : LambdaComparator.values())
            if (c instanceof Comparator)
                System.out.printf("%7s is an instance of Comparator\n", c);

        System.out.println();

        for (Comparator<Student> c : LambdaComparator.values())
            if (c instanceof Enum)
                System.out.printf("%7s is an instance of Enum\n", c);

    }

    public static void rankByField(String rankBy, List<Student> list, boolean desc) {
        System.out.println("Before using LambdaComparator class for the first time.");
        //Comparator<Student> comparator = ScoreComparator.valueOf(rankBy);
        Comparator<Student> comparator = LambdaComparator.valueOf(rankBy); // LambdaComparator.rankBy
        System.out.println("After  using LambdaComparator class for the first time.");
        System.out.println();
        if (desc)
            //comparator = Collections.reverseOrder(comparator);
            comparator = comparator.reversed();

        PriorityQueue<Student> pq = new PriorityQueue<>(comparator);

        for (Student student : list)
            pq.offer(student);

        while (pq.size() > 0)
            System.out.println(pq.poll());

        System.out.println();
    }
}
