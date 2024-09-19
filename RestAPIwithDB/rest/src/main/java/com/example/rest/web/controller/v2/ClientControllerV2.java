package com.example.rest.web.controller.v2;

import com.example.rest.mapper.v1.ClientMapper;
import com.example.rest.model.Client;
import com.example.rest.service.ClientService;
import com.example.rest.web.model.ClientListResponse;
import com.example.rest.web.model.ClientResponse;
import com.example.rest.web.model.UpsertClientRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/client")
@RequiredArgsConstructor
public class ClientControllerV2
{
    private final ClientService dataBaseClientService;
    private final ClientMapper clientMapper;

    @GetMapping
    public ResponseEntity<ClientListResponse> findAll()
    {
        return ResponseEntity.ok(clientMapper.clientListToClientResponseList(dataBaseClientService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(clientMapper.clientToResponse(dataBaseClientService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid UpsertClientRequest request)
    {
        Client newClient = dataBaseClientService.save(clientMapper.requestToClient(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(clientMapper.clientToResponse(newClient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable Long id, @RequestBody @Valid UpsertClientRequest request)
    {
        Client updatedClient = dataBaseClientService.update(clientMapper.requestToClient(id, request));
        return ResponseEntity.ok(clientMapper.clientToResponse(updatedClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        dataBaseClientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
