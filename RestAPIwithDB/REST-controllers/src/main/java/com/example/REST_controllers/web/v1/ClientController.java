package com.example.REST_controllers.web.v1;

import com.example.REST_controllers.mapper.v1.ClientMapper;
import com.example.REST_controllers.model.Client;
import com.example.REST_controllers.service.ClientService;
import com.example.REST_controllers.web.model.ClientListResponse;
import com.example.REST_controllers.web.model.ClientResponse;
import com.example.REST_controllers.web.model.UpsertClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController
{
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping()
    public ResponseEntity<ClientListResponse> findAll()
    {
        return ResponseEntity.ok(
                clientMapper.clientListToResponseList(clientService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(
                clientMapper.clientToResponse(clientService.findById(id))
        );
    }

    @PostMapping()
    public ResponseEntity<ClientResponse> create(@RequestBody UpsertClientRequest client)
    {
        Client newClient = clientService.save(clientMapper.requestToClient(client));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientMapper.clientToResponse(newClient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable("id") Long clientId, @RequestBody UpsertClientRequest request)
    {
        Client updateClient = clientService.update(clientMapper.requestToClient(clientId,request));
        return ResponseEntity.ok(clientMapper.clientToResponse(updateClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        clientService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
