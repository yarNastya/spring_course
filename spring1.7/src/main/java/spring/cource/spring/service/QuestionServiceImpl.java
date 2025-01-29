package spring.cource.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.cource.spring.domain.Question;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final FileService fileService;

    private final IOService ioService;

    public List<Question> fillQuestionList() {
        BufferedReader reader = fileService.readFile();
        List<Question> questionList = new ArrayList<>();
        try {
            for (String line; (line = reader.readLine()) != null; ) {
                String[] phrases = line.split(";");
                Question question = new Question(phrases[0]);
                if (isNotBlank(phrases[1])) {
                    question.setCorrectAnswerNum(Integer.valueOf(phrases[1]));
                }

                for (int i = 2; i < phrases.length; i++) {
                    question.addAnswerOption(phrases[i]);
                }
                questionList.add(question);
            }
        } catch (Exception exp) {
            ioService.writeString(exp.getMessage());
        }
        return questionList;

    }

}
