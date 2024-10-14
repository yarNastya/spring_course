package spring.cource.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import spring.cource.spring.service.SurveyService;


@ComponentScan
public class SpringApplication {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringApplication.class);
        SurveyService surveyService = context.getBean(SurveyService.class);
        surveyService.makeSurvey();

    }

}
