package com.example.elasticjoblitespringbootstarter.pojo;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/14 下午12:00
 */
@Repository
@Getter
@Setter
public class DataRepository {
    private Map<Long, Foo> datas = new ConcurrentHashMap<>(20, 1);

    public DataRepository() {
        init();
    }

    private void init() {
        addData(1L, 5L, "beijing");
        addData(6L, 10L, "shanghai");
        addData(11L, 15L, "guangzhou");
        addData(16L, 20L, "shenzhen");
    }

    private void addData(final long idFrom, final long idTo, final String location) {
        for (Long i = idFrom; i <= idTo; i++) {
            datas.put(i, new Foo(i, location, Foo.Status.TODO));
        }
    }

    public List<Foo> findTodoData(String location) {
        List<Foo> foos = Lists.newArrayList();
        for (Map.Entry<Long, Foo> em : datas.entrySet()) {
            Foo foo = em.getValue();
            if (location.equals(foo.getLocation()) && Foo.Status.TODO == foo.getStatus()) {
                foos.add(foo);
            }
        }
        return foos;
    }

    public void setCompleted(final long id) {
        //设置状态，不让他一直抓取
        datas.get(id).setStatus(Foo.Status.COMPLETED);
    }

}
