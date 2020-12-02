package com.somaeja.post.controller;

import com.somaeja.post.dto.CreatePostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PostValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CreatePostDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "titleEmpty","title is null");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "contentEmpty","content is null");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "priceEmpty","price is null");
	}
}
