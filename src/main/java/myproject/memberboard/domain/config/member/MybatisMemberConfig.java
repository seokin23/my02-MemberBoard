package myproject.memberboard.domain.config.member;

import lombok.RequiredArgsConstructor;
import myproject.memberboard.domain.board.repository.BoardRepository;
import myproject.memberboard.domain.board.repository.MemoryBoardRepository;
import myproject.memberboard.domain.board.service.BoardServiceImpl;
import myproject.memberboard.domain.login.LoginService;
import myproject.memberboard.domain.member.repository.MemberRepository;
import myproject.memberboard.domain.member.repository.mybatis.MemberMapper;
import myproject.memberboard.domain.member.repository.mybatis.MybatisMemberRepository;
import myproject.memberboard.domain.member.service.MemberService;
import myproject.memberboard.domain.member.service.MemberServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@MapperScan("myproject.memberboard.domain.member.repository.mybatis")
public class MybatisMemberConfig {

    private final MemberMapper memberMapper;
    @Bean
    MemberRepository memberRepository(){
        return new MybatisMemberRepository(memberMapper);
    }
    @Bean
    MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    LoginService loginService(){
        return new LoginService(memberRepository());
    }
}
