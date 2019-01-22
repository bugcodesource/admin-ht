package com.sc.adminht.mapper.user;

import com.sc.adminht.entity.user.Address;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>  </p>
 *
 * @author： shencong <br/>
 * @date： 2019/1/7 17:58 <br/>
 * @version： V1.0 <br/>
 */
@Mapper
@Repository
public interface AddressMapper {
    int createAddress(Address address);
}
