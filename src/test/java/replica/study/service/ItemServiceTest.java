//package replica.study.service;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//import replica.study.config.ReplicationRoutingDataSource;
//import replica.study.dto.ItemRequestDto;
//import uk.org.lidalia.slf4jtest.TestLogger;
//import uk.org.lidalia.slf4jtest.TestLoggerFactory;
//import static uk.org.lidalia.slf4jtest.LoggingEvent.info;
//
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class ItemServiceTest {
//
//    @Autowired
//    private ItemService itemService;
//
//    private final TestLogger logger = TestLoggerFactory.getTestLogger(ReplicationRoutingDataSource.class);
//
//    @DisplayName("slave를 이용해 아이템을 조회한다")
//    @Transactional(readOnly = true)
//    @Test
//    public void testGetItemsUsesSlaveDataSource() {
//        // When: readOnly 트랜잭션으로 아이템을 조회
//        List<ItemRequestDto> items = itemService.getItems();
//
//        // Then: 로그를 통해 slave 데이터소스를 사용했는지 확인
//        assertThat(logger.getLoggingEvents())
//                .contains(info("Selected DataSource: slave"));
//
//        // 아이템 목록이 정상적으로 조회되는지 확인
//        assertThat(items).isNotNull();
//    }
//
//    @DisplayName("master를 이용해 아이템을 저장한다")
//    @Test
//    @Transactional
//    void append() {
//    }
//}