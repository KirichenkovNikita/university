package ru.systematic.university.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.systematic.university.service.AvatarService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class ApproveControllerTest {

    @Mock
    private AvatarService service;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ApproveController(service)).build();
    }

    @Test
    void avatarShouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/approve"))
                .andExpect(view().name("approve/avatar"));
    }

    @Test
    void approveAvatarShouldReturnCorrectView() throws Exception {
        mockMvc.perform(post("/approve/approveAvatar")
                .param("id", String.valueOf(1L))
                .param("userType", "")
                .header("referer", "test"))
                .andExpect(view().name("redirect:test"));
    }

    @Test
    void banAvatarShouldReturnCorrectView() throws Exception {
        mockMvc.perform(post("/approve/banAvatar")
                .param("id", String.valueOf(1L))
                .param("userType", "")
                .header("referer", "test"))
                .andExpect(view().name("redirect:test"));
    }
}
