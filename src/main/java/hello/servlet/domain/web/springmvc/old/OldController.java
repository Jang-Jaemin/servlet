package hello.servlet.domain.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component("/springmvc/old-controller") // <-- : 이 컨트롤러는 /springmvc/old-controller 라는 이름의 스프링 빈으로 등록되었다.
public class OldController implements Controller {


    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return null;


    }
}
//  정리
//  OldController 핸들러매핑, 어댑터
//  OldController 를 실행하면서 사용된 객체는 다음과 같다.
//  HandlerMapping = BeanNameUrlHandlerMapping
//  HandlerAdapter = SimpleControllerHandlerAdapter