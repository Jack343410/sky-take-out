package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Api(tags="C端地址簿接口")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookservice;
    /**
     * 新增地址
     * @param addressBook
     * @return
     */
    @PostMapping
    @ApiOperation("新增地址")
    private Result add(@RequestBody AddressBook addressBook){
        log.info("新增地址");
        addressBookservice.add(addressBook);
        return Result.success();
    }

    /**
     * 查询用户所在地址
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询当前登录用户所在地址")
    public Result<List<AddressBook>> list(){
        log.info("查询当前登录用户所在地址");
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookservice.list(addressBook);
        return Result.success(list);
    }

    /**
     * 查询默认地址
     * @return
     */
    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> getDefault(){
        log.info("查询默认地址");
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(1);
        List<AddressBook> list = addressBookservice.list(addressBook);
        if(list!=null && list.size()>0){
            return Result.success(list.get(0));
        }
        return Result.error("没有查询到默认地址");
    }

    /**
     * 修改地址
     * @param addressBook
     * @return
     */
    @PutMapping
    @ApiOperation("修改地址")
    public Result update(@RequestBody AddressBook addressBook){
        log.info("修改地址");
        addressBookservice.update(addressBook);
        return Result.success();
    }

    /**
     * 根据id删除地址
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("根据id删除地址")
    public Result deleteById(Long id){
        log.info("根据id删除地址");
        addressBookservice.deleteById(id);
        return Result.success();
    }

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> getById(@PathVariable Long id){
        log.info("根据id查询地址");
        AddressBook addressBook = addressBookservice.getById(id);
        return Result.success(addressBook);
    }
    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result setDefault(@RequestBody AddressBook addressBook){
        log.info("设置默认地址");
        addressBookservice.setDefault(addressBook);
        return Result.success();
    }
}
