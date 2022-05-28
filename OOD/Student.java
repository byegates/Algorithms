package OOD;

import org.jetbrains.annotations.NotNull;

import java.util.*;

enum Grade0 {Fail, Pass}
enum Grade1 {F, E, D, C, B, A}

public record Student(int id, String name, int math, Grade0 pe, Grade1 en) implements Comparable<Student> {
    static int[] ID = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
    static String[] NAME = new String[]{"Sunny", "Pete", "Jason", "Lucy", "Kate", "Anna", "Nate", "John", "Sun"};
    static int[] MATH = new int[] {90, 80, 75, 60, 83, 95, 77, 93, 88};
    static Grade0[] PE = new Grade0[] {Grade0.Pass, Grade0.Pass, Grade0.Fail, Grade0.Fail, Grade0.Pass, Grade0.Fail, Grade0.Fail, Grade0.Pass, Grade0.Fail};
    static Grade1[] EN = new Grade1[] {Grade1.A, Grade1.B, Grade1.F, Grade1.D, Grade1.E, Grade1.D, Grade1.A, Grade1.C, Grade1.B};

    @Override
    public int compareTo(@NotNull Student o) {
        return Integer.compare(id, o.id);
    }

    public static Student[] sampleArray() {
        Student[] res = new Student[NAME.length];

        for (int i = 0; i < NAME.length; i++)
            res[i] = new Student(ID[i], NAME[i], MATH[i], PE[i], EN[i]);

        return res;
    }

    public static List<Student> sampleList() {
        List<Student> res = new ArrayList<>(NAME.length);

        for (int i = 0; i < NAME.length; i++)
            res.add(new Student(ID[i],NAME[i], MATH[i], PE[i], EN[i]));

        Collections.shuffle(res);
        return res;
    }

    public static void main(String[] args) {
        List<Student> list = sampleList();
        System.out.println(Arrays.toString(StudentComparators.values()));
        list.sort(StudentComparators.valueOf("EN").reversed());
        for (Student stu : list) System.out.println(stu);
    }
}
