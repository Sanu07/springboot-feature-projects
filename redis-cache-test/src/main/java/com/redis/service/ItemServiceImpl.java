package com.redis.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.stereotype.Service;

import com.redis.dao.ItemRepository;
import com.redis.model.Item;

@Service
public class ItemServiceImpl {

	@Autowired
	private ItemRepository itemRepo;

	private RedisTemplate<String, Item> redisTemplate;
	private HashOperations hashOperations;
	private ValueOperations<String, Item> valueOp;

	public ItemServiceImpl(RedisTemplate<String, Item> redisTemplate) {
		this.redisTemplate = redisTemplate;
		hashOperations = redisTemplate.opsForHash();
		valueOp = redisTemplate.opsForValue();
	}

	HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();

	private int count = 0;

	public List<Item> getAllItems() {
		return itemRepo.findAll();
	}

	@Cacheable(value = "items", key = "#id")
	public Item getItem(int id) {
		System.out.println("Getting from DB::getItem" + ++count);
		return itemRepo.findById(id).orElse(null);
	}

	public Item saveItem(Item item) {
		Item savedItem = itemRepo.save(item);
		writeHash(savedItem);
		return savedItem;
	}

	@CachePut(value = "items", key = "#item.id")
	public Item updateItem(Item item) {
		System.out.println("Updating in cache::updateItem" + ++count);
		return itemRepo.save(item);
	}

	@CacheEvict(value = "items", allEntries = true)
	public void deleteItem(int id) {
		System.out.println("Deleting from cache::deleteItem" + ++count);
		itemRepo.deleteById(id);
	}

	public void writeHash(Item item) {
		valueOp.set("items::" + item.getId(), item);
	}

	public Item loadHash(int key) {

		Map<byte[], byte[]> loadedHash = hashOperations.entries(key);
		return (Item) mapper.fromHash(loadedHash);
	}
	
	public List<Item> saveInBulk(List<Item> items) {
		return itemRepo.saveAllAndFlush(items);
	}
}
