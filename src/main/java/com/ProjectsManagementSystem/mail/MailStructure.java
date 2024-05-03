package com.ProjectsManagementSystem.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MailStructure {

    private String subject;
    private String message;
}
