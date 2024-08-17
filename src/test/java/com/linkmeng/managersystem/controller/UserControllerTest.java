package com.linkmeng.managersystem.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    public UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_authCheck_normal() throws Exception {
        String baseCode = "ewogICAgInVzZXJJZCI6IDEyMzQ1NiwKICAgICJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwKICAgICJyb2xlIjogImFkbWluIgp9";
        String requestBody = "{\"userId\": 123456,\"endpoint\": [\"resource A\",\"resource B\",\"resource C\"]}";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .header("User-Info", baseCode)
                .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_authCheck_badRequest() throws Exception {
        String baseCode = "ewogICAgInVzZXJJZCI6IDEyMzQ1NiwKICAgICJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwKICAgICJyb2xlIjogImFkbWluIgp9";
        String requestBody = "{\"endpoint\": [\"resource A\",\"resource B\",\"resource C\"]}";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .header("User-Info", baseCode)
                .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void test_authCheck_forbidden() throws Exception {
        String baseCode = "eyJ1c2VySWQiOiAxMjM0NTYsICJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwgInJvbGUiOiAidXNlciJ9";
        String requestBody = "{\"userId\": 123456,\"endpoint\": [\"resource A\",\"resource B\",\"resource C\"]}";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .header("User-Info", baseCode)
                .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void test_authCheck_internalServerError() throws Exception {
        String baseCode = "e====";
        String requestBody = "{\"userId\": 123456,\"endpoint\": [\"resource A\",\"resource B\",\"resource C\"]}";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .header("User-Info", baseCode)
                .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}