package com.example.exchange;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ExchangeValueRepository
 *
 * @author: Xugang Song
 * @create: 2020/9/24
 */
public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {

    /**
     * Interface for value exchange
     *
     * @param from from currency
     * @param to   to currency
     * @return ExchangeValue
     */
    ExchangeValue findByFromAndTo(String from, String to);
}
