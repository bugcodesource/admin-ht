package com.sc.adminht.entity.user;

import lombok.Data;

/**
 * <p>  </p>
 *
 * @author： shencong <br/>
 * @date： 2019/1/7 17:52 <br/>
 * @version： V1.0 <br/>
 */
@Data
public class Address {

    private int id;
    private String province;
    private String city;
    private String county;
    private String town;
    private String village;
}
