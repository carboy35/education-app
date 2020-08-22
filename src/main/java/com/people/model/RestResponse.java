package com.people.model;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RestResponse<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	@NonNull private boolean response;
	@NonNull private String message;
	@NonNull private String code;
	private T data;
}

