import java.lang.reflect.Array;
import java.util.*;

/*
    MergeSort any object type that is comparable
 */
public class MergeSort <T extends Comparable<T>> {

    // comparable Object array method starts here
    public static <T extends Comparable<? super T>> T[] sort(T[] data) {
        if (data == null || data.length <= 1) return data;
        T[] helper = (T[]) Array.newInstance(data.getClass().getComponentType(), data.length);
        mergeSort(data, helper, 0, data.length - 1);
        return data;
    }

    private static <T extends Comparable<? super T>> void mergeSort(T[] data, T[] helper, int l, int r) {
        if (l == r) return;
        int m = l + (r - l) / 2;
        mergeSort(data, helper, l, m);
        mergeSort(data, helper, m + 1, r);
        merge(data, helper, l, m, r);
    }

    private static <T extends Comparable<? super T>> void merge(T[] data, T[] helper, int l, int m, int r) {
        for (int i = 0; i <= r; i++) helper[i] = data[i];
        int i = l, j = m + 1, k = l;
        while (i <= m && j <= r)
            data[k++] = helper[i].compareTo(helper[j]) < 0 ? helper[i++] : helper[j++];
        while (i <= m)
            data[k++] = helper[i++];
    }
    // comparable Object array method ends here

    // List of Comparable starts here
    public static <T extends Comparable<? super T>> List<T> sort(List<T> list) {
        if (list == null || list.size() <= 1) return list;
        List<T> l2 = new ArrayList<>(list);
        mergeSort(list, l2, 0, list.size() - 1);
        return list;
    }

    private static <T extends Comparable<? super T>> void mergeSort(List<T> list, List<T> l2, int l, int r) {
        if (l == r) return;
        int m = l + (r - l) / 2;
        mergeSort(list, l2, l, m);
        mergeSort(list, l2, m + 1, r);
        merge(list, l2, l, m, r);
    }

    private static <T extends Comparable<? super T>> void merge(List<T> list, List<T> l2, int l, int m, int r) {
        for (int k = l; k <= r; k++) l2.set(k, list.get(k));
        int i = l, j = m + 1, k = l;
        while (i <= m && j <= r)
            list.set(k++, l2.get(i).compareTo(l2.get(j)) < 0 ? l2.get(i++) : l2.get(j++));
        while (i <= m)
            list.set(k++, l2.get(i++));
    }
    // List of Comparable ends here

    //  Object Array with Comparator
    public static <T> T[] sort(T[] data, Comparator<T> comparator) {
        if (data == null || data.length <= 1) return data;
        T[] helper = (T[]) Array.newInstance(data.getClass().getComponentType(), data.length);
        mergeSort(data, helper, 0, data.length - 1, comparator);
        return data;
    }

    private static <T> void mergeSort(T[] data, T[] helper, int l, int r, Comparator<T> comparator) {
        if (l == r) return;
        int m = l + (r - l) / 2;
        mergeSort(data, helper, l, m, comparator);
        mergeSort(data, helper, m + 1, r, comparator);
        merge(data, helper, l, m, r, comparator);
    }

    private static <T> void merge(T[] data, T[] helper, int l, int m, int r, Comparator<T> comparator) {
        for (int i = 0; i <= r; i++) helper[i] = data[i];
        int i = l, j = m + 1, k = l;
        while (i <= m && j <= r)
            data[k++] = comparator.compare(helper[i], helper[j]) < 0 ? helper[i++] : helper[j++];
        while (i <= m)
            data[k++] = helper[i++];

    }
    // Object Array with Comparator ends here

    // List of Object with Comparator starts here
    public static <T> List<T> sort(List<T> list, Comparator<T> comparator) {
        if (list == null || list.size() <= 1) return list;
        List<T> l2 = new ArrayList<>(list);
        mergeSort(list, l2, 0, list.size() - 1, comparator);
        return list;
    }

    private static <T> void mergeSort(List<T> list, List<T> l2, int l, int r, Comparator<T> comparator) {
        if (l == r) return;
        int m = l + (r - l) / 2;
        mergeSort(list, l2, l, m, comparator);
        mergeSort(list, l2, m + 1, r, comparator);
        merge(list, l2, l, m, r, comparator);
    }

    private static <T> void merge(List<T> list, List<T> l2, int l, int m, int r, Comparator<T> comparator) {
        for (int k = l; k <= r; k++) l2.set(k, list.get(k));
        int i = l, j = m + 1, k = l;
        while (i <= m && j <= r)
            list.set(k++, comparator.compare(l2.get(i), l2.get(j)) < 0 ? l2.get(i++) : l2.get(j++));
        while (i <= m)
            list.set(k++, l2.get(i++));
    }
    // List of Object with Comparator ends here

        public static void printStudents(Student[] students) {
        for (Student stu : students)
            System.out.println(stu);
    }

    public static void printStudents(List<Student> students) {
        for (Student stu : students)
            System.out.println(stu);
    }

    public static void main(String[] args) {
        LambdaComparator math = LambdaComparator.MATH;
        System.out.println();
        List<Student> stu1 = Student.createStudentsList();
        List<Student> stu2 = Student.createStudentsList();
        printStudents(MergeSort.sort(stu1));
        System.out.println();

        Collections.sort(stu2);
        System.out.println(stu1.equals(stu2));
        Student[] arr1 = Student.createStudentsArray();
        Student[] arr2 = Student.createStudentsArray();
        Arrays.sort(arr1);
        MergeSort.sort(arr2);
        System.out.println(Arrays.equals(arr1, arr2));
        List<Student> stu3 = Student.createStudentsList();
        List<Student> stu4 = Student.createStudentsList();
        stu3.sort(math);
        MergeSort.sort(stu4, math);
        System.out.println(stu3.equals(stu4));
        Student[] arr3 = Student.createStudentsArray();
        Student[] arr4 = Student.createStudentsArray();
        Arrays.sort(arr3, math);
        MergeSort.sort(arr4, math);
        System.out.println(Arrays.equals(arr3, arr4));

        System.out.println();
        System.out.println(Arrays.toString(MergeSort.sort(new Integer[] {})));
        System.out.println(Arrays.toString(MergeSort.sort((Integer[]) null)));
        System.out.println(Arrays.toString(MergeSort.sort(new Integer[] {1})));
        System.out.println(Arrays.toString(MergeSort.sort(new Integer[] {3, 1})));
        System.out.println(Arrays.toString(MergeSort.sort(new Integer[] {3, 1, 2})));
        System.out.println(Arrays.toString(MergeSort.sort(new Integer[] {9, 3, 7, 1, 6, 2, 5})));

        System.out.println();
        List<Integer> int1 = Arrays.asList(9, 3, 7, 1, 6, 2, 5);
        List<Integer> int2 = Arrays.asList(7, 1, 6, 9, 2, 5, 3);
        Collections.sort(int1);
        MergeSort.sort(int2);
        System.out.println(int1.equals(int2));
    }
}
