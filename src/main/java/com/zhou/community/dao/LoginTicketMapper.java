package com.zhou.community.dao;

import com.zhou.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface LoginTicketMapper {

    @Insert({"insert into login_ticket(user_id, ticket, status, expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({"select * from login_ticket where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Select({"select * from login_ticket where user_id=#{userId}"})
    LoginTicket selectByUserId(int userId);

    @Update({"update login_ticket set status=#{status} where ticket=#{ticket}"})
    int updateLoginTicket(String ticket, int status);

    @Update({"update login_ticket set ticket=#{ticket},status=#{status},expired=#{date} where user_id=#{userId}"})
    int updateLoginTicketAll(int userId, String ticket, int status, Date date);
}
