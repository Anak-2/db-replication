package replica.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import replica.study.dto.ItemAppendRequestDto;
import replica.study.dto.ItemRequestDto;
import replica.study.service.ItemService;

import java.util.List;

@RestController
public class ItemController {

    private final ItemService itemService;

    public ItemController(final ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public List<ItemRequestDto> getItems(){
        return itemService.getItems();
    }

    @PostMapping("/items")
    public void appendItem(@RequestBody final ItemAppendRequestDto itemAppendRequestDto){
        itemService.append(itemAppendRequestDto);
    }
}
