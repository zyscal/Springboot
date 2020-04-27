package com.example.demo.mapper;

import com.example.demo.pojo.STM32;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository
public interface STM32Mapper {
    List<STM32> querySTM32List();
    List<STM32> listofSTM32byRouter(@Param("router_IPv6") String router_IPv6 );
    STM32 findSTM32byIP(@Param("stm32_IPv6") String stm32_IPv6);
    boolean insertaSTM32(STM32 stm32);
    int numofSTM32(@Param("router_IPv6") String router_IPv6);
    boolean updateSTM32(@Param("stm32_IPv6") String stm32_IPv6,@Param("stm32_name") String stm32_name,
                        @Param("power_ID")int power_ID);
    boolean deleteSTM32(@Param("stm32_IPv6") String stm32_IPv6);
    String findSTM32namebyIP(@Param("stm32_IPv6") String stm32_IPv6);
}
