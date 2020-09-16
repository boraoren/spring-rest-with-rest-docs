package com.bora.spring.rest.with.restdocs.api;

import com.bora.spring.rest.with.restdocs.models.EmployeeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.context.WebApplicationContext;


@WebMvcTest(EmployeesApi.class)
@AutoConfigureRestDocs
@ExtendWith({ RestDocumentationExtension.class})
public class EmployeesApiTest {
    
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    EmployeesApiTest(@Autowired ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    /**
     * webApplicationContext
     * restDocumentationContextProvider
     * will be injected.
     */
    public void setup(WebApplicationContext webApplicationContext,
                    RestDocumentationContextProvider restDocumentationContextProvider){
        
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .build();
            
    }

    @Test
    public void post_v1_employeeRequestWithNameBob_shouldHttpStatusBe201()
        throws Exception{

        //given
        String withName = "Bob";
        EmployeeRequest employeeRequest = new EmployeeRequest(withName);
        
        //when
        ResultActions postResultActions = mockMvc
            .perform(post("/api/v1/employees")
            .content(asJson(employeeRequest))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(document("create-employee", 
                postRequestFieldsSnippet(),
                postResponseFieldsSnippet()
            ));
            
        //then
        postResultActions
            .andExpect(status()
            .isCreated());
        

    }

    private RequestFieldsSnippet postRequestFieldsSnippet(){
        return requestFields(
                    attributes(
                        key("title")
                        .value("Fields for employee create request")),
                        new FieldDescriptor[]{
                            fieldWithPath("name")
                            .description("Name of the employee")
                            .attributes(key("constraints")
                            .value("Must not be null. Must not be empty"))
                        }
                    );
    }

    private ResponseFieldsSnippet postResponseFieldsSnippet(){
        return responseFields(
            attributes(key("title")
                .value("Fields for employee create response")),
                new FieldDescriptor[]{
                    fieldWithPath("id").description("Id of the employee")
                        .attributes(key("constraints")
                        .value("Must not be null. Must not be empty")),
                    
                    fieldWithPath("name").description("Name of the employee")
                        .attributes(key("constraints")
                        .value("Must not be null. Must not be empty"))
                }
        );
    }

    private String asJson(final EmployeeRequest employeeRequest)
        throws JsonProcessingException{
        return objectMapper.writeValueAsString(employeeRequest);
    }

}
