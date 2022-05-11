package io.github.xxyopen.novel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付宝支付
 * </p>
 *
 * @author xiongxiaoyang
 * @date 2022/05/11
 */
@TableName("pay_alipay")
public class PayAlipay implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 支付宝交易号
     */
    private String tradeNo;

    /**
     * 买家支付宝账号 ID
     */
    private String buyerId;

    /**
     * 交易状态;TRADE_SUCCESS-交易成功
     */
    private String tradeStatus;

    /**
     * 订单金额;单位：分
     */
    private Integer totalAmount;

    /**
     * 实收金额;单位：分
     */
    private Integer receiptAmount;

    /**
     * 开票金额
     */
    private Integer invoiceAmount;

    /**
     * 交易创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 交易付款时间
     */
    private LocalDateTime gmtPayment;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Integer receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public Integer getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Integer invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtPayment() {
        return gmtPayment;
    }

    public void setGmtPayment(LocalDateTime gmtPayment) {
        this.gmtPayment = gmtPayment;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "PayAlipay{" +
        "id=" + id +
        ", outTradeNo=" + outTradeNo +
        ", tradeNo=" + tradeNo +
        ", buyerId=" + buyerId +
        ", tradeStatus=" + tradeStatus +
        ", totalAmount=" + totalAmount +
        ", receiptAmount=" + receiptAmount +
        ", invoiceAmount=" + invoiceAmount +
        ", gmtCreate=" + gmtCreate +
        ", gmtPayment=" + gmtPayment +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
