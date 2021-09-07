package com.redis.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.redis.dao.ItemRepository;
import com.redis.model.Item;
import com.redis.service.ItemServiceImpl;

@RestController
@RequestMapping("items")
public class ItemController {
    
	@Autowired
	ItemServiceImpl itemService;
    
//	private RedisTemplate<String, Item> redisTemplate;
//	private HashOperations hashOperations;
//
//	public ItemController(RedisTemplate<String, Item> redisTemplate) {
//		this.redisTemplate = redisTemplate;
//		hashOperations = redisTemplate.opsForHash();
//	}
//
//    HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems(){
    	List<Item> items = itemService.getAllItems();
    	return ResponseEntity.ok().body(items);
    }

    @GetMapping("{itemId}")
    public ResponseEntity<Item> getItem(@PathVariable int itemId){
    	Item item = itemService.getItem(itemId);
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item item){
    	Item savedItem = itemService.saveItem(item);
        return new ResponseEntity<Item>(savedItem, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Item> updateItem(@RequestBody Item item){
    	Item updatedItem = itemService.updateItem(item);
        return new ResponseEntity<Item>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable int id){
        itemService.deleteItem(id);
    	return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
    
//    public void writeHash(int key, Item item) {
//
//        Map<byte[], byte[]> mappedHash = mapper.toHash(item);
//        hashOperations.putAll(key, mappedHash);
//      }
//
//      public Item loadHash(int key) {
//
//        Map<byte[], byte[]> loadedHash = hashOperations.entries(key);
//        return (Item) mapper.fromHash(loadedHash);
//      }
    
    @GetMapping("save-in-bulk")
    public ResponseEntity<List<Item>> saveInBulk() {
    	Supplier<Item> itemsSupplier = () -> {
    		int idCounter = new Random().nextInt(Integer.MAX_VALUE);
    		return new Item(idCounter, "name-" + idCounter, "category-" + idCounter);
    	};
    	List<Item> items = Stream.generate(itemsSupplier).limit(10).collect(Collectors.toList());
    	return ResponseEntity.ok(itemService.saveInBulk(items));
    }
}