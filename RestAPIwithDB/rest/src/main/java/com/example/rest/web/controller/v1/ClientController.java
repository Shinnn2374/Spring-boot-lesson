package com.example.rest.web.controller.v1;

import com.example.rest.exception.EntityNotFoundException;
import com.example.rest.mapper.v1.ClientMapper;
import com.example.rest.model.Client;
import com.example.rest.service.ClientService;
import com.example.rest.web.model.ClientListResponse;
import com.example.rest.web.model.ClientResponse;
import com.example.rest.web.model.UpsertClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController
{
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping
    public ResponseEntity<ClientListResponse> findAll()
    {
        return ResponseEntity.ok(
                clientMapper.clientListToClientResponseList(clientService.findAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(clientMapper.clientToResponse(clientService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody UpsertClientRequest request)
    {
        Client newClient = clientService.save(clientMapper.requestToClient(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(clientMapper.clientToResponse(newClient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable("id") Long clientId, @RequestBody UpsertClientRequest request)
    {
        Client updatedClient = clientService.update(clientMapper.requestToClient(clientId, request));
        return ResponseEntity.ok(clientMapper.clientToResponse(updatedClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        clientService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
