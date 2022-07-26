package hello.servlet.domain.web.frontcontroller.v4;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

import java.util.Map;

public class MemberListControllerV4 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object>
            model) {
        List<Member> members = memberRepository.findAll();
        model.put("members", members);
        return "members";
    }
}
