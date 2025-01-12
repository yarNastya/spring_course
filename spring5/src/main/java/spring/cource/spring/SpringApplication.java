package spring.cource.spring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.cource.spring.service.SurveyService;

@SpringBootApplication
public class SpringApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = org.springframework.boot.SpringApplication.run(SpringApplication.class);
        SurveyService surveyService = context.getBean(SurveyService.class);
        surveyService.makeSurvey();
    }

}
