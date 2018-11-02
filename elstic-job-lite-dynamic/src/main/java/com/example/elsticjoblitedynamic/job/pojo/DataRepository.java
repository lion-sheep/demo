package com.example.elsticjoblitedynamic.job.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/14 下午12:00
 */
@Getter
@Setter
public class DataRepository {
    private static Map<Long, Foo> datas = new ConcurrentHashMap<>(20, 1);

    public DataRepository() {
        init();
    }

    private void init() {
        addData(1L, 5L, "北京");
        addData(6L, 10L, "上海");
        addData(11L, 15L, "广州");
        addData(16L, 20L, "深圳");
        Foo foo = new Foo(21L, "深圳", Foo.Status.COMPLETED);
        datas.put(21L, foo);
    }

    private void addData(final long idFrom, final long idTo, final String location) {
        for (Long i = idFrom; i <= idTo; i++) {
            datas.put(i, new Foo(i, location, Foo.Status.TODO));
        }
    }

    public List<Foo> findTodoData(String location) {
        List<Foo> foos = new ArrayList<>();
        for (Map.Entry<Long, Foo> em : datas.entrySet()) {
            Foo foo = em.getValue();
            if (location.equals(foo.getLocation()) && foo.getStatus() == Foo.Status.TODO) {
                foos.add(foo);
            }
        }
        return foos;
    }
}
