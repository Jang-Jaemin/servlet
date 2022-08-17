package hello.servlet.domain.web.springmvc.v2;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class SpringMemberControllerV2 {

    @Controller
    @RequestMapping("/springmvc/v2/members")
    public class SpringMemberControllerV2 {

        private MemberRepository memberRepository = MemberRepository.getInstance();

        @RequestMapping("/new-form")
        public ModelAndView newForm() {
            return new ModelAndView("new-form");
        }

        @RequestMapping("/save")
        public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {

            String username = request.getParameter("username");

            int age = Integer.parseInt(request.getParameter("age"));
            Member member = new Member(username, age);
            memberRepository.save(member);

            ModelAndView mav = new ModelAndView("save-result");
            mav.addObject("member", member);
            return mav;
        }

        @RequestMapping
        public ModelAndView members() {

            List<Member> members = memberRepository.findAll();

            ModelAndView mav = new ModelAndView("members");
            mav.addObject("members", members);
            return mav;
        }
    }
}

//  조합

//  컨트롤러 클래스를 통합하는 것을 넘어서 조합도 가능하다.
//  다음 코드는 /springmvc/v2/members 라는 부분에 중복이 있다.
//  @RequestMapping("/springmvc/v2/members/new-form")
//  @RequestMapping("/springmvc/v2/members")
//  @RequestMapping("/springmvc/v2/members/save")

//  물론 이렇게 사용해도 되지만, 컨트롤러를 통합한 예제 코드를 보면 중복을 어떻게 제거했는지 확인할 수 있다.
//  클래스 레벨에 다음과 같이 @RequestMapping 을 두면 메서드 레벨과 조합이 된다.

//  조합 결과
//  클래스 레벨 @RequestMapping("/springmvc/v2/members")
//  메서드 레벨 @RequestMapping("/new-form") /springmvc/v2/members/new-form
//  메서드 레벨 @RequestMapping("/save") /springmvc/v2/members/save
//  메서드 레벨 @RequestMapping /springmvc/v2/members