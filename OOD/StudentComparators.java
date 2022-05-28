package OOD;

import java.util.Comparator;
import java.util.function.BiFunction;
public enum StudentComparators implements Comparator<Student> {
    ID((s1, s2) -> Integer.compare(s1.id(), s2.id())),
    NAME((s1, s2) -> s1.name().compareTo(s2.name())),
    MATH((s1, s2) -> Integer.compare(s1.math(), s2.math())),
    PE((s1, s2) -> s1.pe().compareTo(s2.pe())),
    EN((s1, s2) -> s1.en().compareTo(s2.en())),
    ;

    private final BiFunction<Student, Student, Integer> func;

    StudentComparators(BiFunction<Student, Student, Integer> func) {
        this.func = func;
    }

    @Override
    public int compare(Student o1, Student o2) {
        return func.apply(o1, o2);
    }
}
