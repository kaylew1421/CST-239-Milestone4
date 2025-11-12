package com.ck.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.util.*;


public class InventoryManager {

    private final Map<String, InventoryItem> items = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final FileService fileService;

    public InventoryManager(FileService fileService) {
        this.fileService = fileService;
    }

   
    public void loadFromJson(Path jsonPath) throws FileServiceException, InventoryException {
        String json = fileService.readFile(jsonPath);
        try {
            List<InventoryItem> list = mapper.readValue(json, new TypeReference<List<InventoryItem>>(){});
            items.clear();
            for (InventoryItem ii : list) {
                if (ii.getProduct() == null || ii.getProduct().getId() == null) {
                    throw new InventoryException("Invalid JSON entry: product or id is null");
                }
                items.put(ii.getProduct().getId(), ii);
            }
        } catch (JsonProcessingException jpe) {
            throw new InventoryException("Invalid JSON: " + jpe.getOriginalMessage());
        }
    }

    public String toJson() throws InventoryException {
        try {
            return mapper.writerWithDefaultPrettyPrinter()
                         .writeValueAsString(new ArrayList<>(items.values()));
        } catch (JsonProcessingException e) {
            throw new InventoryException("Serialization error: " + e.getOriginalMessage());
        }
    }

    public void upsert(Product product, int quantity) {
        InventoryItem ii = items.get(product.getId());
        if (ii == null) {
            items.put(product.getId(), new InventoryItem(product, quantity));
        } else {
            ii.setProduct(product);
            ii.setQuantity(quantity);
        }
    }

    public InventoryItem getByProductId(String productId) throws InventoryException {
        InventoryItem ii = items.get(productId);
        if (ii == null) throw new InventoryException("Product not found: " + productId);
        return ii;
    }

    public boolean inStock(String productId, int qty) throws InventoryException {
        return getByProductId(productId).getQuantity() >= qty;
    }

    public void decrementStock(String productId, int qty) throws InventoryException {
        InventoryItem ii = getByProductId(productId);
        ii.decrement(qty);
    }

    public Collection<InventoryItem> allItems() {
        return Collections.unmodifiableCollection(items.values());
    }
}
