/* (C)2025 */
package com.hmm.cbui.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hmm.cbui.util.AES128Util;

@Configuration
public class AESConfig {
  @Value("${spring.aes.key}")
  private String aesKey;

  @Bean
  public AES128Util aes128Util() {
    return new AES128Util(aesKey);
  }
}
