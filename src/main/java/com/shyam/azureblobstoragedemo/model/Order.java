package com.shyam.azureblobstoragedemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author SVadikari on 4/12/19
 */
@Data
public class Order {

    private String orderId;

    private int count;

    private String item;

    private BigDecimal amount;
}
