package spring.cource.spring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.cource.spring.service.SurveyService;

@SpringBootApplication
public class SpringApplication {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringApplication.class);
        SurveyService surveyService = context.getBean(SurveyService.class);
        surveyService.makeSurvey();

    }

}
