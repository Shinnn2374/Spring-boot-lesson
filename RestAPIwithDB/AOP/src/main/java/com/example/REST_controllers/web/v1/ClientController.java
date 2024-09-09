package com.example.REST_controllers.web.v1;

import com.example.REST_controllers.mapper.v1.ClientMapper;
import com.example.REST_controllers.model.Client;
import com.example.REST_controllers.service.ClientService;
import com.example.REST_controllers.web.model.ClientListResponse;
import com.example.REST_controllers.web.model.ClientResponse;
import com.example.REST_controllers.web.model.UpsertClientRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor

@Tag(name = "Client v1", description = "Client API version v1")
public class ClientController
{
    private final ClientService clientServiceImpls;
    private final ClientMapper clientMapper;

    @GetMapping()
    @Operation(
            summary = "get clients",
            description = "get all clients",
            tags = {"client"}
    )
    public ResponseEntity<ClientListResponse> findAll()
    {
        return ResponseEntity.ok(
                clientMapper.clientListToResponseList(clientServiceImpls.findAll()));
    }

    @Operation(
            summary = "Get client by id",
            description = "Get client by id, return id, name,list of orders",
            tags = {"client","id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = ClientResponse.class), mediaType = "application.json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application.json")
                    }
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(
                clientMapper.clientToResponse(clientServiceImpls.findById(id))
        );
    }

    @PostMapping()
    public ResponseEntity<ClientResponse> create(@RequestBody UpsertClientRequest client)
    {
        Client newClient = clientServiceImpls.save(clientMapper.requestToClient(client));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientMapper.clientToResponse(newClient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable("id") Long clientId, @RequestBody UpsertClientRequest request)
    {
        Client updateClient = clientServiceImpls.update(clientMapper.requestToClient(clientId,request));
        return ResponseEntity.ok(clientMapper.clientToResponse(updateClient));
    }

    @Operation(
            summary = "Delete client by id",
            description = "Delete client by id",
            tags = {"client", "id"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        clientServiceImpls.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
