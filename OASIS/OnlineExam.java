import java.util.*;

class Question {
    String question;
    String[] options;
    int answer;

    Question(String question, String[] options, int answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }
}

public class OnlineExam {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Username and Password
        String username = "admin";
        String password = "1234";

        System.out.println("===== ONLINE EXAM SYSTEM =====");

        // Login
        System.out.print("Enter Username: ");
        String u = sc.next();

        System.out.print("Enter Password: ");
        String p = sc.next();

        if (!u.equals(username) || !p.equals(password)) {
            System.out.println("Invalid Login!");
            return;
        }

        System.out.println("Login Successful!\n");

        // Questions
        Question[] q = {
            new Question(
                "Which language is used for Java programming?",
                new String[]{"1. Python", "2. Java", "3. C", "4. HTML"},
                2
            ),

            new Question(
                "Which keyword is used to inherit a class?",
                new String[]{"1. import", "2. implements", "3. extends", "4. package"},
                3
            )
        };

        int score = 0;

        // Exam Start
        for (int i = 0; i < q.length; i++) {

            System.out.println("Q" + (i + 1) + ": " + q[i].question);

            for (String option : q[i].options) {
                System.out.println(option);
            }

            System.out.print("Enter Answer: ");
            int ans = sc.nextInt();

            if (ans == q[i].answer) {
                score++;
            }
        }

        // Result
        System.out.println("\n===== RESULT =====");
        System.out.println("Score: " + score + "/" + q.length);

        sc.close();
    }
}