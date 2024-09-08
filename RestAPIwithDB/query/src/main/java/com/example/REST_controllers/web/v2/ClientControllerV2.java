package com.example.REST_controllers.web.v2;

import com.example.REST_controllers.mapper.v2.ClientMapperV2;
import com.example.REST_controllers.model.Client;
import com.example.REST_controllers.service.ClientService;
import com.example.REST_controllers.web.model.ClientListResponse;
import com.example.REST_controllers.web.model.ClientResponse;
import com.example.REST_controllers.web.model.UpsertClientRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/client")
@RequiredArgsConstructor

public class ClientControllerV2
{
    private final ClientService dataBaseClientService;
    private final ClientMapperV2 clientMapperV2;


    @GetMapping
    public ResponseEntity<ClientListResponse> findAll()
    {
        return ResponseEntity.ok(clientMapperV2.clientListToResponseList(
                dataBaseClientService.findAll()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(clientMapperV2.clientToResponse(
                dataBaseClientService.findById(id)
        ));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid UpsertClientRequest request)
    {
        Client newClient = dataBaseClientService.save(clientMapperV2.requestToClient(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(clientMapperV2.clientToResponse(newClient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable Long clientId, @RequestBody @Valid UpsertClientRequest request)
    {
        Client updateClient = dataBaseClientService.update(clientMapperV2.requestToClient(clientId,request));
        return ResponseEntity.ok(clientMapperV2.clientToResponse(updateClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        dataBaseClientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
