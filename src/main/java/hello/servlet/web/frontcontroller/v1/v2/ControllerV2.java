package hello.servlet.web.frontcontroller.v1.v2;

import hello.servlet.web.frontcontroller.v1.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV2 {
    MyView process(HttpServlet request, HttpServletResponse response) throws ServletException, IOException;

}
