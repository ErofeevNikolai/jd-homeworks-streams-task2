import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        //1. Найти количество несовершеннолетних (т.е. людей младше 18 лет).
        long numberMinors = persons.stream()
                .filter(age -> age.getAge() < 18)
                .count();



//        //2. Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет)
        List<String> listNamesConscripts = persons.stream()
                .filter(sex -> sex.getSex().equals(Sex.MAN))                             // в данном случае как корректно сравнивать через == или equals  ???
                .filter(age -> age.getAge() >= 18 && age.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());


        //3. Список потенциально работоспособных людей
        List listWorkingPeople = persons.stream()
                .filter(person -> person.getEducation().equals(Education.HIGHER))
                .filter(person -> person.getAge() >=18)
                .filter((person -> (person.getSex().equals(Sex.MAN) && person.getAge()<= 65) ||    // Данная конструкция кажется перегруженной, но как сортировку
                        (person.getSex().equals(Sex.WOMAN)  && person.getAge() <=60)))             // с разными условиями, еще и в каждом условии по два критерия сортировки
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());


    }
}
