package com.mbo.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbo.dto.EmployeeDto;
import com.mbo.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

@AutoConfigureRestDocs
@WebMvcTest(EmployeeController.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class EmployeeControllerTest {

    public static final String API_EMPLOYEES = "/api/employees";
    @MockBean
    EmployeeService employeeService;

    //@InjectMocks
    //EmployeeController employeeController;

    //@Autowired
    //WebApplicationContext context;

    //@Autowired
    //RestDocumentationContextProvider restDocumentationContextProvider;

    @Autowired
    MockMvc mockMvc;

    List<EmployeeDto> employeesDto = new ArrayList<>();

    @BeforeEach
    void setUp() {

        //MockitoAnnotations.openMocks(this);
        //mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        //mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(documentationConfiguration(restDocumentationContextProvider)).build();
        employeesDto.add(new EmployeeDto());
        employeesDto.add(new EmployeeDto());
        employeesDto.add(new EmployeeDto());
    }

    @Test
    void findAll() throws Exception {

        //When, Then
        when(employeeService.findAll()).thenReturn(employeesDto);
        mockMvc.perform(get(API_EMPLOYEES).
                accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", equalTo(3)));
    }

    @Test
    void findBySector() throws Exception {

        //When, Then
        when(employeeService.findBySector(anyString())).thenReturn(employeesDto);
        mockMvc.perform(get(API_EMPLOYEES+"/bySector")
                .accept(MediaType.APPLICATION_JSON)
                .param("sector", "Computer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", equalTo(3)))
                .andDo(document(API_EMPLOYEES+"/bySector",
                        requestParameters(
                            parameterWithName("sector").description("Le secteur de l'employé à chercher")
                        ),

                        responseFields(
                                fieldWithPath("[].firstName").description("Le prénom de l'employé"),
                                fieldWithPath("[].lastName").description("Le nom de l'employé"),
                                fieldWithPath("[].salary").description("Le salaire de l'employé"),
                                fieldWithPath("[].job").description("Le métier l'employé"),
                                fieldWithPath("[].sector").description("Le secteur de l'employé")
                        )));
    }

    @Test
    void findByLastName() throws Exception {

        //Given
        EmployeeDto employeeDto = new EmployeeDto().builder().lastName("BOUMZZI").build();

        //When, Then
        when(employeeService.findByLastName(anyString())).thenReturn(employeeDto);
        mockMvc.perform(get(API_EMPLOYEES+"/byLastName")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("lastName", "BOUMZZI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", equalTo("BOUMZZI")))
                .andDo(document(API_EMPLOYEES+"/byLastName",
                        requestParameters(
                            parameterWithName("lastName").description("Le nom de l'employé à chercher")
                        ),
                        responseFields(
                                fieldWithPath("firstName").type("String").description("Le prénom de l'employé"),
                                fieldWithPath("lastName").type("String").description("Le nom de l'employé"),
                                fieldWithPath("salary").type("double").description("Le salaire de l'employé"),
                                fieldWithPath("job").type("String").description("Le métier l'employé"),
                                fieldWithPath("sector").type("String").description("Le secteur de l'employé")
                        )));
    }

    @Test
    void addNewEmployee() throws Exception {

        //Given
        EmployeeDto employeeDto = new EmployeeDto().builder().
                                    lastName("BOUMZZI").firstName("Mehdi").job("Développeur Java").sector("Informatique").build();
        ObjectMapper objectMapper = new ObjectMapper();

        //When, Then
        when(employeeService.addNewEmployee(any())).thenReturn(employeeDto);

        ConstrainedFields constrainedFields = new ConstrainedFields(EmployeeDto.class);

        mockMvc.perform(post(API_EMPLOYEES)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new EmployeeDto().builder().lastName("BOUMZZI").firstName("Mehdi").
                        job("Développeur Java").sector("Informatique").build())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.lastName", equalTo("BOUMZZI")))
                .andDo(document(API_EMPLOYEES,
                        requestFields(
                                constrainedFields.withPath("firstName").description("Le prénom de l'employé"),
                                constrainedFields.withPath("lastName").description("Le nom de l'employé"),
                                constrainedFields.withPath("salary").description("Le salaire de l'employé"),
                                constrainedFields.withPath("job").description("Le métier l'employé"),
                                constrainedFields.withPath("sector").description("Le secteur de l'employé")
                        )));
    }

    @Test
    void updateEmployee() throws Exception {

        //Given
        EmployeeDto employeeDto = new EmployeeDto().builder().
                                        lastName("BOUMZZI").firstName("Mehdi").job("Développeur Java").sector("Informatique").build();
        ObjectMapper objectMapper = new ObjectMapper();

        //When, Then
        when(employeeService.updateEmployee(anyString(),any())).thenReturn(employeeDto);
        mockMvc.perform(patch(API_EMPLOYEES+"/idToUpdate")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new EmployeeDto().builder().lastName("BOUMZZI").firstName("Mehdi").
                                job("Développeur Java").sector("Informatique").build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", equalTo("BOUMZZI")));
    }

    @Test
    void deleteEmployee() throws Exception {

        //When, Then
        mockMvc.perform(delete(API_EMPLOYEES+"/idToDelete"))
                .andExpect(status().isGone());
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}