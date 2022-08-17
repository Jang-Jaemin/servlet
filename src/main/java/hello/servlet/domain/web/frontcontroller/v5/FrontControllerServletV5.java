package hello.servlet.domain.web.frontcontroller.v5;

import hello.servlet.domain.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.domain.web.frontcontroller.ModelView;
import hello.servlet.domain.web.frontcontroller.v1.MyView;
import hello.servlet.domain.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.domain.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.domain.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrontControllerServletV5 {

    @WebServlet(name = "frontControllerServletV5", urlPatterns = "/frontcontroller/v5/*")
    public class FrontControllerServletV5<MyHandlerAdapter> extends HttpServlet {
        private final Map<String, Object> handlerMappingMap = new HashMap<>();
        private final List<ControllerV3HandlerAdapter> handlerAdapters = new ArrayList<ControllerV3HandlerAdapter>();

        public FrontControllerServletV5() {
            initHandlerMappingMap(); // 핸들러 맵핑 초기화
            initHandlerAdapters(); //어댑터 초기화
            //생성자는 핸들러 맵핑과 어탭터를 동시에 초기화 시킨다는 코드이다.
        }
        private void initHandlerMappingMap() {
            handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new
                    MemberFormControllerV3());
            handlerMappingMap.put("/front-controller/v5/v3/members/save", new
                    MemberSaveControllerV3());
            handlerMappingMap.put("/front-controller/v5/v3/members", new
                    MemberListControllerV3());
        }
        private void initHandlerAdapters() {
            handlerAdapters.add(new ControllerV3HandlerAdapter());
        }
        @Override
        protected void service(HttpServletRequest request, HttpServletResponse
                response)
                throws ServletException, IOException {
            Object handler = getHandler(request); // 핸들러 맵핑
            if (handler == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            MyHandlerAdapter adapter = getHandlerAdapter(handler);
            ModelView mv = adapter.handle(request, response, handler);
            MyView view = viewResolver(mv.getViewName());
            view.render(mv.getModel(), request, response);
        }
        private Object getHandler(HttpServletRequest request) {
            String requestURI = request.getRequestURI();
            return handlerMappingMap.get(requestURI);
        }
        private MyHandlerAdapter getHandlerAdapter(Object handler) {
            for (MyHandlerAdapter adapter : handlerAdapters) {
                if (adapter.supports(handler)) {
                    return adapter;
                }
            }
            throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다.handler=" + handler);
        }

        private MyView viewResolver(String viewName) {
            return new MyView("/WEB-INF/views/" + viewName + ".jsp");

        }
    }


    //  컨트롤러(Controller) --> 핸들러(Handler)
    //  이전에는 컨트롤러를 직접 매핑해서 사용했다. 그런데 이제는 어댑터를 사용하기 때문에, 컨트롤러 뿐만
    //  아니라 어댑터가 지원하기만 하면, 어떤 것이라도 URL에 매핑해서 사용할 수 있다.
    //  그래서 이름을 컨트롤러에서 더 넒은 범위의 핸들러로 변경했다
