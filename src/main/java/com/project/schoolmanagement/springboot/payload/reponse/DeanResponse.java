package com.project.schoolmanagement.springboot.payload.reponse;

import com.project.schoolmanagement.springboot.payload.reponse.abstracts.BaseUserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class DeanResponse extends BaseUserResponse {
}
