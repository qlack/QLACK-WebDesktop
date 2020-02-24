package webdesktop.exception;

import static org.junit.Assert.assertNotNull;
import com.eurodyn.qlack.webdesktop.common.exception.WdException;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WdExceptionTest {

  @Test
  public void WdExceptionConstructorsTest()  {
    WdException exception = new WdException();
    WdException exception1 = new WdException("message");
    WdException exception2 = new WdException("message",new Throwable());
    WdException exception3 = new WdException(new Throwable());
    assertNotNull(exception);
    assertNotNull(exception1);
    assertNotNull(exception2);
    assertNotNull(exception3);
  }

}
