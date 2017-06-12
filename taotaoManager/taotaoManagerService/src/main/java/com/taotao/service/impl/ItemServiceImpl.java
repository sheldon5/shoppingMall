package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 孙建荣 on 17-5-30.下午9:59
 */
@Service
public class ItemServiceImpl implements ItemService {

    private final TbItemMapper itemMapper;
    private final TbItemDescMapper tbItemDescMapper;

    @Autowired
    public ItemServiceImpl(TbItemMapper itemMapper, TbItemDescMapper tbItemDescMapper) {
        this.itemMapper = itemMapper;
        this.tbItemDescMapper = tbItemDescMapper;
    }

    /**
     * 根据商品Id查询商品
     *
     * @param itemId 商品id
     * @return 商品信息
     */
    @Override
    public TbItem getItemById(long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        // 设置分页信息
        PageHelper.startPage(page, rows);
        // 执行查询
        TbItemExample tbItemExample = new TbItemExample();
        List<TbItem> tbItems = itemMapper.selectByExample(tbItemExample);
        // 获取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);
        // 返回结果对象
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setTotal(pageInfo.getTotal());
        easyUIDataGridResult.setRows(tbItems);
        return easyUIDataGridResult;
    }

    @Override
    public TaotaoResult addItem(TbItem tbItem, String desc) {
        // 生成商品id
        long itemId = IDUtils.genItemId();
        // 补全item的属性
        tbItem.setId(itemId);
        // 补全商品状态  1-正常 2-下架   3-删除
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        // 向商品描述表中插入一条信息
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItem.setUpdated(new Date());
        tbItem.setCreated(new Date());
        // 向商品描述表中插入数据
        tbItemDescMapper.insert(tbItemDesc);
        // 返回结果
        return TaotaoResult.ok();
    }
}













