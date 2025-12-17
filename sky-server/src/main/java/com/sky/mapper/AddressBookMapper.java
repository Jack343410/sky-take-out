package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.AddressBook;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    /**
     * 新增地址
     * @param addressBook
     */
    @Insert("insert into address_book (user_id, consignee, sex, phone, province_code, province_name, city_code, city_name, district_code, district_name, detail, label, is_default)"+
    "values (#{userId},#{consignee},#{sex},#{phone},#{provinceCode},#{provinceName},#{cityCode},#{cityName},#{districtCode},#{districtName},#{detail},#{label},#{isDefault})")
    void add(AddressBook addressBook);

    /**
     * 查询当前登录用户所在地址
     * @param addressBook
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 修改地址
     * @param addressBook
     */
    void update(AddressBook addressBook);

    /**
     * 根据id删除地址
     * @param id
     * @return
     */
    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @Select("select * from address_book where id=#{id}")
    AddressBook getById(Long id);

    /**
     * 将所有默认地址值设为0
     * @param addressBook
     */
    @Update("update address_book set is_default = #{isDefault} where user_id = #{userId}")
    void updateIsDefault(AddressBook addressBook);


}
