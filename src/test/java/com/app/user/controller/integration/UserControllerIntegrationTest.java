package com.app.user.controller.integration;

import com.app.user.dto.UserRegistrationRequest;
import com.app.user.dto.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRegistrationRequest validUser;

    @BeforeEach
    void setUp() {
        validUser = new UserRegistrationRequest();
        validUser.setFirstName("John");
        validUser.setLastName("Doe");
        validUser.setEmail("john.doe@example.com");
        validUser.setPassword("password123");
        validUser.setPhoneNumber("1234567890");
    }

    @Test
    @WithMockUser
    void registerUser_ValidRequest_ReturnsCreated() throws Exception {
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(validUser.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(validUser.getLastName()))
                .andExpect(jsonPath("$.email").value(validUser.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(validUser.getPhoneNumber()));
    }

    @Test
    @WithMockUser
    void registerUser_DuplicateEmail_ReturnsBadRequest() throws Exception {
        // Simulate user registration to create a duplicate email scenario
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));

        UserRegistrationRequest duplicateUser = new UserRegistrationRequest();
        duplicateUser.setFirstName("Jane");
        duplicateUser.setLastName("Doe");
        duplicateUser.setEmail("john.doe@example.com");
        duplicateUser.setPassword("differentpassword");
        duplicateUser.setPhoneNumber("0987654321");

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(duplicateUser)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email already in use."));
    }

    @Test
    void registerUser_Unauthenticated_ReturnsUnauthorized() throws Exception {
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void registerUser_InvalidRequest_ReturnsBadRequest() throws Exception {
        UserRegistrationRequest invalidUser = new UserRegistrationRequest();
        invalidUser.setFirstName(""); // Invalid first name
        invalidUser.setLastName("Doe");
        invalidUser.setEmail("invalid-email"); // Invalid email
        invalidUser.setPassword("pass"); // Invalid password
        invalidUser.setPhoneNumber("123");
        
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }
}