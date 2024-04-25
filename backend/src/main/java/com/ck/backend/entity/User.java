package com.ck.backend.entity;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

@Data
@Mapper
public class User {

  private long userId;
  private String userEmail;
  private String username;
  private String password;
  private long grade;
  private String userImg;
  private String defaultAddress;
  private long identity;
  private long classid;

}
