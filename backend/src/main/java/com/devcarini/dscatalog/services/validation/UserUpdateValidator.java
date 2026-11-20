package com.devcarini.dscatalog.services.validation;

import com.devcarini.dscatalog.controllers.handlers.errors.FieldMessage;
import com.devcarini.dscatalog.dto.UserUpdateDTO;
import com.devcarini.dscatalog.entities.User;
import com.devcarini.dscatalog.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public void initialize(UserUpdateValid ann) {
    }

    @Override
    public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        Map<String, String> uriVariables =
                (Map<String, String>) httpServletRequest.getAttribute(
                        HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE
                );
        Long userId = Long.parseLong(uriVariables.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        User user = userRepository.findByEmail(dto.email());
        if (user != null && userId != user.getId()) {
            list.add(new FieldMessage("email", "Email já existente"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}