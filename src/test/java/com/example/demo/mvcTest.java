package com.example.demo;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApplication.class})
@AutoConfigureMockMvc
public class mvcTest{

    @Autowired
    private MockMvc mockMvc;

    //check if request passes though when no username provided
    @Test
    @Order(1)
    public void noUsernameTest() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/balance"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String expectedResponseBody =
                "{\"msg\":\"username missing\",\"code\":100,\"url\":\"http://localhost/api/balance\"}";

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        TestCase.assertEquals(expectedResponseBody,actualResponseBody);

    }

    //check if expected amount of balance is returned
    @Test
    @Order(2)
    public void getBalanceTest() throws Exception {
        //Get balance of preset userTest(initial balance is set to 0)
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/balance?username=userTest"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String expectedResponseBody = "0";

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        TestCase.assertEquals(expectedResponseBody,actualResponseBody);

    }

    //credit action test
    @Test
    @Order(3)
    public void creditTest() throws Exception {
        //create first credit transaction and increase balance to 1000
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/credit?username=userTest")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\"transactionID\":\"testCredit\",\"amount\":1000}")
        ).andExpect(MockMvcResultMatchers.status().isOk());

        //check if user balance updated
        MvcResult currentBalance = mockMvc.perform(MockMvcRequestBuilders.get("/api/balance?username=userTest"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        TestCase.assertEquals("1000",currentBalance.getResponse().getContentAsString());

        //redo credit action with a redundant transactionID
        MvcResult redundantTransactionID = mockMvc.perform(
                MockMvcRequestBuilders.put("/api/credit?username=userTest")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\"transactionID\":\"testCredit\",\"amount\":1000}")
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        //check if correct error is returned
        String expectedResponseBody =
                "{\"msg\":\"Transaction ID already exist\",\"code\":100,\"url\":\"http://localhost/api/credit\"}";
        TestCase.assertEquals(expectedResponseBody,redundantTransactionID.getResponse().getContentAsString());

        //check if balance is updated which it should not
        currentBalance = mockMvc.perform(MockMvcRequestBuilders.get("/api/balance?username=userTest"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        TestCase.assertEquals("1000",currentBalance.getResponse().getContentAsString());

    }

    //withdraw action test
    @Test
    @Order(4)
    public void withdrawTest() throws Exception {
        //withdraw 1000 from user balance
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/withdraw?username=userTest")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\"transactionID\":\"testWithdraw\",\"amount\":1000}")
        ).andExpect(MockMvcResultMatchers.status().isOk());

        //check if user balance updated
        MvcResult currentBalance = mockMvc.perform(MockMvcRequestBuilders.get("/api/balance?username=userTest"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        TestCase.assertEquals("0",currentBalance.getResponse().getContentAsString());

        //redo action with a redundant transactionID
        MvcResult redundantTransactionID = mockMvc.perform(
                MockMvcRequestBuilders.put("/api/withdraw?username=userTest")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\"transactionID\":\"testWithdraw\",\"amount\":1000}")
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        //check if correct error is returned
        String expectedResponseBody =
                "{\"msg\":\"Transaction ID already exist\",\"code\":100,\"url\":\"http://localhost/api/withdraw\"}";

        TestCase.assertEquals(expectedResponseBody,redundantTransactionID.getResponse().getContentAsString());

        //check if balance is updated even if shouldn't
        currentBalance = mockMvc.perform(MockMvcRequestBuilders.get("/api/balance?username=userTest"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        TestCase.assertEquals("0",currentBalance.getResponse().getContentAsString());

        //withdraw amount from user balance which exceed current balance
        MvcResult exceedBalance= mockMvc.perform(
                MockMvcRequestBuilders.put("/api/withdraw?username=userTest")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\"transactionID\":\"testWithdrawExceed\",\"amount\":1000}")
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        //expected to return not have enough balance error
        expectedResponseBody =
                "{\"msg\":\"user does not have enough balance for withdraw\",\"code\":100,\"" +
                        "url\":\"http://localhost/api/withdraw\"}";

        TestCase.assertEquals(expectedResponseBody,exceedBalance.getResponse().getContentAsString());

        //check if balance changed
        currentBalance = mockMvc.perform(MockMvcRequestBuilders.get("/api/balance?username=userTest"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        TestCase.assertEquals("0",currentBalance.getResponse().getContentAsString());
    }

    //delete all transaction records created during test
    @After
    public void afterTest() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.delete("/test/delete_transaction?username=userTest"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        System.out.println("After");
    }
}
