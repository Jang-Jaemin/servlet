package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.v1.MyView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrontControllerServletV4 {

    @WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")

public class FrontControllerServletV4 extends HttpServlet {
        private Map<String, MemberListControllerV4> controllerMap = new HashMap<>();

        public FrontControllerServletV4() {
            controllerMap.put("/front-controller/v4/members/new-form", new
                    MemberFormControllerV4());
            controllerMap.put("/front-controller/v4/members/save", new
                    MemberSaveControllerV4());
            controllerMap.put("/front-controller/v4/members", new
                    MemberListControllerV4());
        }

        @Override
        protected void service(HttpServletRequest request, HttpServletResponse
                response)
                throws ServletException, IOException {

            String requestURI = request.getRequestURI();
            MemberListControllerV4 controller = controllerMap.get(requestURI);

            if (controller == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Map<String, String> paramMap = createParamMap(request);
            Map<String, Object> model = new HashMap<>(); //추가

            String viewName = controller.process(paramMap, model);
            MyView view = viewResolver(viewName);
            view.render(model, request, response);
        }

        private Map<String, String> createParamMap(HttpServletRequest request) {
            Map<String, String> paramMap = new HashMap<>();
            request.getParameterNames().asIterator()
                    .forEachRemaining(paramName -> paramMap.put(paramName,
                            request.getParameter(paramName)));
            return paramMap;
        }

        private MyView viewResolver(String viewName) {
            return new MyView("/WEB-INF/views/" + viewName + ".jsp");
        }
    }
    // 참고사항
    // 이번 버전의 컨트롤러는 매우 단순하고 실용적이다. 기존 구조에서 모델을 파라미터로 넘기고, 뷰의 논리 이름을 반환한다는 작은 아이디어를 적용했을 뿐인데, 컨트롤러를 구현하는 개발자 입장에서 보면 이제
    // 군더더기 없는 코드를 작성할 수 있다.
    // 또한 중요한 사실은 여기까지 한번에 온 것이 아니라는 점이다. 프레임워크가 점진적으로 발전하는 과정속에서 이런 방법도 찾을 수 있었다.
    // 프레임워크나 공통 기능이 수고로워야 사용하는 개발자가 편리해진다.