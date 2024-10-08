package com.example.rest.mapper.v1;

import com.example.rest.model.Client;
import com.example.rest.web.model.ClientListResponse;
import com.example.rest.web.model.ClientResponse;
import com.example.rest.web.model.UpsertClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClientMapper
{
    private final OrderMapper orderMapper;

    public Client requestToClient(UpsertClientRequest request)
    {
        Client client = new Client();
        client.setName(request.getName());
        return client;
    }
    public Client requestToClient(Long clientId, UpsertClientRequest request)
    {
        Client client = requestToClient(request);
        client.setId(clientId);
        return client;
    }
    public ClientResponse clientToResponse(Client client)
    {
        ClientResponse response = new ClientResponse();
        response.setName(client.getName());
        response.setId(client.getId());
        response.setOrders(orderMapper.orderListToResponseList(client.getOrders()));
        return response;
    }
    public ClientListResponse clientListToClientResponseList(List<Client> clients)
    {
        ClientListResponse response = new ClientListResponse();
        response.setClients(clients.stream().map(this::clientToResponse).collect(Collectors.toList()));
    return response;
    }
}
