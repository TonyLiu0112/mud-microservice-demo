package com.tony.demo.microservice.mud.services.inventory.endpoints;

import com.tony.demo.microservice.mud.services.inventory.service.bean.InventoryService;
import com.tony.demo.microservice.mud.services.inventory.service.bean.response.InventoryRes;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.wrench.utils.restfulapi.response.RestfulBuilder.*;

@RestController
@RequestMapping("query")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("inventories/product/{pId}")
    public ResponseEntity<RestfulResponse> getInventory(@PathVariable("pId") Long productId) {
        try {
            Optional<InventoryRes> optional = inventoryService.getInventory(productId);
            if (optional.isPresent())
                return ok(optional.get());
            return notFound();
        } catch (Exception e) {
            return serverError();
        }
    }

}
