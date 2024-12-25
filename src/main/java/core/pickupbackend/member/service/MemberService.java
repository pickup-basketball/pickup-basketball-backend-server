package core.pickupbackend.member.service;

import core.pickupbackend.member.domain.Member;
import core.pickupbackend.member.dto.AddMemberRequestDto;
import core.pickupbackend.member.dto.UpdateMemberRequestDto;
import core.pickupbackend.member.repository.JdbcMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final JdbcMemberRepository memberRepository;

    public MemberService(final JdbcMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(final AddMemberRequestDto addMemberRequestDto) {
        return memberRepository.save(addMemberRequestDto.toEntity());
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(final Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found By Id"));
    }

    public Member getMemberByEmail(final String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Not Found By Email"));
    }

    public Member getMemberByNickname(final String nickname) {
        return memberRepository.findByEmail(nickname).orElseThrow(() -> new RuntimeException("Not Found By Nickname"));
    }

    public void deleteMemberById(final Long id) {
        memberRepository.delete(id);
    }

    public void updateMemberById(final UpdateMemberRequestDto dto) {
        memberRepository.update(dto.toEntity());
    }
}
