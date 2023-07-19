package com.project.schoolmanagement.springboot.payload.request;

import com.project.schoolmanagement.springboot.payload.request.abstracts.BaseUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ViceDeanRequest extends BaseUserRequest {
}
