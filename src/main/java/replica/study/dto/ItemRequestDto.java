package replica.study.dto;

import lombok.Builder;
import replica.study.entity.Item;

@Builder
public record ItemRequestDto(
        long itemId,
        String name
) {

    public static ItemRequestDto from(Item item) {
        return ItemRequestDto.builder()
                .itemId(item.getId())
                .name(item.getName())
                .build();
    }
}
