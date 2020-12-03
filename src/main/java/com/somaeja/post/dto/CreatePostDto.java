package com.somaeja.post.dto;


import lombok.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Builder @Getter
@AllArgsConstructor @NoArgsConstructor
public class CreatePostDto {

	@NotNull @NotEmpty
	private String title;

	@NotNull @NotEmpty
	private String content;

	@NotNull @Min(0)
	private Long price;

	@NotNull @NotEmpty
	private String location;

	private boolean isNegotiable;
	private boolean isOfflineTrade;

}
