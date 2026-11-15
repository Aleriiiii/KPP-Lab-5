# Лабораторна робота № 5 — Візуальні додатки та побудова користувацьких інтерфейсів

**Рівень завдання:** 3
**Варіант № 6** — предметний клас `ElectricityUsage` з ЛР № 1 (облік споживання електроенергії).

## Мета
Ознайомитись із можливостями Java у розробці візуальних додатків (Swing), навчитись використовувати різні елементи інтерфейсу та обробляти їхні події.

## Що реалізовано
| Рівень | Вимога | Реалізація |
|---|---|---|
| 1 | Клас з ЛР 1/2 | `ElectricityUsage` (попередні показники, поточні показники, тариф, кількість днів) |
| 1 | 3–4 `JTextField` | 4 поля — по одному на кожен параметр конструктора |
| 1 | Кнопка створює об'єкт через конструктор | «Створити запис» → `new ElectricityUsage(...)` |
| 1 | `JLabel` з початковим значенням `0` | лічильник біля кнопок |
| 2 | Об'єкти додаються в колекцію | `ArrayList<ElectricityUsage>` у класі `UsageStorage` |
| 2 | `JLabel` = довжина колекції | `storage.size()` після кожного додавання |
| 3 | Кнопка збереження колекції у файл | «Зберегти у файл» → CSV (UTF-8, роздільник `;`) |

Некоректне введення (не число, поточні показники менші за попередні, тариф ≤ 0, днів ≤ 0) показує повідомлення про помилку, об'єкт не створюється. Дробові числа можна вводити і через крапку, і через кому.

## Структура проєкту
```
KPP-Lab-5/
├── .mvn/wrapper/maven-wrapper.properties   # Maven Wrapper
├── mvnw, mvnw.cmd                           # запуск Maven без встановлення
├── pom.xml                                  # Java 21, JUnit
├── submission.properties                    # номер лаби та варіанта
├── src/
│   ├── main/java/ua/khnu/kpp/lab5/
│   │   ├── Main.java              # точка входу, відкриває форму
│   │   ├── MainForm.java          # GUI (extends JFrame), обробники кнопок
│   │   ├── ElectricityUsage.java  # предметний клас з ЛР 1
│   │   └── UsageStorage.java      # колекція об'єктів + збереження у файл
│   └── test/java/ua/khnu/kpp/lab5/
│       └── UsageStorageTest.java  # тести колекції та запису у файл
├── .gitignore
└── README.md
```

## Інструменти та версії
* **JDK:** 21 або новіший (проєкт компілюється для Java 21)
* **Build tool:** Apache Maven (через Maven Wrapper)
* **Testing:** JUnit Jupiter 6
* **IDE:** Apache NetBeans (або IntelliJ IDEA)

## Команди для запуску

Windows (PowerShell):
```powershell
.\mvnw.cmd clean verify                          # компіляція + тести
java -cp target/classes ua.khnu.kpp.lab5.Main    # запуск програми
```

Linux / macOS:
```bash
./mvnw clean verify
java -cp target/classes ua.khnu.kpp.lab5.Main
```

Після `verify` також створюється `target/lab-assignment-5-1.0.0.jar`, який запускається командою:
```bash
java -jar target/lab-assignment-5-1.0.0.jar
```

**У NetBeans:** File → Open Project → вибрати папку проєкту → правою кнопкою на проєкт → **Run** (головний клас `ua.khnu.kpp.lab5.Main`).

## Як користуватися
1. Введіть попередні й поточні показники лічильника, тариф і кількість днів.
2. Натисніть **«Створити запис»**: об'єкт з'явиться у списку, лічильник збільшиться на 1.
3. Натисніть **«Зберегти у файл»**, виберіть ім'я файлу, і колекцію буде записано у CSV.

Приклад вмісту файлу:
```
previous;current;tariff;days;consumption;cost;averageDaily
100.00;250.00;4.32;30;150.00;648.00;5.00
250.00;300.00;2.00;10;50.00;100.00;5.00
```

## Використані компоненти Swing
`JFrame`, `JPanel`, `JLabel`, `JTextField`, `JButton`, `JTextArea`, `JScrollPane`, `JFileChooser`, `JOptionPane`; менеджери компонування `BorderLayout`, `GridLayout`, `FlowLayout`; інтерфейс `ActionListener`.
