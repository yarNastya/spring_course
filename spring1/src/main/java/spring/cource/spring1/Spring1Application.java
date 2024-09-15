package spring.cource.spring1;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.cource.spring1.domain.Question;
import spring.cource.spring1.service.FileService;
import spring.cource.spring1.service.QuestionService;

import java.io.BufferedReader;
import java.util.List;


public class Spring1Application {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");
        String fileName = context.getBean(String.class);
        FileService fileService = context.getBean(FileService.class);
        QuestionService questionService = context.getBean(QuestionService.class);

        BufferedReader reader = fileService.readFile(fileName);
        List<Question> questionList = questionService.fillQuestionList(reader);
        questionService.printQuestionList(questionList);


    }

}
