package hello.jdbc.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class UnCheckedTest {

    @Test
    void unChecked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unChecked_throw() {
        Service service = new Service();
        assertThatThrownBy(() -> {
            service.callThrow();
        }).isInstanceOf(MyUnCheckedException.class);
        assertThat(RuntimeException.class.isAssignableFrom(MyUnCheckedException.class)).isTrue();
    }

    static class MyUnCheckedException extends RuntimeException {
        public MyUnCheckedException(String message) {
            super(message);
        }
    }

    static class Service {
        Repository repository = new Repository();

        public void callCatch() {
            try {
                repository.call();
            } catch (MyUnCheckedException e) {
                log.info("예외처리, message={}", e.getMessage(), e);
            }
        }

        public void callThrow() {
            repository.call();
        }
    }


    static class Repository {
        public void call() {
            throw new MyUnCheckedException("Ex");
        }
    }



}
