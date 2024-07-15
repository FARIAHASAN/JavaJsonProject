import org.hamcrest.internal.SelfDescribingValueIterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class UserCreation {
    public static void main(String[] args) throws IOException, ParseException {
        String name,password;
        String FileLocation = "./src/main/resources/users.json";
        JSONParser jp = new JSONParser();
        JSONArray users = (JSONArray) jp.parse(new FileReader(FileLocation));
        JSONObject newUser1= new JSONObject();
        JSONObject newUser2= new JSONObject();

        newUser1.put("username","faria");
        newUser1.put("password" ,"1234");
        newUser1.put("role","student");
        users.add(newUser1);
        newUser2.put("username","admin");
        newUser2.put("password" ,"1234");
        newUser2.put("role","admin");
        users.add(newUser2);

        FileWriter fr = new FileWriter(FileLocation);
        fr.write(String.valueOf(users));
        fr.flush();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Your name:");
        name = scanner.nextLine();
        System.out.println("Enter Your Password:");
        password = scanner.nextLine();
       UserCreation userC = new UserCreation();
        userC.checkCredentials(name,password,FileLocation);
    }

    private void checkCredentials(String name, String password,String file) throws IOException, ParseException {
        JSONParser jp = new JSONParser();

       JSONArray users= (JSONArray) jp.parse(new FileReader(file));
       for (Object user: users)
       {    JSONObject jo = (JSONObject) user;
           String username= jo.get("username").toString();
           String Password= jo.get("password").toString();
           String Role= jo.get("role").toString();
           if(username.equals(name) && Password.equals(password)) {
               if(Role.equals("student"))
               { System.out.println("Welcome " + username+" to the quiz! We will throw you 10 questions." +
                       " Each MCQ mark is 1 and no negative marking. Are you ready? Press 's' to start.");
                   quizTest();

                   }
               else
               {
                   System.out.println("Welcome admin! Please create new questions in the question bank.");
                   questionGenerate();
                   }

               return;

           }

       }
        System.out.println("No such user exists");

    }

    private void quizTest() throws IOException, ParseException {

        Scanner scanner = new Scanner(System.in);
        JSONParser jp =new JSONParser();
        JSONArray ques = (JSONArray) jp.parse(new FileReader("./src/main/resources/quiz.json"));
        int i=1,mark=0;
        Random random = new Random();
        String question,option1, option2,option3,option4,answerkey, student_answer;
        String input = scanner.nextLine();
        if(input.equals("s")) {
            while (i != 11) {
                int randomNumber = random.nextInt(22);
                JSONObject jo = (JSONObject) ques.get(randomNumber);
                question = (String) jo.get("question");
                option1 = (String) jo.get("Option 1");
                option2 = (String) jo.get("Option 2");
                option3 = (String) jo.get("Option 3");
                option4 = (String) jo.get("Option 4");
                answerkey = (String) jo.get("answerkey");
                System.out.println("[Question "+i+" ] "+question);
                System.out.println("1. "+option1);
                System.out.println("2. "+option2);
                System.out.println("3. "+option3);
                System.out.println("4. "+option4);
                student_answer = scanner.nextLine();
                if(student_answer.equals(answerkey))
                {
                 mark++;
                }
              i=i+1;

            }

            if(mark<2){
                System.out.println("Very sorry you are failed. You have got "+mark+ "out of 10");
            } else if (mark>2 && mark<5) {
                System.out.println("Very poor! You have got"+mark+"out of 10");
                
            } else if (mark>5 && mark<8) {
                System.out.println("Good! You have got "+mark+ "out of 10");
                
            } else if (mark>8) {
                System.out.println("Excellent! You have got "+mark+ "out of 10");

            }
        }


    }

    private void questionGenerate() throws IOException, ParseException {

        System.out.println("(press s for start and q for quit)");
        Scanner scanner = new Scanner(System.in);
        JSONParser jp =new JSONParser();
        JSONArray ques = (JSONArray) jp.parse(new FileReader("./src/main/resources/quiz.json"));
       String input = scanner.nextLine();
       String question,option1, option2,option3,option4,answerkey;
        //System.out.println("number of questions"+ques.size());
       while(!input.equals("q"))
       {
           System.out.println("Input your question");
           question = scanner.nextLine();
           System.out.println("Input option 1");
           option1 = scanner.nextLine();
           System.out.println("Input option 2");
           option2 = scanner.nextLine();
           System.out.println("Input option 3");
           option3 = scanner.nextLine();
           System.out.println("Input option 4");
           option4 = scanner.nextLine();
           System.out.println("What is the answer key?");
           answerkey = scanner.nextLine();
           JSONObject quizQ = new JSONObject();
           quizQ.put("Option 1",option1);
           quizQ.put("Option 2",option2);
           quizQ.put("Option 3",option3);
           quizQ.put("Option 4",option4);
           quizQ.put("question",question);
           quizQ.put("answerkey",answerkey);
           ques.add(quizQ);

           FileWriter fw = new FileWriter("./src/main/resources/quiz.json");
           fw.write(String.valueOf(ques));
           fw.flush();
           fw.close();

           System.out.println("Do you want to add question? (press s for start and q for quit)");
            input = scanner.nextLine();



       }

    }


}
