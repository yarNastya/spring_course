package spring.cource.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import spring.cource.spring.domain.Student;
import spring.cource.spring.service.SurveyService;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final SurveyService surveyService;
    private Student student;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption String firstName, @ShellOption String lastName) {
        if (isNotBlank(firstName) && isNotBlank(lastName)) {
            this.student = new Student().setFirstName(firstName).setLastName(lastName);
            return String.format("Добро пожаловать, %s %s", firstName, lastName);
        } else {
            return "Пожалуйста, введите свое имя и фамилию";
        }
    }

    @ShellMethod(value = "Pass the test", key = {"p", "pass test"})
    @ShellMethodAvailability(value = "isPassTestCommandAvailable")
    public String passTest() {
        surveyService.makeSurvey(student);
        return "Тестирование завершено";
    }

    private Availability isPassTestCommandAvailable() {
        return student == null ? Availability.unavailable("Log in first") : Availability.available();
    }
}
