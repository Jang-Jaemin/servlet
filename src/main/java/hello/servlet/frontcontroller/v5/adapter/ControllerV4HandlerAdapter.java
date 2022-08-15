package hello.servlet.frontcontroller.v5.adapter;

import hello.servlet.frontcontroller.ModelView;
import hello.servlet.frontcontroller.v4.ControllerV4;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter {

    public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
        @Override
        public boolean supports(Object handler) { // <--- handler 가 ControllerV4 인 경우에만 처리하는 어댑터이다.

            return (handler instanceof ControllerV4);

        }

        @Override
        public ModelView handle(HttpServletRequest request, HttpServletResponse
                response, Object handler) {

            // 실행 로직 .
            ControllerV4 controller = (ControllerV4) handler;
            Map<String, String> paramMap = createParamMap(request);
            Map<String, Object> model = new HashMap<>();
            String viewName = controller.process(paramMap, model);

            // 어댑터 변환.
            ModelView mv = new ModelView(viewName);
            mv.setModel(model);

            return mv;
        }

        private Map<String, String> createParamMap(HttpServletRequest request) {

            Map<String, String> paramMap = new HashMap<>();
            request.getParameterNames().asIterator()
                    .forEachRemaining(paramName -> paramMap.put(paramName,
                            request.getParameter(paramName)));
            return paramMap;
        }

    }
}


//  정리
//  v1: 프론트 컨트롤러를 도입
//  기존 구조를 최대한 유지하면서 프론트 컨트롤러를 도입

//  v2: View 분류
//  단순 반복 되는 뷰 로직 분리

//  v3: Model 추가
//  서블릿 종속성 제거
//  뷰 이름 중복 제거

//  v4: 단순하고 실용적인 컨트롤러
//  v3와 거의 비슷
//  구현 입장에서 ModelView를 직접 생성해서 반환하지 않도록 편리한 인터페이스 제공

//  v5: 유연한 컨트롤러
//  어댑터 도입
//  어댑터를 추가해서 프레임워크를 유연하고 확장성 있게 설계


//  여기에 애노테이션을 사용해서 컨트롤러를 더 편리하게 발전시킬 수도 있다. 만약 애노테이션을 사용해서
//  컨트롤러를 편리하게 사용할 수 있게 하려면 어떻게 해야할까? 바로 애노테이션을 지원하는 어댑터를
//  추가하면 된다!
//  다형성과 어댑터 덕분에 기존 구조를 유지하면서, 프레임워크의 기능을 확장할 수 있다.

//  스프링 MVC
//  여기서 더 발전시키면 좋겠지만, 스프링 MVC의 핵심 구조를 파악하는데 필요한 부분은 모두 만들어보았다.
//  사실은 여러분이 지금까지 작성한 코드는 스프링 MVC 프레임워크의 핵심 코드의 축약 버전이고, 구조도 거의 같다