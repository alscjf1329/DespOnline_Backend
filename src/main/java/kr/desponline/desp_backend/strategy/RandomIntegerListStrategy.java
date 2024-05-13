package kr.desponline.desp_backend.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class RandomIntegerListStrategy implements Function<Integer, List<Integer>> {

    @Override
    public List<Integer> apply(Integer size) {
        List<Integer> resultList = new ArrayList<>();

        for (int i = 0; i < size / 2; i++) {
            resultList.add(i);
            resultList.add(i);
        }
        Collections.shuffle(resultList);
        return resultList;
    }
}
