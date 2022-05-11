package io.github.xxyopen.novel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 微信支付
 * </p>
 *
 * @author xiongxiaoyang
 * @date 2022/05/11
 */
@TableName("pay_wechat")
public class PayWechat implements Serializable {

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
     * 微信支付订单号
     */
    private String transactionId;

    /**
     * 交易类型;JSAPI-公众号支付 NATIVE-扫码支付 APP-APP支付 MICROPAY-付款码支付 MWEB-H5支付 FACEPAY-刷脸支付
     */
    private String tradeType;

    /**
     * 交易状态;SUCCESS-支付成功 REFUND-转入退款 NOTPAY-未支付 CLOSED-已关闭 REVOKED-已撤销（付款码支付） USERPAYING-用户支付中（付款码支付） PAYERROR-支付失败(其他原因，如银行返回失败)
     */
    private String tradeState;

    /**
     * 交易状态描述
     */
    private String tradeStateDesc;

    /**
     * 订单总金额;单位：分
     */
    private Integer amount;

    /**
     * 用户支付金额;单位：分
     */
    private Integer payerTotal;

    /**
     * 支付完成时间
     */
    private LocalDateTime successTime;

    /**
     * 支付者用户标识;用户在直连商户appid下的唯一标识
     */
    private String payerOpenid;

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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public String getTradeStateDesc() {
        return tradeStateDesc;
    }

    public void setTradeStateDesc(String tradeStateDesc) {
        this.tradeStateDesc = tradeStateDesc;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPayerTotal() {
        return payerTotal;
    }

    public void setPayerTotal(Integer payerTotal) {
        this.payerTotal = payerTotal;
    }

    public LocalDateTime getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(LocalDateTime successTime) {
        this.successTime = successTime;
    }

    public String getPayerOpenid() {
        return payerOpenid;
    }

    public void setPayerOpenid(String payerOpenid) {
        this.payerOpenid = payerOpenid;
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
        return "PayWechat{" +
        "id=" + id +
        ", outTradeNo=" + outTradeNo +
        ", transactionId=" + transactionId +
        ", tradeType=" + tradeType +
        ", tradeState=" + tradeState +
        ", tradeStateDesc=" + tradeStateDesc +
        ", amount=" + amount +
        ", payerTotal=" + payerTotal +
        ", successTime=" + successTime +
        ", payerOpenid=" + payerOpenid +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
