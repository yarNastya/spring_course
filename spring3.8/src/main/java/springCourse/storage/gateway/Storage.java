package springCourse.storage.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import springCourse.storage.domain.Good;
import springCourse.storage.domain.Package;

import java.util.Collection;

@MessagingGateway
public interface Storage {
    @Gateway(requestChannel = "goodChannel", replyChannel = "packageChannel")
    Package process(Collection<Good> good);
}
