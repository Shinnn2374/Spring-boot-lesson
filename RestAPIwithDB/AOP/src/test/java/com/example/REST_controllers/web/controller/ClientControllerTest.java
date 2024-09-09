package com.example.REST_controllers.web.controller;

import com.example.REST_controllers.AbstractTestController;
import com.example.REST_controllers.StringTestUtils;
import com.example.REST_controllers.mapper.v1.ClientMapper;
import com.example.REST_controllers.model.Client;
import com.example.REST_controllers.model.Order;
import com.example.REST_controllers.service.ClientService;
import com.example.REST_controllers.web.model.ClientListResponse;
import com.example.REST_controllers.web.model.ClientResponse;
import com.example.REST_controllers.web.model.OrderResponse;
import com.example.REST_controllers.web.model.UpsertClientRequest;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

public class ClientControllerTest extends AbstractTestController
{
    @MockBean
    private ClientService clientService;

    @MockBean
    private ClientMapper clientMapper;

    @Test
    public void whenFindAll_thenReturnAllClients() throws Exception
    {
        List<Client> clients = new ArrayList<>();
        clients.add(createClient(1L,null));
        Order order = createOrder(1L,100L,null);
        clients.add(createClient(2L,order));

        List<ClientResponse> clientResponses = new ArrayList<>();
        clientResponses.add(createClientResponse(1L,null));
        OrderResponse orderResponse = createOrderResponse(1L,100L);

        clientResponses.add(createClientResponse(2L,orderResponse));

        ClientListResponse clientListResponse = new ClientListResponse(clientResponses);

        Mockito.when(clientService.findAll()).thenReturn(clients);
        Mockito.when(clientMapper.clientListToResponseList(clients)).thenReturn(clientListResponse);

        String actualResponse = mockMvc.perform(get("/api/v1/client"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("/Users/mihail/Desktop/prog/java/SpringFramework/lESSON/RestAPIwithDB/testingREST-controllers/src/test/resources/response/find_all_clients_response.json");
        Mockito.verify(clientService,Mockito.times(1)).findAll();
        Mockito.verify(clientMapper,Mockito.times(1)).clientListToResponseList(clients);
        JsonAssert.assertJsonEquals(expectedResponse,actualResponse);
    }

    @Test
    public void whenGetClientById_thenReturnClientById() throws Exception
    {
        Client client = createClient(1L,null);
        ClientResponse clientResponse = createClientResponse(1L,null);
        Mockito.when(clientService.findById(1L)).thenReturn(client);
        Mockito.when(clientMapper.clientToResponse(client)).thenReturn(clientResponse);

        String actualResponse = mockMvc.perform(get("api/v1/client/1")).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("/Users/mihail/Desktop/prog/java/SpringFramework/lESSON/RestAPIwithDB/testingREST-controllers/src/test/resources/response/find_client_by_id_response.json");
        Mockito.verify(clientService,Mockito.times(1)).findById(1L);
        Mockito.verify(clientMapper,Mockito.times(1)).clientToResponse(client);

        JsonAssert.assertJsonEquals(expectedResponse,actualResponse);
    }

    @Test
    public void whenCreateClient_thenReturnNewClient() throws Exception
    {
        Client client = createClient(1L,null);
        client.setName("Client 1");
        Client createdClient = createClient(1L,null);
        ClientResponse clientResponse = createClientResponse(1L,null);
        UpsertClientRequest request = new UpsertClientRequest("Client 1");
        Mockito.when(clientService.save(client)).thenReturn(createdClient);
        Mockito.when(clientMapper.requestToClient(request)).thenReturn(client);
        Mockito.when(clientMapper.clientToResponse(client)).thenReturn(clientResponse);

        String actualResponse = mockMvc.perform(post("api/v1/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("/Users/mihail/Desktop/prog/java/SpringFramework/lESSON/RestAPIwithDB/testingREST-controllers/src/test/resources/response/create_client_response.json");

        Mockito.verify(clientService,Mockito.times(1)).save(client);
        Mockito.verify(clientMapper,Mockito.times(1)).requestToClient(request);
        Mockito.verify(clientMapper,Mockito.times(1)).clientToResponse(client);
        JsonAssert.assertJsonEquals(expectedResponse,actualResponse);
    }

    @Test
    public void thenUpdateClient_thenReturnUpdatedClient() throws Exception
    {
        UpsertClientRequest request = new UpsertClientRequest("Client 1");
        Client updatedClient = new Client(1L,"New Client 1", new ArrayList<>());
        ClientResponse clientResponse = new ClientResponse(1L,"New Client 1", new ArrayList<>());
        Mockito.when(clientService.update(updatedClient)).thenReturn(updatedClient);
        Mockito.when(clientMapper.requestToClient(1L,request)).thenReturn(updatedClient);
        Mockito.when(clientMapper.clientToResponse(updatedClient)).thenReturn(clientResponse);

        String actualResponse = mockMvc.perform(put("api/v1/client/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("/Users/mihail/Desktop/prog/java/SpringFramework/lESSON/RestAPIwithDB/testingREST-controllers/src/test/resources/response/update_client_response.json");

        Mockito.verify(clientService,Mockito.times(1)).update(updatedClient);
        Mockito.verify(clientMapper,Mockito.times(1)).requestToClient(request);
        Mockito.verify(clientMapper,Mockito.times(1)).clientToResponse(updatedClient);

        JsonAssert.assertJsonEquals(expectedResponse,actualResponse);
    }

    @Test
    public void whenDeleteClientById_thenReturnStatusNoContest() throws Exception
    {
        mockMvc.perform(delete("/api/v1/client/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(clientService,Mockito.times(1)).deleteById(1L);
    }
}
