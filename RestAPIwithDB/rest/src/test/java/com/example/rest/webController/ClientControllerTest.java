package com.example.rest.webController;

import com.example.rest.AbstractTestController;
import com.example.rest.StringTestUtils;
import com.example.rest.mapper.v1.ClientMapper;
import com.example.rest.model.Client;
import com.example.rest.model.Order;
import com.example.rest.service.ClientService;
import com.example.rest.web.model.ClientListResponse;
import com.example.rest.web.model.ClientResponse;
import com.example.rest.web.model.OrderResponse;
import com.example.rest.web.model.UpsertClientRequest;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
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
        clientResponses.add(createdClientResponse(1L,null));
        OrderResponse orderResponse = createOrderResponse(1L,100L);
        clientResponses.add(createdClientResponse(1L,orderResponse));
        ClientListResponse clientListResponse = new ClientListResponse(clientResponses);

        Mockito.when(clientService.findAll()).thenReturn(clients);
        Mockito.when(clientMapper.clientListToClientResponseList(clients)).thenReturn(clientListResponse);
        String actualResponse = mockMvc.perform(get("/api/v1/client"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expextedResponse = StringTestUtils.readStringFromResource("response/find_all_clients_response.json");

        Mockito.verify(clientService, Mockito.times(1)).findAll();
        Mockito.verify(clientMapper, Mockito.times(1)).clientListToClientResponseList(clients);
        JsonAssert.assertJsonEquals(expextedResponse, actualResponse);
    }

    @Test
    public void whenGetClientById_thenReturnClientById() throws Exception
    {
        Client client = createClient(1L,null);
        ClientResponse clientResponse = createdClientResponse(1L,null);

        Mockito.when(clientService.findById(1L)).thenReturn(client);
        Mockito.when(clientMapper.clientToResponse(client)).thenReturn(clientResponse);

        String actualResponse = mockMvc.perform(get("/api/v1/client/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/find_client_by_id_response.json");

        Mockito.verify(clientService, Mockito.times(1)).findById(1L);
        Mockito.verify(clientMapper, Mockito.times(1)).clientToResponse(client);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreatedClient_thenReturnNewClient() throws Exception
    {
        Client client = new Client();
        client.setName("client 1");
        Client createdClient = createClient(1L,null);
        ClientResponse clientResponse = createdClientResponse(1L,null);
        UpsertClientRequest request = new UpsertClientRequest("client 1");

        Mockito.when(clientService.save(client)).thenReturn(createdClient);
        Mockito.when(clientMapper.requestToClient(request)).thenReturn(client);
        Mockito.when(clientMapper.clientToResponse(createdClient)).thenReturn(clientResponse);

        String actualResponse = mockMvc.perform(post("/api/v1/client/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/create_client_response.json");

        Mockito.verify(clientService, Mockito.times(1)).save(client);
        Mockito.verify(clientMapper, Mockito.times(1)).clientToResponse(createdClient);
        Mockito.verify(clientMapper, Mockito.times(1)).requestToClient(request);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenUpdateClient_thenUpdateClient() throws Exception
    {
        UpsertClientRequest request = new UpsertClientRequest("newClient");
        Client updateClient = new Client(1L,"newClient", new ArrayList<>());
        ClientResponse clientResponse = new ClientResponse(1L,"newClient", new ArrayList<>());

        Mockito.when(clientService.update(updateClient)).thenReturn(updateClient);
        Mockito.when(clientMapper.requestToClient(request)).thenReturn(updateClient);
        Mockito.when(clientMapper.clientToResponse(updateClient)).thenReturn(clientResponse);

        String actualResponse = mockMvc.perform(put("/api/v1/client/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/update_client_response.json");
        Mockito.verify(clientService, Mockito.times(1)).update(updateClient);
        Mockito.verify(clientMapper, Mockito.times(1)).clientToResponse(updateClient);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenDeleteClientById_thenReturnStatusNoContent() throws Exception
    {
        mockMvc.perform(delete("/api/v1/client/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(clientService, Mockito.times(1)).deleteById(1L);
    }
}
