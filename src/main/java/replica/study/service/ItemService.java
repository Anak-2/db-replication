package replica.study.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import replica.study.dto.ItemAppendRequestDto;
import replica.study.dto.ItemRequestDto;
import replica.study.entity.Item;
import replica.study.repository.ItemRepository;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(final ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional(readOnly = true)
    public List<ItemRequestDto> getItems() {
        return itemRepository.getAll().stream().map(ItemRequestDto::from).toList();
    }

    @Transactional
    public void append(final ItemAppendRequestDto itemAppendRequestDto) {
        Item item = Item.builder()
                .name(itemAppendRequestDto.name())
                .build();
        itemRepository.save(item);
    }
}
