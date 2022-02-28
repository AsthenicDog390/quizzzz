package server.api;

import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

class ActivityControllerTest {

    public List<Integer> nextInts;
    private MyRandom random;
    private TestActivityRepository repo;

    private ActivityController sut;

    @BeforeEach
    public void setup() {
        random = new ActivityControllerTest.MyRandom();
        repo = new TestActivityRepository();
        sut = new ActivityController(random, repo);
        nextInts = new ArrayList<>();
        nextInts.add(2);
        nextInts.add(1);
        nextInts.add(1);
        nextInts.add(0);
        nextInts.add(1);
    }

    @Test
    public void cannotAddNullActivity() {
        List<Activity> list = new ArrayList<>();
        list.add(getActivity(null));
        var actual = sut.add(list);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void getAll() {
        Activity one = getActivity("1");
        repo.save(one);
        Activity two = getActivity("2");
        repo.save(two);
        Activity three = getActivity("3");
        repo.save(three);

        var result = sut.getAll();
        assertEquals(3, result.size());
        assertEquals(one, result.get(0));
        assertEquals(two, result.get(1));
        assertEquals(three, result.get(2));
    }

    @Test
    void getById() {
        Activity one = getActivity("one");
        repo.save(one);
        Activity two = getActivity("two");
        repo.save(two);
        Activity three = getActivity("three");
        repo.save(three);

        assertEquals(one, sut.getById(1).getBody());
        assertEquals(two, sut.getById(2).getBody());
        assertEquals(three, sut.getById(3).getBody());
    }

    @Test
    void add() {
        List<Activity> activities = new ArrayList<Activity>();
        Activity one = getActivity("one");
        activities.add(one);
        Activity two = getActivity("two");
        activities.add(two);
        Activity three = getActivity("three");
        activities.add(three);

        sut.add(activities);

        assertEquals(one, sut.getById(1).getBody());
        assertEquals(two, sut.getById(2).getBody());
        assertEquals(three, sut.getById(3).getBody());
    }

    @Test
    void getRandom() {
        Activity one = getActivity("one");
        repo.save(one);
        Activity two = getActivity("two");
        repo.save(two);
        Activity three = getActivity("three");
        repo.save(three);

        assertEquals(three, sut.getRandom().getBody());
        assertEquals(1, random.numCalled);
    }

    @Test
    void getNRandom() {
        Activity one = getActivity("one");
        repo.save(one);
        Activity two = getActivity("two");
        repo.save(two);
        Activity three = getActivity("three");
        repo.save(three);

        List<Activity> result = sut.getNRandom(4).getBody();
        assertEquals(4, result.size());
        assertEquals(5, random.numCalled);
        assertEquals(three, result.get(0));
        assertEquals(two, result.get(1));
        assertEquals(one, result.get(2));
        assertEquals(two, result.get(3));
    }


    private Activity getActivity(String name) {
        return new Activity(name, name, 0, name);
    }

    @SuppressWarnings("serial")
    public class MyRandom extends Random {

        public int numCalled = 0;

        @Override
        public int nextInt(int bound) {
            int i = numCalled;
            numCalled++;
            return nextInts.get(i);
        }
    }
}