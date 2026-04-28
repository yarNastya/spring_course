package springCourse.storage.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import springCourse.storage.domain.Good;
import springCourse.storage.domain.Package;
import springCourse.storage.gateway.Storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MainService {
    private final Storage storage;

    private static final List<Good> ALL_GOODS = List.of(
            new Good("laptop",true),
            new Good("keyboard",false),
            new Good("monitor",true),
            new Good("speakers",false),
            new Good("headphones",false),
            new Good("mouse",false),
            new Good("printer",true),
            new Good("scanner",true)

    );

    public void start() throws InterruptedException {
        while (true) {
            Thread.sleep(3000);
            Collection<Good> goods = generateGoodList();
            System.out.println("New order: " +
                    goods.stream().map(Good::getName)
                            .collect(Collectors.joining(",")));
            Package package1 = storage.process(goods);
            System.out.println("Ready package with order number: " + package1.getOrderNumber());
            System.out.println("----------------------------------------");
        }
    }

    private static Good generateGood() {
        return ALL_GOODS.get(RandomUtils.secure().randomInt(0, ALL_GOODS.size()-1));
    }

    private static Collection<Good> generateGoodList() {
        List<Good> goods = new ArrayList<>();
        for (int i = 0; i < RandomUtils.secure().randomInt(1, 4); ++i) {
            goods.add(generateGood());
        }
        return goods;
    }
}
