package com.example.REST_controllers.mapper.v1;

import com.example.REST_controllers.model.Client;
import com.example.REST_controllers.web.model.ClientListResponse;
import com.example.REST_controllers.web.model.ClientResponse;
import com.example.REST_controllers.web.model.UpsertClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClientMapper
{
    private final OrderMapper orderMapper;

    public Client requestToClient(UpsertClientRequest upsertClientRequest)
    {
        Client client = new Client();
        client.setName(upsertClientRequest.getName());
        return client;
    }

    public Client requestToClient(Long clientId, UpsertClientRequest upsertClientRequest)
    {
        Client client = requestToClient(upsertClientRequest);
        client.setId(clientId);
        return client;
    }

    public ClientResponse clientToResponse(Client client)
    {
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(client.getId());
        clientResponse.setName(client.getName());
        clientResponse.setOrders(orderMapper.orderListToResponseList(client.getOrders()));
        return clientResponse;
    }

    public ClientListResponse clientListToResponseList(List<Client> clients)
    {
        ClientListResponse response = new ClientListResponse();
        response.setClients(clients.stream().map(this::clientToResponse).collect(Collectors.toList()));
        return response;
    }
}
