package spring.cource.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import spring.cource.spring.service.SurveyService;


@ComponentScan
@PropertySource("classpath:application.properties")
public class SpringApplication {

    @Value("${requiredNumber}")
      private static int requiredNumber;
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringApplication.class);
        SurveyService surveyService = context.getBean(SurveyService.class);
        surveyService.makeSurvey(requiredNumber);

    }

}
