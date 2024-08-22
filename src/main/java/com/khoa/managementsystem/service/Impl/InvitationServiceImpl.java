package com.khoa.managementsystem.service.Impl;

import com.khoa.managementsystem.exception.BusinessException;
import com.khoa.managementsystem.exception.ProjectExceptionEnum;
import com.khoa.managementsystem.model.Invitation;
import com.khoa.managementsystem.repository.InvitationRepository;
import com.khoa.managementsystem.service.EmailService;
import com.khoa.managementsystem.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;

    private final EmailService emailService;

    @Override
    public void sendInvitation(String email, Long projectId) {

        String invitationToken = UUID.randomUUID().toString();

        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);

        invitationRepository.save(invitation);

        String invitationLink = "http://localhost:5173/accept_invitation?token=" + invitationToken;
        emailService.sendEmailWithToken(email, invitationLink);

    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) {

        Invitation invitation = invitationRepository.findByToken(token);
        if(invitation == null){
            throw new BusinessException(ProjectExceptionEnum.INVITATION_NOT_FOUND);
        }
        return invitation;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {

        Invitation invitation = invitationRepository.findByEmail(userEmail);

        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {

        Invitation invitation = invitationRepository.findByToken(token);
        invitationRepository.delete(invitation);

    }
}
